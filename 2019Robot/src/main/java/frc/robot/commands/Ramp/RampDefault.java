package frc.robot.commands.Ramp;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RampDefault extends Command {

    public RampDefault(){
        requires(Robot.ramp);
    }

    protected void initialize() {
        Robot.ramp.setBottomSpeed(0);
        Robot.ramp.setTopSpeed(0);
    }

    protected void execute() {
        Robot.ramp.setBottomSpeed(0);
        Robot.ramp.setTopSpeed(0);
    }

    protected boolean isFinished() {
        return false;
    }

}