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


import org.firstinspires.ftc.robotcore.internal.files.DataLogger;
import org.firstinspires.ftc.teamcode.command_factory.IndexerCommandFactory;
import org.firstinspires.ftc.teamcode.command_factory.ShooterCommandFactory;
import org.firstinspires.ftc.teamcode.commands.ArcadeDrive;
import org.firstinspires.ftc.teamcode.commands.RepeatThriceCommand;
import org.firstinspires.ftc.teamcode.commands.TurnToGoal;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Indexer;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
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
    private Limelight limelight;

    public void setSide() {
        Config.isRedAlliance = false;
    }

    public Pose startPose(){
        return new Pose(25.733, 123.2, Math.toRadians(315));
    }

    @Override
    public void initialize() {
        this.driver = new GamepadEx(this.gamepad1);
        this.indexer = new Indexer(hardwareMap, telemetry);
        this.drivetrain = Drivetrain.getInstance(hardwareMap, startPose());
        this.shooter = new Shooter(hardwareMap, telemetry);
        this.intake = new Intake(hardwareMap, telemetry);
        this.kicker = new Kicker(hardwareMap, telemetry);
        this.operator = new GamepadEx(this.gamepad2);
        this.limelight = new Limelight(hardwareMap, drivetrain);
        setSide();
        double dir = Config.isRedAlliance ? 1 : -1;
        drivetrain.setDefaultCommand(new ArcadeDrive(drivetrain,
            () -> driver.getLeftX() * dir,
            () -> driver.getLeftY() * dir,
            () -> driver.getRightX()));


        driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(intake.intakeBall())
            .whenReleased(intake.stop());



        new Trigger(intake::isBeamBroken).and(driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER))
                .whenActive(indexer.rotate120Cmd(false));

        new Trigger(intake::isIntaking).and(new Trigger(indexer::checkJam)).whenActive(
                new SequentialCommandGroup(
                        indexer.rotate120Cmd(true),
                        intake.ejectBall(),
                        new WaitCommand(300),
                        intake.intakeBall()
            )
        );

        new Trigger(intake :: checkJam).whenActive(
                new SequentialCommandGroup(
                    intake.ejectBall(),
                    indexer.rotate120Cmd(false),
                    new WaitCommand(300),
                    intake.intakeBall()

                )
        );



        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker)))
                .whenReleased(ShooterCommandFactory.resetShooter(indexer, shooter, kicker));

        new Trigger(() -> driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) >= 0.5)
            .whenActive(new RepeatThriceCommand(ShooterCommandFactory.shootArtifactFar(indexer, shooter, kicker)))
                .whenInactive(ShooterCommandFactory.resetShooter(indexer, shooter, kicker));



        operator.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(indexer.rotateRightCmd());
        operator.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(indexer.rotateLeftCmd());
        operator.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(indexer.rotate120Cmd(false));
        operator.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(indexer.rotate120Cmd(true));

        driver.getGamepadButton(GamepadKeys.Button.A).whenPressed(intake.ejectBall()).whenReleased(intake.stop());
        driver.getGamepadButton(GamepadKeys.Button.Y).whenPressed(indexer.reset());

        register(shooter, intake, kicker);
        schedule(new InstantCommand(limelight::init));
        waitForStart();
        schedule(new RunCommand(telemetry::update));
        schedule(new RunCommand(()->{
                telemetry.addData("x", drivetrain.getPosition().getX());
                telemetry.addData("y", drivetrain.getPosition().getY());
                telemetry.addData("heading", drivetrain.getPosition().getHeadingRadians());

        }

        ));

    }


}