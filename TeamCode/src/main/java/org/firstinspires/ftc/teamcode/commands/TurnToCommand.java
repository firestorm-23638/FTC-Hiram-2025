package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

public class TurnToCommand extends CommandBase {
    private double radians;
    private Drivetrain drivetrain;

    public TurnToCommand(double radians, Drivetrain drivetrain) {
        this.radians = radians;
        this.drivetrain = drivetrain;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        drivetrain.rotateTo(radians);
    }

    @Override
    public boolean isFinished() {
        return !drivetrain.followerIsBusy();
    }
}
