package com.jacobwunder.libstatics;

public class Force {
    private double magnitude;
    private double angle;
    private Point location;

    public Force(double magnitude, double angle, Point location) {
        this.magnitude = magnitude;
        this.angle = angle;
        this.location = location;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Point getLocation() { return location; }

    public void setLocation(Point location) { this.location = location; }
}
