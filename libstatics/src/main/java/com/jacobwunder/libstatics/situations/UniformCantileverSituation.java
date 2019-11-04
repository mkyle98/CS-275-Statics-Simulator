package com.jacobwunder.libstatics.situations;

public class UniformCantileverSituation extends SimulatorSituation {

    public static String situationName = "UniformCantilever";

    @Override
    public void simulate() {

    }

    @Override
    public void handleUpdate(String name, Object rcv_value) {
        if (name.equals("PI TIME")) {
            float value = (float) rcv_value;
            System.out.println("PI TIME!!!!! got value: " + value);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("lmao alright");
            }

            sendMessage("PI Returned", value);
        }
    }
}
