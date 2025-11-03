package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

@TeleOp
public class TramBlueSideTest extends CommandOpMode {
    private Drivetrain drivetrain;
    protected Pose getStartingPose() {
        return new Pose(25.733, 123.2, Math.toRadians(315));
    }

    @Override
    public void initialize() {
        this.drivetrain = new Drivetrain(hardwareMap, getStartingPose());
        register(drivetrain);

        schedule(new RunCommand(()->{
            telemetry.addData("x", drivetrain.getPosition().getX());
            telemetry.addData("y", drivetrain.getPosition().getY());
            telemetry.addData("heading", drivetrain.getPosition().getHeadingRadians());

            telemetry.addData("xOffset", 40.02416918429003-drivetrain.getPosition().getX());
            telemetry.addData("yOffset", 80.4833836858006-drivetrain.getPosition().getY());
            telemetry.addData("headingOffset", Math.toRadians(180)-(drivetrain.getPosition().getHeadingRadians()));
            telemetry.update();
        }));

    }






}
