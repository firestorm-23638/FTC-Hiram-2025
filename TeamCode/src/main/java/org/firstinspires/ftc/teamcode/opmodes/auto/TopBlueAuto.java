package org.firstinspires.ftc.teamcode.opmodes.auto;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
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
import org.firstinspires.ftc.teamcode.commands.SlowFollowPath;
import org.firstinspires.ftc.teamcode.commands.TurnToCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Indexer;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@Autonomous(name = "Top Blue Auto", group = "Auto", preselectTeleOp = "Blue TeleOp")
public class TopBlueAuto extends CommandOpMode {
    protected Drivetrain drivetrain;
    protected Shooter shooter;
    protected Intake intake;
    protected Kicker kicker;
    protected Indexer indexer;
    protected Limelight limelight;

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
    protected PathChain scanObelisk;

    private double makeRed(double x) {
        return 144 - x;
    }

    protected Pose getStartingPose() {
        return new Pose(23.233, 128.991, Math.toRadians(-37));
    }


    protected void buildPaths(Drivetrain drivetrain) {

//        scanObelisk = drivetrain.getPathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(51.118, 135.517), new Pose(48.278, 97.945))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(-37), Math.toRadians(70))
//                .build();
//        scanObelisk = drivetrain.getPathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(48.278, 97.945), new Pose(58.949, 82.224))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(70), Math.toRadians(135))
//                .build();
//
//        scorePreload = drivetrain.getPathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(58.949, 82.224), new Pose(40.242, 84.181))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
//                .build();
//
//        goToFirst = drivetrain.getPathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(40.242, 84.181), new Pose(12.616, 83.964))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
//                .build();
//
//        pickUpFirst = drivetrain.getPathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(12.616, 83.964), new Pose(58.731, 82.441))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
//                .build();
//
//        scoreFirst = drivetrain.getPathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(58.731, 82.441), new Pose(43.505, 60.254))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
//                .build();
//
//        goToSecond = drivetrain.getPathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(43.505, 60.254), new Pose(20.882, 60.036))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
//                .build();
//
//        pickUpSecond = drivetrain.getPathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(20.882, 60.036), new Pose(58.731, 82.441))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
//                .build();
//
//        scoreSecond = drivetrain.getPathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(58.731, 82.441), new Pose(43.287, 35.674))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
//                .build();
//
//        goToThird = drivetrain.getPathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(43.287, 35.674), new Pose(17.837, 35.891))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
//                .build();
//
//        pickUpThird = drivetrain.getPathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(17.837, 35.891), new Pose(58.514, 83.094))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
//                .build();
//
//        leave = drivetrain.getPathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(58.514, 83.094), new Pose(31.758, 83.311))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
//                .build();
//    }
        scanObelisk = drivetrain.getPathBuilder()
                .addPath(
                        new BezierLine(new Pose(23.233, 128.991), new Pose(55.631, 92.203))
                )
                .setLinearHeadingInterpolation(Math.toRadians(-37), Math.toRadians(70))
                .build();

        scorePreload = drivetrain.getPathBuilder()
                .addPath(
                        new BezierLine(new Pose(55.631, 92.203), new Pose(63.471, 94.619))
                )
                .setLinearHeadingInterpolation(Math.toRadians(70), Math.toRadians(145))
                .build();
//                .addPath(
//                        new BezierLine(new Pose(56.12084592145015, 135.51661631419938), new Pose(58.949, 82.224))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
//                .build();

        int angdeg = 183;
        goToFirst = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(63.471, 94.619), new Pose(54, 92)))
                .setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(angdeg))
                .build();

        pickUpFirst = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(54, 92), new Pose(30, 92)))
                .setLinearHeadingInterpolation(Math.toRadians(angdeg), Math.toRadians(angdeg))
                .build();

        scoreFirst = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(30, 92), new Pose(63.471, 94.619)))
                .setLinearHeadingInterpolation(Math.toRadians(angdeg), Math.toRadians(145))
                .build();

        goToSecond = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(63.471, 94.619), new Pose(58.242, 67)))
                .setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(angdeg))
                .setBrakingStrength(0.5)
                .setBrakingStart(2)
                .build();

        pickUpSecond = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(58.242, 67), new Pose(30, 67)))
                .setLinearHeadingInterpolation(Math.toRadians(angdeg), Math.toRadians(angdeg))
                .build();

        scoreSecond = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(30, 67), new Pose(63.471, 94.619)))
                .setLinearHeadingInterpolation(Math.toRadians(angdeg), Math.toRadians(145))
                .build();

        goToThird = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(63.471, 94.619), new Pose(58, 43)))
                .setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(angdeg))
                .build();

        pickUpThird = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(58, 43), new Pose(30, 43)))
                .setLinearHeadingInterpolation(Math.toRadians(angdeg), Math.toRadians(angdeg))
                .build();

        scoreThird = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(30, 43), new Pose(63.471, 94.619)))
                .setLinearHeadingInterpolation(Math.toRadians(177), Math.toRadians(145))
                .build();

        leave = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(63.471, 94.619), new Pose(50, 54)))
                .setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(angdeg))
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
                        new SlowFollowPath(drivetrain, pickUpFirst, 0.25)
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
