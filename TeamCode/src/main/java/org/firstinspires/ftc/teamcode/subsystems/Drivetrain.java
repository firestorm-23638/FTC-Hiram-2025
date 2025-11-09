package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.util.Config;
import org.firstinspires.ftc.teamcode.util.Position;

//TODO: Snap to angle function
public class Drivetrain extends SubsystemBase {
    private final Follower follower;
    private Position position;
    private static final double kp = 0.035;
    private static Drivetrain drivetrain;

    public static Drivetrain getInstance(HardwareMap hardwareMap, Pose pose) {
        if (drivetrain == null) {
            drivetrain = new Drivetrain(hardwareMap, pose);
        }

        return drivetrain;
    }

    public Drivetrain(HardwareMap hw, Pose pose) {
        follower = Constants.createFollower(hw);
        follower.setStartingPose(pose);

    }

    @Override
    public void periodic() {
        follower.update();
        Pose pose = follower.getPose();
        this.position = new Position(pose.getX(), pose.getY(), pose.getHeading());
    }

    public void updateTeleOpDrive(double x, double y, double rx) {
        follower.setTeleOpDrive(y,x,rx, false);
    }
    public void startTeleOpDrive(){
        follower.startTeleopDrive();
    }

    //TODO: Finish this
    public void rotateYaw(double radians) {
        this.follower.turn(radians, true);
    }

    public void rotateTo(double radians) {
        this.follower.turnTo(radians);
    }

    public void setMax(double max) {
        this.follower.setMaxPower(max);
    }


    public Position getPosition() {
        return this.position;
    }

    public void followPath(PathChain path) {
        follower.followPath(path, true);
    }

    public boolean followerIsBusy() {
        return follower.isBusy();
    }

    public PathBuilder getPathBuilder() {
        return new PathBuilder(this.follower);
    }

    public Command turnToGoal(Limelight limelight) {
        return new ParallelRaceGroup(
            new RunCommand(() -> updateTeleOpDrive(0, 0, -limelight.getGoalAngle(Config.isRedAlliance) * kp)),
            new WaitUntilCommand(() -> Math.abs(limelight.getGoalAngle(Config.isRedAlliance)) < 5)
        );
    }
}