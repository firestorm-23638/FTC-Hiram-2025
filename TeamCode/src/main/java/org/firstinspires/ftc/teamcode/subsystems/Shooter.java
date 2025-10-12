package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter extends SubsystemBase{

    private static double MAX_SPEED = 3900;
    private DcMotorEx shooter;
    private double targetSpeed;
    public Shooter(HardwareMap hardwareMap){
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
    }

    // ramp up to certain rpm
    public void rampUp(double targetSpeed){
        shooter.setPower(targetSpeed/MAX_SPEED);
    }
    public Command shootBall(double targetSpeed){
        return new InstantCommand(()->rampUp(targetSpeed));
    }
    public Command waitUntilFast(){
        return new WaitUntilCommand(this::checkSpeed);
    }
    public Command stopShoot(){
        return new InstantCommand(()->shooter.setPower(0));
    }
    // return the current speed
    public double getSpeed(){
       return shooter.getVelocity()/28*60;
    }

    // check that the current speed is close to the target speed
    public boolean checkSpeed(){
         return (getSpeed() > targetSpeed-50 && getSpeed() < targetSpeed+50);

    }

}
