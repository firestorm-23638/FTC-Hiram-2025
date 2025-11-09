package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.Debouncer;
import org.firstinspires.ftc.teamcode.util.JamChecker;

public class Intake extends SubsystemBase {
    private DcMotorEx intakeRoller;
    private DigitalChannel beamBreak;
    private Telemetry telemetry;
    private boolean isIntaking = false;
    private Debouncer debouncer;
    private JamChecker jamChecker;

    public Intake(HardwareMap hMap, Telemetry telemetry) {
        intakeRoller = hMap.get(DcMotorEx.class, "intakeRoller");
        intakeRoller.setDirection(DcMotorSimple.Direction.REVERSE);
        beamBreak = hMap.digitalChannel.get("beamBreak");
        this.telemetry = telemetry;
        debouncer = new Debouncer(500, true);
        jamChecker = new JamChecker(intakeRoller, 3);
    }

    public CommandBase stop() {
        return new InstantCommand(() -> intakeRoller.setPower(0), this);
    }

    public CommandBase intakeBall() {
        return new InstantCommand(() -> {
            intakeRoller.setPower(1);
            isIntaking = true;
        }, this);
    }

    public CommandBase ejectBall() {
        return new InstantCommand(() -> {
            intakeRoller.setPower(-1);
            isIntaking = false; }, this);
    }

    public boolean checkJam(){
        return debouncer.update(jamChecker.isJammed());
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

    public boolean isIntaking() {
        return isIntaking;
    }
}
