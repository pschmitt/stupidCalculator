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
public class NumberInputTouchListener extends MyOnTouchListener {
    private static final String TAG = "NumberTouchListener";

    static final int INPUT_THRESHOLD = 0;
    static final int NUMBER_INCREASE_THRESHOLD = 100;
    private double downX;
    private double downY;
    private int nb;

    public NumberInputTouchListener(MainActivity mainActivity) {
        super(mainActivity);
        nb = 0;
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

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                this.downX = event.getX();
                this.downY = event.getY();
                nb = 0;
                this.activity.updateInputView(nb);
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                Vector2D swipe = MathUtils.vectorFromPoint(downX, downY, event.getX(), event.getY());

                double length = swipe.length();
                if (length > NUMBER_INCREASE_THRESHOLD) {
                    if (nb < 9) {
                        this.nb++;
                    } else {
                        nb = 0;
                    }
                    this.downX = event.getX();
                    this.downY = event.getY();
                    this.activity.updateInputView(nb);
                }
                return true;
            }
            case MotionEvent.ACTION_UP:
                Vector2D swipe = MathUtils.vectorFromPoint(downX, downY, event.getX(), event.getY());
                double length = swipe.length();
                if (length > INPUT_THRESHOLD) {
                    this.activity.add(new io.lxl.android.stupidCalculator.model.Number(nb));
                    this.nb = 0;
                }
                return true;
        }
        return false;
    }
}
