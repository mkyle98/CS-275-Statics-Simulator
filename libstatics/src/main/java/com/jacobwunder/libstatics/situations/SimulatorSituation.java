package com.jacobwunder.libstatics.situations;

public abstract class SimulatorSituation {

    abstract public void simulate();

    public void onUpdate() {

    }

    public static class UpdatePacket {

        public UpdatePacket() {

        }

    }
}
