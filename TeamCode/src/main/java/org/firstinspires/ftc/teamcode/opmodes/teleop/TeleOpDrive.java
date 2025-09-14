package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

@TeleOp(name = "TeleOp Drive", group = "Drive")
public class TeleOpDrive extends LinearOpMode {

    @Override
    public void runOpMode() {
        Drivetrain drive = new Drivetrain(hardwareMap);

        telemetry.addLine("Initialized... starting");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            double y  = -gamepad1.left_stick_y;
            double x  = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            if (gamepad1.right_bumper) {
                y *= 0.4; x *= 0.4; rx *= 0.4;
            }

            drive.mecanum(x, y, rx);

            telemetry.addData("y", "%.2f", y);
            telemetry.addData("x", "%.2f", x);
            telemetry.addData("rx", "%.2f", rx);
            telemetry.update();
        }
    }
}