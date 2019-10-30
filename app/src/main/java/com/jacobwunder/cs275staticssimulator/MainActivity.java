package com.jacobwunder.cs275staticssimulator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private CanvasView customCanvas;
    private ViewGroup mainLayout;
    private ImageView image;
    private int xDelta;
    private int forceLocation = 720;
    private int forceAmount = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
        mainLayout = (FrameLayout) findViewById(R.id.main);
        image = (ImageView) findViewById(R.id.forceArrow);
        image.setOnTouchListener(onTouchListener());

        customCanvas.setForceArrowAmount(forceAmount);
        customCanvas.setForceArrowLocation(forceLocation);
    }

    private OnTouchListener onTouchListener() {
        return new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                forceLocation = (int) event.getRawX();

                customCanvas.setForceArrowLocation(forceLocation);

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        break;

                    case MotionEvent.ACTION_UP:
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
                return true;
            }
        };
    }

    public void clearCanvas(View v) {
        customCanvas.clearCanvas();
    }

}