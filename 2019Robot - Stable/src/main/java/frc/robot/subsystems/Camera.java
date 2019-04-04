package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Camera extends Subsystem {

    public boolean isLit = false;

    @Override
    protected void initDefaultCommand() {
        CameraServer.getInstance().startAutomaticCapture();
    }

}