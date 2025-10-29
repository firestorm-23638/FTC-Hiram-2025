package org.firstinspires.ftc.teamcode.command_factory;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.Indexer;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class IndexerCommandFactory {

    public static Command intakeArtifact(Intake intake, Indexer indexer) {
        return new SequentialCommandGroup(
                intake.intakeBall(),
                intake.waitUntilArtifactInjested(),
//                new WaitCommand(50),
                indexer.rotate120Cmd(false),
                indexer.nearTarget().withTimeout(500)
                // add indexer when madox finished.
        );
    }


}
