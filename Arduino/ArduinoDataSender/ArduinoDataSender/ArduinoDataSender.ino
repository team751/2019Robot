/**
   Authors: Kenny Akers
   Organization: Team 751 Barn2 Robotics
   Competition: 2018 FRC
   Email: kakers19@priorypanther.com

   This Arduino program establishes a Serial communication protocol with the RoboRIO.
   Currently, it is programmed to work with queries of the format:
                  <query_token><separator_token><request_ID>
                  Ex. Q-123

   And to respond with the following format:
                  <request_ID><separator><orientation><separator><left_pulse_count><separator><right_pulse_count>
                  Ex. 123-130-42-80

   The Arduino will send this data 3 times unless it receives the confirmation token from the RoboRIO, at which
   point it will stop. If it does not receive this token before it has sent the data 3 times, it will stop.

   Methods initializeIMU, recalibrate, and getOrientation were written by Aidan Chandra (achandra@priorypanther.com)
   and are used with the Adafruit BNO055 breakout board.
*/

#include <Wire.h>
#include <Adafruit_Sensor.h>
#include <Adafruit_BNO055.h>
#include <utility/imumaths.h>

#define TRIGPINLEFT 12
#define ECHOPINLEFT 13
#define TRIGPINRIGHT 11
#define ECHOPINRIGHT 10

Adafruit_BNO055 bno = Adafruit_BNO055();

//unsigned long requestID; // Variable to hold the query ID of the incoming request.
String startSymbol = "[";
String separator = "-"; // Character used to separate command from ID number.
String endSymbol = "]";
//String queryToken = "Q"; // Token used by the RoboRIO to initiate a request.
//String confirmationToken = "OK"; // Token used by the RoboRIO signal that it successfully received the correct response to the query (i.e. both request and response IDs match).
const int switchTime = 500; // Debounce time in microseconds.

volatile double orientation = 0.0;
volatile double leftUltrasonicDistance = 0;
volatile double rightUltrasonicDistance = 0;

volatile unsigned long leftLastMicros = 0;
volatile unsigned long rightLastMicros = 0;

const boolean debug = false; // Debug flag.

void setup() {
  Serial.begin(9600);
  Serial.setTimeout(10);
  pinMode(3, INPUT_PULLUP);
  pinMode(2, INPUT_PULLUP);
  pinMode(ECHOPINLEFT, INPUT);
  pinMode(TRIGPINLEFT, OUTPUT);
  pinMode(ECHOPINRIGHT, INPUT);
  pinMode(TRIGPINRIGHT, OUTPUT);
  initializeIMU();
}

void loop() {
  orientation = getOrientation();
  leftUltrasonicDistance = getUltrasonicCm(TRIGPINLEFT, ECHOPINLEFT);
  rightUltrasonicDistance = getUltrasonicCm(TRIGPINRIGHT, ECHOPINRIGHT);
  
  sendData();
  if (Serial.read() == 'c') {
    recalibrate();
  }
}

void sendData() {
  String returnable = startSymbol + String(orientation) + separator + String(leftUltrasonicDistance) + separator + String(rightUltrasonicDistance) + endSymbol;
  Serial.println(returnable);
}

void recalibrate() {
  if (debug) {
    Serial.println("Recalibrating... HOLD STILL");
  }
  initializeIMU();
}

/*
   Returns the current orientation in degrees
   debug true will print out verbose debug messages

   Note: A delay is reccommended if this function will be
    called in quick succession. No more than 100ms is neccessary
*/
float getOrientation() {
  imu::Vector<3> euler = bno.getVector(Adafruit_BNO055::VECTOR_EULER);
  if (debug) {
    Serial.println("Getting orientation (X)");
  }
  if (debug) {
    Serial.println("    " + String(euler.x()));
  }     
  return (float) euler.x();
}

double getUltrasonicCm(unsigned int trigPin, unsigned int echoPin) {
  digitalWrite(trigPin, HIGH);
  delay(10);
  digitalWrite(trigPin, LOW);
  unsigned long pulseLength = pulseIn(echoPin, HIGH);

  return (pulseLength) * 0.034;
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
  if (debug) {
    Serial.println("Initializing BNO055");
  }
  if (!bno.begin()) {
    Serial.println("    Failed");
    return false;
  }
  if (debug) {
    Serial.println("    Success");
  }
  //bno.setExtCrystalUse(true);
  return true;
}
