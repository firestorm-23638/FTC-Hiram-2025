package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.util.Config;

import java.util.function.DoubleSupplier;

public class TurnToGoal extends CommandBase {
    private Drivetrain drivetrain;
    private DoubleSupplier x,y;
    private Limelight limelight;

    public TurnToGoal(Drivetrain drivetrain,
                       Limelight limelight,
                       DoubleSupplier x,
                       DoubleSupplier y
                       ) {

        this.drivetrain = drivetrain;
        this.x = x;
        this.y = y;
        this.limelight = limelight;

        addRequirements(this.drivetrain);
    }
    @Override
    public void initialize() {
        drivetrain.startTeleOpDrive();
    }

    @Override
    public void execute() {
        drivetrain.updateTeleOpDrive(-x.getAsDouble(), y.getAsDouble(), -limelight.getGoalAngle(Config.isRedAlliance)/80d);
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
