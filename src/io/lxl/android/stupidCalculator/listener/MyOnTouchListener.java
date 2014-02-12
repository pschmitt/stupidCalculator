package io.lxl.android.stupidCalculator.listener;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import io.lxl.android.stupidCalculator.activity.MainActivity;
import io.lxl.android.stupidCalculator.model.EqualOperator;
import io.lxl.android.stupidCalculator.model.Operator;
import io.lxl.android.stupidCalculator.model.Vector2D;
import io.lxl.android.stupidCalculator.utils.MathUtils;

/**
 * Created by skad on 07/02/14.
 */
public class MyOnTouchListener implements View.OnTouchListener {

    static final String TAG = "MyGestureListener";
    private MainActivity activity;
    static final int MIN_DISTANCE = 500;
    private double downX;
    private double downY;
    //private ArrayList<Vector2D> listpoints;
    int nb = 0;
    private boolean mRequestedResult = false;
    private long mTimeRequestResult;

    public MyOnTouchListener(MainActivity activity) {
        //listpoints = new ArrayList<Vector2D>();
        this.activity = activity;
        this.nb = 0;
        this.downX = 0;
        this.downY = 0;
    }

    public void reset() {
        mRequestedResult = false;
    }

    private boolean oneFingerAction(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                //listpoints.add(new Vector2D(event.getX(),event.getY()));
                this.activity.Updatechar(nb);
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                Vector2D swipe = MathUtils.vectorFromPoint(downX, downY, event.getX(), event.getY());

                double length = swipe.length();
                if (length > MIN_DISTANCE) {
                    if (nb < 9) {
                        this.nb++;
                    } else {
                        nb = 0;
                    }
                    this.downX = event.getX();
                    this.downY = event.getY();
                    this.activity.Updatechar(nb);
                }

                /*listpoints.add(new Vector2D(event.getX(),event.getY()));

                if(listpoints.size()>=4)
                {   Vector2D lastpt = listpoints.get(listpoints.size()-1);
                    Vector2D lastptminus = listpoints.get(listpoints.size()-2);
                    Vector2D lastptminus2 = listpoints.get(listpoints.size()-3);
                    Vector2D lastptminus3 = listpoints.get(listpoints.size()-4);

                    Vector2D last = MathUtils.vectorFromPoint(lastptminus.getX(),lastptminus.getY(),lastpt.getX(),lastpt.getY());
                    Vector2D beforelast = MathUtils.vectorFromPoint(lastptminus3.getX(),lastptminus3.getY(),lastptminus2.getX(),lastptminus2.getY());

                    double angle = MathUtils.angleFromTwoPoint((int)beforelast.getX(),(int)beforelast.getY(),(int)last.getX(),(int)last.getY());
                    Log.d(TAG, "Angle = " + angle);

                    if (angle>45.0)
                    {
                        nb++;
                        listpoints.clear();
                        listpoints.add(new Vector2D(event.getX(),event.getY()));
                        Log.d(TAG, "nb = " + nb);
                    }
                }*/
                return true;
            }
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                if (this.activity.isChiffre()) {
                    this.activity.AddingNB(new io.lxl.android.stupidCalculator.model.Number(nb));
                } else {
                    switch (event.getPointerCount()) {
                        case 1:
                            this.activity.AddingOP(new Operator(Operator.TYPE.PLUS));
                            return true;
                        /*case 2:
                            this.activity.AddingOP(new Operator(Operator.TYPE.MINUS));
                            return true;
                        case 3:
                            this.activity.AddingOP(new Operator(Operator.TYPE.MULT));
                            return true;
                        case 4:
                            this.activity.AddingOP(new Operator(Operator.TYPE.DIV));
                            return true; */
                    }
                }
                this.nb = 0;
                return true;
        }
        return false;
    }

    private boolean twoFingerAction(MotionEvent event) {
        // TODO: Handle MINUS input
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "Moving with 2 fingers");
                return true;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "Cancel 2 fingers");
                return true;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "UP 2 fingers");
                this.activity.AddingOP(new EqualOperator());
                mTimeRequestResult = System.currentTimeMillis();
                mRequestedResult = true;
                return true;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d(TAG, "DOWN 2 fingers");
                return true;
        }

        return false;
    }

    private boolean threeFinderAction(MotionEvent event) {
        Log.d(TAG, "3 finger action");
        switch (event.getAction()) {
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                this.activity.AddingOP(new Operator(Operator.TYPE.MULT));
                return true;
        }
        return false;
    }

    private boolean fourFingerAction(MotionEvent event) {
        Log.d(TAG, "4 finger action");
        switch (event.getAction()) {
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                this.activity.AddingOP(new Operator(Operator.TYPE.DIV));
                return true;
        }
        return false;
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

        if (mRequestedResult) {
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
                if (System.currentTimeMillis() - mTimeRequestResult > 3000) {
                    activity.reset(); // Note: this sets mRequestedResult to false
                }
                return true;
            }
            return false;
        }

        switch (nbptAction) {
            case 1:
                return oneFingerAction(event);
            case 2:
                return twoFingerAction(event);
            case 3:
                return threeFinderAction(event);
            case 4:
                return fourFingerAction(event);
        }

        return false;
    }

}