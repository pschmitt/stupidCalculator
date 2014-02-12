package io.lxl.android.stupidCalculator.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import io.lxl.android.stupidCalculator.R;
import io.lxl.android.stupidCalculator.listener.MyOnTouchListener;
import io.lxl.android.stupidCalculator.model.Number;
import io.lxl.android.stupidCalculator.model.Operation;
import io.lxl.android.stupidCalculator.model.Operator;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends Activity implements Observer {


    private static final String TAG = "Velocity";
    private TextView mActualView;
    private TextView mCalculusView;
    private Operation mOperation;
    private boolean mIsRegisteredAsObserver = false;
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
        registerObserver();

        // Test
        //mOperation.addObject(new Number(9));
        //mOperation.addObject(new Operator(Operator.TYPE.MULT));
        //mOperation.addObject(new Number(7));
        // mOperation.addObject(new EqualOperator());
        //mCalculusView.setText(mOperation.toString() + " = " + mOperation.getResult());
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterObserver();
    }

    private void unregisterObserver() {
        if (mIsRegisteredAsObserver)
            mOperation.deleteObserver(this);
    }

    private void registerObserver() {
        if (!mIsRegisteredAsObserver)
            mOperation.addObserver(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_reset:
                mOperation.reset();
                mCalculusView.setText("");
                return true;
        }
        return false;
    }

    public void Updatechar(int nb){
        mActualView.setText(Integer.toString(nb));
    }

    public void AddingNB(int nb){
        mOperation.addObject(new Number(nb));
        mCalculusView.setText(mOperation.toString());
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

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof Operation) {
            mCalculusView.setText(mOperation.toString());
        }
    }
}
