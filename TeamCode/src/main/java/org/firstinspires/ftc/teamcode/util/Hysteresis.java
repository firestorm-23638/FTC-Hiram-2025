package org.firstinspires.ftc.teamcode.util;

public class Hysteresis {
    private final int hysteriaTimeMS;
    private double lastTimeExpectedMS;
    private final boolean desiredState;

    public Hysteresis(int hysteriaTimeMS, boolean desiredState) {
        this.hysteriaTimeMS = hysteriaTimeMS;
        // Prevent bug where if the state updates at the beginning where update always returns desiredState
        this.lastTimeExpectedMS = -(hysteriaTimeMS + 1);
        this.desiredState = desiredState;
    }

    /**
     * Returns true if the desired state remains true for the hysteria time
     * @param newState boolean
     * @return boolean
     */
    public boolean update(boolean newState) {
        double currentTimeMS = System.nanoTime() / 1e6;

        if (newState == desiredState) {
            lastTimeExpectedMS = currentTimeMS;
            return desiredState;
        }

        if ((currentTimeMS - lastTimeExpectedMS) > hysteriaTimeMS) {
            return !desiredState;
        }
        return desiredState;
    }
}
