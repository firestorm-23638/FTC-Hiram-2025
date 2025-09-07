package org.firstinspires.ftc.teamcode.control.controlboards;

import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

public interface IDriverControlBoard {

    Trigger wantIntakeArtifact();

    Trigger shouldAlignToBase();

    Trigger wantToShootArtifact();
    Trigger wantToShootAllArtifacts();
    Trigger wantToEjectArtifact();

    Trigger shouldShiftIndexerLeft();
    Trigger shouldShiftIndexerRight();

    Trigger shouldDeployClimber();


    double getForwardDrivePower();
    double getStrafeDrivePower();
    double getRotationDrivePower();
}
