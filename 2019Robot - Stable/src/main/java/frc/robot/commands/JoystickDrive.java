package frc.robot.commands;

import frc.robot.*;
import edu.wpi.first.wpilibj.command.Command;

public class JoystickDrive extends Command {

	public JoystickDrive() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		double x = Robot.oi.driverStick.getRawAxis(4);
		double y = Robot.oi.driverStick.getRawAxis(5);

		// double newX = this.sigmoidCurve(x);
		// double newY = this.sigmoidCurve(y);

		Robot.drivetrain.arcadeDrive(y, x, true);

	}

	private double sigmoidCurve(double x) {
		return (1.0 / (0.5 + Math.pow(Math.E, -(x + 0.7)))) - 1.0;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
