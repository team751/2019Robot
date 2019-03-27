package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.commands.JoystickDrive;

public class Drivetrain extends Subsystem {
    public DifferentialDrive difDrive;
    public boolean invertX;

    public Drivetrain() {
        difDrive = new DifferentialDrive(Robot.oi.leftGroup, Robot.oi.rightGroup);
        invertX = false;
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new JoystickDrive());
    }

    public void arcadeDrive(double x, double y, boolean bool) {
        // if (Robot.oi.hatchBackSwitch.get()) {
        //     if (-x > 0) {
        //         difDrive.arcadeDrive(0, y, bool);
        //     }
        // } else {
        //     difDrive.arcadeDrive(-x, y, bool);
        // }
        if(invertX) {
            difDrive.arcadeDrive(x, y, bool);
        } else {
            difDrive.arcadeDrive(-x, y, bool);
        }
    }

}