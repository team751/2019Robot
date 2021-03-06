package frc.robot;

import frc.robot.arduino.ArduinoDataListener;
import frc.robot.commands.Hatch.*;
import frc.robot.commands.Ramp.Deploy;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.TimedRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
	public static OI oi = new OI();
	public static final int delayTimeSeconds = 4;

	public static final Drivetrain drivetrain = new Drivetrain();
	public static final Camera camera = new Camera();
	public static final PowerDistributionPanel pdp = new PowerDistributionPanel(0);
	public static final ArduinoDataListener ADL = new ArduinoDataListener();

	public static final Ramp ramp = new Ramp();
	public static final Hatch hatch = new Hatch();
	public static NTPReader ntpReader = new NTPReader();

	public static DifferentialDrive robotDrive;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	public void robotInit() {
		oi.init();
		// instantiate the command used for the autonomous period

		// autonomousCommand = new Autonomous();
		// Thread motorControlThread = new Thread(autonomousJoystickSimulator);
		// motorControlThread.start();

		System.out.println("creating ADL thread");

		Thread listenerThread = new Thread(ADL);
		listenerThread.start();

		// SmartDashboard.putData("Circle", new DriveCircle());
		SmartDashboard.putNumber("Speed Cap", 1.0);
		SmartDashboard.setDefaultNumber("Speed Cap", 1.0);
		SmartDashboard.putBoolean("Fine Mode", false);
		SmartDashboard.putNumber("Tolerance", 1.0);

		SmartDashboard.putData("Actuate Hatch", new ActuateHatch());
		SmartDashboard.putData("Hatch Manual", new ManualHatch());
		SmartDashboard.putData("Deploy Ramps", new Deploy());


		SmartDashboard.putData("Actuate Hatch", new ActuateHatch());
		SmartDashboard.putData("Hatch Manual", new ManualHatch());
		SmartDashboard.putData("Drivetrain", drivetrain.difDrive);
		SmartDashboard.putNumber("IMU Position (degrees)", 0);
		SmartDashboard.putNumber("Left Speed Constant", 1.0);
		SmartDashboard.putNumber("Right Speed Constant", 1.0);
		SmartDashboard.putData("Toggle Light", new ToggleLimelight());
		SmartDashboard.putData("Correct Angle", new CorrectAngle());

		SmartDashboard.putData("Drivetrain", drivetrain.difDrive);
		SmartDashboard.putBoolean("drivetrain Invert x", drivetrain.invertX);
		SmartDashboard.putNumber("Left Speed Constant", 1.0);
		SmartDashboard.putNumber("Right Speed Constant", 1.0);
		SmartDashboard.putData("Toggle Light", new ToggleLimelight());
		SmartDashboard.putNumber("Heading to go to(degrees)", 1.0);
		// SmartDashboard.putData("Go to Heading", new SemiAuto(
		// 				SmartDashboard.getNumber("Heading to go to(degrees)", 
		// 										ADL.getOrientation())));

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();

		if (SmartDashboard.getBoolean("Fine Mode", false)) {
			MultiSpeedController.speedFactor = (0.25);
		} else {
			MultiSpeedController.speedFactor = (SmartDashboard.getNumber("Speed Cap", 1));
		}

		SmartDashboard.putData(pdp);
		hatch.seatMotor.set(0);
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
		//if (autonomousCommand != null) {
		//	autonomousCommand.start();
		//}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		// this.printArduinoInfo();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		//
		//if (autonomousCommand != null)
		//	autonomousCommand.cancel();
	}

	/**
	 * This function is called when the disabled button is hit. You can use it to
	 * reset subsystems before shutting down.
	 */
	public void disabledInit() {
		//if (autoEnabled) {
		//	autonomousCommad.cancel();
		//}
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		if (SmartDashboard.getBoolean("Fine Mode", false)) {
			MultiSpeedController.speedFactor = (0.25);
		} else {
			MultiSpeedController.speedFactor = (SmartDashboard.getNumber("Speed Cap", 1));
		}
		SmartDashboard.putData(pdp);

		//ADL.fetchData();
		//ntpReader.printOutTables();
		SmartDashboard.putNumber("IMU Position (degrees)", ADL.getOrientation());
		System.out.println(ADL);
	}

	/**
	 * This function is called periodically during test mode
	 */
	@SuppressWarnings("deprecation")
	public void testPeriodic() {
		LiveWindow.run();
	}

}
