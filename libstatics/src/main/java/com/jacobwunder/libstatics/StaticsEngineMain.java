package com.jacobwunder.libstatics;

import java.util.function.Function;

public class StaticsEngineMain {

    private static double force;

    public static void main(String[] args) {
        double length = 20;
        double elasticity = 4.92;
        double inertia = 12.3;
        force = 20;

        System.out.println(simulateElastic(force, length, elasticity, inertia, 15));
        System.out.println(simulateAngle(force, length, elasticity, inertia, 15));

        final Beam beam = new Beam(Beam.Material.Default, length);

        beam.meshApply(new Function<Point, Void>() {
            @Override
            public Void apply(Point point) {
                double defl = simulateElastic(beam, point.getZ());
                point.setY(-defl);
                return null;
            }
        });

        beam.meshApply(new Function<Point, Void>() {
            @Override
            public Void apply(Point point) {
                System.out.println(point);
                return null;
            }
        });
    }

    static double simulateElastic(Beam beam, double poi) {
        return simulateElastic(force, beam.getLength(), beam.getElasticity(), beam.getInertia(), poi);
    }

    static double simulateElastic(double force, double length, double elasticity, double inertia, double poi) {
        double deflection;
        double poiLength = (3 * length) - poi;
        double formula = (force * (Math.pow(poi, 2))) / (6 * elasticity * inertia);
        deflection = formula * poiLength;
        return deflection;
    }

    static double simulateAngle(double force, double length, double elasticity, double inertia, double poi){
        double angle;
        double poiLength = (2 * length) - poi;
        double formula = (force * (poi)) / (2 * elasticity * inertia);
        angle = formula * poiLength;
        return angle;
    }

}
