package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake extends SubsystemBase {
    private DcMotor intakeRoller;
    private DigitalChannel beamBreak;

    public Intake(HardwareMap hMap){
        intakeRoller = hMap.get(DcMotor.class, "intakeRoller");
        beamBreak = hMap.get(DigitalChannel.class, "beamBreak");
    }
    public CommandBase stop(){
        return new InstantCommand(()->intakeRoller.setPower(0));
    }

    public CommandBase intakeBall(){
        return new RunCommand(()->intakeRoller.setPower(1));
    }

    public CommandBase ejectBall(){
        return new RunCommand(()->intakeRoller.setPower(-1));
    }

    public boolean isBeamBroken(){
        return beamBreak.getState();
    }

    public CommandBase waitUntilArtifactInjested() {
        return new WaitUntilCommand(this::isBeamBroken);
    }

}
