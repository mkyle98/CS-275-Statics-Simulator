package com.jacobwunder.cs275staticssimulator;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Dimension;

import com.jacobwunder.cs275staticssimulator.threading.SimulatorClient;
import com.jacobwunder.libstatics.Beam;
import com.jacobwunder.libstatics.Point;

public class CanvasView extends View {

    private Bitmap beamBitmap;
    Context context;
    private Paint mPaint;
    private Paint pPaint;


    private static final int WIDTH = Beam.POINT_COUNT;
    private static final int HEIGHT = Beam.POINT_COUNT;
    private static final int COUNT = (WIDTH) * (HEIGHT);
    private float[] mBitmapVerts = new float[COUNT*2];

    private Point[] mBeamMesh;

    private SimulatorClient mSimulatorClient;
    private int forceArrowLocation;
    private int forceArrowAmount;

    private int beamX;
    private int beamWidth;
    private int beamHeight;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

        beamBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.beamp);
        mPaint = new Paint();
        pPaint = new Paint();
    }

    public void setSimulatorClient(SimulatorClient simulatorClient) {
        mSimulatorClient = simulatorClient;

        mSimulatorClient.onReceiveSimulatorMessage("beam update", value -> {
            Beam beam = (Beam) value;
            mBeamMesh = beam.getMesh();

            invalidate();
            return null;
        });
    }

    public void drawMesh(Canvas canvas) {

        //DEFINE BEAM SIZE: setBeamSize(int width, int height) in meters
        setBeamSize(10,1);

        //DEFINE VERTICES---------------------------------
        float minX = getBeamX();
        float minY = 800;
        float sizeX = getBeamWidth();
        float sizeY = getBeamHeight();

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {

                mBitmapVerts[i * 2 * HEIGHT + (2 * j)    ] = sizeX / (WIDTH - 1)  * j + minX;
                mBitmapVerts[i * 2 * HEIGHT + (2 * j) + 1] = sizeY / (HEIGHT - 1) * i + minY;

                if (mBeamMesh != null) {
                    mBitmapVerts[i * 2 * HEIGHT + (2 * j)    ] += mBeamMesh[j].getX();
                    mBitmapVerts[i * 2 * HEIGHT + (2 * j) + 1] += mBeamMesh[j].getY();
                }
            }
        }

        canvas.drawBitmapMesh(
            beamBitmap,
            WIDTH - 1,
            HEIGHT - 1,
            mBitmapVerts,
            0,
            null,
            0,
            mPaint
        );
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        drawMesh(canvas);
        mPaint.setTextSize(50);
        canvas.drawText("Location on Screen: "+ getForceArrowLocation(), 50, 50, mPaint);
        canvas.drawText("Location on Beam: "+ (getForceArrowLocation()-getBeamX()), 50, 150, mPaint);
        canvas.drawText("Force: "+ getForceArrowAmount()+"N", 50, 250, mPaint);

        for (int i = 0; i < mBitmapVerts.length; i += 2) {
            canvas.drawPoint(mBitmapVerts[i], mBitmapVerts[i + 1], pPaint);
        }
    }

    public void setForceArrowLocation(int location){forceArrowLocation = location;}

    public void setForceArrowAmount(int newtons){forceArrowAmount = newtons;}

    public int getForceArrowLocation(){return forceArrowLocation;}

    public int getForceArrowAmount(){return forceArrowAmount;}

    public void setBeamSize(int width, int height){beamWidth = width; beamHeight = height;}

    public int getBeamWidth(){return beamWidth*100;}

    public int getBeamHeight(){return beamHeight*100;}

    public int getBeamX(){return beamX = MainActivity.getScreenCenter()-(getBeamWidth()/2);}

}