package io.lxl.android.stupidCalculator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pschmitt on 2/7/14.
 */
public class Operation extends GestureObject {

    private List<GestureObject> mObjectList;

    public Operation() {
        mObjectList = new ArrayList<GestureObject>();
    }

    public boolean isValid() {
        // TODO
        GestureObject previousObject = null;
        if (mObjectList.isEmpty()) {
            return true;
        }
        GestureObject firstObject = mObjectList.get(0);
        // An operation should start with a number or an operation
        if (!(firstObject instanceof Number) && !(firstObject instanceof Operation)) {
            return false;
        }
        for (GestureObject gestureObject : mObjectList) {
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
                if (!gestureObject.equals(mObjectList.get(mObjectList.size() - 1))) {
                    return false;
                }

            }
            previousObject = gestureObject;
        }
        return true;
    }

    public int getResult() throws ArithmeticException {
        if (!isValid()) {
            throw new ArithmeticException("This operation is invalid");
        }
        // TODO!
        return 0;
    }

    public void addObject(GestureObject gestureObject) {
        mObjectList.add(gestureObject);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (GestureObject gestureObject : mObjectList) {
            sb.append(gestureObject.toString());
        }
        if (!mObjectList.isEmpty()) {
            if (isValid()) {
                sb.append(getResult()).append("✓");
            } else {
                sb.append("✗");
            }
        }
        return sb.toString();
    }
}
