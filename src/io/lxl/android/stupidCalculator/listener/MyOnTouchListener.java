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
    private boolean mTwoFingerSwipe = false;

    public MyOnTouchListener(MainActivity activity) {
        //listpoints = new ArrayList<Vector2D>();
        this.activity = activity;
        this.nb = 0;
        this.downX = 0;
        this.downY = 0;
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
                if (length > MIN_DISTANCE && nb < 9) {
                    this.nb++;
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
            case MotionEvent.ACTION_UP: {
                if (this.activity.isChiffre()) {
                    this.activity.AddingNB(new io.lxl.android.stupidCalculator.model.Number(nb));
                } else {
                    this.activity.AddingOP(new Operator(Operator.TYPE.PLUS));
                }
                this.nb = 0;
                return true;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                switch (event.getPointerCount()) {
                    case 2:
                        this.activity.AddingOP(new Operator(Operator.TYPE.MINUS));
                        return true;
                    case 3:
                        this.activity.AddingOP(new Operator(Operator.TYPE.MULT));
                        return true;
                    case 4:
                        this.activity.AddingOP(new Operator(Operator.TYPE.DIV));
                        return true;
                }
            }
        }
        return false;
    }

    private boolean twoFingerAction(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "Moving with 2 fingers");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "Cancel 2 fingers");
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "UP 2 fingers");
                this.activity.AddingOP(new EqualOperator());
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d(TAG, "DOWN 2 fingers");
                break;
        }

        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int maskedAction = event.getActionMasked();
        int nbptAction = event.getPointerCount();

        Log.d(TAG, "Evt action  = " + maskedAction);
        Log.d(TAG, "Evt nb touch = " + nbptAction);

        switch (nbptAction) {
            case 1:
                return oneFingerAction(event);
            case 2:
                return twoFingerAction(event);
        }
        return false;
    }

}