package com.jacobwunder.libstatics;

import java.util.Arrays;
import java.util.function.Function;

public class Beam {

    public static int POINT_COUNT = 20;

    private Point[] topFaceMesh;
    private Point[] botFaceMesh;
    private double length;
    private double elasticity;
    private double inertia;

    public enum Material {
        Default
    }

    public Beam(Material material, double length) {

        this.topFaceMesh = new Point[POINT_COUNT];
        this.botFaceMesh = new Point[POINT_COUNT];
        this.length = length;

        switch (material) {
            case Default:
                elasticity = 4.92;
                inertia = 12.3;
        }

        for (int i = 0; i < topFaceMesh.length; i++) {
            topFaceMesh[i] = new Point(length / POINT_COUNT * i, 0.0, 0.0);
        }
    }

    public Beam(Beam other) {
        this.topFaceMesh = Arrays.copyOf(other.topFaceMesh, other.topFaceMesh.length);
        this.botFaceMesh = Arrays.copyOf(other.botFaceMesh, other.botFaceMesh.length);
        this.length = other.length;
        this.elasticity = other.elasticity;
        this.inertia = other.inertia;
    }

    public Point[] getTopFaceMesh() {
        return topFaceMesh;
    }

    public Point[] getBotFaceMesh() {
        return botFaceMesh;
    }

    public double getLength() {
        return length;
    }

    public double getInertia() {
        return inertia;
    }

    public double getElasticity() {
        return elasticity;
    }

    public void meshApplyTop(Function<Point, Void> fn) {
        for (Point p : topFaceMesh) {
            fn.apply(p);
        }
    }

    public void meshApplyBot(Function<Point, Void> fn) {
        for (Point p : botFaceMesh) {
            fn.apply(p);
        }
    }

    @Override
    public String toString() {
        return "Beam{" +
                "topFaceMesh=" + Arrays.toString(topFaceMesh) +
                ", length=" + length +
                ", elasticity=" + elasticity +
                ", inertia=" + inertia +
                '}';
    }
}
