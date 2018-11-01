package com.fractals.main;

import com.fractals.algorithms.Algorithm;
import com.fractals.utilities.CalcUtility;
import com.fractals.utilities.Interval;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.MemoryImageSource;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Display extends JFrame {

    Display(Algorithm algorithm) {
        setSize(algorithm.getWidth(), algorithm.getHeight() + 30);
        setVisible(true);
        this.algorithm = algorithm;
        this.zoomStack = new Stack<>();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                formMousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                formMouseReleased(e);
            }
        });
        drawImage();
    }

    private void formMousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            xClicked = e.getX();
            yClicked = e.getY()- 30;
        }
    }

    private void formMouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {

            int xReleased = e.getX();
            int yReleased = e.getY() - 30;

            int xDistance = Math.abs(xReleased - xClicked);
            int yDistance = (int) (xDistance / algorithm.getAspectRatio());

            if (xDistance < 10) {
                return;
            }

            yReleased = yReleased > yClicked ? yClicked + yDistance : yClicked - yDistance;

            Pair<Interval, Interval> oldPair = new Pair<>(algorithm.getXInterval(), algorithm.getYInterval());
            zoomStack.push(oldPair);

            Graphics g = getGraphics();
            g.drawRect(xReleased > xClicked ? xClicked : xReleased, (yReleased > yClicked ? yClicked : yReleased) + 30, xDistance, yDistance);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
            }

            double clickedScaledX = CalcUtility.scale(xClicked, new Interval(0, algorithm.getWidth()), algorithm.getXInterval());
            double releasedScaledX = CalcUtility.scale(xReleased, new Interval(0, algorithm.getWidth()), algorithm.getXInterval());
            double clickedScaledY = CalcUtility.scale(yClicked, new Interval(0, algorithm.getHeight()), algorithm.getYInterval(), true);
            double releasedScaledY = CalcUtility.scale(yReleased, new Interval(0, algorithm.getHeight()), algorithm.getYInterval(), true);

            Interval newXInterval = new Interval(clickedScaledX, releasedScaledX);
            Interval newYInterval = new Interval(clickedScaledY, releasedScaledY);
            algorithm.setXInterval(newXInterval);
            algorithm.setYInterval(newYInterval);
            drawImage();
        }

        if (SwingUtilities.isRightMouseButton(e)) {

            if (!zoomStack.empty()) {
                Pair<Interval, Interval> newPair = zoomStack.pop();

                Interval oldXInterval = newPair.getKey();
                Interval oldYInterval = newPair.getValue();

                algorithm.setXInterval(oldXInterval);
                algorithm.setYInterval(oldYInterval);
                drawImage();
            }
        }
    }

    private void drawImage() {
        int[] pixelArray = algorithm.calculatePixelArray();
        this.image = createImage(new MemoryImageSource(algorithm.getWidth(), algorithm.getHeight(), pixelArray, 0, algorithm.getWidth()));
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(this.image, 0, 30, this);
    }

    private Algorithm algorithm;
    private Stack<Pair<Interval, Interval>> zoomStack;
    private Image image = null;
    private int xClicked;
    private int yClicked;
}
