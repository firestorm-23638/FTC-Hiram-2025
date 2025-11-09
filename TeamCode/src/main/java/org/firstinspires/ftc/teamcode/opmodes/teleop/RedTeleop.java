package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Config;

@TeleOp(name = "Red TeleOp")
public class RedTeleop extends BlueTeleop {

    @Override
    public void setSide() {
        Config.isRedAlliance = true;
    }

    @Override
    public Pose startPose(){
        return new Pose(0, 0, Math.toRadians(180));

    }
}
