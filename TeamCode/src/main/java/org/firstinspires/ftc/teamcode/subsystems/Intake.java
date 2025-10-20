package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake extends SubsystemBase {
    private DcMotor intakeRoller;
    private DigitalChannel beamBreak;
    private Telemetry telemetry;

    public Intake(HardwareMap hMap, Telemetry telemetry) {
        intakeRoller = hMap.get(DcMotor.class, "intakeRoller");
        intakeRoller.setDirection(DcMotorSimple.Direction.REVERSE);
        beamBreak = hMap.digitalChannel.get("beamBreak");
        this.telemetry = telemetry;
    }

    public CommandBase stop() {
        return new InstantCommand(() -> intakeRoller.setPower(0), this);
    }

    public CommandBase intakeBall() {
        return new InstantCommand(() -> intakeRoller.setPower(1), this);
    }

    public CommandBase ejectBall() {
        return new InstantCommand(() -> intakeRoller.setPower(-1), this);
    }

    public boolean isBeamBroken() {
        return beamBreak.getState();
    }

    @Override
    public void periodic() {
        super.periodic();
        telemetry.addData("isBeamBroken", isBeamBroken());
    }

    public CommandBase waitUntilArtifactInjested() {
        return new WaitUntilCommand(this::isBeamBroken);
    }

}
