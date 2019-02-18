package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

public class NTPReader extends Subsystem {
    private NetworkTableInstance ntpInstance;

    public NTPReader(){
        ntpInstance = NetworkTableInstance.getDefault();
    }

    @Override
    protected void initDefaultCommand() {
    }

    public double getDistance() {
        return ntpInstance.getTable("rpi").getEntry("distance").getDouble(0);
    }

    public double getError() {
        return -1;
    }
}