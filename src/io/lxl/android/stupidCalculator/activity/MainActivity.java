package io.lxl.android.stupidCalculator.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import io.lxl.android.stupidCalculator.R;
import io.lxl.android.stupidCalculator.listener.MyOnTouchListener;
import android.widget.TextView;
import io.lxl.android.stupidCalculator.model.Number;
import io.lxl.android.stupidCalculator.model.Operation;
import io.lxl.android.stupidCalculator.model.Operator;

public class MainActivity extends Activity {


    private static final String DEBUG_TAG = "Velocity";
    private TextView mActualView;
    private TextView mCalculusView;
    private Operation mOperation;
    private boolean chiffre = true;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        LinearLayout mainLayout = (LinearLayout)this.findViewById(R.id.main);
        MyOnTouchListener activitySwipeDetector = new MyOnTouchListener(this);
        mainLayout.setOnTouchListener(activitySwipeDetector);

        // Retain views
        mCalculusView = (TextView) findViewById(R.id.text_calculus);
        mActualView = (TextView) findViewById(R.id.actual_calculus);

        // Init
        mOperation = new Operation();

        // Test
        //mOperation.addObject(new Number(9));
        //mOperation.addObject(new Operator(Operator.TYPE.MULT));
        //mOperation.addObject(new Number(7));
        // mOperation.addObject(new EqualOperator());
        //mCalculusView.setText(mOperation.toString() + " = " + mOperation.getResult());
    }

    public void Updatechar(int nb){
        mActualView.setText(Integer.toString(nb));
    }

    public void AddingNB(int nb){
        mOperation.addObject(new Number(nb));
        mCalculusView.setText(mOperation.toString() + " = ");
        mActualView.setText("");
        this.chiffre = false;
    }

    public void AddingOP(Operator.TYPE op){
        mOperation.addObject(new Operator(op));
        this.chiffre = true;
    }

    public boolean isChiffre()
    {
        return chiffre;
    }
}
