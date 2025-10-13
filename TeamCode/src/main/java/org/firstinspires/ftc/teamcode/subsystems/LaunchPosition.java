package org.firstinspires.ftc.teamcode.subsystems;

public class LaunchPosition {
    private Drivetrain drivetrain;

    public LaunchPosition(Drivetrain myDrivetrain) {

    }

    // TODO: Make drivetrain drive towards goal until it reaches a radius, there is no need to get an exact position on the radius
    // could be done with a while loop checking position, but this may cost too much for performance
    public Position getClosestRadius() {
return  new Position(0 ,0 ,0 );
    }
}
