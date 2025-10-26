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

    private final DcMotorEx flywheelMotor;
    private final Telemetry telemetry;

    public static final double LOAD_CURRENT = 3;
    private static final double MAX_SPEED = 4200;
    private boolean willReachTargetSSpeed = false;
    private static final double ACCEPTABLE_RPM_ERROR = 50;
    private double targetRPM = 0;
    private double currentRPM = 0;
    private double lastRPM = 0;

    private long lastTime = 0;

    public Shooter(HardwareMap hardwareMap, Telemetry telemetry) {
        flywheelMotor = hardwareMap.get(DcMotorEx.class, "shooter");
        this.telemetry = telemetry;
        lastTime = System.nanoTime();
    }

    private double calculateShooterPower(double targetRPM, double currentRPM) {
        double speedError = this.targetRPM - getSpeed();

        return (this.targetRPM / MAX_SPEED) + (speedError * 0.0012);
    }

    @Override
    public void periodic() {
        long now = System.nanoTime();
        double dt = (now - lastTime) / 1e9;
        lastTime = now;

        this.currentRPM = getSpeed();
        double flywheelAccel = (currentRPM - lastRPM) / Math.max(1e3, dt);

        if (targetRPM - currentRPM >= 500) {
            flywheelMotor.setPower(MAX_SPEED);
        } else {
            double power = calculateShooterPower(targetRPM, this.currentRPM);
            double current = flywheelMotor.getCurrent(CurrentUnit.AMPS);
            if (current > LOAD_CURRENT) {
                power = Math.min(MAX_SPEED, power * 1.1);
            }
            flywheelMotor.setPower(power);
        }

        double ROC = (currentRPM - lastRPM) / dt;

        if (currentRPM + (.5 * ROC) + ACCEPTABLE_RPM_ERROR > targetRPM) {
            willReachTargetSSpeed = true;
        }

        telemetry.addData("rpm", getSpeed());
        telemetry.addData("atSpeed", checkSpeed(targetRPM));
        telemetry.addData("loopDt", dt);
        this.lastRPM = currentRPM;
    }

    // ramp up to certain rpm
    public void setRPM(double targetSpeed) {
        this.targetRPM = targetSpeed;
    }

    public Command shootBall(double targetSpeed) {
        return new RunCommand(() -> setRPM(targetSpeed), this);
    }

    public Command waitUntilFast(double targetSpeed) {
        return new WaitUntilCommand(() -> checkSpeed(targetSpeed));
    }

    public Command stopShoot() {
        return new InstantCommand(() -> flywheelMotor.setPower(0), this);
    }

    // return the current speed
    public double getSpeed() {
        return flywheelMotor.getVelocity() / 28 * 60;
    }

    // check that the current speed is close to the target speed
    public boolean checkSpeed(double targetSpeed) {
        if (willReachTargetSSpeed) {
            willReachTargetSSpeed = false;
            return true;
        }
        return false;
    }

}
