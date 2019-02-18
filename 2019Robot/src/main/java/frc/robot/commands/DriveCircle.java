package frc.robot.commands;

import frc.robot.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveCircle extends Command {
    private double radius;
    private double speed;
    private static final double robotDistFromCenter = 1.02; // Distance from center of robot to the wheels (in feet)

    public DriveCircle(double radius, double speed) {
        super("Circle");
        this.radius = radius;
        this.speed = speed;

    }

    // initialize() - This method sets up the command and is called immediately
    // before the command is executed for the first time and every subsequent time
    // it is started .
    // Any initialization code should be here.
    protected void initialize() {

    }

    /*
     * execute() - This method is called periodically (about every 20ms) and does
     * the work of the command. Sometimes, if there is a position a subsystem is
     * moving to, the command might set the target position for the subsystem in
     * initialize() and have an empty execute() method.
     */
    protected void execute() {
       
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    

}