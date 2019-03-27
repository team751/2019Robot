package frc.robot.commands.Hatch;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Stow extends CommandGroup {

    public Stow(double speed) {
        this.addSequential(new SetSeatAngle(0));
        this.addSequential(new ToBottom(speed));
    }
}