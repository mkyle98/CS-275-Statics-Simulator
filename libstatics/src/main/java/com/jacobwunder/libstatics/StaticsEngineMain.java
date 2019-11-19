package com.jacobwunder.libstatics;

import com.jacobwunder.libstatics.situations.EndLoadedCantileverSituation;

public class StaticsEngineMain {

    public static void main(String[] args) {

        Beam beam = new Beam(Beam.Material.Default, 20);
        Point point = new Point(15, 0, 0);
        Force force = new Force(20, 0 , point);
        EndLoadedCantileverSituation situation = new EndLoadedCantileverSituation();
        System.out.println(beam.getTopFaceMesh());
//        situation.updateParameters(beam, force);
        System.out.println(beam.getTopFaceMesh());

//
//        double length = 20;
//        double elasticity = 4.92;
//        double inertia = 12.3;
//        double force1 = 20;
//
//        System.out.println(simulateElastic(force1, length, elasticity, inertia, 15));
//        System.out.println(simulateAngle(force1, length, elasticity, inertia, 15));

//
//        final Beam beam = new Beam(Beam.Material.Default, length);
//
//        beam.meshApplyTop(new Function<Point, Void>() {
//            @Override
//            public Void apply(Point point) {
//                double defl = simulateElastic(beam, point.getZ());
//                point.setY(-defl);
//                return null;
//            }
//        });
//
//        beam.meshApplyTop(new Function<Point, Void>() {
//            @Override
//            public Void apply(Point point) {
//                System.out.println(point);
//                return null;
//            }
//        });
    }

    private static double simulateElastic(double force, double length, double elasticity, double inertia, double poi) {
        double deflection;
        double poiLength = (3 * length) - poi;
        double formula = (force * (Math.pow(poi, 2))) / (6 * elasticity * inertia);
        deflection = formula * poiLength;
        return deflection;
    }

    private static double simulateAngle(double force, double length, double elasticity, double inertia, double poi){
        double angle;
        double poiLength = (2 * length) - poi;
        double formula = (force * (poi)) / (2 * elasticity * inertia);
        angle = formula * poiLength;
        return angle;
    }

}

