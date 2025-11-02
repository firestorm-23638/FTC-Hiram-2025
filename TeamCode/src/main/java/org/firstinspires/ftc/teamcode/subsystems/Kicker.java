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

    private Command setPos(int millis, double pos) {
        return new RunCommand(() -> kicker.setPosition(pos), this).withTimeout(millis);
    }

    public Command pushBall(int millis) {
        return setPos(millis, 0.22);
    }

    public Command retract(int millis) {
        return setPos(millis, 0.01);
    }

    public Command pushBall(){
        return pushBall(400);
    }
    public Command retract(){
        return retract(200);
    }


}
