package com.jacobwunder.cs275staticssimulator.threading;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jacobwunder.cs275staticssimulator.R;

public class SimulatorClient {

    private static final String TAG = "HandlerThread";
    private int requestNumber = 0;

    public static int MESSAGE_ID = 0;

    private SimulatorHandlerThread mHandlerThread;
    private Handler mResponseHandler;

    public SimulatorClient() {
        mResponseHandler = new ResponseHandler();

        mHandlerThread = new SimulatorHandlerThread(mResponseHandler);
        mHandlerThread.start();
        mHandlerThread.getLooper();

//        mRequestButton.setOnClickListener((View v) -> {
//            //mStopButton.setEnabled(true);
//            ++requestNumber;
//            String msg = String.format("message %d", requestNumber);
//            mHandlerThread.queueMessage(msg);
//        });
    }

    public void onDestroy() {
        mHandlerThread.clearQueue();
        mHandlerThread.quit();
    }

    private class ResponseHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_ID) {
                String target = (String) msg.obj;
                Log.i(TAG, "got a response: " + target);
//                mTextView.setText(target);
            }
        }
    }
}
