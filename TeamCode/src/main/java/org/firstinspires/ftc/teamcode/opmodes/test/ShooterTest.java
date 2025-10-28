package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RepeatCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.command_factory.ShooterCommandFactory;
import org.firstinspires.ftc.teamcode.commands.RepeatThriceCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Indexer;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp(name = "Shooter Test", group = "Tests")

public class ShooterTest extends CommandOpMode {
    private GamepadEx driver;
    private Shooter shooter;
    private Kicker kicker;
    private Indexer indexer;

    @Override
    public void initialize() {
        this.driver = new GamepadEx(gamepad1);
        this.shooter = new Shooter(hardwareMap, telemetry);
        this.kicker = new Kicker(hardwareMap, telemetry);
        this.indexer = new Indexer(hardwareMap, telemetry);


        this.driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
            .whenPressed(new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker)))
            .whenReleased(ShooterCommandFactory.resetShooter(indexer, shooter, kicker));


        register(shooter, kicker, indexer);

        schedule(new RunCommand(telemetry::update));
    }
}
