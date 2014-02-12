package io.lxl.android.stupidCalculator.model;

import java.util.Observable;

/**
 * Created by pschmitt on 2/7/14.
 */
public abstract class GestureObject extends Observable {
    abstract String evalString();
}
