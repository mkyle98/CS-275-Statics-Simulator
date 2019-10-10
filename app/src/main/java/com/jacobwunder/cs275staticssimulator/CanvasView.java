package com.jacobwunder.cs275staticssimulator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;

public class CanvasView extends View {

    public int width;
    public int height;
    private Bitmap mBitmap;
    private Bitmap testBitmap;
    private Canvas mCanvas;
    private Path mPath;
    Context context;
    private Paint mPaint;
    private Paint pPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;

    private static final int WIDTH = 3;
    private static final int HEIGHT = 3;
    private static final int COUNT = WIDTH * HEIGHT;
    private float[] mVerts = new float[COUNT*2];

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

        // we set a new Path
        mPath = new Path();

        // and we set a new Paint with the desired attributes
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);

        // and we set a new Paint with the desired attributes
        pPaint = new Paint();
        pPaint.setAntiAlias(true);
        pPaint.setColor(Color.BLACK);
        pPaint.setStyle(Paint.Style.STROKE);
        pPaint.setStrokeJoin(Paint.Join.ROUND);
        pPaint.setStrokeWidth(15f);

        //define our bitmap
        testBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.beamp);

    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

//        // your Canvas will draw onto the defined Bitmap
//        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        mCanvas = new Canvas(mBitmap);
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw the mPath with the mPaint on the canvas when onDraw
        canvas.drawPath(mPath, mPaint);

        //DEFINE VERTICES---------------------------------
        float minX = 40;
        float minY = 800;
        float maxX = 1200;
        float maxY = 100;

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                mVerts[i * 2 * HEIGHT + (2 * j)    ] = maxX / WIDTH * j + minX;
                mVerts[i * 2 * HEIGHT + (2 * j) + 1] = maxY / HEIGHT * i + minY;
            }
        }

        System.out.println(Arrays.toString(mVerts));

        //DRAW BITMAP MESH---------------------------------
        canvas.drawBitmapMesh(testBitmap, WIDTH - 1,HEIGHT - 1, mVerts,0, null,0,mPaint);

        //DRAW POINTS--------------------------------------
        for (int i = 0; i < mVerts.length; i += 2) {
            canvas.drawPoint(mVerts[i], mVerts[i + 1], pPaint);
        }
    }

    // when ACTION_DOWN start touch according to the x,y values
    private void startTouch(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    // when ACTION_MOVE move touch according to the x,y values
    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    public void clearCanvas() {
        mPath.reset();
        invalidate();
    }

    // when ACTION_UP stop touch
    private void upTouch() {
        mPath.lineTo(mX, mY);
    }

    //override the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }
}