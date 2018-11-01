package com.fractals.utilities;

public class CalcUtility {
    private CalcUtility() {}

    public static double scale(double n, Interval oldInterval, Interval newInterval) {
        return scale(n, oldInterval, newInterval, false);
    }

    public static double scale(double n, Interval oldInterval, Interval newInterval, boolean inverseOld) {
        return inverseOld ? (newInterval.getMax() - newInterval.getMin()) * (n - oldInterval.getMax()) / (oldInterval.getMin() - oldInterval.getMax()) + newInterval.getMin() : (newInterval.getMax() - newInterval.getMin()) * (n - oldInterval.getMin()) / (oldInterval.getMax() - oldInterval.getMin()) + newInterval.getMin();
    }
}
