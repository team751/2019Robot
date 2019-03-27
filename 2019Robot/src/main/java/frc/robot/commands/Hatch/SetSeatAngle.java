package frc.robot.commands.Hatch;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class SetSeatAngle extends Command {
    double distance;
    double dirrection;
    double last;
    double start;
    double location;

    
    public SetSeatAngle(int location){
        requires(Robot.hatch);
        //Robot.hatch.counter.reset();
        this.location = location;
        
        //System.out.println(distance + "   " + location + "  " + Robot.hatch.angle);
    }

    // initialize() - This method sets up the command and is called immediately
    // before the command is executed for the first time and every subsequent time
    // it is started .
    // Any initialization code should be here.
    protected void initialize() {
        Robot.hatch.counter.reset();
        last = 0;
        start = Robot.hatch.angle;
        double a = location - Robot.hatch.angle;
        dirrection =1;
        if (a < 0){
            a *= -1;
            dirrection = -1;
        }
        distance = Math.abs(location);
    }

    /*
     * execute() - This method is called periodically (about every 20ms) and does
     * the work of the command. Sometimes, if there is a position a subsystem is
     * moving to, the command might set the target position for the subsystem in
     * initialize() and have an empty execute() method.
     */
    protected void execute() {
        Robot.hatch.seatMotor.set(dirrection);
        //int dis = (int) Robot.hatch.counter.getDistance();
        Robot.hatch.angle = start + (Robot.hatch.counter.getDistance() * dirrection);
        double dis = Robot.hatch.angle;

        //if (dis != last) System.out.println(dis + "   " + dirrection + "   " + location);
        last = dis;
        //Robot.hatch.angle = (int) (start   + (dis*dirrection));
        //System.out.println(Robot.oi.aIn.getVoltage());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        boolean finished = false;
        if (dirrection > 0){
            if (Robot.hatch.angle >= location){
                finished = true;
            }
        }else{
            if (Robot.hatch.angle <= location){
                finished = true;
            }
        }
        if (finished) {
            Robot.hatch.seatMotor.set(0);
            //Robot.hatch.angle += Robot.hatch.counter.getDistance() * dirrection;
        }
        return (finished);
        
    }
}