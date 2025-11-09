package org.firstinspires.ftc.teamcode.util;

public class Vect2 {
    private double x;
    private double y;

    public Vect2(double x, double y) {
        this.x = x;
        this.y = y;
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

    public static Vect3 add(Vect3 a, Vect3 b) {
        return new Vect3(a.getX() + b.getX(), a.getY() + b.getY(), a.getZ() + b.getZ());
    }

    public static Vect3 subtract(Vect3 a, Vect3 b) {
        return new Vect3(a.getX() - b.getX(), a.getY() - b.getY(), a.getZ() - b.getZ());
    }

    public static double dotProduct(Vect2 a, Vect2 b) {
        return ((a.getX() * b.getX()) + (a.getY() * b.getY()));
    }

    public static double getDistance(Vect2 a, Vect2 b) {
        return Math.hypot(a.getX() - b.getX(), a.getY() - b.getY());
    }

    public double getMagnitude() {
        return Math.hypot(this.getX(), this.getY());
    }
}
