package com.jacobwunder.cs275staticssimulator;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.jacobwunder.cs275staticssimulator.threading.SimulatorClient;
import com.jacobwunder.libstatics.situations.EndLoadedCantileverSituation;


import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toolbar;

public class MainActivity extends Activity {

    private CanvasView mCustomCanvas;
    private SimulatorClient mSimulatorClient;
    private ViewGroup mainLayout;
    private ImageView image;
    private float xDelta;
    private float forceLocationScreen = getScreenCenter();
    private int mScreenOrientation;
    static int screenWidth;


    //static int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScreenOrientation = getResources().getConfiguration().orientation;

        //IF statement required to update the location of the beam based on screen orientation
        if(mScreenOrientation == 1){
            screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        }else if(mScreenOrientation == 2){
            screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        }

        mSimulatorClient = new SimulatorClient();
        mCustomCanvas = findViewById(R.id.signature_canvas);
        mCustomCanvas.setSimulatorClient(mSimulatorClient);
        mCustomCanvas = findViewById(R.id.signature_canvas);
        mainLayout = (FrameLayout) findViewById(R.id.main);
        image = findViewById(R.id.forceArrow);
        image.setOnTouchListener(onTouchListener());

        mSimulatorClient.sendMesage("LoadSituation", EndLoadedCantileverSituation.situationName);
        mSimulatorClient.onReceiveSimulatorMessage("beam update", value -> null);

        mCustomCanvas.setForceArrowLocation(forceLocationScreen);
        mCustomCanvas.setForceLocationOnBeam();

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setProgress(0);
        //seekBar.incrementProgressBy(10);
        seekBar.setMax(200);
        TextView seekBarValue = findViewById(R.id.seekBarValue);
        seekBarValue.setText("Force "+ seekBar.getProgress()+ "N");
        TextView beamLocationValue = findViewById(R.id.beamLocationValue);
        beamLocationValue.setText("Location on Beam: " + mCustomCanvas.getForceLocationOnBeam() + "m");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //progress = progress / 10;
                //progress = progress * 10;
                seekBarValue.setText("Force: " + seekBar.getProgress() +"N");
                mCustomCanvas.setForceArrowAmount(seekBar.getProgress());
                //image.setScaleY(+1);
                mCustomCanvas.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar){}
        });
    }

    //Lucas TODO: create abstraction
    private OnTouchListener onTouchListener() {

        return new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                float x;

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        xDelta = view.getX() - event.getRawX();
                        break;

                    case MotionEvent.ACTION_UP:
                        //Commented out to stop real time updating
//                        mSimulatorClient.sendMesage(
//                            "force location update",
//                            (double) forceLocationScreen / width
//                        );
                        break;

                    case MotionEvent.ACTION_MOVE:
                        x = (int)event.getRawX() + xDelta;
                        // check if the arrow is off beam
                        if ((x <= mCustomCanvas.getBeamX()-image.getWidth()/2 || x >= (mCustomCanvas.getBeamX() + mCustomCanvas.getBeamWidth())-image.getWidth()/2))
                        {
                            break;
                        }
                        view.setX(x);
                        break;

                    default:
                        return false;
                }
                forceLocationScreen = image.getX()+image.getWidth()/2;
                mCustomCanvas.setForceArrowLocation(forceLocationScreen);
                TextView beamLocationValue = findViewById(R.id.beamLocationValue);
                beamLocationValue.setText("Location on Beam: " + mCustomCanvas.getForceLocationOnBeam() + "m");
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

    public static int getScreenCenter(){return screenWidth/2;}

    public void Calculate(View v) {
        mSimulatorClient.sendMesage(
                "force location update",
                (double) mCustomCanvas.getForceArrowLocation() / mCustomCanvas.getBeamWidth()
        );
    }

    public void Reset(View v) {
        this.recreate();
    }


}



