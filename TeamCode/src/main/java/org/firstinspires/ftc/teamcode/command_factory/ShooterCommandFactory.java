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
                        indexer.rotate60Cmd(true).andThen(indexer.nearTarget().withTimeout(500)),
                        shooter.shootBall(2900).raceWith(shooter.waitUntilFast(2900))
                ),
                kicker.pushBall(),
                kicker.retract(),
                indexer.rotate60Cmd(true)
        );
    }

    public static Command shootArtifact(Indexer indexer, Shooter shooter, Kicker kicker, double targetSpeed) {
        return new SequentialCommandGroup(
                new ParallelCommandGroup(
                        indexer.rotate60Cmd(true).andThen(indexer.nearTarget().withTimeout(250)),
                        shooter.shootBall(targetSpeed).raceWith(shooter.waitUntilFast(targetSpeed).withTimeout(5000))
                ),
                kicker.pushBall(),
                kicker.retract(),
                indexer.rotate60Cmd(true)
        );
    }


    public static Command shootArtifactFar(Indexer indexer, Shooter shooter, Kicker kicker) {
        return new SequentialCommandGroup(
            new ParallelCommandGroup(
                indexer.rotate60Cmd(true).andThen(indexer.nearTarget().withTimeout(250)),
                shooter.shootBall(3400).raceWith(shooter.waitUntilFast(3400).withTimeout(5000))
            ),
            kicker.pushBall(),
            kicker.retract(),
            indexer.rotate60Cmd(true)
        );
    }


    public static Command resetShooter(Indexer indexer, Shooter shooter, Kicker kicker) {

        return new SequentialCommandGroup(
                kicker.retract(),
                indexer.rotateToNearestIndexCmd(),
                shooter.shootBall(1000)
        );
    }


    public static Command revUpForAuto(Indexer indexer, Shooter shooter, Kicker kicker){

        return new SequentialCommandGroup(
                kicker.retract(),
                indexer.rotateToNearestIndexCmd(),
                shooter.rampUp(2500)
        );


    }
}
