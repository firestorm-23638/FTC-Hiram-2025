package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LimitSwitch {
    private DigitalChannel limitSwitch;
    private Telemetry telemetry;
    public LimitSwitch(DigitalChannel limitSwitch, Telemetry telemetry) {
        this.limitSwitch = limitSwitch;
        this.telemetry = telemetry;
        limitSwitch.setMode(DigitalChannel.Mode.INPUT);
    }

    public boolean isZero() {
        telemetry.addData("ZERO_SWITCH", "TRUE");
        return limitSwitch.getState();
    }
}
