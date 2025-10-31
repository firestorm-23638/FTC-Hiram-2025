package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "Shooter Velocity Test", group = "Velocity Tests")
public class ShooterVelocityTest extends OpMode {
    private DcMotorEx leftFlywheelMotor;
    private DcMotorEx rightFlywheelMotor;


    @Override
    public void init() {
        leftFlywheelMotor = hardwareMap.get(DcMotorEx.class, "leftShooter");
        rightFlywheelMotor = hardwareMap.get(DcMotorEx.class, "rightShooter");
    }

    @Override
    public void loop() {
        leftFlywheelMotor.setPower(1);
        rightFlywheelMotor.setPower(-1);

        telemetry.addData("Left RPM", leftFlywheelMotor.getVelocity() / 28 * 60);
        telemetry.update();
    }
}
