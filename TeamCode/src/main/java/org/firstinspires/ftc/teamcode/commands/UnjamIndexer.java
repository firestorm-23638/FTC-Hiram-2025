//package org.firstinspires.ftc.teamcode.commands;
//
//import com.arcrobotics.ftclib.command.Command;
//import com.arcrobotics.ftclib.command.InstantCommand;
//import com.arcrobotics.ftclib.command.SequentialCommandGroup;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//
//import org.firstinspires.ftc.teamcode.subsystems.Indexer;
//import org.firstinspires.ftc.teamcode.util.JamChecker;
//
//public class UnjamIndexer {
//    public Command unjam(Indexer indexer) {
//        DcMotorEx motor = indexer.getMotor().getMotor();
//        JamChecker jamChecker = new JamChecker(motor, 9.2f);
//        return new SequentialCommandGroup(
//            new InstantCommand(indexer::rotateLeftCmd),
//            new InstantCommand(indexer::rotateRightCmd)
//        );
//    }
//}
