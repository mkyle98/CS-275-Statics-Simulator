package com.jacobwunder.libstatics.situations;

import com.jacobwunder.libstatics.Beam;
import com.jacobwunder.libstatics.Force;

public abstract class SimulatorSituation {
/*
To-do:
Subclass SimulatorSituation: Cantilever beams first
Import methods from StaticsEngineMain
Figure out how to store forces
Figure out properties points need to have
Force class: Magnitude, location, angle, in xyz
For the abstract, a ticking function, and something to update parameters.
 */
    abstract public void simulate();

    abstract public Beam updateParameters(Beam beam, Force force);

}
