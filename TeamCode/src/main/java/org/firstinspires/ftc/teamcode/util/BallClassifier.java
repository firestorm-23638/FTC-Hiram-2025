package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Arrays;

public class BallClassifier {
    private final AnalogInput[] inputs = new AnalogInput[4];
    private static final double greenVoltage = 41;
    private static final double purpleVoltage = 67;

    int tolerance = 10;

    /**
     * Creates a new BallClassifier object
     * @param hw HardwareMap
     */
    public BallClassifier(HardwareMap hw) {
        for (int i = 0; i < 4; i++) {
            inputs[i] = hw.get(AnalogInput.class, "ballSensor" + i);
        }
    }

    /**
     * Returns the median color of the ball
     * @return double (the median voltage of the analog sensors)
     */
    public double getMedian() {
        double[] voltages = new double[4];
        for (int i = 0; i < 4; i++) {
            voltages[i] = inputs[i].getVoltage();
        }
        Arrays.sort(voltages);
        return (voltages[1] + voltages[2]) / 2;
    }

    public String getClosestColor(int voltage) {
        double deltaGreen = Math.abs(greenVoltage - voltage);
        double deltaPurple = Math.abs(purpleVoltage - voltage);
        if (deltaPurple > tolerance && deltaGreen > tolerance) {
            // bad color tolerance
            return "unknown";
        } else if (deltaGreen < deltaPurple) {
            // voltage closer to green
            return "green";
        } else {
            // voltage closer to purple
            return "purple";
        }
    }
}
