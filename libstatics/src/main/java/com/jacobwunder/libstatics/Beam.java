package com.jacobwunder.libstatics;

import java.util.function.Function;

public class Beam {

    private static int POINT_COUNT = 20;

    private Point[] mesh;
    private double length;
    private double elasticity;
    private double inertia;

    public enum Material {
        Default
    }

    public Beam(Material material, double length) {

        this.mesh = new Point[POINT_COUNT];
        this.length = length;

        switch (material) {
            case Default:
                elasticity = 4.92;
                inertia = 12.3;
        }

        for (int i = 0; i < mesh.length; i++) {
            mesh[i] = new Point(length / POINT_COUNT * i, 0.0, 0.0);
        }
    }

    public Point[] getMesh() {
        return mesh;
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

    public void meshApply(Function<Point, Void> fn) {
        for (Point p : mesh) {
            fn.apply(p);
        }
    }
}
