package com.jacobwunder.libstatics.situations;

public class UniformCantileverSituation extends SimulatorSituation {

    public static String situationName = "UniformCantilever";

    @Override
    public void simulate() {

    }

    @Override
    public void handleUpdate(String name, Object rcv_value) {
        if (name.equals("force location update")) {
            double value = (double) rcv_value;
            System.out.println("force location update!!!!! got value: " + value);
        }
    }
}
