package com.fractals.algorithms;

import com.fractals.utilities.CalcUtility;
import com.fractals.utilities.ColourModes;
import com.fractals.utilities.Interval;

public class Mandelbrot extends Algorithm {
    public Mandelbrot(Interval xInterval, Interval yInterval, int maxI, int width, ColourModes colourMode) {
        super(xInterval, yInterval, maxI, width, colourMode);
    }

    @Override
    public int[] calculatePixelArray() {
        int[] output = new int[width * height];

        for (int Py = 0; Py < height; Py++) {
            double y0 = CalcUtility.scale(Py, new Interval(0, height), yInterval, true);
            for (int Px = 0; Px < width; Px++) {
                double x0 = CalcUtility.scale(Px, new Interval(0, width), xInterval);
                double x = 0;
                double y = 0;

                int i = 0;

                while(x*x + y*y < 4 && i < maxI) {
                    double xTemp = x*x - y*y + x0;
                    y = 2*x*y + y0;
                    x = xTemp;
                    i++;
                }

                output[Px+width*Py] = i;
            }
        }
        output = calculateColouredPixelArray(output);
        return output;
    }
}
