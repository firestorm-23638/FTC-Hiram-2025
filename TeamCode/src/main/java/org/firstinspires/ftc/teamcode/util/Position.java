package org.firstinspires.ftc.teamcode.util;

public class Position {
    private Vect2 position;
    private double headingRadians;

    public Position(double x, double y, double headingRadians) {
        this.position = new Vect2(x, y);
        this.headingRadians = headingRadians;
    }

   public Vect2 getVect2() {
        return this.position;
   }

   public double getX() {
        return position.getX();
   }

    public double getY() {
        return position.getY();
    }

    public void setX(double x) {
        position.setX(x);
    }

    public void setY(double y) {
        position.setY(y);
    }

    public double getHeadingRadians() {
        return headingRadians;
    }

    public double getHeadingDegrees() { return headingRadians * (180 / Math.PI); }

    public void setHeadingRadians(double headingRadians) {
        this.headingRadians = headingRadians;
    }
}
