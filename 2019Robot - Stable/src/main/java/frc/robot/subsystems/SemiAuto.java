// package frc.robot.subsystems;

// import edu.wpi.first.wpilibj.command.Command;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import frc.robot.Robot;
// import frc.robot.subsystems.Drivetrain;
// import frc.robot.subsystems.NTPReader;

// public class SemiAuto extends Command {
//     private NTPReader table;
//     private double tolerance;
//     private double prevError;
//     private double prevIntegral;

//     public SemiAuto() {
//         super("SemiAuto");
//         requires(Robot.drivetrain);
//         this.prevError = 0.0;
//         this.prevIntegral = 0.0;
//     }

//     protected void initialize() {
//         table = new NTPReader();
//         SmartDashboard.getNumber("Tolerance", 5.0);
//         System.out.println("Error: " + table.getError());

//         // Turn on the lime light and adjust exposure to GRIP levels
//     }

//     /*
//      * execute() - This method is called periodically (about every 20ms) and does
//      * the work of the command. Sometimes, if there is a position a subsystem is
//      * moving to, the command might set the target position for the subsystem in
//      * initialize() and have an empty execute() method.
//      */
//     protected void execute() {

//     }

//     // Reset the error and integral calculations from the previous turn or
//     // driveStraight command.
//     public void resetPID() {
//     }

//     public void align() {
//         double error = table.getError();
//         int mod = 1;
//         if (error < 0) {
//             mod = -1;
//         }
//         error = Math.abs(error);
//         double speed = Math.pow(1.1, error) / 304.48;

//         Robot.drivetrain.leftSpeedController.set(speed * mod * -1);
//         Robot.drivetrain.rightSpeedController.set(speed * mod);
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

//         if (motorSpeed < 0 && motorSpeed > -Robot.drivetrain.MIN_MOTOR_SPEED_LEFT) { // Need to verify/test
//             motorSpeed = -Robot.drivetrain.MIN_MOTOR_SPEED_LEFT;
//         } else if (motorSpeed > 0 && motorSpeed < Robot.drivetrain.MIN_MOTOR_SPEED_RIGHT) {
//             motorSpeed = Robot.drivetrain.MIN_MOTOR_SPEED_RIGHT;
//         }

//         Robot.drivetrain.leftSpeedController.set(motorSpeed);
//         Robot.drivetrain.rightSpeedController.set(-motorSpeed);
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
//             Robot.drivetrain.leftSpeedController.set(1.0 - (driveSpeed + motorSpeed));
//             Robot.drivetrain.rightSpeedController.set(driveSpeed);
//         } else if (motorSpeed > 0.0) {
//             Robot.drivetrain.leftSpeedController.set(1.0 - driveSpeed);
//             Robot.drivetrain.rightSpeedController
//                     .set(1.0 - (driveSpeed + motorSpeed) - Robot.drivetrain.MIN_MOTOR_SPEED_RIGHT);
//         } else {
//             Robot.drivetrain.leftSpeedController.set(1.0 - driveSpeed);
//             Robot.drivetrain.rightSpeedController.set(driveSpeed);
//         }
//     }

//     private double map(double x, double in_min, double in_max, double out_min, double out_max) {
//         return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
//     }

//     // Make this return true when this Command no longer needs to run execute()
//     protected boolean isFinished() {
//         double error = table.getError();
//         this.prevError = 0.0;
//         this.prevIntegral = 0.0;

//         // Turn off lime light and set exposure back


//         return (Math.abs(error) < tolerance);
//     }

// }