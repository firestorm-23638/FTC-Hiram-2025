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

    private double makeXRed(double x) {
        return 144 - x;
    }

    private double makeAngRed(double x) {
        return 180 - x;
    }
    @Override
    public Pose startPose(){
        return new Pose(115.109402086, 135.240909836, 225);

    }
}
