package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class IndexerVelocityTest extends OpMode {
    private DcMotorEx spindexerMotor;
    @Override
    public void init() {
        spindexerMotor = hardwareMap.get(DcMotorEx.class, "spindexerMotor");
    }

    @Override
    public void loop() {
        double pow = gamepad1.left_trigger;
        spindexerMotor.setPower(-pow);
        telemetry.addData("Power", pow);
        telemetry.update();
    }
}
