package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Turret;

@TeleOp(name = "Turret Test", group = "Tests")
public class TurretTest extends CommandOpMode {
    private Turret turret;
    private GamepadEx operator;

    private double angle = 0;
    @Override
    public void initialize() {
        this.turret = new Turret(hardwareMap, telemetry);
        this.operator = new GamepadEx(this.gamepad1);

        turret.setAngle(0);

        operator.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(this::decreaseAngle);
        operator.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(this::increaseAngle);

        schedule(new RunCommand(() -> {
            telemetry.addData("Angle", angle);
            telemetry.update();
        }));
    }

    public void increaseAngle() {
        angle += 5;
        turret.setAngle(angle);
    }
    public void decreaseAngle() {
        angle -= 5;
        turret.setAngle(angle);
    }

}
