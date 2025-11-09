package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp(name = "Shooter Velocity Test", group = "Velocity Tests")
public class ShooterVelocityTest extends CommandOpMode {
    private Shooter shooter;
    @Override
    public void initialize() {
        this.shooter = new Shooter(hardwareMap, telemetry);


        register(shooter);
        waitForStart();

        schedule(shooter.shootBall(3100));
        schedule(new RunCommand(telemetry::update));
    }
}
