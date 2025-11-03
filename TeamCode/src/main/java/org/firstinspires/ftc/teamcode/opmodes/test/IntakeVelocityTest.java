package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.commands.SlowFollowPath;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Indexer;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@TeleOp
public class IntakeVelocityTest extends CommandOpMode {
    private Drivetrain drivetrain;
    private Intake intake;
    protected PathChain pickUp;
    private Indexer indexer;

    @Override
    public void initialize() {
        this.drivetrain = new Drivetrain(hardwareMap, new Pose(0, 0, 0));
        this.intake = new Intake(hardwareMap, telemetry);


        pickUp = drivetrain.getPathBuilder()
                .addPath(
                        new BezierLine(new Pose(0, 0), new Pose(15, 0))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();
        new Trigger(intake::isBeamBroken).and(new Trigger(intake::isIntaking))
                .whenActive(indexer.rotate120Cmd(false));
        schedule(
                new SequentialCommandGroup(

                        new ParallelCommandGroup(
                                new SlowFollowPath(drivetrain, pickUp, 0.4),
                                intake.intakeBall()
                        ),

                        intake.stop()));

    }


}
