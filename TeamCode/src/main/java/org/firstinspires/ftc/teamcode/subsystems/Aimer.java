package org.firstinspires.ftc.teamcode.subsystems;

// index is radius; start at index+1
public class Aimer {
    private final Vect3 blueGoalPosition = new Vect3(13, 136.5, 39.75); // goal is 38.75 inches tall (not including top section)
    private final Vect3 redGoalPosition = new Vect3(131, 136.5, 39.75);
    // every 6 inches
    private final int[] rpmMap = {1, 2, 3};
    private final boolean isBlue;
    private Drivetrain drivetrain;
    public Aimer(Drivetrain drivetrain, boolean isBlue) {
        this.isBlue = isBlue;
        this.drivetrain = drivetrain;
    }

    public LaunchParams aim() {
        // fixed yaw for now
        Vect2 goalPosition;
        if (this.isBlue) {
            goalPosition = blueGoalPosition.getVect2();
        } else {
            goalPosition = redGoalPosition.getVect2();
        }
        Vect2 robotVect2 = drivetrain.getPosition().getVect2();
        double radius = Vect2.getDistance(goalPosition, robotVect2);
    }

    public double getRPM(double x, double y) {
        double radius = Math.hypot(x, y);
        int roundedRadius = (int) Math.round(radius);
        int index = roundedRadius - 1;

        return rpmMap[index];
    }

    public double getRadiansToGoal() {
        Position robotPos = drivetrain.getPosition();
        Vect3 goalPos = this.isBlue ? this.blueGoalPosition : this.redGoalPosition;
        double headingRadians = robotPos.getHeadingRadians();
        double totalRadians = Math.atan2(goalPos.getX() - robotPos.getX(), goalPos.getY() - robotPos.getY());

        return totalRadians - headingRadians;
    }
}
