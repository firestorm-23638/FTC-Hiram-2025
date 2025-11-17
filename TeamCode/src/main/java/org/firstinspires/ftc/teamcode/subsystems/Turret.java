package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.Debouncer;
import org.firstinspires.ftc.teamcode.util.JamChecker;
import org.firstinspires.ftc.teamcode.util.Motor;

public class Turret extends SubsystemBase {
    private HardwareMap hardwareMap;
    private Motor turretMotor;
    private Telemetry telemetry;
//    private Limelight limelight;
    private static final int EPS = 30;
    private static final int SAFE_DEGREE_CLOCKWISE = 270;
    public final static float encoderResolution = 537.7f;
    public final static double kP = 0.0005;
    public final static double kD = 0.0000143458;

    private static final double GEAR_RATIO = (double) 136 / 24;

    public Turret(HardwareMap hardwareMap, Telemetry telemetry) {
         this.turretMotor = new Motor(hardwareMap.get(DcMotorEx.class, "turretMotor"),
                 encoderResolution,
                 kP,
                 kD
         );
//         this.limelight = limelight;
         this.telemetry = telemetry;
    }

    @Override
    public void periodic() {
        turretMotor.rotate();

        telemetry.addData("CurrentTick", turretMotor.getCurrentTick());
        telemetry.addData("target", turretMotor.getTargetTick());
    }

    /**
     * Sets the angle absolutely using the origin as a point of reference
     * ex. currentAngle = 60, rotating 40 would rotate backwards 20 degrees instead of rotating to 100)
     * @param angle Absolute angle to rotate to
     */
    public void setAngle(double angle) {
        assert(angle >= 0 && angle <= 360);
        if (angle <= SAFE_DEGREE_CLOCKWISE) {
            turretMotor.rotateToAngle(angle, GEAR_RATIO,true);
        } else {
            turretMotor.rotateToAngle(angle - 360, GEAR_RATIO, true);
        }
    }

    public void faceGoal() {
        
    }

}
