package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MultiSpeedController implements SpeedController {
	private SpeedController[] speedControllers;
	private double speed = 1;
	public static double speedFactor = 1;

	public static final double speedConstantLeft = SmartDashboard.getNumber("Left Speed Constant", 1.0); // Get value from SmartDashboard
	public static final double speedConstantRight = SmartDashboard.getNumber("Right Speed Constant", 1.0); // Get value from SmartDashboard

	public MultiSpeedController(SpeedController motor1, SpeedController motor2, SpeedController motor3) {
		speedControllers = new SpeedController[3];
		speedControllers[0] = motor1;
		speedControllers[1] = motor2;
		speedControllers[2] = motor3;
		this.set(0.0);
	}

	public void setSpeedFactor(double factor) {
		speedFactor = factor;
	}

	@Override
	public double get() {
		return this.speed;
	}

	@Override
	public void set(double speed) {
		//this.speed = speed;
		//speed *= this.speedFactor;
		 if (speed < 0){
		 	this.speed = Math.max(speed, -speedFactor);
		 }else if (speed > 0){
		 	this.speed = Math.min(speed, speedFactor);
		 }else{
		 	this.speed = 0;
		 }
		 for (SpeedController speedController : this.speedControllers) {
		 	speedController.set(this.speed);
		 }
	}

	@Override
	public void pidWrite(double output) {
		this.set(output);
	}

	@Override
	public void disable() {
		for (SpeedController speedController : this.speedControllers) {
			speedController.disable();
		}
	}

	@Override
	public void setInverted(boolean isInverted) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getInverted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void stopMotor() {
		// TODO Auto-generated method stub

	}
}
