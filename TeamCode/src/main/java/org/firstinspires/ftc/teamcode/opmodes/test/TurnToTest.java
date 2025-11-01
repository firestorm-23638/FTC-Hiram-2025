package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.commands.ArcadeDrive;
import org.firstinspires.ftc.teamcode.commands.TurnToCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
@TeleOp(name = "Turn Test", group = "Tests")

public class TurnToTest extends CommandOpMode {
    private GamepadEx driver;
    private Drivetrain drivetrain;
    @Override
    public void initialize() {
        this.driver = new GamepadEx(gamepad1);
        this.drivetrain = new Drivetrain(hardwareMap, new Pose(0, 0, 0));

        register(drivetrain);

        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new TurnToCommand(Math.toRadians(45), drivetrain));
        driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(new TurnToCommand(Math.toRadians(135), drivetrain));

    }

}
