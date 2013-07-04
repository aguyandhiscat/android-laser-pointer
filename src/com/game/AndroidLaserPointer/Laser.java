package com.game.AndroidLaserPointer;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;

public class Laser extends ShapeDrawable {
    private static final String TAG = "AndroidLaserPointerGame";

    public float radius;
    public float diameter;

    private int x;
    private int y;

    private float oscillationPeriod;
    private float totalTimer;

    private ValueAnimator xAnimator;
    private ValueAnimator yAnimator;
    private final long animatorDuration = 500;

    public Laser() {
        super(new OvalShape());

        x = y = -1;
        totalTimer = 0f;
        radius = 40f;
        diameter = radius*2;
        oscillationPeriod = (float) Math.PI/32;
        getPaint().setColor(0x88ff0000);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public Laser directX(int x) {
        this.x = x;
        reBounds();

        Log.i(TAG, "Lx: "+this.x);

        return this;
    }

    public Laser animateX(int x) {
        if(this.x < 0) {
            return directX(x);
        } else {
            xAnimator = ValueAnimator.ofInt(this.x, x);
            xAnimator.setDuration(animatorDuration);
            xAnimator.setRepeatCount(0);
            xAnimator.start();

            xAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    if(animation.isRunning()) {
                        directX(((Integer) animation.getAnimatedValue()).intValue());
                    }
                }
            });

            return this;
        }
    }

    public Laser directY(int y) {
        this.y = y;
        reBounds();

        Log.i(TAG, "Ly: "+this.y);

        return this;
    }

    public Laser animateY(int y) {
        if(this.y < 0) {
            return directY(y);
        } else {
            yAnimator = ValueAnimator.ofInt(this.y, y);
            yAnimator.setDuration(animatorDuration);
            yAnimator.setRepeatCount(0);
            yAnimator.start();

            yAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    if(animation.isRunning()) {
                        directY(((Integer) animation.getAnimatedValue()).intValue());
                    }
                }
            });

            return this;
        }
    }

    public Laser r(float r) {
        radius = r;
        diameter = radius*2;
        reBounds();

        return this;
    }

    public void reBounds() {
        setBounds((int)(x-radius), (int)(y-radius), (int)(x+radius), (int)(y+radius));
    }

    public void step(long deltaTimeMs) {
        totalTimer += deltaTimeMs;

        if(totalTimer >= 1500) {
            Log.i(TAG, "Timer: " + totalTimer);

            // We hit half a second. Lets randomly move the laser
            this.animateX(x + ((int) (50*Math.random()-25)));
            this.animateY(y + ((int) (50*Math.random()-25)));

            totalTimer = 0;
        }

//        float t = oscillationPeriod/(deltaTimeMs*1000);
//        this.r((float)(radius + 3*Math.sin(t)));

    }

    public boolean hit(int x, int y) {
        double dist = Math.sqrt((double) Math.pow(x-this.x, 2) + Math.pow(y-this.y, 2));
        if(dist <= radius) {
            return true;
        }
        return false;
    }
}
