package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.teamcode.command_factory.IndexerCommandFactory;
import org.firstinspires.ftc.teamcode.command_factory.ShooterCommandFactory;
import org.firstinspires.ftc.teamcode.commands.ArcadeDrive;
import org.firstinspires.ftc.teamcode.commands.RepeatThriceCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Indexer;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.util.Config;

@TeleOp(name = "Blue TeleOp")
public class BlueTeleop extends CommandOpMode {
    private Drivetrain drivetrain;
    private GamepadEx driver;
    private Shooter shooter;
    private Intake intake;
    private Kicker kicker;
    private Indexer indexer;
    private GamepadEx operator;

    public void setSide() {
        Config.isRedAlliance = false;
    }


    @Override
    public void initialize() {
        this.driver = new GamepadEx(this.gamepad1);
        this.indexer = new Indexer(hardwareMap, telemetry);
        this.drivetrain = Drivetrain.getInstance(hardwareMap, new Pose(0,0, Math.toRadians(90)));
        this.shooter = new Shooter(hardwareMap, telemetry);
        this.intake = new Intake(hardwareMap, telemetry);
        this.kicker = new Kicker(hardwareMap, telemetry);
        this.operator = new GamepadEx(this.gamepad2);

        setSide();
        drivetrain.setDefaultCommand(new ArcadeDrive(drivetrain,
            driver::getLeftX,
            driver::getLeftY,
            driver::getRightX));


        driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(intake.intakeBall())
            .whenReleased(intake.stop());



        new Trigger(intake::isBeamBroken).and(driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER))
                .whenActive(indexer.rotate120Cmd(false));


        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
            .whenPressed(new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker)))
            .whenReleased(ShooterCommandFactory.resetShooter(indexer, shooter, kicker));

        new Trigger(() -> driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) >= 0.5)
            .whenActive(new RepeatThriceCommand(ShooterCommandFactory.shootArtifactFar(indexer, shooter, kicker)))
                .whenInactive(ShooterCommandFactory.resetShooter(indexer, shooter, kicker));


        operator.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(indexer.rotateRightCmd());
        operator.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(indexer.rotateLeftCmd());
        operator.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(indexer.rotate120Cmd(false));
        operator.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(indexer.rotate120Cmd(true));

        driver.getGamepadButton(GamepadKeys.Button.A).whenPressed(intake.ejectBall()).whenReleased(intake.stop());
        driver.getGamepadButton(GamepadKeys.Button.Y).whenPressed(indexer.reset());

        register(shooter, intake, kicker);
        schedule(new RunCommand(telemetry::update));
    }


}