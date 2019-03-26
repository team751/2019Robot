// package frc.robot.commands.Hatch;

// import edu.wpi.first.wpilibj.command.Command;
// import frc.robot.Robot;

// public class ActuateHatch extends Command {
//     private boolean finished;

//     public ActuateHatch() {
//         requires(Robot.hatch);
//         finished = false;
//     }

//     protected void initialize() {
//         if (Robot.hatch.isUp) {
//             Robot.hatch.isUp = false;
//             Robot.hatch.turnDown();
            
//         } else {
//             Robot.hatch.isUp = true;
//             Robot.hatch.turnUp();
            
//         }
//     }

//     protected void execute() {
//     }

//     protected boolean isFinished() {
//         return true;
//     }

// }