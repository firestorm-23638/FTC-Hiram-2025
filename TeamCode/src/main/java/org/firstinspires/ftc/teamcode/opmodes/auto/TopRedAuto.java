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
        return new Pose(115.109402086, 135.240909836, Math.toRadians(225));
    }

    public double getTargetSpeed(){
        return 2950;
    }

    @Override
    protected void buildPaths(Drivetrain drivetrain) {
        /* build paths here */
        int shootAng = 132;
        int pickUpAng = 180;
        double shootX = 92;
        double shootY = 94.;

        scanObelisk = drivetrain.getPathBuilder()
                .addPath(
                        new BezierLine(new Pose( makeXRed(25.733), 123.2), new Pose(96.58, 114))
                )
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(315)), Math.toRadians(makeAngRed(50.145)))
                .build();

        scorePreload = drivetrain.getPathBuilder()
                .addPath(
                        new BezierLine(new Pose(96.58, 114), new Pose(shootX, shootY))
                )
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(50.145)), Math.toRadians(makeAngRed(shootAng)))
                .build();
//                .addPath(
//                        new BezierLine(new Pose(56.12084592145015, 135.51661631419938), new Pose(58.949, 82.224))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
//                .build();

        goToFirst = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(shootX, shootY), new Pose(makeXRed(48), 83.86)))
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(133)), Math.toRadians(makeAngRed(pickUpAng)))
                .build();

        pickUpFirst = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(makeXRed(48), 83.86), new Pose(makeXRed(19), 83.86)))
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(pickUpAng)), Math.toRadians(makeAngRed(pickUpAng)))
                .build();

        scoreFirst = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(makeXRed(19), 83.86), new Pose(shootX, shootY)))
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(pickUpAng)), Math.toRadians(makeAngRed(shootAng+6)))
                .build();

        goToSecond = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(shootX, shootY), new Pose(makeXRed(45.8), 60)))
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(shootAng+6)), (Math.toRadians(makeAngRed(pickUpAng))))
                .setBrakingStrength(0.5)
                .setBrakingStart(2)
                .build();

        pickUpSecond = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(makeXRed(45.8), 60), new Pose(makeXRed(19), 60)))
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(pickUpAng)), Math.toRadians(makeAngRed(pickUpAng)))
                .build();

        scoreSecond = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(makeXRed(19), 60), new Pose(shootX, shootY)))
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(pickUpAng)), Math.toRadians(makeAngRed(shootAng+6)))
                .build();

        goToThird = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(shootX, shootY), new Pose(makeXRed(45.8), 37)))
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(shootAng+6)), Math.toRadians(makeAngRed(pickUpAng)))
                .build();

        pickUpThird = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(makeXRed(45.8), 37), new Pose(makeXRed(12), 37)))
                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(pickUpAng)), Math.toRadians(makeAngRed(pickUpAng)))
                .build();

//        scoreThird = drivetrain.getPathBuilder()
//                .addPath(new BezierLine(new Pose(makeXRed(22), 37), new Pose(makeXRed(62.5), 86.6)))
//                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(177)), Math.toRadians(makeAngRed(shootAng+3)))
//                .build();
//
//        leave = drivetrain.getPathBuilder()
//                .addPath(new BezierLine(new Pose(makeXRed(62.5), 86.6), new Pose(makeXRed(50), 54)))
//                .setLinearHeadingInterpolation(Math.toRadians(makeAngRed(shootAng+3)), Math.toRadians(makeAngRed(pickUpAng)))
//                .build();
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
                        new SlowFollowPath(drivetrain, pickUpSecond, 0.32)
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
                        new SlowFollowPath(drivetrain, pickUpThird, 0.28)
                )

                //going to score third batch
//                new ParallelCommandGroup(
//                        shooter.rampUp(targetSpeed),
//                        new FollowPathCommand(drivetrain, scoreThird)
//                ),
//                new RepeatThriceCommand(ShooterCommandFactory.shootArtifact(indexer, shooter, kicker)),


                //LEAVING BELOW

        );

        schedule(auto);


    }
}
