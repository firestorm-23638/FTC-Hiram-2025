package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Kicker;

@TeleOp(name = "Kicker Test", group = "Tests")
public class KickerTest extends CommandOpMode {
    private Kicker kicker;
    private GamepadEx driver;
    @Override
    public void initialize() {
        this.kicker = new Kicker(hardwareMap, telemetry);
        this.driver = new GamepadEx(gamepad1);
        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
                kicker.pushBall()
        ).whenReleased(kicker.retract());

        register(kicker);
    }




}
