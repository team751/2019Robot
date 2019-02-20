package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ToggleLimelight extends Command {

    private boolean finished;

    public ToggleLimelight() {
        this.finished = false;
    }

    protected void initialize() {

    }

    protected void execute() {
        if (Robot.camera.isLit) {
            Robot.oi.lightSolidStateRelayPin.set(false);
            Robot.camera.isLit = false;
        } else {
            Robot.oi.lightSolidStateRelayPin.set(true);
            Robot.camera.isLit = true;
        }
        this.finished = true;
    }

    protected boolean isFinished() {
        return this.finished;
    }

}