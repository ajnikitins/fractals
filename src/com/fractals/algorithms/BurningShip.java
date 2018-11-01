package com.fractals.algorithms;

import com.fractals.utilities.CalcUtility;
import com.fractals.utilities.ColourModes;
import com.fractals.utilities.Interval;

public class BurningShip extends Algorithm {

    public BurningShip(Interval xInterval, Interval yInterval, int maxI, int width, ColourModes colourMode) {
        super(xInterval, yInterval, maxI, width, colourMode);
    }

    @Override
    public int[] calculatePixelArray() {
        int[] output = new int[width * height];

        for (int Py = 0; Py < height; Py++) {
            double y = CalcUtility.scale(Py, new Interval(height, 0), yInterval, true);
            for (int Px = 0; Px < width; Px++) {
                double x = CalcUtility.scale(Px, new Interval(0, width), xInterval);
                double zx = x;
                double zy = y;

                int i = 0;

                while(zx*zx + zy*zy < 4 && i < maxI) {
                    double xTemp = zx*zx - zy*zy + x;
                    zy = Math.abs(2*zx*zy) + y;
                    zx = Math.abs(xTemp);
                    i++;
                }

                output[Px+width*Py] = i;
            }
        }
        output = calculateColouredPixelArray(output);
        return output;
    }
}
