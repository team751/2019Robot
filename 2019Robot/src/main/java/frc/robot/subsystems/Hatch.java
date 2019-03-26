package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.commands.Hatch.HatchDefault;
import frc.robot.commands.Hatch.SetBottom;
import frc.robot.commands.Hatch.SetTop;

//ansa made this component :)
public class Hatch extends Subsystem {

    private Servo servo;
    private DigitalInput backSwitch;
    private DigitalInput topSwitch;
    private DigitalInput bottomSwitch;
    private PWMVictorSPX windowMotor;
    public boolean isUp;

    //Theese angles are wrong but like we cant really fix that because they dont give us testing time


    public Hatch() {
        this.servo = Robot.oi.hatchServo;
        this.windowMotor = Robot.oi.hatchWindowMotor;
        this.backSwitch = Robot.oi.hatchBackSwitch;
        this.topSwitch = Robot.oi.hatchTopSwitch;
        this.bottomSwitch = Robot.oi.hatchBottomSwitch;
        this.isUp = true;
        SmartDashboard.putNumber("Up", 0);
        SmartDashboard.putNumber("Down", 127);
        SmartDashboard.putData("Set Top", new SetTop());
        SmartDashboard.putData("Set Bottom", new SetBottom());
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new HatchDefault());
    }

    protected void teleopPeriodic() {

    }

    public void setAngle(int state) {
        this.servo.setAngle(state);
    }

    public void turnUp() {
        this.setAngle((int)SmartDashboard.getNumber("Up", 0));
    }

    public void turnDown() {
        this.setAngle((int)SmartDashboard.getNumber("Down", 127));
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
            //speed = (getTopSwitch()) ? 0 : speed;
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