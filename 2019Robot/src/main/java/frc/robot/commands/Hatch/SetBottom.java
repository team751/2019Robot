package frc.robot.commands.Hatch;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class SetBottom extends Command {

    public SetBottom() {
        //requires(Robot.hatch);
    }

    protected void initialize() {
        SmartDashboard.putNumber("Down", SmartDashboard.getNumber("Angle", 0));
    }

    protected boolean isFinished() {
        return true;
    }

}