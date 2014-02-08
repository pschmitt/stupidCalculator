package io.lxl.android.stupidCalculator.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import io.lxl.android.stupidCalculator.R;
import io.lxl.android.stupidCalculator.listener.MyOnTouchListener;

public class MainActivity extends Activity {


    private static final String DEBUG_TAG = "Velocity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        LinearLayout mainLayout = (LinearLayout)this.findViewById(R.id.main);
        MyOnTouchListener activitySwipeDetector = new MyOnTouchListener(this);
        mainLayout.setOnTouchListener(activitySwipeDetector);

    }
}