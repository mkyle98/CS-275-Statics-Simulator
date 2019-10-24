package com.jacobwunder.cs275staticssimulator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.jacobwunder.cs275staticssimulator.threading.SimulatorClient;

public class MainActivity extends Activity {

    private CanvasView mCustomCanvas;
    private SimulatorClient mSimulatorClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCustomCanvas = findViewById(R.id.signature_canvas);
        mSimulatorClient = new SimulatorClient();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSimulatorClient.onDestroy();
    }

    public void clearCanvas(View v) {
        mCustomCanvas.clearCanvas();
    }

}