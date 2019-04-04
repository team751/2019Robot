package frc.robot.commands.Hatch;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class CorrectAngle extends Command {
    private boolean finished;

    public CorrectAngle() {
        requires(Robot.hatch);
        finished = false;
    }

    protected void initialize() {

    }

    protected void execute() {
        if (!Robot.hatch.isUp) {
            Robot.hatch.turnDown();
        } else {
            Robot.hatch.turnUp();
        }
        finished = true;
    }

    protected boolean isFinished() {
        return finished;
    }

}