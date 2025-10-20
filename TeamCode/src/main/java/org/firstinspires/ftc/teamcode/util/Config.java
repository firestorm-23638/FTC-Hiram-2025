package org.firstinspires.ftc.teamcode.util;

import org.firstinspires.ftc.teamcode.subsystems.Patterns;

public class Config {
    public static Patterns CURRENT_PATTERN = null;

    public static char[] getMotif() {
        if(CURRENT_PATTERN == Patterns.GPP){
            return new char[]{'G', 'P', 'P'};
        }
        if(CURRENT_PATTERN == Patterns.PGP){
            return new char[]{'P', 'G', 'P'};
        }
        return new char[]{'P', 'P', 'G'};

    }
}

