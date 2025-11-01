package org.firstinspires.ftc.teamcode.subsystems;

// http://limelight.local:5801

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.util.Config;

import java.util.List;

public class Limelight {
    Drivetrain drivetrain;
    IMU imu;
    Limelight3A limelight;
    Telemetry telemetry;
    public Limelight(HardwareMap hw, Drivetrain dt) {
        limelight = hw.get(Limelight3A.class, "limelight");
        drivetrain = dt;
    }

    public void init() {
        limelight.setPollRateHz(100); // get data 100 times per second
        limelight.start(); // start looking
    }

    public Pose3D getRobotPos() {
        LLResult result = limelight.getLatestResult();

        // Pose3D botpose = result.getBotpose();
        Pose3D botpose_mt2 = result.getBotpose_MT2();

        long staleness = result.getStaleness(); // in ms

        // check if staleness is under 100ms
        if (staleness < 100 & result.isValid()) {
            /*
            // megatag 1
            if (botpose != null) {
                double x = botpose.getPosition().x;
                double y = botpose.getPosition().y;
            }

            */

            // use IMU for mt 2 to increase accuracy
            double robotYaw = drivetrain.getPosition().getHeadingRadians();

            limelight.updateRobotOrientation(robotYaw);
            if (botpose_mt2 != null) {
                double x = botpose_mt2.getPosition().x;
                double y = botpose_mt2.getPosition().y;
            }
        }

        return botpose_mt2;
    }
    public Patterns readObelisk() {
        LLResult result = limelight.getLatestResult();

        long staleness = result.getStaleness();

        if (!result.isValid() && staleness > 100) {
            //throw new RuntimeException("Limelight result is invalid or stale");
//            telemetry.addData("limelight_error", "Limelight data is too stale (" + staleness + "ms)");
//            telemetry.update();
            return null;
        }


        List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();
        for (LLResultTypes.FiducialResult fiducial : fiducials) {
            int id = fiducial.getFiducialId(); // The ID number of the fiducial
            switch (id) {
                case 21:
                    Config.CURRENT_PATTERN = Patterns.GPP;
                    return Patterns.GPP;
                case 22:
                    Config.CURRENT_PATTERN = Patterns.PGP;
                    return Patterns.PGP;
                case 23:
                    Config.CURRENT_PATTERN = Patterns.PPG;
                    return Patterns.PPG;
                    default:
                        //throw new RuntimeException("Invalid case ID");
//                        telemetry.addData("limelight_error", "ID:" + id);
//                        telemetry.update();
            }
        }
        return null;
    }

    public double getGoalAngle(boolean isRed) {
        int id = isRed ? 24 : 20;
        LLResult result = limelight.getLatestResult();
        if (result.getStaleness() > 100 || !result.isValid()) {
            return 0;
        }

        List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();
        for (LLResultTypes.FiducialResult fiducialResult : fiducials) {
            if (fiducialResult.getFiducialId() == id) {
                return fiducialResult.getTargetXDegrees();
            }
        }

        return 0;

    }
}

