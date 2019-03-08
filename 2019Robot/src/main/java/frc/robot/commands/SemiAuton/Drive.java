// package frc.robot.commands.SemiAuton;

// import edu.wpi.first.wpilibj.command.Command;
// import frc.robot.Robot;

// public class Drive extends Command {
//     private double prevError;
//     private double prevIntegral;
//     private int minDistanceToWall;

//     public Drive(int minInchesToWall) {
//         requires(Robot.drivetrain);
//         this.minDistanceToWall = minInchesToWall;
//     }

//     protected void initialize() {
//         this.prevError = 0.0;
//         this.prevIntegral = 0.0;
//     }

//     /*
//      * execute() - This method is called periodically (about every 20ms) and does
//      * the work of the command. Sometimes, if there is a position a subsystem is
//      * moving to, the command might set the target position for the subsystem in
//      * initialize() and have an empty execute() method.
//      */
//     protected void execute() {

//     }

//     public void driveStraight(double initialAngle, double driveSpeed, double kp, double ki, double kd) {
//         double currentError = Robot.ADL.getOrientation() - initialAngle;

//         double derivative = (currentError - prevError) * kd;
//         double proportionalError = currentError * kp;
//         double integral = currentError + prevIntegral;

//         prevError = currentError;
//         prevIntegral = integral;

//         integral *= ki;
//         double motorSpeed = map(derivative + proportionalError + integral, -100 * (kp + ki + kd), 100 * (kp + ki + kd),
//                 0, 180);

//         if (motorSpeed < 0.0) {
//             Robot.oi.leftGroup.set(1.0 - (driveSpeed + motorSpeed));
//             Robot.oi.rightGroup.set(driveSpeed);
//         } else if (motorSpeed > 0.0) {
//             Robot.oi.leftGroup.set(1.0 - driveSpeed);
//             Robot.oi.rightGroup.set(1.0 - (driveSpeed + motorSpeed) - Robot.MIN_MOTOR_SPEED_RIGHT);
//         } else {
//             Robot.oi.leftGroup.set(1.0 - driveSpeed);
//             Robot.oi.rightGroup.set(driveSpeed);
//         }
//     }

//     private double map(double x, double in_min, double in_max, double out_min, double out_max) {
//         return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
//     }

//     // Make this return true when this Command no longer needs to run execute()
//     protected boolean isFinished() {
//         if (Robot.ADL.getFrontDistance() < this.minDistanceToWall) {
//             this.prevError = 0.0;
//             this.prevIntegral = 0.0;
//             return true;
//         }
//         return false;
//     }

// }