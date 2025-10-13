package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp(name = "TeleOp")
public class Teleop extends CommandOpMode {
    private Drivetrain drivetrain;
    private GamepadEx driver;
    private Shooter shooter;
    private Intake intake;
    @Override
    public void initialize() {
        this.driver = new GamepadEx(this.gamepad1);
        this.drivetrain = new Drivetrain(hardwareMap, new Pose());
        this.shooter = new Shooter(hardwareMap, telemetry);
        this.intake = new Intake(hardwareMap);
        
        register(drivetrain, shooter, intake);
    }

}