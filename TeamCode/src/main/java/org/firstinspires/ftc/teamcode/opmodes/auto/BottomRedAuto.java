package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
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

@Autonomous(name = "Bottom Red Auto", group = "Auto", preselectTeleOp = "Red TeleOp")
public class BottomRedAuto extends CommandOpMode {
    protected Drivetrain drivetrain;
    protected Shooter shooter;
    protected Intake intake;
    protected Kicker kicker;
    protected Indexer indexer;
    protected Limelight limelight;

    protected PathChain scanObelisk;
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



    protected Pose getStartingPose() {
        return new Pose(83.7275, 12.9886, Math.toRadians(90));
    }

    protected void buildPaths(Drivetrain drivetrain) {

        int pickUpAng = 0;
        double shootAng = 69;
        double shootX = 88.61;
        double shootY = 16.044;
        double goToBallX = 101.5;


        scanObelisk = drivetrain.getPathBuilder()
                .addPath(
                        new BezierLine(new Pose(83.7275, 12.9886), new Pose(83.7275, 17))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();
        scorePreload = drivetrain.getPathBuilder()
                .addPath(
                        new BezierLine(new Pose(83.7275, 17), new Pose(shootX, shootY))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(shootAng+5))
                .build();

        goToFirst = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(shootX, shootY), new Pose(goToBallX, 34)))
                .setLinearHeadingInterpolation(Math.toRadians(shootAng+5), Math.toRadians(pickUpAng))
                .build();

        pickUpFirst = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(goToBallX, 34), new Pose(133, 34)))
                .setLinearHeadingInterpolation(Math.toRadians(pickUpAng), Math.toRadians(pickUpAng))
                .build();

        scoreFirst = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(133 , 34), new Pose(shootX, shootY)))
                .setLinearHeadingInterpolation(Math.toRadians(pickUpAng), Math.toRadians(shootAng))
                .build();

        goToSecond = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(shootX, shootY), new Pose(goToBallX, 59.2)))
                .setLinearHeadingInterpolation(Math.toRadians(shootAng), Math.toRadians(pickUpAng))
                .setBrakingStrength(0.5)
                .setBrakingStart(2)
                .build();

        pickUpSecond = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(goToBallX, 59.2), new Pose(130, 59.2)))
                .setLinearHeadingInterpolation(Math.toRadians(pickUpAng), Math.toRadians(pickUpAng))
                .build();

        scoreSecond = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(130, 59.2), new Pose(shootX, shootY)))
                .setLinearHeadingInterpolation(Math.toRadians(pickUpAng), Math.toRadians(shootAng))
                .build();

        goToThird = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(shootX, shootY), new Pose(goToBallX, 83.8)))
                .setLinearHeadingInterpolation(Math.toRadians(shootAng), Math.toRadians(pickUpAng))
                .build();

        pickUpThird = drivetrain.getPathBuilder()
                .addPath(new BezierLine(new Pose(goToBallX, 83.8), new Pose(127, 83.8)))
                .setLinearHeadingInterpolation(Math.toRadians(pickUpAng), Math.toRadians(pickUpAng))
                .build();

//        scoreThird = drivetrain.getPathBuilder()
//                .addPath(new BezierLine(new Pose(goPickUpX, 83.8), new Pose(shootX, shootY)))
//                .setLinearHeadingInterpolation(Math.toRadians(pickUpAng), Math.toRadians(shootAng))
//                .build();
//
//        leave = drivetrain.getPathBuilder()
//                .addPath(new BezierLine(new Pose(shootX, shootY), new Pose(50, 54)))
//                .setLinearHeadingInterpolation(Math.toRadians(shootAng), Math.toRadians(pickUpAng))
//                .build();
    }

    public double getTargetSpeed(){
        return 3300;
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
                        ShooterCommandFactory.revUpForAuto( indexer,  shooter, kicker),
                        //pipeline 0 is close and pipeline 1 is far
                        new WaitUntilCommand(() -> limelight.readObelisk(1) != null)
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
                        new SlowFollowPath(drivetrain, pickUpFirst, 0.35)
                ),

                new ParallelCommandGroup(
                        indexer.setSlotColor("PPG"),
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
                        new SlowFollowPath(drivetrain, pickUpSecond, 0.35)
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
