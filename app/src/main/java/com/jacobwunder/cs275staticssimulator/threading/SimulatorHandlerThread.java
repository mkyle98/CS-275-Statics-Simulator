package com.jacobwunder.cs275staticssimulator.threading;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.jacobwunder.libstatics.situations.SimulatorSituation;

public class SimulatorHandlerThread<T> extends HandlerThread {

    private static final String TAG = "SimulatorHandlerThread";
    public static final int MESSAGE_ID = 0;
    private Handler mRequestHandler;
    private Handler mResponseHandler;

    private SimulatorSituation situation;

    public SimulatorHandlerThread(Handler responseHandler) {
        super(TAG);
        mResponseHandler = responseHandler;
    }

    public void queueMessage(T target) {
        Log.i(TAG, "queueMessage " + (String) target);
        mRequestHandler.obtainMessage(MESSAGE_ID, target).sendToTarget();
    }

    @Override
    protected void onLooperPrepared() {
        mRequestHandler = new RequestHandler();
    }

    public void clearQueue() {
        mRequestHandler.removeMessages(MESSAGE_ID);
    }

    private void handleRequest(final T target) {
        Log.i(TAG, "process request " + (String) target);
        String s = (String) target;
        mResponseHandler.obtainMessage(MESSAGE_ID, s).sendToTarget();
    }

    private class RequestHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_ID) {
                T target = (T) msg.obj;
                Log.i(TAG, "got a request: " + target);
                handleRequest(target);
            }
        }
    }
}
