package io.lxl.android.stupidCalculator.listener;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import io.lxl.android.stupidCalculator.activity.MainActivity;
import io.lxl.android.stupidCalculator.model.Vector2D;
import io.lxl.android.stupidCalculator.utils.MathUtils;

/**
 * Created by pschmitt on 2/14/14.
 */
public class EqualInputListener extends MyOnTouchListener {
    private static final String TAG = "EqTouchListener";


    static final int EQUAL_INPUT_THRESHOLD = 200;
    private double downX;
    private double downY;

    public EqualInputListener(MainActivity context) {
        super(context);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        int actionIndex = event.getActionIndex();
        int maskedAction = event.getActionMasked();
        int nbptAction = event.getPointerCount();

        Log.d(TAG, "Evt action = " + action);
        Log.d(TAG, "Evt action mask = " + maskedAction);
        Log.d(TAG, "Evt action index = " + actionIndex);
        Log.d(TAG, "Evt nb touch = " + nbptAction);

        /*if (nbptAction != 2) {
            return false;
        }*/

        switch (maskedAction) {
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "Moving with 2 fingers");
                return true;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "Cancel 2 fingers");
                return true;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d(TAG, "UP 2 fingers");
                Vector2D swipe = MathUtils.vectorFromPoint(downX, downY, event.getX(), event.getY());
                double length = swipe.length();
                if (length > EQUAL_INPUT_THRESHOLD) {
                    // this.activity.add(new EqualOperator());
                    activity.requestCalculation();
                }
                return true;
            case MotionEvent.ACTION_POINTER_DOWN:
                downX = event.getX();
                downY = event.getY();
                Log.d(TAG, "DOWN 2 fingers");
                return true;
        }

        // FIXME
        return true;
    }
}
