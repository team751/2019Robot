// package frc.robot.commands.Hatch;

// import edu.wpi.first.wpilibj.command.Command;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import frc.robot.Robot;

// public class ManualHatch extends Command {
    
//     public ManualHatch(){
//         SmartDashboard.putNumber("Angle", 0);
//         requires(Robot.hatch);
//     }

//     // initialize() - This method sets up the command and is called immediately
//     // before the command is executed for the first time and every subsequent time
//     // it is started .
//     // Any initialization code should be here.
//     protected void initialize() {
//         Robot.hatch.setAngle((int)SmartDashboard.getNumber("Angle", 0.0));
//     }

//     /*
//      * execute() - This method is called periodically (about every 20ms) and does
//      * the work of the command. Sometimes, if there is a position a subsystem is
//      * moving to, the command might set the target position for the subsystem in
//      * initialize() and have an empty execute() method.
//      */
//     protected void execute() {
//         Robot.hatch.setAngle((int)SmartDashboard.getNumber("Angle", 0.0));
//     }

//     // Make this return true when this Command no longer needs to run execute()
//     protected boolean isFinished() {
//         return false;
//     }
// }