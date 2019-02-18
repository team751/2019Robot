package frc.robot.commands.Hatch;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class HatchDefault extends Command {

    public HatchDefault() {
        requires(Robot.hatch);
    }

    // initialize() - This method sets up the command and is called immediately
    // before the command is executed for the first time and every subsequent time
    // it is started .
    // Any initialization code should be here.
    protected void initialize() {

    }

    /*
     * execute() - This method is called periodically (about every 20ms) and does
     * the work of the command. Sometimes, if there is a position a subsystem is
     * moving to, the command might set the target position for the subsystem in
     * initialize() and have an empty execute() method.
     */
    protected void execute() {
        double speed = Robot.oi.driverStick.getRawAxis(OI.Controller.LT.getButtonMapping());
        speed -= Robot.oi.driverStick.getRawAxis(OI.Controller.RT.getButtonMapping());
        Robot.hatch.setWindowMotorSpeed(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }
}