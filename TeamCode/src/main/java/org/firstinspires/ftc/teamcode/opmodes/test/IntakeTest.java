package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.controlboards.DriverControlBoard;
import org.firstinspires.ftc.teamcode.control.controlboards.IDriverControlBoard;

@TeleOp(name = "Intake Test", group = "Tests")
public class IntakeTest extends CommandOpMode {
    private IDriverControlBoard driver;

    @Override
    public void initialize() {
        this.driver = new DriverControlBoard(new GamepadEx(gamepad1));


        // while the want intake button is held, do this command until either
        // the command finishes or the button is released.
        this.driver.wantIntakeArtifact().whileActiveOnce(new WaitCommand(200));
    }


}
