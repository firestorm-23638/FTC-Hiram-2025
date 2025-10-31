package org.firstinspires.ftc.teamcode.opmodes.auto;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

@Autonomous(name = "Top Red Auto", group = "Auto", preselectTeleOp = "Red TeleOp")
public class TopRedAuto extends TopBlueAuto {

    @Override
    protected void buildPaths(Drivetrain drivetrain) {
        /* build paths here */

        scorePreload = drivetrain.getPathBuilder()
            .addPath(
                new BezierLine(new Pose(87.227, 135.299), new Pose(85.269, 85.269))
            )
            .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(45))
            .build();

        goToFirst = drivetrain.getPathBuilder()
            .addPath(
                new BezierLine(new Pose(85.269, 85.269), new Pose(101.366, 83.529))
            )
            .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))
            .build();

        pickUpFirst = drivetrain.getPathBuilder()
            .addPath(
                new BezierLine(new Pose(101.366, 83.529), new Pose(127.251, 83.311))
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .setBrakingStrength(0.75)
            .build();

        scoreFirst = drivetrain.getPathBuilder()
            .addPath(
                new BezierLine(new Pose(127.251, 83.311), new Pose(85.269, 85.269))
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
            .build();

        goToSecond = drivetrain.getPathBuilder()
            .addPath(
                new BezierLine(new Pose(85.269, 85.269), new Pose(101.366, 59.601))
            )
            .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))
            .build();

        pickUpSecond = drivetrain.getPathBuilder()
            .addPath(
                new BezierLine(new Pose(101.366, 59.601), new Pose(125.293, 59.601))
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .setBrakingStrength(0.75)
            .build();

        scoreSecond = drivetrain.getPathBuilder()
            .addPath(
                new BezierLine(new Pose(125.293, 59.601), new Pose(85.269, 85.486))
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
            .build();

        goToThird = drivetrain.getPathBuilder()
            .addPath(new BezierLine(new Pose(85.269, 85.486), new Pose(98.973, 35.021)))
            .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))
            .build();

        pickUpThird = drivetrain.getPathBuilder()
            .addPath(
                new BezierLine(new Pose(98.973, 35.021), new Pose(122.900, 35.239))
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .setBrakingStrength(0.75)
            .build();

        scoreThird = drivetrain.getPathBuilder()
            .addPath(
                new BezierLine(new Pose(122.900, 35.239), new Pose(85.051, 85.486))
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
            .build();

        leave = drivetrain.getPathBuilder()
            .addPath(
                new BezierLine(new Pose(85.051, 85.486), new Pose(125.946, 97.450))
            )
            .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(180))
            .build();

    }
}
