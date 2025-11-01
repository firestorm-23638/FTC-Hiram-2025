package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

public class SlowFollowPath extends FollowPathCommand {
    private double speed;
    public SlowFollowPath(Drivetrain drivetrain, PathChain path, double speed) {
        super(drivetrain, path);
        this.speed = speed;
    }

    @Override
    public void initialize() {
        this.drivetrain.setMax(speed);
        super.initialize();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        this.drivetrain.setMax(1);
    }
}
