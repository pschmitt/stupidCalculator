package io.lxl.android.stupidCalculator.model;

/**
 * Created by pschmitt on 2/7/14.
 */
public class Number {
    private int mValue;

    public Number(int value) {
        if (value < 0 || value > 10) {
            throw new RuntimeException("Number must be > 0 and < 10");
        }
        mValue = value;
    }
}
