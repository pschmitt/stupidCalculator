package io.lxl.android.stupidCalculator.listener;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import io.lxl.android.stupidCalculator.MathUtils;
import io.lxl.android.stupidCalculator.model.Vector2D;

/**
 * Created by skad on 07/02/14.
 */
public class MyOnTouchListener implements View.OnTouchListener {

    static final String Tag = "MyGestureListener";
    private Activity activity;
    static final int MIN_DISTANCE = 100;
    private float downX, downY;
    int nb = 0;

    public MyOnTouchListener(Activity activity){
        this.activity = activity;
        nb = 0;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_MOVE:{
                Vector2D swipe = MathUtils.vectorFromPoint(downX,downY,event.getX(), event.getY());
                double lengt = swipe.length();
                if (lengt>MIN_DISTANCE)
                {
                      nb++;
                      downX = event.getX();
                      downY = event.getY();

                }
                Log.i(Tag, "nb = " + nb);

                return true;
            }
        }
        return false;
    }

}