
/**
   Authors: Kenny Akers and Akash Ganesan
   Organization: Team 751 Barn2 Robotics
   Competition: 2019 FRC
   Email: kakers19@priorypanther.com, aganesan20@priorypanther.com
   Arduino program for measuring distance from two ultrasonic sensors with data smoothing.
*/
#include <NewPing.h>
#include <Adafruit_Sensor.h>
#include <Adafruit_BNO055.h>
#include <utility/imumaths.h>
Adafruit_BNO055 bno = Adafruit_BNO055();
#define MAX_DISTANCE 200 // Maximum distance (in cm) to ping.
#define DEBUG false       // Flag for debug information

class UltrasonicSensor {

  private:
    int TRIG_PIN;
    int ECHO_PIN;
    const static int bufferLen = 20;
    float MAX_ACCEPTABLE_Z_SCORE; // 0.5
    float MIN_ACCEPTABLE_Z_SCORE; // -1 * MAX_ACCEPTABLE_Z_SCORE;
    NewPing *sonar;
    int ringBufferVals[bufferLen];
    int ringBufferFlags[bufferLen];
    int bufferStart = 0;

  public:
    UltrasonicSensor(int trig, int echo, double zScoreThreshold) {
      TRIG_PIN = trig;
      ECHO_PIN = echo;
      MAX_ACCEPTABLE_Z_SCORE = zScoreThreshold;
      MIN_ACCEPTABLE_Z_SCORE = -1 * zScoreThreshold;
      *sonar = NewPing(TRIG_PIN, ECHO_PIN, MAX_DISTANCE);
      initializeRingBuffer();
    }

    void updateSensor() {
      int distance = sonar->ping_in();
      addData(distance);
    }

    // if the last fifth of the ring buffer are flagged, then the data has probably reached the hole
    boolean hasReachedHole() {
      int numFlagged = bufferLen / 5;
      for (int i = (0.8 * bufferLen); i < bufferLen; i++) {
        if (DEBUG) {
          Serial.print(ringBufferFlags[i]);
          Serial.print(", ");
        }
        if (ringBufferFlags[i] <= 0) {
          numFlagged--;
        }
      }
      if (DEBUG) {
        Serial.println();
        Serial.print("numFlagged = ");
        Serial.println(numFlagged);
        Serial.println();
      }
      return numFlagged > bufferLen / 10;
    }

    float calculateAvg() {
      int totalValid = 0;
      float total = 0;
      for (int i = 0; i < bufferLen; i++) {
        if (!ringBufferFlags[i]) {
          totalValid++;
          total += ringBufferVals[i];
        }
      }
      return total / (float) totalValid;
    }

    void printData() {
      for (int i = 0; i < bufferLen; i++) {
        if (ringBufferFlags[i] != 0) {
          Serial.print("flagged ");
        } else {
          Serial.print("not flagged ");
        }
        Serial.print(i);
        Serial.print(": ");
        Serial.println(ringBufferVals[i]);
      }

      Serial.print("avg");
      Serial.println(calculateAvg());
      Serial.println("Std deviation");
      Serial.println(calculateStdDeviation());
    }

  private:
    // this will initialize the ring buffer with data
    void initializeRingBuffer() {
      for (int i = 0; i < bufferLen; i++) {
        ringBufferVals[i] = sonar[0].ping_in();
        ringBufferFlags[i] = 0;
        delay(100);
      }
      for (int i = 0; i < bufferLen; i++) {
        ringBufferVals[i] = sonar[0].ping_in();
        ringBufferFlags[i] = 0;
        delay(100);
      }
      for (int j = 0; j < 3 * bufferLen; j++) {
        addData(sonar[0].ping_in());
        delay(100);
      }
    }

    // adds data to the ring buffer
    // if the data is equal to 0, then we throw it out
    void addData(int data) {
      if (data == 0) {
        return;
      }
      ringBufferVals[bufferStart] = data;
      ringBufferFlags[bufferStart] = flagData(data);
      bufferStart = (bufferStart + 1) % bufferLen;
    }

    float calculateStdDeviation() {
      float variance = 0;
      float average = calculateAvg();
      for (int i = 0; i < bufferLen; i++) {
        if (!ringBufferFlags[i]) {
          variance += pow(((float)ringBufferVals[i] - average), 2);
        }
      }
      return sqrt(variance) + 2;
    }

    float calculateZScore(int data) {
      return ((float)data - calculateAvg()) / calculateStdDeviation();
    }

    // if the data is either from the hole, or is invalid, this method will hopefully flag it
    int flagData(int data) {
      float zScore = calculateZScore(data);
      boolean verdict = zScore > MAX_ACCEPTABLE_Z_SCORE || zScore < MIN_ACCEPTABLE_Z_SCORE;
      if (DEBUG) {
        if (verdict) {
          Serial.print("Flagging: ");
        }
        else {
          Serial.print("Not flagging: ");
        }
        Serial.print(data);
        Serial.print(" | zScore = ");
        Serial.println(zScore);
      }

      if (zScore < MIN_ACCEPTABLE_Z_SCORE) {
        return -1;
      }
      else if (zScore > MAX_ACCEPTABLE_Z_SCORE) {
        return 1;
      }
      else {
        return 0;
      }
    }

};


UltrasonicSensor sensors[3] = {UltrasonicSensor(7, 6, 0.5), UltrasonicSensor(9, 8, 0.5), UltrasonicSensor(11, 10, 0.5)};

int isLeftHole = 0;
int isRightHole = 0;
double orientation = 0.0;
int frontDistance = 0;

void setup() {
  Serial.begin(115200); // Open serial monitor at 115200 baud to see ping results.
  initializeIMU();
}

void loop() {
  for (UltrasonicSensor sensor : sensors) {
    sensor.updateSensor();
  }

  isLeftHole = sensors[0].hasReachedHole() ? 1 : 0;
  isRightHole = sensors[1].hasReachedHole() ? 1 : 0;
  frontDistance = sensors[2].calculateAvg();

  sendData();

}

void sendData() {
  String returnable = "[" + String(getOrientation()) + "-" + isLeftHole + "-" + isRightHole + "-" + frontDistance + "]";
  Serial.println(returnable);
}

float getOrientation() {
  imu::Vector<3> euler = bno.getVector(Adafruit_BNO055::VECTOR_EULER);
  return (float) euler.x();
}


/*
   Intitializes the IMU. MUST BE CALLED FIRST
   debug true will print out verbose debug messages
   Returns true for success, false for failure

   NOTE: Initializing or reinitializing the sensor RESETS THE POSITION TO 0.
         IF YOUR SENSOR WAS READING "340" AND YOU RECALIBRATE, 340 WILL NOW BE
         0, EVEN IF YOU DID NOT MOVE THE SENSOR.
*/
boolean initializeIMU() {
  if (!bno.begin()) {
    Serial.println("    Failed");
    return false;
  }

  //bno.setExtCrystalUse(true);
  return true;
}


