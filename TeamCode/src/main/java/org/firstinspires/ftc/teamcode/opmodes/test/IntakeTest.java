package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.controlboards.DriverControlBoard;
import org.firstinspires.ftc.teamcode.control.controlboards.IDriverControlBoard;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@TeleOp(name = "Intake Test", group = "Tests")
public class IntakeTest extends CommandOpMode {
    private IDriverControlBoard driver;
    private Intake intake;
    @Override
    public void initialize() {
        this.driver = new DriverControlBoard(new GamepadEx(gamepad1));

        // while the want intake button is held, do this command until either
        // the command finishes or the button is released.
        this.driver.wantIntakeArtifact()
                .whileActiveOnce(intake.intakeBall())
                .negate()
                .whileActiveOnce(intake.stop());


        this.driver.wantToEjectArtifact().whileActiveOnce(intake.ejectBall());

        register(intake);

    }


}
