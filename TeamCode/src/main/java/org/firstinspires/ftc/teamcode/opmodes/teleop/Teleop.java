package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.commands.ArcadeDriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.IntakeClaw;
import org.firstinspires.ftc.teamcode.subsystems.IntakeExt;

@TeleOp(name = "TeleOp")
public class Teleop extends CommandOpMode {
    private Drivetrain drivetrain;
    private Arm arm;
    private IntakeClaw intakeClaw;
    private GamepadEx driver;
    private IntakeExt intakeExt;

    @Override
    public void initialize() {
        this.arm = new Arm(hardwareMap);
        this.driver = new GamepadEx(this.gamepad1);
        this.drivetrain = new Drivetrain(hardwareMap);
        this.intakeClaw = new IntakeClaw(hardwareMap);
        this.intakeExt = new IntakeExt(hardwareMap);

        this.drivetrain.setDefaultCommand(new ArcadeDriveCommand(drivetrain, this.driver::getLeftY, this.driver::getRightX, this.driver::getLeftX));

        this.driver.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
                        new SequentialCommandGroup(
                                arm.goToPos(Arm.ArmState.SCORE),
                                arm.armClawOpen()
                        ))
                .whenReleased(new SequentialCommandGroup(
                        arm.goToPos(Arm.ArmState.HOME),
                        arm.armClawClose())
                );


        //when button is held the arm goes to the score position. When released, goes back
        this.driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
                        new ParallelCommandGroup(
                                intakeExt.extendIntake(),
                                intakeClaw.openClaw(),
                                intakeClaw.pivotToReady()
                        ))
                .whenReleased(
                        new SequentialCommandGroup(
                                new ParallelCommandGroup(
                                        intakeClaw.pivotToCollect(),
                                        intakeClaw.closeClaw()
                                ),
                                new WaitCommand(700),
                                intakeExt.retractIntake()
                        ));
        //when button is held intake arm goes to ready, when released it goes down, grabs, and comes back.

        this.driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(intakeClaw.rotateto90())
                .whenReleased(intakeClaw.rotateTo0());
        //when button is held claw rotates to 90 degrees, when released it goes back to 0 degrees


        register(arm,intakeClaw, intakeExt);
    }
}