package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

public class AutoCamera extends Subsystem {

    @Override
    protected void initDefaultCommand() {
    }

    public void setLightStatus(boolean on) {
        Robot.oi.lightSolidStateRelayPin.set(on);
    }
}