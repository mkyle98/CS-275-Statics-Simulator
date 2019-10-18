package com.jacobwunder.libstatics.situations;

import com.jacobwunder.libstatics.Beam;
import com.jacobwunder.libstatics.Force;

public class UniformCantileverSituation extends SimulatorSituation {

    @Override
    public void simulate() {

    }

    @Override
    public Beam updateParameters(Beam beam, Force force, double poi){

        return beam;

    }
}
