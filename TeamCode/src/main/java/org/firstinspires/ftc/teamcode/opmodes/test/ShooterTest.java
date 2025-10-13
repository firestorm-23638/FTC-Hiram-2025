package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp(name = "Shooter Test", group = "Tests")

public class ShooterTest extends CommandOpMode{
    private GamepadEx driver;
    private Shooter shooter;

    @Override
    public void initialize() {
        this.driver = new GamepadEx(gamepad1);
        this.shooter = new Shooter(hardwareMap, telemetry);
        this.driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(shooter.shootBall(390))
                .whenReleased(shooter.stopShoot());

        register(shooter);

        schedule(new RunCommand(telemetry ::update));
    }
}
