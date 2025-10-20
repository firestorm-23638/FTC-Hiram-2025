package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RepeatCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.command_factory.IndexerCommandFactory;
import org.firstinspires.ftc.teamcode.control.controlboards.DriverControlBoard;
import org.firstinspires.ftc.teamcode.control.controlboards.IDriverControlBoard;
import org.firstinspires.ftc.teamcode.subsystems.Indexer;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@TeleOp(name = "Intake Test", group = "Tests")
public class IntakeTest extends CommandOpMode {
    private GamepadEx driver;
    private Intake intake;
    private Indexer indexer;
    @Override
    public void initialize() {
        this.intake = new Intake(hardwareMap, telemetry);
        driver = new GamepadEx(gamepad1);
        this.indexer = new Indexer(hardwareMap, telemetry);
        // while the want intake button is held, do this command until either
        // the command finishes or the button is released.
        this.driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed((new RepeatCommand(IndexerCommandFactory.intakeArtifact(intake, indexer))                                                                                                                                                                                                                                                                                                                                                                                                                                                        ))
                .whenReleased(intake.stop());



        register(intake, indexer);
        schedule(new RunCommand(telemetry ::update));
    }


}
