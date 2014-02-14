package io.lxl.android.stupidCalculator.model;

import android.util.Log;
import com.udojava.evalex.Expression;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

/**
 * Created by pschmitt on 2/7/14.
 */
public class Operation extends GestureObject {

    private static final String TAG = "Operation";
    private List<GestureObject> mComponents;

    public Operation() {
        mComponents = new ArrayList<GestureObject>();
    }

    public boolean isEmpty() {
        return mComponents.isEmpty();
    }


    /**
     * Check whether this Operation is valid in its current state
     *
     * @return True if there's no arithmetic errors
     */
    public boolean isValid() {
        // TODO
        GestureObject previousObject = null;
        if (mComponents.isEmpty()) {
            return true;
        }
        GestureObject firstObject = mComponents.get(0);
        // An operation should start with a number or an operation
        if (!(firstObject instanceof Number) && !(firstObject instanceof Operation)) {
            return false;
        }

        if (mComponents.size() > 0) {
            // The last component mustn't be a number etc, it has to be an equal sign
            GestureObject lastObject = mComponents.get(mComponents.size() - 1);
            if (!(lastObject instanceof EqualOperator))
                return false;
        }

        for (GestureObject gestureObject : mComponents) {
            if (gestureObject instanceof Number) {
                // Two consecutive numbers aren't allowed
                // An operation cannot be preceded by a number
                if (previousObject != null
                        && (previousObject instanceof Number || previousObject instanceof Operation)
                        && !(previousObject instanceof EqualOperator)) {
                    return false;
                }
            } else if (gestureObject instanceof Operator
                    && !(gestureObject instanceof EqualOperator)) {
                if (previousObject == null || !(previousObject instanceof Number)) {
                    return false;
                }
            } else if (gestureObject instanceof Operation) {
                if ((previousObject != null || !(previousObject instanceof Operator)
                        || previousObject instanceof EqualOperator)) {
                    return false;
                }
            } else if (gestureObject instanceof EqualOperator) {
                // Equal should be the last item in our list
                if (!gestureObject.equals(mComponents.get(mComponents.size() - 1))) {
                    return false;
                }

            }
            previousObject = gestureObject;
        }
        return true;
    }

    /**
     * Compute the result of this operation
     *
     * @return The result
     * @throws ArithmeticException
     */
    public BigDecimal getResult() throws ArithmeticException {
        if (!isValid()) {
            throw new ArithmeticException("This operation is invalid");
        }
        // TODO!
        BigDecimal result;

        try {
            Expression expression = new Expression(evalString());
            result = expression.eval();
            return result;
        } catch (EmptyStackException e) {
            Log.wtf(TAG, "Cought EmptyStackException: " + evalString());
            e.printStackTrace();
        }
        return new BigDecimal(-1);
    }

    /**
     * Add an object to our component list
     *
     * @param gestureObject The object to add
     */
    public void addObject(GestureObject gestureObject) {
        mComponents.add(gestureObject);
        setChanged();
        notifyObservers();
    }

    /**
     * Remove last object from stack (pop)
     */
    public void undo() {
        if (mComponents != null && !mComponents.isEmpty()) {
            mComponents.remove(mComponents.size() - 1);
            setChanged();
            notifyObservers();
        }
    }

    /**
     * String representation of this operation, minus the trailing equal sign
     *
     * @return This operation as a string ready to be parsed by the Expression class
     */
    public String evalString() {
        StringBuilder sb = new StringBuilder();
        for (GestureObject gestureObject : mComponents) {
            if (!(gestureObject instanceof EqualOperator)) {
                sb.append(gestureObject.evalString());
            }
        }
        return sb.toString();
    }

    public boolean lastAddedObjectWasAnEqualSign() {
        if (mComponents.size() > 0) {
            return (mComponents.get(mComponents.size() - 1) instanceof EqualOperator);
        }
        return false;
    }

    /**
     * String representation
     *
     * @return This operation as a String (eg: 3+4=)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (GestureObject gestureObject : mComponents) {
            sb.append(prefix);
            prefix = " ";
            sb.append(gestureObject.toString());
        }

        if (!mComponents.isEmpty()) {
            if (isValid()) {
                //sb.append("✓");
                sb.append(" ").append(getResult().toPlainString()).append(" ✓");
            } else {
                sb.append(" ✗");
            }
        }
        return sb.toString();
    }

    public void reset() {
        mComponents.clear();
        setChanged();
        notifyObservers();
    }
}
