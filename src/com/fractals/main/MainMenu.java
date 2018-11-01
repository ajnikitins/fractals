package com.fractals.main;

import com.fractals.algorithms.Algorithm;
import com.fractals.algorithms.BurningShip;
import com.fractals.algorithms.Mandelbrot;
import com.fractals.utilities.ColourModes;
import com.fractals.utilities.Interval;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class MainMenu extends JFrame {

    MainMenu() {
        setContentPane(this.contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        defaultsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                defaultsButtonPressed(e);
            }
        });
        drawButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                drawButtonPressed(e);
            }
        });
    }

    private void defaultsButtonPressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
            Algorithm algorithm;

            switch (Objects.requireNonNull(selectedAlgorithm)) {
                case "Mandelbrot":
                    xMinField.setText("-2.5");
                    xMaxField.setText("1");
                    yMinField.setText("-1");
                    yMaxField.setText("1");
                    widthField.setText("500");
                    break;

                case "Burning ship":
                    xMinField.setText("-2.5");
                    xMaxField.setText("1.5");
                    yMinField.setText("-1.5");
                    yMaxField.setText("1");
                    widthField.setText("500");
                    break;

                default:
                    xMinField.setText("-2.5");
                    xMaxField.setText("1");
                    yMinField.setText("-1");
                    yMaxField.setText("1");
                    widthField.setText("500");
            }

            iterationsField.setText("1000");
            colourModeComboBox.setSelectedItem("Monochrome");
        }
    }

    private void drawButtonPressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            double xMin = Double.parseDouble(xMinField.getText());
            double xMax = Double.parseDouble(xMaxField.getText());
            double yMin = Double.parseDouble(yMinField.getText());
            double yMax = Double.parseDouble(yMaxField.getText());
            int width = Integer.parseInt(widthField.getText());
            int maxI = Integer.parseInt(iterationsField.getText());

            String selectedColourMode = (String) colourModeComboBox.getSelectedItem();
            ColourModes colourMode;
            switch (Objects.requireNonNull(selectedColourMode)) {
                case "Monochrome":
                    colourMode = ColourModes.MONOCHROME;
                    break;

                case "Histogram":
                    colourMode = ColourModes.HISTOGRAM;
                    break;

                case "Tri-colour":
                    colourMode = ColourModes.TRICOLOUR;
                    break;

                default:
                    colourMode = ColourModes.MONOCHROME;
            }

            Interval xInterval = new Interval(xMin, xMax);
            Interval yInterval = new Interval(yMin, yMax);

            String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
            Algorithm algorithm;
            switch (Objects.requireNonNull(selectedAlgorithm)) {
                case "Mandelbrot":
                    algorithm = new Mandelbrot(xInterval, yInterval, maxI, width, colourMode);
                    break;

                case "Burning ship":
                    algorithm = new BurningShip(xInterval, yInterval, maxI, width, colourMode);
                    break;

                default:
                    algorithm = new Mandelbrot(xInterval, yInterval, maxI, width, colourMode);
            }

            new Display(algorithm);
        }
    }

    private JPanel contentPane;
    private JTextField xMinField;
    private JTextField xMaxField;
    private JTextField widthField;
    private JButton drawButton;
    private JButton defaultsButton;
    private JComboBox colourModeComboBox;
    private JTextField yMinField;
    private JTextField yMaxField;
    private JComboBox algorithmComboBox;
    private JTextField iterationsField;
}
