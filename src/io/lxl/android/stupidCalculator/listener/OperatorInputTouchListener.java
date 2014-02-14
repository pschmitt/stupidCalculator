package io.lxl.android.stupidCalculator.listener;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import io.lxl.android.stupidCalculator.activity.MainActivity;
import io.lxl.android.stupidCalculator.model.Operator;

/**
 * Created by pschmitt on 2/14/14.
 */
public class OperatorInputTouchListener extends MyOnTouchListener {
    private static final String TAG = "OpTouchListener";

    public OperatorInputTouchListener(MainActivity mainActivity) {
        super(mainActivity);
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

        boolean actionTreated = false;

        switch (action) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                switch (nbptAction) {
                    case 1:
                        this.activity.AddingOP(new Operator(Operator.TYPE.PLUS));
                        break;
                    case 2:
                        this.activity.AddingOP(new Operator(Operator.TYPE.MINUS));
                        break;
                    case 3:
                        this.activity.AddingOP(new Operator(Operator.TYPE.MULT));
                        break;
                    case 4:
                        this.activity.AddingOP(new Operator(Operator.TYPE.DIV));
                        break;
                }
                actionTreated = true;
                break;
        }

        return actionTreated;
    }
}