package io.lxl.android.stupidCalculator.model;

/**
 * Created by pschmitt on 2/7/14.
 */
public class Operator {
    public enum TYPE { PLUS, MINUS, DIV, MULT }

    private TYPE mType;

    public Operator(TYPE type) {
        this.mType = type;
    }
}
