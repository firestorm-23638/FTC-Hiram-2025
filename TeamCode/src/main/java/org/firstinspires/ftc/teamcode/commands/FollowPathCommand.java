package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

public class FollowPathCommand extends CommandBase {
    private Drivetrain drivetrain;
    private PathChain path;

    public FollowPathCommand(Drivetrain drivetrain, PathChain path) {
        this.drivetrain = drivetrain;
        this.path = path;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        drivetrain.followPath(path);
    }

    @Override
    public boolean isFinished() {
        return !drivetrain.followerIsBusy();
    }
}
