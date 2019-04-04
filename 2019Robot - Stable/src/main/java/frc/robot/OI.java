package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.Ramp.*;
import frc.robot.commands.ToggleLimelight;
import frc.robot.commands.Hatch.*;
import frc.robot.subsystems.MultiSpeedController;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	/*
	 * PINOUT -------------------------------- 
	 * PWM 
	 * 0 Right CIM 
	 * 1 Right CIM 
	 * 2 Right CIM 
	 * 3 Left CIM 
	 * 4 Left CIM 
	 * 5 Left CIM 
	 * 6 Ramp Solenoid Branch 
	 * 7 Hatch Seat Motor Power (keep on)
	 * 8 Hatch Window Motor 9 
	 * Hatch Servo
	 * 
	 * DIO 
	 * 0 Limit Switch Hatch Back 
	 * 1 Limit Switch Hatch Top 
	 * 2 Limit Switch Hatch
	 * 4 Hatch Encoder (Reeeeeeeeeeeeeeeeeeeeeeeeeeeee)
	 * Bottom 3 Lime Light Relay
	 * 
	 * USB 0 Camera 1 Arduino
	 * 
	 * 
	 * NETWORK -------------------------------- Eathernet Switch 5 RoboRio 6
	 * OpenMesh Radio 8 Raspberry Pi
	 * 
	 * 
	 * CONTROLLS --------------------------------
	 * 
	 * Analog RT Raise Hatch LT Lower Hatch Joy Arcade Drive
	 * 
	 * Digital RB Intake LB Outtake Y Hatch Up A Hatch Down
	 */

	public enum Controller { // Button mappings for the XBOX One controller
		A(1),
		B(2),
		X(3),
		Y(4),
		LB(5), 
		RB(6), 
		LT(2), // Must use .getRawAxis()
		RT(3), // Must use .getRaxAxis()
		BACK(7), START(8), LEFT_AXIS_PRESS(9), // X-Axis: -1.000 to 1.000 (stick.GetX())
												// Y-Axis: -1.000 to 1.000 (stick.GetY())
		RIGHT_AXIS_PRESS(10);

		private int buttonNum;

		private Controller(int value) {
			this.buttonNum = value;
		}

		public int getButtonMapping() {
			return this.buttonNum;
		}

	}

	public Joystick driverStick = new Joystick(0);

	public Button autoButton = new JoystickButton(driverStick, Controller.Y.buttonNum);
	public Button outTakeButton = new JoystickButton(driverStick, Controller.LB.buttonNum);
	public Button inTakeButton = new JoystickButton(driverStick, Controller.RB.buttonNum);
	public Joystick operatorStick = new Joystick(1);

	// Drive Train-------------------------------------------------
	public PWMVictorSPX[] rightControllers = { new PWMVictorSPX(0), new PWMVictorSPX(1), new PWMVictorSPX(2) };
	public PWMVictorSPX[] leftControllers = { new PWMVictorSPX(3), new PWMVictorSPX(4), new PWMVictorSPX(5) };
	public MultiSpeedController rightGroup = new MultiSpeedController(rightControllers[0], rightControllers[1],
			rightControllers[2]);
	public MultiSpeedController leftGroup = new MultiSpeedController(leftControllers[0], leftControllers[1],
			leftControllers[2]);

	// Camera------------------------------------------------------
	public DigitalOutput lightSolidStateRelayPin = new DigitalOutput(3);
	public Button LightButton = new JoystickButton(driverStick, Controller.BACK.getButtonMapping());

	// Ramp--------------------------------------------------------
	public PWMVictorSPX rampBottomMotor = new PWMVictorSPX(6);
	public Button rampButton = new JoystickButton(driverStick, Controller.START.getButtonMapping());

	// Hatch-------------------------------------------------------
	public PWMVictorSPX hatchWindowMotor = new PWMVictorSPX(8);
	public Servo hatchServo = new Servo(9);
	public DigitalInput hatchBackSwitch = new DigitalInput(0);
	public DigitalInput hatchTopSwitch = new DigitalInput(1);
	public DigitalInput hatchBottomSwitch = new DigitalInput(2);
	
	public AnalogTrigger test = new AnalogTrigger(0);
	public Counter hatchEncoder = new Counter(test);

	public Button servoDownButton = new JoystickButton(driverStick, Controller.A.getButtonMapping());
	public Button servoUpButton = new JoystickButton(driverStick, Controller.Y.getButtonMapping());
	public Button hatchUpButton = new JoystickButton(driverStick, Controller.X.getButtonMapping());
	public Button hatchDownButton = new JoystickButton(driverStick, Controller.B.getButtonMapping());
	public PWMVictorSPX seatMotor = new PWMVictorSPX(7);

	public void init() {
		// Hatch----------------------------------------------
		//servoUpButton.whenPressed(new SetSeatAngle(0));
		servoDownButton.whenPressed(new ActuateHatch());
		//servoButton.whenPressed(new setSeatAngle(360));
		hatchUpButton.whenPressed(new ToTop(0.5));
		hatchDownButton.whenPressed(new Stow(0.5));
		test.setLimitsVoltage(3.0, 3.0);
		hatchEncoder.setDistancePerPulse(2.0583);
		// Ramp-----------------------------------------------
		rampButton.whileHeld(new Deploy());

		// Camera---------------------------------------------
		LightButton.whenPressed(new ToggleLimelight());
	}

}