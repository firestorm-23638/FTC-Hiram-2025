package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

@TeleOp
public class TramRedSideTest extends CommandOpMode {
    private Drivetrain drivetrain;
    protected Pose getStartingPose() {
        return new Pose(115.109402086, 135.240909836, Math.toRadians(225));
    }

    @Override
    public void initialize() {
        this.drivetrain = new Drivetrain(hardwareMap, getStartingPose());
        register(drivetrain);

        schedule(new RunCommand(()->{
            telemetry.addData("x", drivetrain.getPosition().getX());
            telemetry.addData("y", drivetrain.getPosition().getY());
            telemetry.addData("heading", drivetrain.getPosition().getHeadingRadians());

            telemetry.addData("xOffset", 103.9758308157-drivetrain.getPosition().getX());
            telemetry.addData("yOffset", 80.4833836858006-drivetrain.getPosition().getY());
            telemetry.addData("headingOffset", Math.toRadians(0)-(drivetrain.getPosition().getHeadingRadians()));
            telemetry.update();
        }));
    }
}
