package com.game.AndroidLaserPointer;

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


    public Laser() {
        super(new OvalShape());

        radius = 40f;
        diameter = radius*2;
        getPaint().setColor(0x88ff0000);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public Laser x(int x) {
        this.x = x;
        reBounds();

        Log.i(TAG, "Lx: "+this.x);

        return this;
    }

    public Laser y(int y) {
        this.y = y;
        reBounds();

        Log.i(TAG, "Ly: "+this.y);

        return this;
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
        long t = System.currentTimeMillis();
        this.r((float)(radius+3*Math.sin(t)));
    }

    public boolean hit(int x, int y) {
        double dist = Math.sqrt((double) Math.pow(x-this.x, 2) + Math.pow(y-this.y, 2));
        if(dist <= radius) {
            return true;
        }
        return false;
    }
}
