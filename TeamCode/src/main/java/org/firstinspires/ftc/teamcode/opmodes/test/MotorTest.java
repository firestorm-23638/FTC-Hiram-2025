package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp
public class MotorTest extends LinearOpMode {
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");


        while(!opModeIsActive()){}

        while(opModeIsActive()) {
            if (gamepad1.x) {
                frontLeft.setPower(1);
            } else {
                frontLeft.setPower(0);
            }

            if (gamepad1.a) {
                backLeft.setPower(1);
            } else {
                backLeft.setPower(0);
            }

            if (gamepad1.y) {
                frontRight.setPower(1);
            } else {
                frontRight.setPower(0);
            }

            if (gamepad1.b) {
                backRight.setPower(1);
            } else {
                backRight.setPower(0);
            }


        }


    }


}
