package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

//TODO: Snap to angle function
public class Drivetrain extends SubsystemBase {
    private final Follower follower;
    private Position position;

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
        
    }


    public Position getPosition() {
        return this.position;
    }
}