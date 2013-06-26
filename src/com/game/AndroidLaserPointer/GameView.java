package com.game.AndroidLaserPointer;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class GameView extends View {
    private static final String TAG = "AndroidLaserPointerGame";

    private Laser mLaser;
    private TextView mPointsCounter;

    private int mPoints;

    private int MAX_X;
    private int MAX_Y;

    private float mDownX;
    private float mDownY;
    private float mLastX;
    private float mLastY;
    private boolean isOnClick;
    private boolean isOnDrag;
    private final float SCROLL_TRESHOLD = 10f;

    private long then;

    private RefreshHandler mRedrawHandler = new RefreshHandler();


    class RefreshHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            GameView.this.update();
        }

        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    };

    public GameView(Context context) {
        super(context);

        if(!isInEditMode()) {
            initView(context);
        }
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if(!isInEditMode()) {
            initView(context);
        }
    }

    private void initView(Context context) {
        mLaser = new Laser();
        MAX_X = MAX_Y = 0;

        mLaser.x((int)mLaser.radius).y((int)mLaser.radius);
        update();
    }

    public void initNewGame() {
        updatePoints(0);
    }

    public void updatePoints(int pts) {
        mPoints += pts;

        DecimalFormat formatter = new DecimalFormat("###,###");
        mPointsCounter.setText(""+formatter.format(mPoints));
    }

    public void setDependentViews(TextView pointsCounter) {
        mPointsCounter = pointsCounter;
    }

    public void update() {
        long now = System.currentTimeMillis();

        long difference = now - then;

        then = now;

        mLaser.step(difference);

        invalidate();

        mRedrawHandler.sleep(60);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(!isInEditMode()) {
            mLaser.draw(canvas);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(!isInEditMode()) {
            MAX_X = MeasureSpec.getSize(widthMeasureSpec);
            MAX_Y = MeasureSpec.getSize(heightMeasureSpec);
        }
    }

    public void randomLocation() {
        mLaser.x((int) (mLaser.radius + Math.random()*(MAX_X-mLaser.diameter)));
        mLaser.y((int) (mLaser.radius + Math.random()*(MAX_Y-mLaser.diameter)));
    }

    private void laserHit() {
        updatePoints(1000);
        randomLocation();
    }

    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                isOnClick = true;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (isOnClick) {
                    onTouch(ev, mDownX, mDownY);
                }
                isOnClick = isOnDrag = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if(isDragging(ev, mDownX, mDownY)) {
                    onTouchDrag(ev, mDownX, mDownY, mLastX, mLastY);
                    isOnDrag = true;
                    isOnClick = false;
                }
                break;
            default:
                break;
        }

        mLastX = ev.getX();
        mLastY = ev.getY();

        return true;
    }

    private boolean isDragging(MotionEvent ev, float xDown, float yDown) {
        return isOnDrag ||
                (isOnClick && Math.abs(xDown - ev.getX()) > SCROLL_TRESHOLD || Math.abs(yDown - ev.getY()) > SCROLL_TRESHOLD);
    }

    public void onTouchDrag(MotionEvent ev, float xDown, float yDown, float xLast, float yLast) { }

    public void onTouch(MotionEvent ev, float xDown, float yDown) {
        Log.i(TAG, "x: "+xDown);
        Log.i(TAG, "y: "+yDown);

        if(mLaser.hit((int) xDown, (int) yDown)) {
            Log.i(TAG, "Refresh");
            laserHit();
        }
    }
}
