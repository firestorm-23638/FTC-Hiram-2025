package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.command_factory.ShooterCommandFactory;
import org.firstinspires.ftc.teamcode.commands.RepeatThriceCommand;
import org.firstinspires.ftc.teamcode.subsystems.Aimer;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Indexer;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

import java.util.concurrent.atomic.AtomicBoolean;

@TeleOp
public class TramBlueSideTest extends CommandOpMode {
    private Drivetrain drivetrain;
    protected Pose getStartingPose() {
        return new Pose(25.733, 123.2, Math.toRadians(315));
    }
    private Shooter shooter;
    private double currentRPM;
    private Aimer aimer;

    @Override
    public void initialize() {
        this.drivetrain = new Drivetrain(hardwareMap, getStartingPose());
        this.shooter = new Shooter(hardwareMap, telemetry);
        this.currentRPM = 3100;
        this.aimer = new Aimer(drivetrain, true);
        register(drivetrain, shooter);

        AtomicBoolean lastDpadDown = new AtomicBoolean(false);
        AtomicBoolean lastDpadUp = new AtomicBoolean(false);

        schedule(new RunCommand(()->{
//            shooter.setRPM(currentRPM);
            if (!lastDpadUp.get() && gamepad1.dpad_up){
                currentRPM += 50;
            }else if (!lastDpadDown.get() && gamepad1.dpad_down){
                currentRPM -= 50;
            }
            lastDpadUp.set(gamepad1.dpad_up);
            lastDpadDown.set(gamepad1.dpad_down);

            // update rpm
            if (gamepad1.dpad_right){
                shooter.setRPM(currentRPM);
            }

            // kill motor
            if (gamepad1.dpad_left){
                shooter.setRPM(0);
            }

            telemetry.addData("x", drivetrain.getPosition().getX());
            telemetry.addData("y", drivetrain.getPosition().getY());
            telemetry.addData("heading", drivetrain.getPosition().getHeadingRadians());

            telemetry.addData("xOffset", 40.02416918429003-drivetrain.getPosition().getX());
            telemetry.addData("yOffset", 80.4833836858006-drivetrain.getPosition().getY());
            telemetry.addData("headingOffset", Math.toRadians(180)-(drivetrain.getPosition().getHeadingRadians()));

            telemetry.addData("distance", aimer.getDistance(drivetrain.getPosition().getVect2()));
            telemetry.update();
        }));

    }






}
