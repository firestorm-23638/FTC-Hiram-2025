package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.subsystems.Indexer;

@TeleOp(name = "Indexer Test", group = "Tests")
public class IndexerTest extends CommandOpMode {
    public GamepadEx driver;
    public Indexer indexer;
    @Override
    public void initialize() {
        this.driver = new GamepadEx(gamepad1);
        this.indexer = new Indexer(hardwareMap, telemetry);
        this.driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenHeld(indexer.rotate120Cmd(true));
        this.driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenHeld(indexer.rotate120Cmd(false));

        register(indexer);
        schedule(new RunCommand(telemetry :: update));
    }

}
