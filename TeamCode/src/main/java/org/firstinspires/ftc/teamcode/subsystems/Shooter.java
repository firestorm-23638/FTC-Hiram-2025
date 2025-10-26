package org.firstinspires.ftc.teamcode.subsystems;


import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class Shooter extends SubsystemBase {

    private final DcMotorEx leftFlywheelMotor;
    private final DcMotorEx rightFlywheelMotor;
    private final Telemetry telemetry;

    public static final double LOAD_CURRENT = 3;
    private static final double MAX_SPEED = 4200;
    private boolean willReachTargetSpeed = false;
    private static final double ACCEPTABLE_RPM_ERROR = 50;
    private double targetRPM = 0;
    private double currentRPM = 0;
    private double lastRPM = 0;

    private long lastTime = 0;

    public Shooter(HardwareMap hardwareMap, Telemetry telemetry) {
        leftFlywheelMotor = hardwareMap.get(DcMotorEx.class, "leftShooter");
        rightFlywheelMotor = hardwareMap.get(DcMotorEx.class, "rightShooter");
        this.telemetry = telemetry;
        lastTime = System.nanoTime();
    }

    private double calculateShooterPower() {
        double speedError = this.targetRPM - getSpeed();

        return (this.targetRPM / MAX_SPEED) + (speedError * 0.0012);
    }

    @Override
    public void periodic() {
        long now = System.nanoTime();
        double dt = (now - lastTime) / 1e9;
        lastTime = now;

        currentRPM = getSpeed();
        double flywheelAccel = (currentRPM - lastRPM) / Math.max(1e3, dt);

        if (targetRPM - currentRPM >= 500) {
            setPower(MAX_SPEED);
        } else {
            double power = calculateShooterPower();
            double current = leftFlywheelMotor.getCurrent(CurrentUnit.AMPS);
            if (current > LOAD_CURRENT) {
                power = Math.min(MAX_SPEED, power * 1.1);
            }
            setPower(power);
        }

        double ROC = (currentRPM - lastRPM) / dt;

        if (currentRPM + (.5 * ROC) + ACCEPTABLE_RPM_ERROR > targetRPM) {
            willReachTargetSpeed = true;
        }

        telemetry.addData("rpm", getSpeed());
        telemetry.addData("atSpeed", (targetRPM - currentRPM) <= ACCEPTABLE_RPM_ERROR);
        telemetry.addData("loopDt", dt);
        this.lastRPM = currentRPM;
    }

    public void setPower(double power) {
        leftFlywheelMotor.setPower(power);
        rightFlywheelMotor.setPower(-power);
    }

    // ramp up to certain rpm
    public void setRPM(double targetSpeed) {
        this.targetRPM = targetSpeed;
    }

    public Command shootBall(double targetSpeed) {
        return new RunCommand(() -> setRPM(targetSpeed), this);
    }

    public Command waitUntilFast(double targetSpeed) {
        this.targetRPM = targetSpeed;
        return new WaitUntilCommand(this::checkSpeed);
    }

    public Command stopShoot() {
        return new InstantCommand(() -> setPower(0), this);
    }

    // return the current speed
    public double getSpeed() {
        return leftFlywheelMotor.getVelocity() / 28 * 60;
    }

    // check that the current speed is close to the target speed
    public boolean checkSpeed() {
        if (willReachTargetSpeed) {
            willReachTargetSpeed = false;
            return true;
        }
        return false;
    }

}
