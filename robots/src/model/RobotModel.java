package src.model;

import java.util.Observable;

public class RobotModel extends Observable {
    private static final double MAX_VELOCITY = 0.1;
    private static final double MAX_ANGULAR_VELOCITY = 0.001;

    private volatile double robotPositionX = 100;
    private volatile double robotPositionY = 100;
    private volatile double robotDirection = 0;
    private volatile int targetPositionX = 150;
    private volatile int targetPositionY = 100;

    public double getRobotPositionX() { return robotPositionX; }
    public double getRobotPositionY() { return robotPositionY; }
    public double getRobotDirection() { return robotDirection; }
    public int getTargetPositionX() { return targetPositionX; }
    public int getTargetPositionY() { return targetPositionY; }

    public void setTargetPosition(int x, int y) {
        targetPositionX = x;
        targetPositionY = y;
        setChanged();
        notifyObservers();
    }

    public void updatePosition(double duration) {
        double distance = distanceToTarget();
        if (distance < 0.5) return;

        double angleToTarget = calculateAngleToTarget();
        double angularVelocity = calculateAngularVelocity(angleToTarget);
        moveRobot(MAX_VELOCITY, angularVelocity, duration);
    }

    private double distanceToTarget() {
        double dx = targetPositionX - robotPositionX;
        double dy = targetPositionY - robotPositionY;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private double calculateAngleToTarget() {
        double dx = targetPositionX - robotPositionX;
        double dy = targetPositionY - robotPositionY;
        return normalizeAngle(Math.atan2(dy, dx));
    }

    private double calculateAngularVelocity(double angleToTarget) {
        double diff = normalizeAngle(angleToTarget - robotDirection);
        if (diff > Math.PI) diff -= 2 * Math.PI;
        return diff > 0 ? MAX_ANGULAR_VELOCITY : -MAX_ANGULAR_VELOCITY;
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {

        velocity = applyLimits(velocity, 0, MAX_VELOCITY);
        angularVelocity = applyLimits(angularVelocity, -MAX_ANGULAR_VELOCITY, MAX_ANGULAR_VELOCITY);

        double newX = robotPositionX + velocity / angularVelocity *
                (Math.sin(robotDirection + angularVelocity * duration) - Math.sin(robotDirection));
        if (!Double.isFinite(newX)) {
            newX = robotPositionX + velocity * duration * Math.cos(robotDirection);
        }

        double newY = robotPositionY - velocity / angularVelocity *
                (Math.cos(robotDirection + angularVelocity * duration) - Math.cos(robotDirection));
        if (!Double.isFinite(newY)) {
            newY = robotPositionY + velocity * duration * Math.sin(robotDirection);
        }

        double gameWidth = 400;
        double gameHeight = 400;

        if (newX < 0) {
            newX = gameWidth;
        } else if (newX > gameWidth) {
            newX = 0;
        }

        if (newY < 0) {
            newY = gameHeight;
        } else if (newY > gameHeight) {
            newY = 0;
        }

        robotPositionX = newX;
        robotPositionY = newY;
        robotDirection = normalizeAngle(robotDirection + angularVelocity * duration);

        setChanged();
        notifyObservers();
    }


    private double applyLimits(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private double normalizeAngle(double angle) {
        while (angle < 0) angle += 2 * Math.PI;
        while (angle >= 2 * Math.PI) angle -= 2 * Math.PI;
        return angle;
    }

}