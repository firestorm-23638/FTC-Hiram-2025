package org.firstinspires.ftc.teamcode.subsystems;

public class Vect3 {
    private double x;
    private double y;
    private double z;

    public static final Vect3 zero = new Vect3(0, 0, 0);
    public Vect3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public static Vect3 add(Vect3 a, Vect3 b) {
        return new Vect3(a.getX() + b.getX(), a.getY() + b.getY(), a.getZ() + b.getZ());
    }

    public static Vect3 subtract(Vect3 a, Vect3 b) {
        return new Vect3(a.getX() - b.getX(), a.getY() - b.getY(), a.getZ() - b.getZ());
    }

    public static double dotProduct(Vect3 a, Vect3 b) {
        return ((a.getX() * b.getX()) + (a.getY() * b.getY()) + (a.getZ() * b.getZ()));
    }
    
    public static double getDistance(Vect3 a, Vect3 b) {
        double xSquared = (a.getX() - b.getX()) * (a.getX() - b.getX());
        double ySquared = (a.getY() - b.getY()) * (a.getY() - b.getY());
        double zSquared = (a.getZ() - b.getZ()) * (a.getZ() - b.getZ());
        return Math.sqrt(xSquared + ySquared + zSquared);
    }
    
    public double getMagnitude() {
        return Vect3.getDistance(this, Vect3.zero);
    }

    public Vect2 getVect2() {
        return new Vect2(this.x, this.y);
    }
}
