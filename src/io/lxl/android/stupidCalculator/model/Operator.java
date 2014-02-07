package io.lxl.android.stupidCalculator.model;

/**
 * Created by pschmitt on 2/7/14.
 */
public class Operator extends GestureObject {
    public enum TYPE { PLUS, MINUS, DIV, MULT }

    private TYPE mType;

    public Operator(TYPE type) {
        this.mType = type;
    }

    @Override
    public String toString() {
        String str = null;
        switch (this.mType) {
            case PLUS:
                str = "+";
                break;
            case MINUS:
                str = "-";
                break;
            case DIV:
                str = "/";
                break;
            case MULT:
                str = "x";
                break;
        }
        return str;
    }
}
