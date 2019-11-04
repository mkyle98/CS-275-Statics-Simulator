package com.jacobwunder.libstatics.situations;

import com.jacobwunder.libstatics.UpdatePayload;

import java.util.function.Function;

public abstract class SimulatorSituation {

    public static String situationName = null;

    private Function<UpdatePayload, Void> sendUpdateCallback;
    public void setSendUpdateCallback(Function<UpdatePayload, Void> sendUpdateCallback) {
        this.sendUpdateCallback = sendUpdateCallback;
    }

    abstract public void simulate();

    public abstract void handleUpdate(String type, Object value);

    protected void sendMessage(String type, Object value) {
        sendUpdateCallback.apply(new UpdatePayload(type, value));
    }
}
