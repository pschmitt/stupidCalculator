package io.lxl.android.stupidCalculator.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import io.lxl.android.stupidCalculator.R;
import io.lxl.android.stupidCalculator.model.EqualOperator;
import io.lxl.android.stupidCalculator.model.Number;
import io.lxl.android.stupidCalculator.model.Operation;
import io.lxl.android.stupidCalculator.model.Operator;


public class MainActivity extends Activity {

    private TextView mCalculusView;
    private Operation mOperation;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Retain views
        mCalculusView = (TextView) findViewById(R.id.text_calculus);

        // Init
        mOperation = new Operation();

        // Test
        mOperation.addObject(new Number(1));
        mOperation.addObject(new Operator(Operator.TYPE.PLUS));
        mOperation.addObject(new Number(4));
        mOperation.addObject(new EqualOperator());
        mCalculusView.setText(mOperation.toString());
    }
}
