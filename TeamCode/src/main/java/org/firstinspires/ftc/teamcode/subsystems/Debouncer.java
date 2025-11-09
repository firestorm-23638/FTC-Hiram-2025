package org.firstinspires.ftc.teamcode.subsystems;

public class Debouncer {
    private final int desiredTimeMS;
    private long lastTime = 0;
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
        if(newValue != desiredState)
            lastTime = System.nanoTime();
        double timeAtDesired = (double)(System.nanoTime() - lastTime)/1e6;
        if (timeAtDesired >= desiredTimeMS) {
            return desiredState;
        }


        return !desiredState;
    }

    }

