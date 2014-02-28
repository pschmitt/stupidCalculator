package io.lxl.android.stupidCalculator.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import io.lxl.android.stupidCalculator.R;
import io.lxl.android.stupidCalculator.listener.EqualInputListener;
import io.lxl.android.stupidCalculator.listener.MyOnTouchListener;
import io.lxl.android.stupidCalculator.listener.NumberInputTouchListener;
import io.lxl.android.stupidCalculator.listener.OperatorInputTouchListener;
import io.lxl.android.stupidCalculator.model.Number;
import io.lxl.android.stupidCalculator.model.Operation;
import io.lxl.android.stupidCalculator.model.Operator;

import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends Activity implements Observer, View.OnClickListener {

    private static final String TAG = "MainActivity";
    private TextView mActualView;
    private TextView mCalculusView;
    private TextView mInstructionsView;
    private Operation mOperation;
    private MyOnTouchListener mCurrentTouchListener;
    private MyOnTouchListener mPreviousTouchListener;
    private MyOnTouchListener mFirstTouchListener;
    private boolean mIsRegisteredAsObserver = false;
    private MyOnTouchListener mEqualTouchListener;
    private boolean mRequestedOperation;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        MyOnTouchListener operatorTouchListener;
        mFirstTouchListener = new NumberInputTouchListener(this);
        operatorTouchListener = new OperatorInputTouchListener(this);
        mEqualTouchListener = new EqualInputListener(this);

        // Retain views
        mCalculusView = (TextView) findViewById(R.id.text_calculus);
        mActualView = (TextView) findViewById(R.id.actual_calculus);
        mInstructionsView = (TextView) findViewById(R.id.instructions);
        mInstructionsView.setOnClickListener(this);

        // Set next
        mFirstTouchListener.setNext(operatorTouchListener);
        operatorTouchListener.setNext(mFirstTouchListener);

        mCurrentTouchListener = mFirstTouchListener;
        updateTouchListener();

        resetViews();

        // Init
        mRequestedOperation = false;
        mOperation = new Operation();
        registerObserver();
    }

    private void updateTouchListener() {
        LinearLayout mainLayout = (LinearLayout) this.findViewById(R.id.main);
        mainLayout.setOnTouchListener(null);
        mainLayout.setOnTouchListener(mCurrentTouchListener);
        updateInstructions();
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
        mRequestedOperation = false;
        mOperation.reset();
        mCurrentTouchListener = mFirstTouchListener;
        updateTouchListener();
        Log.d(TAG, "[RESET] Operation " + mOperation);
    }

    public void undo() {
        if (!mOperation.isEmpty()) {
            mOperation.undo();
            //mCurrentTouchListener = mPreviousTouchListener;
            if (mCurrentTouchListener instanceof EqualInputListener) {
                mCurrentTouchListener = mFirstTouchListener;
            }
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
                resetViews();
                return true;
            case R.id.action_undo:
                undo();
                return true;
        }
        return false;
    }

    public void updateInputView(int nb) {
        mActualView.setText(Integer.toString(nb));
    }

    public void updateInstructions() {
        if (mCurrentTouchListener instanceof NumberInputTouchListener) {
            mInstructionsView.setText(getString(R.string.instructions_number_input));
        } else if (mCurrentTouchListener instanceof OperatorInputTouchListener) {
            mInstructionsView.setText(getString(R.string.instructions_operator));
        } else if (mCurrentTouchListener instanceof EqualInputListener) {
            if (mRequestedOperation) {
                mInstructionsView.setText(getString(R.string.reset_instructions));
            } else {
                mInstructionsView.setText(getString(R.string.instructions_equal_sign));
            }
        }
    }

    public void requestCalculation() {
        mRequestedOperation = true;
        String resultString = "±∞";
        try {
            BigDecimal result = mOperation.getResult();
            resultString = result.toBigInteger().toString();
        } catch (ArithmeticException e) {
            Log.e(TAG, "Division by zero...");
        }
        mCalculusView.setText(mOperation.getStringRepresentation(true) + " = " + resultString);
        updateInstructions();
    }

    // TODO Refactor -> addNumber
    public void add(Number nb) {
        mOperation.addObject(nb);
        mActualView.setText("");
        updateView();
    }

    // TODO Refactor -> addOperator
    public void add(Operator op) {
        mOperation.addObject(op);
        updateView();
    }

    private void updateView() {
        mCalculusView.setText(mOperation.getStringRepresentation(false));
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof Operation) {
            if (!(mCurrentTouchListener instanceof EqualInputListener)) {
                mPreviousTouchListener = mCurrentTouchListener;
            }
            // FIXME ?
            if (mOperation.isValid()) {
                mCurrentTouchListener = mEqualTouchListener;
            } else {
                mCurrentTouchListener = mCurrentTouchListener.nextState();
            }
            updateTouchListener();
            if (!mRequestedOperation) {
                updateView();
            }
            Log.d(TAG, "[UPDATE] Current state: " + mCurrentTouchListener + " Operation: " + mOperation.toString());
        }
    }

    @Override
    public void onClick(View v) {
        if (mRequestedOperation) {
            reset();
        }
    }
}
