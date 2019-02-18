package frc.robot.commands.Hatch;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ToTop extends Command {
    private double speed;

    public ToTop(double speed){
        requires(Robot.hatch);
        this.speed = speed;
    }

    protected void initialize() {

    }

    protected void execute() {
       Robot.hatch.setWindowMotorSpeed(speed);
    }

    protected boolean isFinished() {
        return Robot.hatch.getTopSwitch();
    }

}