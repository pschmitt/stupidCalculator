package io.lxl.android.stupidCalculator.model;

/**
 * Created by pschmitt on 2/7/14.
 */
public class Number extends GestureObject {
    private int mValue;

    public Number(int value) {
        if (value < 0 || value > 10) {
            throw new RuntimeException("Number must be > 0 and < 10");
        }
        mValue = value;
    }

    @Override
    String evalString() {
        return toString();
    }

    @Override
    public String toString() {
        return new Integer(this.mValue).toString();

    }
}
