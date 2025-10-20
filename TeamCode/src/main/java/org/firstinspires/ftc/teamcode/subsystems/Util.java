package org.firstinspires.ftc.teamcode.subsystems;

public class Util {
    // returns new index with clockwise rotation
    public static int modPlusOne(int indexerSlot) {
        if (indexerSlot == 2) {
            indexerSlot = 0;
        } else {
            indexerSlot++;
        }
        return indexerSlot;
    }
    // returs new index with counter-clockwise rotation
    public static int modMinusOne(int indexerSlot) {
        if (indexerSlot == 0) {
            indexerSlot = 2;
        } else {
            indexerSlot--;
        }
        return indexerSlot;
    }
}
