package org.firstinspires.ftc.teamcode.opmodes.auto;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.command_factory.ShooterCommandFactory;
import org.firstinspires.ftc.teamcode.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.commands.RepeatThriceCommand;
import org.firstinspires.ftc.teamcode.commands.SlowFollowPath;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Indexer;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@Autonomous(name = "Top Red Auto", group = "Auto", preselectTeleOp = "Red TeleOp")
public class TopRedAuto extends TopBlueAuto {

    private double makeXRed(double x) {
        return 144 - x;
    }

    private double makeAngRed(double x) {
        return 180 - x;
    }
    @Override
    protected Pose getStartingPose() {
        return new Pose(makeXRed(23.233), 128.991, Math.toRadians(makeAngRed(-37)));
    }

    @Override
    protected void buildPaths(Drivetrain drivetrain) {
        /* build paths here */

        scanObelisk = drivetrain.getPathBuilder()
                .addPath(
                        new BezierLine(new Pose( makeXRed(23.233), 128.991), new Pose(makeXRed(55.631), 92.203))
                )
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(-37)), Math.toRadians(makeAngRed(70)))
                .build();

        scorePreload = drivetrain.getPathBuilder()
                .addPath(
                        new BezierLine(new Pose(makeXRed(55.631), 92.203), new Pose(makeXRed(59), 94.619))
                )
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(70)), Math.toRadians(makeAngRed(133)))
                .build();
//                .addPath(
//                        new BezierLine(new Pose(56.12084592145015, 135.51661631419938), new Pose(58.949, 82.224))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
//                .build();

        int angdeg = 183;
        goToFirst = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(makeXRed(59), 94.619), new Pose(makeXRed(51), 85)))
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(133)), Math.toRadians(makeAngRed(angdeg)))
                .build();

        pickUpFirst = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(makeXRed(51), 89), new Pose(makeXRed(27), 85)))
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(angdeg)), Math.toRadians(makeAngRed(angdeg)))
                .build();

        scoreFirst = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(makeXRed(22), 83), new Pose(makeXRed(57), 94.619)))
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(angdeg)), Math.toRadians(makeAngRed(133)))
                .build();

        goToSecond = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(makeXRed(57), 94.619), new Pose(makeXRed(58.242), 61)))
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(133)), (Math.toRadians(makeAngRed(angdeg))))
                .setBrakingStrength(0.5)
                .setBrakingStart(2)
                .build();

        pickUpSecond = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(makeXRed(58.242), 61), new Pose(makeXRed(28), 61)))
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(angdeg)), Math.toRadians(makeAngRed(angdeg)))
                .build();

        scoreSecond = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(makeXRed(28), 61), new Pose(makeXRed(57), 94.619)))
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(angdeg)), Math.toRadians(makeAngRed(145)))
                .build();

        goToThird = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(makeXRed(57), 94.619), new Pose(makeXRed(58), 37)))
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(145)), Math.toRadians(makeAngRed(angdeg)))
                .build();

        pickUpThird = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(makeXRed(58), 37), new Pose(makeXRed(28), 37)))
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(angdeg)), Math.toRadians(makeAngRed(angdeg)))
                .build();

        scoreThird = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(makeXRed(28), 37), new Pose(makeXRed(57), 94.619)))
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(177)), Math.toRadians(makeAngRed(145)))
                .build();

        leave = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(makeXRed(57), 94.619), new Pose(makeXRed(50), 54)))
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(145)), Math.toRadians(makeAngRed(angdeg)))
                .build();
    }

    @Override
    public void initialize() {
        this.indexer = new Indexer(hardwareMap, telemetry);
        this.drivetrain = new Drivetrain(hardwareMap, getStartingPose());
        this.shooter = new Shooter(hardwareMap, telemetry);
        this.intake = new Intake(hardwareMap, telemetry);
        this.kicker = new Kicker(hardwareMap, telemetry);
        this.limelight = new Limelight(hardwareMap, drivetrain);
        register(drivetrain, indexer, shooter, intake, kicker);

        buildPaths(drivetrain);
        waitForStart();
        schedule(new RunCommand(telemetry::update));
        schedule(new RunCommand(() -> {
            telemetry.addData("driveX:", drivetrain.getPosition().getX());
            telemetry.addData("driveY:", drivetrain.getPosition().getY());
        })) ;
        int targetSpeed = 2900;

        new Trigger(intake::isBeamBroken).and(new Trigger(intake::isIntaking))
                .whenActive(indexer.rotate120Cmd(false));

        CommandBase auto = new SequentialCommandGroup(
                new InstantCommand(limelight::init),
                //shooting first ball
                indexer.setSlotColor("GPP"),
                new ParallelCommandGroup(
                        new FollowPathCommand(drivetrain, scanObelisk),
                        shooter.rampUp(targetSpeed),
                        new WaitUntilCommand(() -> limelight.readObelisk() != null)
                                .withTimeout(2000)
                                .andThen(indexer.goToBestStartingLocationCmd())
                ),

                new WaitCommand(200),
                new FollowPathCommand(drivetrain, scorePreload),
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
                        new SlowFollowPath(drivetrain, pickUpFirst, 0.35)
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
                        new SlowFollowPath(drivetrain, pickUpSecond, 0.3)
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
                        new SlowFollowPath(drivetrain, pickUpThird, 0.4)
                ),

                //going to score third batch
//                new ParallelCommandGroup(
//                        shooter.rampUp(targetSpeed),
//                        new FollowPathCommand(drivetrain, scoreThird)
//                ),
//                new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker)),


                //LEAVING BELOW
                new FollowPathCommand(drivetrain, leave)
        );

        schedule(auto);


    }
}
