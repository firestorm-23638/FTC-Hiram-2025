package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Kicker extends SubsystemBase {
    private Servo kicker;
    private Telemetry telemetry;
    public Kicker(HardwareMap hMap, Telemetry telemetry){
        this.kicker = hMap.get(Servo.class, "kicker");
    }
    public Command pushBall(){
        return new RunCommand(() ->kicker.setPosition(0.15), this).withTimeout(1000);
    }
    public Command retract(){
        return new RunCommand(() ->kicker.setPosition(0.01), this).withTimeout(250);
    }
}
