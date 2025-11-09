package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp
public class JamTest extends OpMode {
    private DcMotorEx motor;
    @Override
    public void init() {
        this.motor = hardwareMap.get(DcMotorEx.class, "spindexerMotor");
    }

    @Override
    public void loop() {
        motor.setPower(1);
        telemetry.addData("Current", motor.getCurrent(CurrentUnit.AMPS));
    }
}
