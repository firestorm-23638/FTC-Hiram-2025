package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

//TODO: Snap to angle function
public class Drivetrain extends SubsystemBase {
    private final DcMotor frontLeft, frontRight, backLeft, backRight;
    private Position position;

    public Drivetrain(HardwareMap hw) {
        frontLeft = hw.get(DcMotor.class, "frontLeft");
        frontRight = hw.get(DcMotor.class, "frontRight");
        backLeft = hw.get(DcMotor.class, "backLeft");
        backRight = hw.get(DcMotor.class, "backRight");

        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        for (DcMotor m : new DcMotor[]{frontLeft, frontRight, backLeft, backRight}) {
            m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            m.setPower(0);
        }
    }

    public void mecanum(double x, double y, double rx) {
        double fl = -(y + x + rx);
        double bl = -(y - x + rx);
        double fr = -(y - x - rx);
        double br = -(y + x - rx);

        double denom = Math.max(1.0, Math.max(Math.abs(fl),
                Math.max(Math.abs(fr), Math.max(Math.abs(bl), Math.abs(br)))));
        frontLeft.setPower(fl / denom);
        frontRight.setPower(fr / denom);
        backLeft.setPower(bl / denom);
        backRight.setPower(br / denom);
    }

    //TODO: Finish this
    public void rotateYaw(double radians) {
        
    }

    public void stop() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public Position getPosition() {
        return this.position;
    }
}