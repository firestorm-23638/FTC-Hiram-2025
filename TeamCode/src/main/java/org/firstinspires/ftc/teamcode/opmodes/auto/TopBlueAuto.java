package org.firstinspires.ftc.teamcode.opmodes.auto;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.command_factory.ShooterCommandFactory;
import org.firstinspires.ftc.teamcode.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.commands.RepeatThriceCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Indexer;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@Autonomous

public class TopBlueAuto extends CommandOpMode {
    public Drivetrain drivetrain;
    public GamepadEx driver;
    public Shooter shooter;
    public Intake intake;
    public Kicker kicker;
    public Indexer indexer;



            public PathBuilder builder = drivetrain.getPathBuilder();

//            public PathChain scorePreload = builder
//                    .addPath(
//                            new BezierLine(new Pose(57, 135.517), new Pose(60.471, 83.311))
//                    )
//                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(135))
//                    .build();
//
//            public PathChain goToFirst = builder
//                    .addPath(new BezierLine(new Pose(60.471, 83.311), new Pose(45.46223564954683, 82.87613293051359)))
//                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
//                    .build();
//            public PathChain pickUpFirst = builder
//                    .addPath(new BezierLine(new Pose(20.012084592145015, 83.09365558912387), new Pose(16.314, 83.094)))
//                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
//                    .build();
//
//            public PathChain scoreFirst = builder
//                    .addPath(new BezierLine(new Pose(16.314, 83.094), new Pose(60.254, 83.311)))
//                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
//                    .build();
//
//            public PathChain swerveToSecond = builder
//                    .addPath(
//                            new BezierCurve(
//                                    new Pose(60.254, 83.311),
//                                    new Pose(60.036, 61.776),
//                                    new Pose(45.897, 58.731)
//                            )
//                    )
//                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
//                    .build();
//
//            public PathChain pickUpSecond = builder
//                    .addPath(new BezierLine(new Pose(45.897, 58.731), new Pose(17.837, 58.514)))
//                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
//                    .build();
//
//            public PathChain scoreSecond = builder
//                    .addPath(new BezierLine(new Pose(17.837, 58.514), new Pose(60.471, 82.876)))
//                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
//                    .build();
//
//            public PathChain swerveToThird = builder
//                    .addPath(
//                            new BezierCurve(
//                                    new Pose(60.471, 82.876),
//                                    new Pose(62.864, 38.502),
//                                    new Pose(40.024, 35.456)
//                            )
//                    )
//                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
//                    .build();
//
//            public PathChain pickUpThird = builder
//                    .addPath(new BezierLine(new Pose(40.024, 35.456), new Pose(22.622, 35.456)))
//                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
//                    .build();
//
//            public PathChain scoreThird = builder
//                    .addPath(new BezierLine(new Pose(22.622, 35.456), new Pose(59.819, 83.094)))
//                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
//                    .build();
//            public PathChain leave = builder
//                    .addPath(new BezierLine(new Pose(59.819, 83.094), new Pose(36.326, 78.308)))
//                    .setTangentHeadingInterpolation()
//                    .build();
public  PathChain scorePreload = builder
        .addPath(
                new BezierLine(new Pose(51.118, 135.517), new Pose(58.949, 82.224))
        )
        .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(135))
        .build();

    public PathChain goToFirst = builder
            .addPath(new BezierLine(new Pose(58.949, 82.224), new Pose(40.242, 84.181)))
            .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
            .build();

    public PathChain pickUpFirst = builder
            .addPath(new BezierLine(new Pose(40.242, 84.181), new Pose(12.616, 83.964)))
            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
            .build();

    public PathChain scoreFirst = builder
            .addPath(new BezierLine(new Pose(12.616, 83.964), new Pose(58.731, 82.441)))
            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
            .build();

    public PathChain goToSecond = builder
            .addPath(new BezierLine(new Pose(58.731, 82.441), new Pose(43.505, 60.254)))
            .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
            .build();

    public PathChain pickUpSecond = builder
            .addPath(new BezierLine(new Pose(43.505, 60.254), new Pose(20.882, 60.036)))
            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
            .build();

    public PathChain scoreSecond = builder
            .addPath(new BezierLine(new Pose(20.882, 60.036), new Pose(58.731, 82.441)))
            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
            .build();

    public PathChain goToThird = builder
            .addPath(new BezierLine(new Pose(58.731, 82.441), new Pose(43.287, 35.674)))
            .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
            .build();

    public PathChain pickUpThird = builder
            .addPath(new BezierLine(new Pose(43.287, 35.674), new Pose(17.837, 35.891)))
            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
            .build();

    public PathChain scoreThird = builder
            .addPath(new BezierLine(new Pose(17.837, 35.891), new Pose(58.514, 83.094)))
            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
            .build();

    public PathChain leave = builder
            .addPath(new BezierLine(new Pose(58.514, 83.094), new Pose(31.758, 83.311)))
            .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
            .build();


    @Override
    public void initialize() {
        this.driver = new GamepadEx(this.gamepad1);
        this.indexer = new Indexer(hardwareMap, telemetry);
        this.drivetrain = new Drivetrain(hardwareMap, new Pose());
        this.shooter = new Shooter(hardwareMap, telemetry);
        this.intake = new Intake(hardwareMap, telemetry);
        this.kicker = new Kicker(hardwareMap, telemetry);
        register(drivetrain, indexer, shooter, intake, kicker);
        waitForStart();
        schedule(new RunCommand(telemetry::update));

        new SequentialCommandGroup(

                //shooting first ball
                new ParallelCommandGroup(
                        new FollowPathCommand(drivetrain, scorePreload),
                        shooter.rampUp(3200)
                ),
                new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker)),


                //going to first batch
                new FollowPathCommand(drivetrain, goToFirst),


                //picking up first batch
                new ParallelCommandGroup(
                        intake.intakeBall(),
                        new FollowPathCommand(drivetrain, pickUpFirst)
                ),

                
                //going to score first batch
                new ParallelCommandGroup(
                shooter.rampUp(3200),
                new FollowPathCommand(drivetrain, scoreFirst)
                ),
                new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker)),


                //2ND BATCH IS BELOW

                //going to second
                new FollowPathCommand(drivetrain, goToSecond),


                //picking up second batch
                new ParallelCommandGroup(
                        intake.intakeBall(),
                        new FollowPathCommand(drivetrain, pickUpSecond)
                ),


                //going to score second batch
                new ParallelCommandGroup(
                        shooter.rampUp(3200),
                        new FollowPathCommand(drivetrain, scoreSecond)
                ),
                new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker)),


                //THIRD BATCH IS BELOW
                new FollowPathCommand(drivetrain, goToThird),


                //picking up third batch
                new ParallelCommandGroup(
                        intake.intakeBall(),
                        new FollowPathCommand(drivetrain, pickUpThird)
                ),


                //going to score third batch
                new ParallelCommandGroup(
                        shooter.rampUp(3200),
                        new FollowPathCommand(drivetrain, scoreThird)
                ),
                new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker)),


                //LEAVING BELOW
                new FollowPathCommand(drivetrain, leave)
                );





    }



}
