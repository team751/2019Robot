package frc.robot.commands.Hatch;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Intake extends Command {
    private int timer; // Timer that waits 1s (50 * 20ms) to return true, allowing the hatch mechanism to finish moving.

    public Intake() {
	requires(Robot.hatch);
	this.timer = 50;
    }


    protected void initialize() {
        this.timer = 50;
    }

    protected void execute() {
         Robot.hatch.turnUp();
         Robot.hatch.isUp = true;

         this.timer--;
     }

     protected boolean isFinished() {
         return this.timer < 1;
     }

}
