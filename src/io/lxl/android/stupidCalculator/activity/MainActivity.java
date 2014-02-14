package io.lxl.android.stupidCalculator.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import io.lxl.android.stupidCalculator.R;
import io.lxl.android.stupidCalculator.listener.EqualInputListener;
import io.lxl.android.stupidCalculator.listener.MyOnTouchListener;
import io.lxl.android.stupidCalculator.listener.NumberInputTouchListener;
import io.lxl.android.stupidCalculator.listener.OperatorInputTouchListener;
import io.lxl.android.stupidCalculator.model.EqualOperator;
import io.lxl.android.stupidCalculator.model.Number;
import io.lxl.android.stupidCalculator.model.Operation;
import io.lxl.android.stupidCalculator.model.Operator;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends Activity implements Observer {

    private static final String TAG = "MainActivity";
    private TextView mActualView;
    private TextView mCalculusView;
    private Operation mOperation;
    private MyOnTouchListener mCurrentTouchListener;
    private MyOnTouchListener mPreviousTouchListener;
    private MyOnTouchListener mFirstTouchListener;
    private boolean mIsRegisteredAsObserver = false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        MyOnTouchListener touchListener2, touchListener3;
        mFirstTouchListener = new NumberInputTouchListener(this);
        touchListener2 = new OperatorInputTouchListener(this);
        touchListener3 = new EqualInputListener(this);

        // Set next
        mFirstTouchListener.setNext(touchListener2);
        touchListener2.setNext(touchListener3);
        touchListener3.setNext(mFirstTouchListener);

        mCurrentTouchListener = mFirstTouchListener;
        updateTouchListener();

        // Retain views
        mCalculusView = (TextView) findViewById(R.id.text_calculus);
        mActualView = (TextView) findViewById(R.id.actual_calculus);
        resetViews();

        // Init
        mOperation = new Operation();
        registerObserver();
    }

    private void updateTouchListener() {
        LinearLayout mainLayout = (LinearLayout) this.findViewById(R.id.main);
        mainLayout.setOnTouchListener(mCurrentTouchListener);
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

    public void reset() {
        mOperation.reset();
        resetViews();
        mCurrentTouchListener = mFirstTouchListener;
        updateTouchListener();
        Log.d(TAG, "[RESET] Operation " + mOperation);
    }

    public void undo() {
        if (!mOperation.isEmpty()) {
            mOperation.undo();
            mCurrentTouchListener = mPreviousTouchListener;
            updateTouchListener();
        }
        Log.d(TAG, "[UNDO] Operation " + mOperation);
    }

    private void resetViews() {
        mCalculusView.setText("");
        mActualView.setText("");
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
                reset();
                return true;
            case R.id.action_undo:
                undo();
                return true;
        }
        return false;
    }

    public void Updatechar(int nb) {
        mActualView.setText(Integer.toString(nb));
    }

    // TODO Refactor -> addNumber
    public void AddingNB(Number nb) {
        mOperation.addObject(nb);
        mCalculusView.setText(mOperation.toString());
        mActualView.setText("");
    }

    // TODO Refactor -> addOperator
    public void AddingOP(Operator op) {
        // If user drew an equal sign, show him the result
        if (op instanceof EqualOperator) {
            /*BigDecimal result = mOperation.getResult();
            if (result != null)
                mCalcxrdulusView.setText(mCalculusView.getText() + mOperation.getResult().toString());
                */
        }
        mOperation.addObject(op);
    }


    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof Operation) {
            if (!(mCurrentTouchListener instanceof EqualInputListener)) {
                mPreviousTouchListener = mCurrentTouchListener;
            }
            mCurrentTouchListener = mCurrentTouchListener.nextState();
            updateTouchListener();
            mCalculusView.setText(mOperation.toString());
            Log.d(TAG, "[UPDATE] Operation: " + mOperation.toString());
        }
    }
}
