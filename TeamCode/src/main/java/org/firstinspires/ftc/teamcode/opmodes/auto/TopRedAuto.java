package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
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
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@Autonomous
public class TopRedAuto extends TopBlueAuto {

    private Drivetrain drivetrain;
    private Shooter shooter;
    private Intake intake;
    private Kicker kicker;
    private Indexer indexer;
    private Limelight limelight;


    private PathBuilder builder = drivetrain.getPathBuilder();

    protected PathChain scorePreload;
    protected PathChain goToFirst;
    protected PathChain pickUpFirst;
    protected PathChain scoreFirst;
    protected PathChain goToSecond;
    protected PathChain pickUpSecond;
    protected PathChain scoreSecond;
    protected PathChain goToThird;
    protected PathChain pickUpThird;
    protected PathChain scoreThird;
    protected PathChain leave;
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
                    .build();

            scoreThird  = drivetrain.getPathBuilder()
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

    @Override
    public void initialize() {
        this.indexer = new Indexer(hardwareMap, telemetry);
        this.drivetrain = new Drivetrain(hardwareMap, new Pose());
        this.shooter = new Shooter(hardwareMap, telemetry);
        this.intake = new Intake(hardwareMap, telemetry);
        this.kicker = new Kicker(hardwareMap, telemetry);
        this.limelight = new Limelight(hardwareMap, drivetrain);
        register(drivetrain, indexer, shooter, intake, kicker);

        buildPaths(drivetrain);
        waitForStart();
        schedule(new RunCommand(telemetry::update));
        int targetSpeed = 3200;

        new SequentialCommandGroup(
                new InstantCommand(limelight::init),

                //shooting first ball
                new ParallelCommandGroup(
                        new FollowPathCommand(drivetrain, scorePreload),
                        shooter.rampUp(targetSpeed),
                        new WaitUntilCommand(() -> limelight.readObelisk() != null)
                                .andThen(indexer.goToBestStartingLocationCmd())
                ),
                indexer.nearTarget(),
                new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker)),


                //going to first batch
                new ParallelCommandGroup(
                        new FollowPathCommand(drivetrain, goToFirst),
                        indexer.goToSlotCmd(2)
                ),



                //picking up first batch
                new ParallelCommandGroup(
                        intake.intakeBall(),
                        new FollowPathCommand(drivetrain, pickUpFirst)
                ),
                indexer.setSlotColor("GPP"),

                //going to score first batch
                new ParallelCommandGroup(
                        shooter.rampUp(targetSpeed),
                        indexer.goToBestStartingLocationCmd(),
                        new FollowPathCommand(drivetrain, scoreFirst)
                ),
                new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker)),


                //2ND BATCH IS BELOW

                //going to second
                new ParallelCommandGroup(
                        new FollowPathCommand(drivetrain, goToSecond),
                        indexer.goToSlotCmd(2)
                ),



                //picking up second batch
                new ParallelCommandGroup(
                        intake.intakeBall(),
                        new FollowPathCommand(drivetrain, pickUpSecond)
                ),
                indexer.setSlotColor("PGP"),

                //going to score second batch
                new ParallelCommandGroup(
                        indexer.goToBestStartingLocationCmd(),
                        shooter.rampUp(targetSpeed),
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
                        shooter.rampUp(targetSpeed),
                        new FollowPathCommand(drivetrain, scoreThird)
                ),
                new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker)),


                //LEAVING BELOW
                new FollowPathCommand(drivetrain, leave)
        );


    }
}
