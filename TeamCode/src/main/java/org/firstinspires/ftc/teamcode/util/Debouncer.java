package org.firstinspires.ftc.teamcode.util;

public class Debouncer {
    private final int desiredTimeMS;
    private double lastTimeNotInDesiredMS = 0;
    private final boolean desiredState;
    public Debouncer(int desiredTimeMS, boolean desiredState) {
        this.desiredTimeMS = desiredTimeMS;
        this.desiredState = desiredState;
    }


    /**
     * Returns true if the desired state is is consistent throughout the desired time
     * @param newValue boolean
     * @return boolean
     */
    public boolean update(boolean newValue) {
//        totalTimeMS = (newValue == desiredState) ? totalTimeMS + deltaTime : 0;
        double currentTimeMS = System.nanoTime() / 1e6;

        if(newValue != desiredState) {
            lastTimeNotInDesiredMS = currentTimeMS;
        }

        double timeAtDesired = (currentTimeMS - lastTimeNotInDesiredMS);

        if (timeAtDesired >= desiredTimeMS) {
            return desiredState;
        }
        return !desiredState;
    }
}

