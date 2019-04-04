package frc.robot.commands.Hatch;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class SetTop extends Command {

    public SetTop() {
        //requires(Robot.hatch);
    }

    protected void initialize() {
        SmartDashboard.putNumber("Up", SmartDashboard.getNumber("Angle", 0));
    }

    protected boolean isFinished() {
        return true;
    }

}