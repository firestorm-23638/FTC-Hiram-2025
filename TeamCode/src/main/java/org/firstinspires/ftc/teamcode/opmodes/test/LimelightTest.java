package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.Patterns;

@TeleOp(name = "Limelight Test", group = "Tests")
public class LimelightTest extends OpMode {
    Limelight limelight;

    @Override
    public void init() {
        limelight = new Limelight(hardwareMap, null);
        limelight.init();
    }

    @Override
    public void loop() {
        Patterns patterns = limelight.readObelisk();

        if (patterns != null)
            telemetry.addData("Motif", patterns.name());

        telemetry.update();

    }
}
