/**
   Authors: Kenny Akers and Akash Ganesan
   Organization: Team 751 Barn2 Robotics
   Competition: 2019 FRC
   Email: kakers19@priorypanther.com, aganesan20@priorypanther.com

   Arduino program for measuring distance from two ultrasonic sensors with data smoothing.

*/

#include <NewPing.h>

#define SONAR_NUM 2      // Number of sensors.
#define MAX_DISTANCE 200 // Maximum distance (in cm) to ping.
#define DEBUG true       // Flag for debug information

const double INTER_SENSOR_DISTANCE_INCHES = 6.5; // Distance between both US sensors in inches.

const int TRIG_PIN_LEFT = 7;
const int ECHO_PIN_LEFT = 6;

const int TRIG_PIN_RIGHT = 9;
const int ECHO_PIN_RIGHT = 8;

const int bufferLen = 20;

const float MAX_ACCEPTABLE_Z_SCORE = 0.5;
const float MIN_ACCEPTABLE_Z_SCORE = -1 * MAX_ACCEPTABLE_Z_SCORE;

NewPing sonar[SONAR_NUM] = {   // Sensor object array.
  NewPing(TRIG_PIN_LEFT, ECHO_PIN_LEFT, MAX_DISTANCE), // Each sensor's trigger pin, echo pin, and max distance to ping.
  NewPing(TRIG_PIN_RIGHT, ECHO_PIN_RIGHT, MAX_DISTANCE)
};

int ringBufferVals[bufferLen];
int ringBufferFlags[bufferLen];

int bufferStart = 0;

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


void setup() {
  Serial.begin(115200); // Open serial monitor at 115200 baud to see ping results.
  initializeRingBuffer();
}


void loop() {
  delay(200);
  if (DEBUG) {
    //printData();
  }

  int leftDistance = sonar[0].ping_in();
  addData(leftDistance);
  if (hasReachedHole()) {
    Serial.println("Hole has been reached----------------------------");
    return;
  }
  else {
    Serial.print("distance to wall is |");
    Serial.println(calculateAvg());
  }
}




