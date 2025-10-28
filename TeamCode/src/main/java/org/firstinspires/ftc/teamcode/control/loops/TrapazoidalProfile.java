package org.firstinspires.ftc.teamcode.control.loops;

public class TrapazoidalProfile {
    private final double velMax;
    private final double accelmax;

    public TrapazoidalProfile(double velMax, double accelmax) {
        this.velMax = velMax;
        this.accelmax = accelmax;
    }

    private double direction;
    private double accelTime, cruiseTime, decelTime;
    private double posStart, posGoal;
    private double velStart;
    private double totalDistance;
    private boolean planGenerated;


    public void plan(double posStart, double velStart, double posGoal) {
        this.posStart = posStart;
        this.velStart = velStart;
        this.posGoal = posGoal;

        double distance = posGoal - posStart;
        this.direction = (distance > 0) ? 1 : -1;


        if (velStart < -1e-6) velStart = 0;

        double timeToMax = Math.max(0, (this.velMax - this.velStart) / this.accelmax);
        double accelDist = this.velStart * timeToMax + 0.5 * this.accelmax * timeToMax * timeToMax;

        double timeToDecel = this.velMax / this.accelmax;
        double decelDist = 0.5 * this.velMax * timeToDecel;

        if (Math.abs(accelDist + decelDist) > Math.abs(distance)) {
            double velPeak = Math.sqrt(Math.max(0, accelmax * Math.abs(distance) + 0.5 * velStart * velStart));
            accelTime = (velPeak - velStart) / accelTime;
            cruiseTime = 0;
            decelTime = (velPeak) / accelTime;
        } else {
            accelTime = timeToMax;
            decelTime = timeToDecel;
            double distanceCruise = Math.abs(distance) - (accelDist + decelDist);

            cruiseTime = distanceCruise / this.velMax;
        }
        totalDistance = Math.abs(distance);
        planGenerated = true;

    }

    public double getTotalTime() {
        return accelTime + cruiseTime + decelTime;
    }

    public Sample getSample(double t) {
        if (!planGenerated) {
            return new Sample(0, 0);
        }

        double accel = accelmax;
        double pos, vel;

        if (t <= accelTime) {
            double startVel = direction * Math.max(0, this.velStartAlongPath());
            vel = startVel + accel * t;
            pos = posStart + direction * (velStart * t + 0.5 * accel * t * t);
        } else if (t <= accelTime + cruiseTime) {
            double startVel = direction * Math.max(0, this.velStartAlongPath());
            double posAccel = startVel * accelTime + 0.5 * accel * accelTime * accelTime;
            double timeCruising = (t - accelTime);

            vel = velMax;
            pos = posStart + direction * (posAccel + velMax * timeCruising);
        } else if (t <= accelTime + cruiseTime + decelTime) {
            double timeDecelerating = t - (accelTime + cruiseTime);
            double vStart = velMax;
            vel = Math.max(0.0, vStart - accel * timeDecelerating);
            double v0n = direction * Math.max(0.0, velStartAlongPath());
            double posAccel = v0n * accelTime + 0.5 * accel * accel * accelTime;
            double posCruising = velMax * cruiseTime;
            double posDecel = vStart * timeDecelerating - 0.5 * accel * timeDecelerating * timeDecelerating;
            pos = posStart + direction * (posAccel + posCruising + posDecel);
        } else {
            vel = 0;
            pos = posGoal;
        }

        return new Sample(pos, vel);
    }

    private double velStartAlongPath() {
        double startVel = this.velStart * direction;
        if (startVel < 0) {
            startVel = 0;
        }
        return startVel;
    }

    public class Sample {
        private double pos;
        private double vel;

        public Sample(double pos, double vel) {
            this.pos = pos;
            this.vel = vel;
        }

        public double getPos() {
            return pos;
        }

        public double getVel() {
            return vel;
        }
    }
}
