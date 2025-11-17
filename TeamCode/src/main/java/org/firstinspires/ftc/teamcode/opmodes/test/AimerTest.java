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
import org.firstinspires.ftc.teamcode.util.LaunchParams;

@TeleOp(name = "Aimer Test", group = "Tests")
public class AimerTest extends CommandOpMode {
    private GamepadEx driver;
    private Drivetrain drivetrain;
    private Aimer aimer;

    @Override
    public void initialize() {
        this.driver = new GamepadEx(gamepad1);
        this.drivetrain = new Drivetrain(hardwareMap, new Pose());
        this.aimer = new Aimer(drivetrain, true);

        this.driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(
                        new RunCommand(
                                () -> {
                                    LaunchParams params = aimer.aim();
                                    double yawRadians = params.yawRadians;
                                    drivetrain.rotateTo(yawRadians);
                                }
                        )
                );

        schedule(new RunCommand(()->{
            telemetry.addData("heading", drivetrain.getPosition().getHeadingDegrees());
            telemetry.update();
        }));
    }

}
