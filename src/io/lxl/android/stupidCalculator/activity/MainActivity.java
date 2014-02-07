package io.lxl.android.stupidCalculator.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import io.lxl.android.stupidCalculator.R;
import io.lxl.android.stupidCalculator.model.GestureObject;

public class MainActivity extends Activity {

    private TextView mCalculusView;
    private List<GestureObject> mObjectList;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Retain views
        mCalculusView = (TextView) findViewById(R.id.text_calculus);

        // Init list
        mObjectList = new ArrayList<GestureObject>();
    }
}
