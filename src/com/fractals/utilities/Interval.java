package com.fractals.utilities;

import java.util.Arrays;
import java.util.Objects;

public class Interval {
    private final double[] interval;

    public Interval(double a, double b) {
        this.interval = new double[] { a, b };
        Arrays.sort(interval);
    }

    double getMin() { return interval[0]; }
    double getMax() { return interval[1]; }

    public double getDistance() { return Math.abs(interval[0] - interval[1]); }

    @Override
    public int hashCode() { return (int) (Double.doubleToLongBits(interval[0]) ^ Double.doubleToLongBits(interval[1])); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Interval other = (Interval) obj;
        return Objects.equals(this.interval[1], other.interval[1]) &&
                Objects.equals(this.interval[0], other.interval[1]);
    }

    @Override
    public String toString() {
        return "Minimum: " + this.interval[0] + " Maximum: " + this.interval[1];
    }
}
