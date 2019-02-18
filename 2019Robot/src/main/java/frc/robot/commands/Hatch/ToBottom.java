package frc.robot.commands.Hatch;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ToBottom extends Command {
    private double speed;

    public ToBottom(double speed){
        requires(Robot.hatch);
        this.speed = speed;
    }

    protected void initialize() {

    }

    protected void execute() {
       Robot.hatch.setWindowMotorSpeed(-speed);
    }

    protected boolean isFinished() {
        return Robot.hatch.getBottomSwitch();
    }

}