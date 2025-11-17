package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Motor {
    private int currentTick;
    private int targetTick;
    private final DcMotorEx motor;
    public final float encoderResolution;
    public final double kP;
    public final double kD;

    private long lastTime = 0;
    private double lastError = 0;
    private double errors = 0;
    private final JamChecker jamChecker;
    private final Debouncer debouncer;

    public Motor(DcMotorEx motor, float encoderResolution, double kP, double kD) {
        this.motor = motor;
        this.encoderResolution = encoderResolution;
        this.kP = kP;
        this.kD = kD;
        lastTime = System.nanoTime();
        jamChecker = new JamChecker(motor, 3, 0.1f);;
        debouncer = new Debouncer(500, true);
    }

    // Returns true if the motor is busy
    public boolean isBusy() {
        return motor.isBusy();
    }

    // Rotates toward the target position
    public long rotate() {
        long now = System.nanoTime();
        long dt = (long) ((now - lastTime) / 1e9);
        lastTime = now;

        currentTick = -motor.getCurrentPosition();
        double err =  currentTick - targetTick;
        double velError = -this.getVelocity() - 0;

        double power = -(kP * err) - (kD * velError);

        motor.setPower(power + Math.signum(power)*0.01);
        return dt;
    }

    public void clearErrors() {
        errors = 0;
    }

    public void setTargetPosition(int targetTick){
        this.targetTick = targetTick;
    }

    public void rotateToAngle(double angle, double ratio, boolean absolute) {
        int calculatedTick = (int) ((angle / 360) * encoderResolution * ratio);
        if (absolute) {
            rotateToTick(calculatedTick);
        } else {
            double relativeTick = motor.getCurrentPosition() % encoderResolution;
            rotateToTick((int) (relativeTick + calculatedTick));
        }
    }
    public void rotateToTick(int tick) {
        targetTick = tick;
    }

    public void reset(){
        targetTick = 0;
        currentTick = 0;
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public boolean checkJam(){
        return debouncer.update(jamChecker.isJammed());
    }

    /**
     *
     * @return int (the number of revolutions the motor has made)
     */
    public int getRevolutions() {
        return (int) (-motor.getCurrentPosition() / encoderResolution);
    }

    public int getCurrentTick(){
        return this.currentTick;
    }
    public int getTargetTick(){
        return  this.targetTick;
    }

    public double getVelocity() {
        return motor.getVelocity();
    }
}