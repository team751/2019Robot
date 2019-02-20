package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.commands.Ramp.RampDefault;

public class Ramp extends Subsystem {

    private PWMVictorSPX topRampMotor;
    private PWMVictorSPX bottomRampMotor;

    //Constructor takes Servo Pin
    public Ramp(){
        topRampMotor = Robot.oi.rampTopMotor;
        bottomRampMotor = Robot.oi.rampBottomMotor;
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new RampDefault());
    }

    public void setTopSpeed(double speed){
        topRampMotor.set(speed);
    }

    public void setBottomSpeed(double speed){
        bottomRampMotor.set(speed);
    }

}