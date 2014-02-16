package io.lxl.android.stupidCalculator.listener;

import android.view.MotionEvent;
import android.view.View;
import io.lxl.android.stupidCalculator.activity.MainActivity;

/**
 * Created by skad on 07/02/14.
 */
public class MyOnTouchListener implements View.OnTouchListener {

    static final String TAG = "MyGestureListener";
    protected MainActivity activity;
    private MyOnTouchListener mNext;
    private MyOnTouchListener mPrevious;

    public MyOnTouchListener(MainActivity activity) {
        this.activity = activity;
    }

    public void setNext(MyOnTouchListener mNext) {
        this.mNext = mNext;
    }

    public void setPrevious(MyOnTouchListener mPrevious) {
        this.mPrevious = mPrevious;
    }

    public MyOnTouchListener previousState() {
        return mPrevious;
    }

    public MyOnTouchListener nextState() {
        return mNext;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}