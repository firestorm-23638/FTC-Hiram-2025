package org.firstinspires.ftc.teamcode.command_factory;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.Indexer;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

public class ShooterCommandFactory {

    public static Command shootArtifact(Indexer indexer, Shooter shooter, Kicker kicker) {
        return new SequentialCommandGroup(
                new ParallelCommandGroup(
                        indexer.rotate60Cmd(true).andThen(indexer.nearTarget()),
                        shooter.shootBall(3600).raceWith(shooter.waitUntilFast(3600))
                ),
                kicker.pushBall(),
                kicker.retract(),
                indexer.rotate60Cmd(true)
        );
    }
}
