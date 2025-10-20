package org.firstinspires.ftc.teamcode.subsystems;

// index is radius; start at index+1
public class Aimer {
    private final Vect3 blueGoalPosition = new Vect3(13, 136.5, 39.75); // goal is 38.75 inches tall (not including top section)
    private final Vect3 redGoalPosition = new Vect3(131, 136.5, 39.75);
    // every 6 inches
    private final double[] rpmMap = {1, 2, 3};
    private final boolean isBlue;
    private Drivetrain drivetrain;
    public Aimer(Drivetrain drivetrain, boolean isBlue) {
        this.isBlue = isBlue;
        this.drivetrain = drivetrain;
    }

    /**
     *
     * This function aims and finds the launch parameters
     * @return LaunchParams (RPM, rotation angle in radians)
     */
    public LaunchParams aim() {
        Vect2 goalPos = this.isBlue ? this.blueGoalPosition.getVect2() : this.redGoalPosition.getVect2();
        Vect2 robotVect2 = drivetrain.getPosition().getVect2();
        double radius = Vect2.getDistance(goalPos, robotVect2);
        long index = Math.round(radius / 6) - 1;
        double rpm = rpmMap[(int) index];
        return new LaunchParams(rpm, this.getYaw());
    }

    /**
     * This function gets the yaw needed to rotate towards the goal
     * @return double (the angle needed to rotate towards the goal in radians)
     */
    private double getYaw() {
        Position robotPos = drivetrain.getPosition();
        Vect3 goalPos = this.isBlue ? this.blueGoalPosition : this.redGoalPosition;
        double headingRadians = robotPos.getHeadingRadians();
        double totalRadians = Math.atan2(goalPos.getX() - robotPos.getX(), goalPos.getY() - robotPos.getY());

        return totalRadians - headingRadians;
    }
}
