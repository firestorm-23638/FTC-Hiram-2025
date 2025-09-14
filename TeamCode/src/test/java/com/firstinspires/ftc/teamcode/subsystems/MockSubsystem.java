package com.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.mockito.Mock;

public class MockSubsystem extends SubsystemBase {
    private Servo servo;

    public MockSubsystem(HardwareMap map) {
        this.servo = map.get(Servo.class, "servo");
    }


    public void setPos(double pos) {
        this.servo.setPosition(pos);
    }


    public Command goToPos(double pos) {
        if (pos > 180)
            return new InstantCommand(() -> setPos(180));

        if (pos < 0)
            return new InstantCommand(() -> setPos(0));

        return new InstantCommand(() -> setPos(180));
    }


}
