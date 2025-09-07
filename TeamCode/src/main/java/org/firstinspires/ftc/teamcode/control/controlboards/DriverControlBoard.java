package org.firstinspires.ftc.teamcode.control.controlboards;

import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;



public class DriverControlBoard implements IDriverControlBoard {
    private GamepadEx driver;

    public DriverControlBoard(GamepadEx driver) {
        this.driver = driver;
    }

    @Override
    public Trigger wantIntakeArtifact() {
        return driver.getGamepadButton(GamepadKeys.Button.A);
    }

    @Override
    public Trigger shouldAlignToBase() {
        return driver.getGamepadButton(GamepadKeys.Button.X);
    }

    @Override
    public Trigger wantToShootArtifact() {
        return driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
            .and(driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)).negate();
    }

    @Override
    public Trigger wantToShootAllArtifacts() {
        return driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
            .and(wantToShootArtifact());
    }

    @Override
    public Trigger wantToEjectArtifact() {
        return driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
            .and(driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).negate());
    }

    @Override
    public Trigger shouldShiftIndexerLeft() {
        return driver.getGamepadButton(GamepadKeys.Button.DPAD_LEFT);
    }

    @Override
    public Trigger shouldShiftIndexerRight() {
        return driver.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT);
    }

    @Override
    public Trigger shouldDeployClimber() {
        return driver.getGamepadButton(GamepadKeys.Button.DPAD_DOWN);
    }

    @Override
    public double getForwardDrivePower() {
        return driver.getLeftY();
    }

    @Override
    public double getStrafeDrivePower() {
        return driver.getLeftX();
    }

    @Override
    public double getRotationDrivePower() {
        return driver.getRightX();
    }
}
