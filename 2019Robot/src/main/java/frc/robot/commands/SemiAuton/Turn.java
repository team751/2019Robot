// package frc.robot.commands.SemiAuton;
// import edu.wpi.first.wpilibj.command.Command;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import frc.robot.Robot;

// /*public class Turn extends Command {
//     private double tolerance;
//     private double prevError;
//     private double prevIntegral;
//     private double targetAngle;

//     public Turn(double angle) {
//         requires(Robot.drivetrain);
//         this.prevError = 0.0;
//         this.prevIntegral = 0.0;
//         this.targetAngle = angle;
//     }

//     protected void initialize() {
//         this.tolerance = SmartDashboard.getNumber("Tolerance", 5.0);

//         turnToHeading(this.targetAngle, 0.3, 0, 0.1);
//     }

//     /*
//      * execute() - This method is called periodically (about every 20ms) and does
//      * the work of the command. Sometimes, if there is a position a subsystem is
//      * moving to, the command might set the target position for the subsystem in
//      * initialize() and have an empty execute() method.
//      */
//     protected void execute() {

//     }

//     public void turnToHeading(double targetHeading, double kp, double ki, double kd) {
//         double currentHeading = Robot.ADL.getOrientation(); // Get orientation from IMU - needs to be implemented
//         double currentError = currentHeading - targetHeading;

//         double derivative = (currentError - prevError) * kd;
//         double proportionalError = currentError * kp;
//         double integral = currentError + prevIntegral;

//         this.prevError = currentError;
//         this.prevIntegral = integral;

//         integral *= ki;

//         double motorSpeed = this.map(derivative + proportionalError + integral, -100.0 * (kp + ki + kd),
//                 100.0 * (kp + ki + kd), -1.0, 1.0);

//         if (motorSpeed < 0 && motorSpeed > -Robot.MIN_MOTOR_SPEED_LEFT) { // Need to verify/test
//             motorSpeed = -Robot.MIN_MOTOR_SPEED_LEFT;
//         } else if (motorSpeed > 0 && motorSpeed < Robot.MIN_MOTOR_SPEED_RIGHT) {
//             motorSpeed = Robot.MIN_MOTOR_SPEED_RIGHT;
//         }

//         Robot.oi.leftGroup.set(motorSpeed);
//         Robot.oi.rightGroup.set(-motorSpeed);
//     }

//     private double map(double x, double in_min, double in_max, double out_min, double out_max) {
//         return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
//     }

//     // Make this return true when this Command no longer needs to run execute()
//     protected boolean isFinished() {
//         if (Math.abs(this.targetAngle - Robot.ADL.getOrientation()) < tolerance) {
//             this.prevError = 0.0;
//             this.prevIntegral = 0.0;
//             return true;
//         }
//         return false;
//     }

// }