package frc.robot.commands.Hatch;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ActuateHatch extends Command {
    private boolean finished;

    public ActuateHatch() {
        requires(Robot.hatch);
        finished = false;
    }

    protected void initialize() {

    }

    protected void execute() {
        if (Robot.hatch.isUp) {
            Robot.hatch.turnDown();
            Robot.hatch.isUp = false;
        } else {
            Robot.hatch.turnUp();
            Robot.hatch.isUp = true;
        }
        finished = true;
    }

    protected boolean isFinished() {
        return finished;
    }

}