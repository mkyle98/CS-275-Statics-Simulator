package com.jacobwunder.libstatics.situations;


import com.jacobwunder.libstatics.Beam;
import com.jacobwunder.libstatics.Force;
import com.jacobwunder.libstatics.Point;

import java.util.function.Function;

public class SinglePointCantileverSituation extends SimulatorSituation {

    public static String situationName = "SinglePointCantilever";

    Beam beam = new Beam(Beam.Material.Default, 20);
    Point forceLocation = new Point(beam.getLength(), 0, 0);
    Force force = new Force(5, 0 , forceLocation);

    @Override
    public void simulate() {
        beam.meshApply(poi -> {
            poi.setY(simulateElastic(beam, force, poi.getX()));
            return null;
        });
    }

    @Override
    public void handleUpdate(String type, Object rcv_value) {
        if (type.equals("force location update")) {
            double value = (double) rcv_value;
            System.out.println("force location update!!!!! got value: " + value);

            forceLocation.setX(value * beam.getLength());
            System.out.println(force.getLocation());

            simulate();

            System.out.println("Beam after calulations!" + beam);
            sendMessage("beam update", new Beam(beam));
        }
    }

    private static double simulateElastic(Beam beam, Force force, double poi) {
        return simulateElastic(force.getMagnitude(), beam.getLength(), beam.getElasticity(), beam.getInertia(), poi);
    }

    private static double simulateAngle(Beam beam, Force force, double poi) {
        return simulateAngle(force.getMagnitude(), beam.getLength(), beam.getElasticity(), beam.getInertia(), poi);
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
