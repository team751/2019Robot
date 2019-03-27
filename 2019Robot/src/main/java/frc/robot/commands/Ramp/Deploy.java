package frc.robot.commands.Ramp;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Deploy extends Command {

    public Deploy(){
        requires(Robot.ramp);
    }

    protected void initialize() {
        Robot.ramp.setBottomSpeed(1);
        //Robot.ramp.setTopSpeed(1);
    }

    protected void execute() {
        Robot.ramp.setBottomSpeed(1);
        //Robot.ramp.setTopSpeed(1);
    }

    protected boolean isFinished() {
        return true;          
    }

}