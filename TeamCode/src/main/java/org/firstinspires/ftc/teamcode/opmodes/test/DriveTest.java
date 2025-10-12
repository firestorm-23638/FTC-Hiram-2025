package org.firstinspires.ftc.teamcode.opmodes.test;

import android.app.GameManager;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ArcadeDrive;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

@TeleOp(name = "Drive Test", group = "Tests")
public class DriveTest extends CommandOpMode {
    private GamepadEx driver;
    private Drivetrain drivetrain;

    @Override
    public void initialize() {
        this.driver = new GamepadEx(gamepad1);
        this.drivetrain = new Drivetrain(hardwareMap, new Pose());

        drivetrain.setDefaultCommand(new ArcadeDrive(drivetrain,
                driver::getLeftX,
                driver::getLeftY,
                driver::getRightX));
    }

}
