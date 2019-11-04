package com.jacobwunder.cs275staticssimulator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jacobwunder.cs275staticssimulator.threading.SimulatorClient;
import com.jacobwunder.libstatics.situations.UniformCantileverSituation;

public class MainActivity extends Activity {

    private CanvasView mCustomCanvas;
    private SimulatorClient mSimulatorClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSimulatorClient = new SimulatorClient();
        System.out.println("Loading situation!!!!!!!");
        mSimulatorClient.sendMesage("LoadSituation", UniformCantileverSituation.situationName);

        mSimulatorClient.onReceiveSimulatorMessage("PI Returned", value -> {
            System.out.println("Getting the pi returned" + (float) value);
            return null;
        });

        mCustomCanvas = findViewById(R.id.signature_canvas);
        mCustomCanvas.setSimulatorClient(mSimulatorClient);
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