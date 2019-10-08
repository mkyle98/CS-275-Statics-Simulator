package com.jacobwunder.staticsengine;

import java.lang.Math;




public class StaticsEngine {


    private static double length;
    private static double force;
    private static double elasticity;
    private static double inertia;



    public static void main(String[] args) {
        length = 20;
        force = 20;
        elasticity = 4.92;
        inertia = 12.3;

        System.out.println(simulateElastic(force, length, elasticity, inertia, 15));
        System.out.println(simulateAngle(force, length, elasticity, inertia, 15));

    }

    /*
    * This should take the force, location (as a fraction of the length)
    * and point of interest (as a fraction of the length)
    * and calculate the deflection of the beam at the given point of interest
    *
    * You should assume as many variable as you can, and represent
    * them as constants in this class
    *
    * */


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
