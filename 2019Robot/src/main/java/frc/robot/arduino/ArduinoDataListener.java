package frc.robot.arduino;

import edu.wpi.first.wpilibj.SerialPort;

public class ArduinoDataListener implements Runnable {
	private double orientation;
	private double leftDistance, rightDistance;
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

	@Override
	public void run() {
		while (true) {
			if (arduinoPort == null) {
				connect();
				System.out.println("attemptedconnecting");
			} else {
				//System.out.println("fetchingdata");
				fetchData();
			}
		}
	}

	public void stop() {
	}

	private void getArduinoMessage() {
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
			// System.out.println("Received String: " + message);
			String[] data = arduinoMessage.split("-");

			System.out.println("got data");

			switch (data[0].toLowerCase()) {
			case "imu":
				orientation = Double.parseDouble(data[1]);
				break;
			case "us":
				leftDistance = Double.parseDouble(data[1]);
				rightDistance = Double.parseDouble(data[2]);
				break;
			default:
				System.out.println("Non-typical data sent: " + data[0]);
				break;
			}

			arduinoOverwrite = true;
		}
	}

	private void fetchData() {
		if (arduinoOverwrite) {
			arduinoMessage = arduinoPort.readString();
		}

		getArduinoMessage();
	}

	@Override
	public String toString() {
		return "Orientation: " + orientation + " LeftDistance: " + leftDistance + " rightDistance: " + rightDistance;
	}

}
