package frc.robot.commands.Hatch;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class ManualHatch extends Command {
    private double tolerance = 3.5;
    private int dirrection = 0;
    
    public ManualHatch(){
        SmartDashboard.putNumber("Angle", 0);
        requires(Robot.hatch);
    }

    // initialize() - This method sets up the command and is called immediately
    // before the command is executed for the first time and every subsequent time
    // it is started .
    // Any initialization code should be here.
    protected void initialize() {
        Robot.hatch.setAngle((int)SmartDashboard.getNumber("Angle", 0.0));
    }

    /*
     * execute() - This method is called periodically (about every 20ms) and does
     * the work of the command. Sometimes, if there is a position a subsystem is
     * moving to, the command might set the target position for the subsystem in
     * initialize() and have an empty execute() method.
     */
    protected void execute() {
        
        // dirrection = 0;
        // if (Math.abs(Robot.hatch.angleTarget - Robot.hatch.angle) > tolerance){
        //     if (Robot.hatch.angleTarget - Robot.hatch.angle < 0){
        //         dirrection = -1;
        //     }else{
        //         dirrection = 1;
        //     }
        //     Robot.hatch.angle += Robot.hatch.counter.getDistance() * dirrection;
        //     Robot.hatch.counter.reset();
        // }
        // Robot.hatch.seatMotor.set(dirrection);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }
}