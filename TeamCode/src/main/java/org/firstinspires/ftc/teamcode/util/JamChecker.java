package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class JamChecker {
    private final DcMotorEx motor;
    private final double STALL_CURRENT;
    private final float TOLERANCE;
    /**
     *
     * @param motor The motor to check for jams
     * @param stallCurrent The stall current of the motor
     * @param tolerance How close to the stall current the motor should be to be considered jammed
     */
    public JamChecker(DcMotorEx motor, double stallCurrent, float tolerance) {
        this.motor = motor;
        this.STALL_CURRENT = stallCurrent;
        this.TOLERANCE = tolerance;
    }

    /**
     *
     * @param motor The motor to check for jams
     * @param stallCurrent The stall current of the motor
     */
    public JamChecker(DcMotorEx motor, double stallCurrent) {
        this(motor, stallCurrent, 0.1f);
    }

    /**
     * Returns true if the motor is within the tolerance of the stall current
     * @return boolean
     */
    public boolean isJammed() {
        return motor.getCurrent(CurrentUnit.AMPS) >= (STALL_CURRENT - TOLERANCE);
    }
}
