package com.jacobwunder.cs275staticssimulator;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.jacobwunder.cs275staticssimulator.threading.SimulatorClient;
import com.jacobwunder.libstatics.situations.UniformCantileverSituation;


import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends Activity {


    private CanvasView mCustomCanvas;
    private SimulatorClient mSimulatorClient;
    private ViewGroup mainLayout;
    private ImageView image;
    private int xDelta;
    private int forceLocation = 720;
    private int forceAmount = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSimulatorClient = new SimulatorClient();
        mSimulatorClient.sendMesage("LoadSituation", UniformCantileverSituation.situationName);

        mSimulatorClient.onReceiveSimulatorMessage("PI Returned", value -> {
            System.out.println("Getting the pi returned" + (float) value);
            return null;
        });

        mSimulatorClient.onReceiveSimulatorMessage("beam update", value -> {


            return null;
        });

        mCustomCanvas = findViewById(R.id.signature_canvas);
        mCustomCanvas.setSimulatorClient(mSimulatorClient);
        mCustomCanvas = findViewById(R.id.signature_canvas);
        mainLayout = (FrameLayout) findViewById(R.id.main);
        image = findViewById(R.id.forceArrow);
        image.setOnTouchListener(onTouchListener());

        mCustomCanvas.setForceArrowAmount(forceAmount);
        mCustomCanvas.setForceArrowLocation(forceLocation);
    }

    private OnTouchListener onTouchListener() {
        return new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;

                int x = (int) event.getRawX();
                forceLocation = (int) event.getRawX();
                mCustomCanvas.setForceArrowLocation(forceLocation);

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        break;

                    case MotionEvent.ACTION_UP:
                        mSimulatorClient.sendMesage(
                            "force location update",
                            (double) forceLocation / width
                        );
                        break;

                    case MotionEvent.ACTION_MOVE:
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }

                mainLayout.invalidate();
                mCustomCanvas.invalidate();
                return true;
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSimulatorClient.onDestroy();
    }
}