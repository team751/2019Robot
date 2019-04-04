package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;

public class NTPReader extends Subsystem {
    private NetworkTableInstance ntpInstance;

    public NTPReader(){
        ntpInstance = NetworkTableInstance.getDefault();
    }

    @Override
    protected void initDefaultCommand() {
    }

    public void printOutTables(){
        System.out.println("nte tables:");
        for(NetworkTableEntry nte : ntpInstance.getEntries("", 0)){
            System.out.println(nte.getName());
        }
    }

    public double getDistance() {
        return ntpInstance.getTable("rpi").getEntry("distance").getDouble(0);
    }

    public double getError() {
        return -1;
    }
}