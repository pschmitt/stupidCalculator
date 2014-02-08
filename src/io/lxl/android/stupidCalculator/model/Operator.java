package io.lxl.android.stupidCalculator.model;

/**
 * Created by pschmitt on 2/7/14.
 */
public class Operator extends GestureObject {
    public enum TYPE { PLUS, MINUS, DIV, MULT }

    private TYPE mType;

    protected Operator() {
    }

    public Operator(TYPE type) {
        this.mType = type;
    }

    @Override
    String evalString() {
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
                str = "*";
                break;
        }
        return str;
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
                str = "×";
                break;
        }
        return str;
    }
}
