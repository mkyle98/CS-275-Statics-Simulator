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

    private static final int WIDTH = 2;
    private static final int HEIGHT = 2;
    private static final int COUNT = (WIDTH + 1) * (HEIGHT + 1);
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

        //SOME TESTS
        /*
        canvas.drawRect(50, 150,150,120, mPaint);
        canvas.drawBitmap(testBitmap, 60,100,null);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        canvas.drawText("HELLO", 500, 500, paint);
        */

        // draw the mPath with the mPaint on the canvas when onDraw
        canvas.drawPath(mPath, mPaint);

        //DEFINE VERTICES---------------------------------
        int x1= 40;
        int x2= 520;
        int x3= 1040;
        int y1=800;
        int y2=900;
        int y3=1000;

        //ROW 1
        mVerts[0]= x1;     //x0    1
        mVerts[1]= y1;     //y0    2
        mVerts[2]= x2;     //x1    3
        mVerts[3]= y1;     //y1    4
        mVerts[4]= x3;     //x2    5
        mVerts[5]= y1;     //y2    6
        //ROW 2
        mVerts[6]= x1;     //x3    7
        mVerts[7]= y2;     //y3    8
        mVerts[8]= x2;     //x4    9
        mVerts[9]= y2;     //y4    10
        mVerts[10]= x3;     //x5    11
        mVerts[11]= y2;     //y5    12
        //ROW 3
        mVerts[12]= x1;     //x6    13
        mVerts[13]= y3;     //y6    14
        mVerts[14]= x2;     //x7    15
        mVerts[15]= y3;     //y7    16
        mVerts[16]= x3;     //x8    17
        mVerts[17]= y3;     //y8    18

        //DRAW BITMAP MESH---------------------------------
        canvas.drawBitmapMesh(testBitmap, WIDTH,HEIGHT, mVerts,0, null,0,mPaint);

        //DRAW POINTS--------------------------------------
        canvas.drawPoint(x1,y1,pPaint);
        canvas.drawPoint(x2,y1,pPaint);
        canvas.drawPoint(x3,y1,pPaint);
        canvas.drawPoint(x1,y2,pPaint);
        canvas.drawPoint(x2,y2,pPaint);
        canvas.drawPoint(x3,y2,pPaint);
        canvas.drawPoint(x1,y3,pPaint);
        canvas.drawPoint(x2,y3,pPaint);
        canvas.drawPoint(x3,y3,pPaint);

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