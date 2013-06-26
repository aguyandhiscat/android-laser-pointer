package com.game.AndroidLaserPointer;

import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;

public class Laser extends ShapeDrawable {
    private static final String TAG = "AndroidLaserPointerGame";

    public final int radius = 40;
    public final int diameter = 80;

    private int x;
    private int y;


    public Laser() {
        super(new OvalShape());

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

    public void reBounds() {
        setBounds((int)x-radius, (int)y-radius, (int)x+radius, (int)y+radius);
    }

    public void step(long deltaTimeMs) { }

    public boolean hit(int x, int y) {
        double dist = Math.sqrt((double) Math.pow(x-this.x, 2) + Math.pow(y-this.y, 2));
        if(dist <= radius) {
            return true;
        }
        return false;
    }
}
