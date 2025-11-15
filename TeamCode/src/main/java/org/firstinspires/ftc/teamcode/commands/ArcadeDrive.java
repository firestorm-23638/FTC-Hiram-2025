package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

import java.util.function.DoubleSupplier;

public class ArcadeDrive extends CommandBase {
    private Drivetrain drivetrain;
    private DoubleSupplier x,y,rx;

    public ArcadeDrive(Drivetrain drivetrain,
                       DoubleSupplier x,
                       DoubleSupplier y,
                       DoubleSupplier rx) {

        this.drivetrain = drivetrain;
        this.x = x;
        this.y = y;
        this.rx = rx;
        addRequirements(this.drivetrain);
    }
    @Override
    public void initialize() {
        drivetrain.startTeleOpDrive();
        drivetrain.setMax(1);
    }

    @Override
    public void execute() {
        drivetrain.updateTeleOpDrive(-x.getAsDouble(), y.getAsDouble(), -rx.getAsDouble());
    }

    @Override
    public boolean isFinished() {
        return super.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}

