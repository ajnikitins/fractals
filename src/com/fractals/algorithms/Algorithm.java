package com.fractals.algorithms;

import com.fractals.utilities.ColourModes;
import com.fractals.utilities.Interval;

public abstract class Algorithm {
    public Algorithm(Interval xInterval, Interval yInterval, int maxI, int width, ColourModes colourMode) {
        this.xInterval = xInterval;
        this.yInterval = yInterval;
        this.width = width;
        this.aspectRatio = xInterval.getDistance() / yInterval.getDistance();
        this.height = (int) (width / aspectRatio);
        this.maxI = maxI;
        this.colourMode = colourMode;
    }

    Interval xInterval;
    Interval yInterval;
    int maxI;
    int width;
    int height;
    private ColourModes colourMode;
    private double aspectRatio;

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public Interval getXInterval() {
        return xInterval;
    }

    public void setXInterval(Interval xInterval) {
        this.xInterval = xInterval;
    }

    public Interval getYInterval() {
        return yInterval;
    }

    public void setYInterval(Interval yInterval) {
        this.yInterval = yInterval;
    }

    public int getMaxI() {
        return maxI;
    }

    public void setMaxI(int maxI) {
        this.maxI = maxI;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ColourModes getColourMode() {
        return colourMode;
    }

    public void setColourMode(ColourModes colourMode) {
        this.colourMode = colourMode;
    }

    public double getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }
// </editor-fold>

    /**
     *
     * @return An iteration array
     */
    public abstract int[] calculatePixelArray();

    /**
     *
     * @param input An iteration array
     * @return A coloured array
     */
    int[] calculateColouredPixelArray(int[] input) {
        int[] output = new int[width * height];

        switch (colourMode) {
            case MONOCHROME:
                for (int i = 0; i < input.length; i++) {
                    output[i] = 255<<24 | (input[i] >= maxI ? 0 : (255<<16 | 255<<8 | 255));
                }
                break;

            case HISTOGRAM:
                int[] histogram = new int[maxI+1];

                for (int pixel : input) {
                    histogram[pixel]++;
                }

                double total = 0.0;
                for (int i = 0; i < maxI; i++) {
                    total += histogram[i];
                }

                for (int o = 0; o < input.length; o++) {

                    double hue = 0.0;
                    for (int i = 0; i <= input[o]; i++) {
                        hue += histogram[i] / total;
                    }

                    int green = (int) (hue * 255);
                    int blue = 255 - green;
                    output[o] = (255<<24) | (green<<8) | blue;
                }
                break;

            case TRICOLOUR:
                for (int i = 0; i < input.length; i++) {
                    output[i] = 255<<24 | (input[i] >= maxI ? 0 : (input[i] >= 50 ? 255<<8 | 255 : (input[i] >= 20 ? (135<<16 | 206<<8 | 250) : 255)));
                }
                break;
        }

        return output;
    }
}
