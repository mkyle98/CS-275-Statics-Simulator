package com.jacobwunder.libstatics.situations;


import com.jacobwunder.libstatics.Beam;
import com.jacobwunder.libstatics.Force;
import com.jacobwunder.libstatics.Point;

import java.util.function.Function;

public abstract class SinglePointCantileverSituation extends SimulatorSituation {
    @Override
    public void simulate() {

    }

    @Override
    public Beam updateParameters(Beam beam, Force force, double poi){
        double angle = simulateAngle(beam, poi ,force);
        final double elastic = simulateElastic(beam, poi, force);

        beam.meshApply(new Function<Point, Void>() {
            @Override
            public Void apply(Point point) {
                point.setY(-elastic);
                return null;
            }
        });

        return beam;
    }

    static double simulateElastic(Beam beam, double poi, Force force) {
        return simulateElastic(force.getMagnitude(), beam.getLength(), beam.getElasticity(), beam.getInertia(), poi);
    }

    static double simulateAngle(Beam beam, double poi, Force force) {
        return simulateAngle(force.getMagnitude(), beam.getLength(), beam.getElasticity(), beam.getInertia(), poi);
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
