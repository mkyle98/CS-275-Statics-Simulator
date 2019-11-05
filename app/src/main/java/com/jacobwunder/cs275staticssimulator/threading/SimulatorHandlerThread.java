package com.jacobwunder.cs275staticssimulator.threading;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.jacobwunder.libstatics.UpdatePayload;
import com.jacobwunder.libstatics.situations.SimulatorSituation;
import com.jacobwunder.libstatics.situations.SinglePointCantileverSituation;
import com.jacobwunder.libstatics.situations.UniformCantileverSituation;

public class SimulatorHandlerThread extends HandlerThread {

    private static final String TAG = "SimulatorHandlerThread";
    public static final int MESSAGE_ID = 0;
    private Handler mRequestHandler;
    private Handler mResponseHandler;

    private SimulatorSituation situation;

    public SimulatorHandlerThread(Handler responseHandler) {
        super(TAG);
        mResponseHandler = responseHandler;
    }

    @Override
    protected void onLooperPrepared() {
        mRequestHandler = new RequestHandler();
    }

    public void clearQueue() {
        mRequestHandler.removeMessages(MESSAGE_ID);
    }

    private void handleRequest(final UpdatePayload payload) {

        Log.i(TAG, "handling request (backend): " + payload);

        if (payload.type.equals("LoadSituation")) {
            String v = (String) payload.value;

            if (v.equals(UniformCantileverSituation.situationName)) {
                situation = new UniformCantileverSituation();
            }

            if (v.equals(SinglePointCantileverSituation.situationName)) {
                situation = new SinglePointCantileverSituation();
            }

            situation.setSendUpdateCallback((x -> {
                mResponseHandler.obtainMessage(MESSAGE_ID, x).sendToTarget();
                return null;
            }));
        } else {
            situation.handleUpdate(payload.type, payload.value);
        }
    }

    public void createAndSendMessageToSelf(String type, Object value) {
        UpdatePayload payload = new UpdatePayload(type, value);
        System.out.println("HELLOOOO" + payload);
        System.out.println("mRequestHandler: " + mRequestHandler);
        mRequestHandler.obtainMessage(MESSAGE_ID, payload).sendToTarget();
    }

    private class RequestHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_ID) {
                UpdatePayload payload = (UpdatePayload) msg.obj;
                Log.i(TAG, "got a request (backend): " + payload);
                handleRequest(payload);
            }
        }
    }
}
