package frc.robot.arduino;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArduinoDataListener implements Runnable {
	private double orientation;
	private double leftDistance, rightDistance, frontDistance;
	private SerialPort arduinoPort;
	private String arduinoMessage;
	private boolean arduinoOverwrite;

	private void connect() {
		try {
			arduinoPort = new SerialPort(9600, SerialPort.Port.kUSB);
			arduinoPort.setReadBufferSize(1);
			arduinoPort.setTimeout(0.001);
		} catch (RuntimeException e) {
			System.out.println("No Arduino serial connection");
		}
	}

	public ArduinoDataListener() {
		orientation = 0.0;
		leftDistance = -1;
		rightDistance = -1;
		arduinoMessage = "";
		arduinoOverwrite = true;

		// sourceType = PIDSourceType.kDisplacement;
		SmartDashboard.putNumber("Orientation", this.orientation);
		SmartDashboard.putNumber("Left Side Distance", this.leftDistance);
		SmartDashboard.putNumber("Right Side Distance", this.rightDistance);
		SmartDashboard.putNumber("Front Distance", this.frontDistance);
		connect();
	}

	public double getOrientation() {
		return orientation;
	}

	public double getLeftDistance() {
		return leftDistance;
	}

	public double getRightDistance() {
		return rightDistance;
	}

	public double getFrontDistance() {
		return frontDistance;
	}

	@Override
	public void run() {
		while (true) {
			if (arduinoPort == null) {
				connect();
				//System.out.println("attemptedconnecting");
			} else {
				// System.out.println("fetchingdata");
				fetchData();
			}
		}
	}

	public void stop() {
	}

	private void getArduinoMessage() {
		try {
			int bracketIndex = arduinoMessage.indexOf("[");
			if (bracketIndex == -1) {
				return;
			}
			arduinoMessage = arduinoMessage.substring(bracketIndex);
			int endOfMessage = arduinoMessage.lastIndexOf(']');
			int startOfMessage = arduinoMessage.lastIndexOf('[', endOfMessage);

			if (endOfMessage <= startOfMessage) {
				arduinoMessage += arduinoPort.readString();
				arduinoOverwrite = false;
			} else {
				arduinoMessage = arduinoMessage.substring(startOfMessage + 1, endOfMessage);
				//System.out.println("Received String: " + arduinoMessage);
				String[] data = arduinoMessage.split("-");

				this.orientation = Double.parseDouble(data[0]);
				if(data.length > 1) {
					this.leftDistance = Double.parseDouble(data[1]);
					
					if(data.length > 2) {
						this.rightDistance = Double.parseDouble(data[2]);

						if(data.length > 3) {
							this.frontDistance = Double.parseDouble(data[3]);
						}
					}
				}
				SmartDashboard.putNumber("Orientation", this.orientation);
				SmartDashboard.putNumber("Left Side Distance", this.leftDistance);
				SmartDashboard.putNumber("Right Side Distance", this.rightDistance);
				SmartDashboard.putNumber("Front Distance", this.frontDistance);

				arduinoOverwrite = true;
				arduinoMessage = "";
			}
		}catch(NumberFormatException e){}
	}
	
	public void fetchData() {


		//if (arduinoOverwrite) {
			arduinoMessage += arduinoPort.readString();
		//}

		//System.out.println("message is "+ arduinoMessage);
		getArduinoMessage();
	}

	@Override
	public String toString() {
		return "Orientation: " + orientation + " LeftDistance: " + leftDistance + " rightDistance: " + rightDistance + " frontDistance: " + this.frontDistance;
	}

}
