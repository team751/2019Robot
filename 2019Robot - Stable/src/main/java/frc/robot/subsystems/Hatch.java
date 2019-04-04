package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.commands.Hatch.HatchDefault;
import frc.robot.commands.Hatch.SetSeatAngle;

//ansa made this component :)
public class Hatch extends Subsystem {
    public double angle = 0;
    public double angleTarget = 0;
    private Servo servo;
    private DigitalInput backSwitch;
    private DigitalInput topSwitch;
    private DigitalInput bottomSwitch;
    private PWMVictorSPX windowMotor;
    public PWMVictorSPX seatMotor;
    public boolean isUp;
    //public AnalogTrigger test;
    public Counter counter;

    //Theese angles are wrong but like we cant really fix that because they dont give us testing time
    public final int up = 0;
    public final int down = 180;
    



    public Hatch() {
        this.servo = Robot.oi.hatchServo;
        this.windowMotor = Robot.oi.hatchWindowMotor;
        this.backSwitch = Robot.oi.hatchBackSwitch;
        this.topSwitch = Robot.oi.hatchTopSwitch;
        this.bottomSwitch = Robot.oi.hatchBottomSwitch;
        this.seatMotor = Robot.oi.seatMotor;
        this.isUp = true;
        this.counter = Robot.oi.hatchEncoder;
        this.seatMotor.set(0);
        this.angle=0;

    }



    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new HatchDefault());
    }

    protected void teleopPeriodic() {

    }

    public void setAngle(int state) {
        this.angleTarget = state;
    }

    public void turnUp() {
        this.setAngle(up);
    }

    public void turnDown() {
        this.setAngle(down);
    }

    public int getAngle() {
        return (int) servo.getAngle();
    }

    public boolean getBackSwitch() {
        return backSwitch.get();
    }

    public boolean getTopSwitch() {
        return topSwitch.get();
    }

    public boolean getBottomSwitch() {
        return bottomSwitch.get();
    }

    // Unsafe set method
    public void setWindowMotorSpeedRaw(double speed) {
        windowMotor.set(-speed);
    }

    public void setWindowMotorSpeed(double speed) {

        if (speed == 0){
        return;
        }
        if (speed > 0){
        speed = (getTopSwitch()) ? 0 : speed;
        this.setWindowMotorSpeedRaw(speed);
        return;
        }else{
        speed = (getBottomSwitch()) ? 0 : speed;
        this.setWindowMotorSpeedRaw(speed);
        return;
        }
        //this.setWindowMotorSpeedRaw(speed);
    }

}