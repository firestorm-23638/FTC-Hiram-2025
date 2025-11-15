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
        return new Pose(25.733, 123.2, Math.toRadians(315));
    }


    protected void buildPaths(Drivetrain drivetrain) {

        int pickUpAng = 180;
        int shootAng = 132;

        scanObelisk = drivetrain.getPathBuilder()
                .addPath(
                        new BezierLine(new Pose(25.733, 123.2), new Pose(57.7, 94.1))
                )
                .setLinearHeadingInterpolation(Math.toRadians(315), Math.toRadians(50.145))
                .build();

        scorePreload = drivetrain.getPathBuilder()
                .addPath(
                        new BezierLine(new Pose(57.7, 94.1), new Pose(60.5, 86.6))
                )
                .setLinearHeadingInterpolation(Math.toRadians(50.145), Math.toRadians(shootAng))
                .build();
//                .addPath(
//                        new BezierLine(new Pose(56.12084592145015, 135.51661631419938), new Pose(58.949, 82.224))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
//                .build();




        goToFirst = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(60.5, 86.6), new Pose(48, 83.86)))
                .setLinearHeadingInterpolation(Math.toRadians(shootAng), Math.toRadians(pickUpAng))
                .build();

        pickUpFirst = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(48, 83.86), new Pose(19, 83.86)))
                .setLinearHeadingInterpolation(Math.toRadians(pickUpAng), Math.toRadians(pickUpAng))
                .build();

        scoreFirst = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(19, 83.86), new Pose(60.5, 86.6)))
                .setLinearHeadingInterpolation(Math.toRadians(pickUpAng), Math.toRadians(shootAng+6))
                .build();

        goToSecond = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(60.5, 86.6), new Pose(45.8, 60)))
                .setLinearHeadingInterpolation(Math.toRadians(shootAng+6), Math.toRadians(pickUpAng))
                .setBrakingStrength(0.5)
                .setBrakingStart(2)
                .build();

        pickUpSecond = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(45.8, 60), new Pose(19, 60)))
                .setLinearHeadingInterpolation(Math.toRadians(pickUpAng), Math.toRadians(pickUpAng))
                .build();

        scoreSecond = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(19, 60), new Pose(60.5, 86.6)))
                .setLinearHeadingInterpolation(Math.toRadians(pickUpAng), Math.toRadians(shootAng+6))
                .build();

        goToThird = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(60.5, 86.6), new Pose(45.8, 37)))
                .setLinearHeadingInterpolation(Math.toRadians(shootAng+6), Math.toRadians(pickUpAng))
                .build();

        pickUpThird = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(45.8, 37), new Pose(22, 37)))
                .setLinearHeadingInterpolation(Math.toRadians(pickUpAng), Math.toRadians(pickUpAng))
                .build();

        scoreThird = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(22, 37), new Pose(60.5, 86.6)))
                .setLinearHeadingInterpolation(Math.toRadians(pickUpAng), Math.toRadians(shootAng+3))
                .build();

        leave = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(60.5, 86.6), new Pose(50, 54)))
                .setLinearHeadingInterpolation(Math.toRadians(shootAng+3), Math.toRadians(pickUpAng))
                .build();
    }

    public double getTargetSpeed(){
        return 2900;
    }

    @Override
    public void initialize() {
        this.indexer = new Indexer(hardwareMap, telemetry);
        this.drivetrain = Drivetrain.getInstance(hardwareMap, getStartingPose(), true);
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

        new Trigger(intake::isBeamBroken).and(new Trigger(intake::isIntaking))
                .whenActive(indexer.rotate120Cmd(false));

        CommandBase auto = new SequentialCommandGroup(
                new InstantCommand(limelight::init),
                //shooting first ball
                indexer.setSlotColor("GPP"),
                new ParallelCommandGroup(
                        new FollowPathCommand(drivetrain, scanObelisk),
                        shooter.rampUp(getTargetSpeed()),
                        new WaitUntilCommand(() -> limelight.readObelisk() != null)
                                .withTimeout(2000)
                                .andThen(indexer.goToBestStartingLocationCmd())
                ),

                new WaitCommand(200),
                intake.ejectBall(),
                new FollowPathCommand(drivetrain, scorePreload),
                new WaitCommand(200),
                new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker, getTargetSpeed())),


                //going to first batch
                new ParallelCommandGroup(
                        new FollowPathCommand(drivetrain, goToFirst),
                        indexer.goToSlotCmd(2)
                ),


                //picking up first batch
                new ParallelCommandGroup(
                        intake.intakeBall(),
                        new SlowFollowPath(drivetrain, pickUpFirst, 0.3)
                ),

                new ParallelCommandGroup(
                        indexer.setSlotColor("GPP"),
                        ShooterCommandFactory.revUpForAuto(indexer, shooter, kicker)
                ),

                //going to score first batch
                new ParallelCommandGroup(
//                        shooter.rampUp(targetSpeed),
                        intake.ejectBall(),
                        indexer.goToBestStartingLocationCmd(),
                        new FollowPathCommand(drivetrain, scoreFirst)
                ),

                new ParallelCommandGroup(
                intake.stop(),
                new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker, getTargetSpeed()))

                ),


                //2ND BATCH IS BELOW

                //going to second
                new ParallelCommandGroup(
                        new FollowPathCommand(drivetrain, goToSecond),
                        indexer.goToSlotCmd(2)
                ),


                //picking up second batch
                new ParallelCommandGroup(
                        intake.intakeBall(),
                        new SlowFollowPath(drivetrain, pickUpSecond, 0.27)
                ),

                new ParallelCommandGroup(
                ShooterCommandFactory.revUpForAuto(indexer, shooter, kicker),
                indexer.setSlotColor("PGP")
                ),

                //going to score second batch
                new ParallelCommandGroup(
                        indexer.goToBestStartingLocationCmd(),
//                        shooter.rampUp(targetSpeed),
                        new FollowPathCommand(drivetrain, scoreSecond),
                        intake.ejectBall()
                ),
                new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker, getTargetSpeed())),


                //THIRD BATCH IS BELOW
                new ParallelCommandGroup(
                intake.stop(),
                new FollowPathCommand(drivetrain, goToThird)),


                //picking up third batch
                new ParallelCommandGroup(
                        intake.intakeBall(),
                        new SlowFollowPath(drivetrain, pickUpThird, 0.3)
                )

                //going to score third batch
//                new ParallelCommandGroup(
//                        ShooterCommandFactory.revUpForAuto(indexer, shooter, kicker),
//                        shooter.rampUp(targetSpeed),
//                        new FollowPathCommand(drivetrain, scoreThird)
//                ),
//                new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker, getTargetSpeed())),


                //LEAVING BELOW
//                new FollowPathCommand(drivetrain, leave)
        );

        schedule(auto);


    }


}
