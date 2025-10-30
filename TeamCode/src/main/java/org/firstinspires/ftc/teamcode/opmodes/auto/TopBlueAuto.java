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

            public PathChain scorePreload = builder
                    .addPath(
                            new BezierLine(new Pose(51.118, 135.517), new Pose(60.471, 83.311))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(-90), Math.toRadians(135))
                    .build();

            public PathChain pickUpFirst = builder
                    .addPath(new BezierLine(new Pose(60.471, 83.311), new Pose(16.314, 83.094)))
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                    .build();

            public PathChain scoreFirst = builder
                    .addPath(new BezierLine(new Pose(16.314, 83.094), new Pose(60.254, 83.311)))
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .build();

            public PathChain swerveToSecond = builder
                    .addPath(
                            new BezierCurve(
                                    new Pose(60.254, 83.311),
                                    new Pose(60.036, 61.776),
                                    new Pose(45.897, 58.731)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                    .build();

            public PathChain pickUpSecond = builder
                    .addPath(new BezierLine(new Pose(45.897, 58.731), new Pose(17.837, 58.514)))
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                    .build();

            public PathChain scoreSecond = builder
                    .addPath(new BezierLine(new Pose(17.837, 58.514), new Pose(60.471, 82.876)))
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .build();

            public PathChain swerveToThird = builder
                    .addPath(
                            new BezierCurve(
                                    new Pose(60.471, 82.876),
                                    new Pose(62.864, 38.502),
                                    new Pose(40.024, 35.456)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                    .build();

            public PathChain pickUpThird = builder
                    .addPath(new BezierLine(new Pose(40.024, 35.456), new Pose(22.622, 35.456)))
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                    .build();

            public PathChain scoreThird = builder
                    .addPath(new BezierLine(new Pose(22.622, 35.456), new Pose(59.819, 83.094)))
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .build();
            public PathChain leave = builder
                    .addPath(new BezierLine(new Pose(59.819, 83.094), new Pose(36.326, 78.308)))
                    .setTangentHeadingInterpolation()
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

                new ParallelCommandGroup(
                        new FollowPathCommand(drivetrain, scorePreload),
                        shooter.rampUp(3200)
                ),
                new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker)),
                //shooting first ball


                new ParallelCommandGroup(
                        intake.intakeBall(),
                        new FollowPathCommand(drivetrain, pickUpFirst)
                        //picking up first batch
                ),

                new ParallelCommandGroup(
                shooter.rampUp(3200),
                new FollowPathCommand(drivetrain, scoreFirst)
                //going to score first batch
                ),
                new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker)),

                //2ND BATCH IS BELOW
                new ParallelCommandGroup(
                        intake.intakeBall(),
                        new FollowPathCommand(drivetrain, pickUpSecond)
                        //picking up second batch
                ),

                new ParallelCommandGroup(
                        shooter.rampUp(3200),
                        new FollowPathCommand(drivetrain, scoreSecond)
                        //going to score second batch
                ),
                new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker)),

                //THIRD BATCH IS BELOW

                new ParallelCommandGroup(
                        intake.intakeBall(),
                        new FollowPathCommand(drivetrain, pickUpFirst)
                        //picking up third batch
                ),

                new ParallelCommandGroup(
                        shooter.rampUp(3200),
                        new FollowPathCommand(drivetrain, scoreFirst)
                        //going to score third batch
                ),
                new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker)),


                //LEAVING BELOW
                new FollowPathCommand(drivetrain, leave)
                );





    }



}
