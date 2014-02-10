package io.lxl.android.stupidCalculator.listener;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import io.lxl.android.stupidCalculator.MathUtils;
import io.lxl.android.stupidCalculator.activity.MainActivity;
import io.lxl.android.stupidCalculator.model.Operator;
import io.lxl.android.stupidCalculator.model.Vector2D;

/**
 * Created by skad on 07/02/14.
 */
public class MyOnTouchListener implements View.OnTouchListener {

    static final String Tag = "MyGestureListener";
    private MainActivity activity;
    static final int MIN_DISTANCE = 100;
    private double downX;
    private double downY;
    //private ArrayList<Vector2D> listpoints;
    int nb = 0;

    public MyOnTouchListener(MainActivity activity){
        //listpoints = new ArrayList<Vector2D>();
        this.activity = activity;
        this.nb = 0;
        this.downX = 0;
        this.downY = 0;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int maskedAction = event.getActionMasked();
        int nbptAction = event.getPointerCount();

        Log.d(Tag, "Evt action  = " + maskedAction);
        Log.d(Tag, "Evt nb touch = " + nbptAction);

        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN: {
                //listpoints.add(new Vector2D(event.getX(),event.getY()));
                this.activity.Updatechar(nb);
                return true;
            }
            case MotionEvent.ACTION_MOVE:{
                Vector2D swipe = MathUtils.vectorFromPoint(downX, downY, event.getX(), event.getY());

                double lengt = swipe.length();
                if (lengt>MIN_DISTANCE && nb < 9)
                {
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
                    Log.d(Tag, "Angle = " + angle);

                    if (angle>45.0)
                    {
                        nb++;
                        listpoints.clear();
                        listpoints.add(new Vector2D(event.getX(),event.getY()));
                        Log.d(Tag, "nb = " + nb);
                    }
                }*/
                return true;
            }
            case MotionEvent.ACTION_UP:{
                if(this.activity.isChiffre())
                {
                    this.activity.AddingNB(nb);

                }
                else
                {
                     this.activity.AddingOP(Operator.TYPE.PLUS);
                }
                this.nb = 0;
                return true;
            }
            case MotionEvent.ACTION_POINTER_UP:{
                switch(nbptAction ){
                    case 2:
                        this.activity.AddingOP(Operator.TYPE.MINUS);
                        return true;
                    case 3:
                        this.activity.AddingOP(Operator.TYPE.MULT);
                        return true;
                    case 4:
                        this.activity.AddingOP(Operator.TYPE.DIV);
                        return true;
                }
            }
        }
        return false;
    }

}