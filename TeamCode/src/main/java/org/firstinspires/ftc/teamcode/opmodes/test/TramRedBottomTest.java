package org.firstinspires.ftc.teamcode.opmodes.test;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

@TeleOp
public class TramRedBottomTest extends CommandOpMode {
    private Drivetrain drivetrain;
    protected Pose getStartingPose() {
        return new Pose(83.7275, 12.9886, Math.toRadians(90));
    }

    @Override
    public void initialize() {
        this.drivetrain = new Drivetrain(hardwareMap, getStartingPose());
        register(drivetrain);

        schedule(new RunCommand(()->{
            telemetry.addData("x", drivetrain.getPosition().getX());
            telemetry.addData("y", drivetrain.getPosition().getY());
            telemetry.addData("heading", drivetrain.getPosition().getHeadingRadians());

            telemetry.addData("xOffset", 104.37293233082707-drivetrain.getPosition().getX());
            telemetry.addData("yOffset", 32.91428571428571-drivetrain.getPosition().getY());
            telemetry.addData("headingOffset", Math.toRadians(0)-(drivetrain.getPosition().getHeadingRadians()));
            telemetry.update();
        }));

    }
}
