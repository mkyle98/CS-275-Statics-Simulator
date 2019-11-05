package com.jacobwunder.cs275staticssimulator.threading;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.jacobwunder.libstatics.UpdatePayload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SimulatorClient {

    private static final String TAG = "HandlerThread";
    private int requestNumber = 0;

    public static int MESSAGE_ID = 0;

    private SimulatorHandlerThread mHandlerThread;
    private Handler mResponseHandler;

    private Map<String, ArrayList<Function<Object, Void>>> callbacks;

    public SimulatorClient() {
        mResponseHandler = new ResponseHandler();

        callbacks = new HashMap<>();

        mHandlerThread = new SimulatorHandlerThread(mResponseHandler);
        mHandlerThread.start();
        mHandlerThread.getLooper();
    }

    public void sendMesage(String type, Object value) {
        System.out.println("sending message " + type + " " + value);
        mHandlerThread.createAndSendMessageToSelf(type, value);
    }

    public void onReceiveSimulatorMessage(String name, Function<Object, Void> callback) {
        ArrayList<Function<Object, Void>>
                cbs = callbacks.getOrDefault(name, new ArrayList<>());
        cbs.add(callback);
        callbacks.put(name, cbs);
    }

    public void onDestroy() {
        mHandlerThread.clearQueue();
        mHandlerThread.quit();
    }

    private class ResponseHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_ID) {
                UpdatePayload payload = (UpdatePayload) msg.obj;
                Log.i(TAG, "handling message (client): " + payload);

                ArrayList<Function<Object, Void>>
                        cbs = callbacks.getOrDefault(payload.type, new ArrayList<>());
                for (Function<Object, Void> callback: cbs) {
                    callback.apply(payload.value);
                }
            }
        }
    }
}
