/**
   Author: Kenny Akers
   Organization: Team 751 Barn2 Robotics
   Competition: 2019 FRC
   Email: kakers19@priorypanther.com

   Arduino program for turning a robot to a certain heading and driving straight
   using PID control. Currently used on a VEX robot as a proof-of-concept.
*/
#include <Adafruit_Sensor.h>
#include <Adafruit_BNO055.h>
#include <utility/imumaths.h>
#include <Servo.h>

Servo leftMotor;
Servo rightMotor;
Adafruit_BNO055 bno = Adafruit_BNO055();

double prevError = 0.0;
double prevIntegral = 0.0;
double targetAngle = 0.0;

const double MIN_MOTOR_SPEED_CW = 28.0; // Floor for the motor speed going CW - minimum power to move the motor.
const double MIN_MOTOR_SPEED_CCW = 15.0; // Floor for the motor speed going CCW - minimum power to move the motor.
const double SPEED_MULTIPLIER = 2.0;
const int DELAY = 50; // PID loop time interval in ms.
const boolean debug = false; // Debug flag.

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  leftMotor.attach(11);
  rightMotor.attach(10);
  initializeIMU();
  int dir = 1;
  int degreesToTurn = 90.0;
  targetAngle = getOrientation() + (dir * degreesToTurn);

  int currentAngle = getOrientation();
  int driveSpeed = 20;

  while (true) {
    //turnPID(targetAngle, 0.3, 0, 0.1);
    drivePID(currentAngle, 90 - driveSpeed, 0.9, 0.0, 0.0);
    delay(DELAY);
  }
}

void loop() {
  // put your main code here, to run repeatedly:
}

void drivePID(double initialAngle, double driveSpeed, double kp, double ki, double kd) {
  double currentError = getOrientation() - initialAngle;

  double derivative = (currentError - prevError) * kd;
  double proportionalError = currentError * kp;
  double integral = currentError + prevIntegral;

  prevError = currentError;
  prevIntegral = integral;

  integral *= ki;
  double motorSpeed = map(derivative + proportionalError + integral,
                          -100 * (kp + ki + kd), 100 * (kp + ki + kd), 0, 180);
  Serial.print("Speed: ");
  Serial.print(motorSpeed);
  Serial.print(" | Error: " );
  Serial.print(currentError);
  Serial.print(" | P: ");
  Serial.print(proportionalError);
  Serial.print(" | D: ");
  Serial.print(derivative);
  Serial.print(" | I: ");
  Serial.println(integral);

  if (motorSpeed < 90) {
    Serial.println("<90");
    leftMotor.write(180 - (driveSpeed + (motorSpeed - 90)));
    rightMotor.write(driveSpeed);
  }
  else if (motorSpeed > 90) {
    Serial.println(">90");
    leftMotor.write(180 - driveSpeed);
    rightMotor.write(180 - (driveSpeed + (motorSpeed - 90)) - MIN_MOTOR_SPEED_CW);
  }
  else {
    leftMotor.write(180 - driveSpeed);
    rightMotor.write(driveSpeed);
  }

}

// Increase p, then d, then i
void turnPID(double target, double kp, double ki, double kd) {
  double currentAngle = getOrientation();
  double currentError = currentAngle - target;

  //assert targetAngle >= 0;

  double derivative = (currentError - prevError) * kd;
  double proportionalError = currentError * kp;
  double integral = currentError + prevIntegral;

  prevError = currentError;
  prevIntegral = integral;

  integral *= ki;
  double motorSpeed = map(derivative + proportionalError + integral,
                          -100 * (kp + ki + kd), 100 * (kp + ki + kd), 0, 180);

  if (motorSpeed < 90 && motorSpeed > 90 - MIN_MOTOR_SPEED_CW) {
    motorSpeed = 90 - MIN_MOTOR_SPEED_CW;
  }
  else if (motorSpeed > 90 && motorSpeed < 90 + MIN_MOTOR_SPEED_CCW) {
    motorSpeed = 90 + MIN_MOTOR_SPEED_CCW;
  }

  Serial.print("Speed: ");
  Serial.print(motorSpeed);
  Serial.print(" | Angle: ");
  Serial.print(currentAngle);
  Serial.print(" | Target: ");
  Serial.print(targetAngle);
  Serial.print(" | Error: " );
  Serial.print(currentError);
  Serial.print(" | P: ");
  Serial.print(proportionalError);
  Serial.print(" | D: ");
  Serial.print(derivative);
  Serial.print(" | I: ");
  Serial.println(integral);

  leftMotor.write(180 - motorSpeed);
  rightMotor.write(180 - motorSpeed);
}

/*
   Returns the current orientation in degrees
   debug true will print out verbose debug messages
   Note: A delay is reccommended if this function will be
   called in quick succession. No more than 100ms is neccessary
*/
float getOrientation() {
  imu::Vector<3> euler = bno.getVector(Adafruit_BNO055::VECTOR_EULER);
  float orientation = (float) euler.x();
  if (orientation > 180.0) {
    orientation -= 360;
  }
  return orientation;
}

boolean initializeIMU() {
  if (debug) {
    Serial.println("Initializing BNO055");
  }
  if (!bno.begin()) {
    Serial.println("Failed");
    return false;
  }
  if (debug) {
    Serial.println("Success");
  }
  //bno.setExtCrystalUse(true);
  return true;
}

