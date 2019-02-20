package frc.robot.commands.Hatch;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Stow extends CommandGroup {

    public Stow(double speed) {
        this.addSequential(new Intake());
        this.addSequential(new ToBottom(speed));
    }
}