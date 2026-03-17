//15824071 ZHANG JINGYANG
package task10_2;

import javax.swing.*;

// Inherit JFrame
public class Plot2D extends JFrame {

    // Constructor that takes String window_title
    Plot2D(String window_title) {
        // Set the window title using the super class' (JFrame) constructor
        super(window_title);

        // Set the default close operation to EXIT_ON_CLOSE
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set window size to 800 x 400
        setSize(800,400);
    }

    public static void main(String[] args) {
        Matrix points = new Matrix(args[0]);

        GraphPanel graphPanel = new GraphPanel(points);

        Plot2D frame = new Plot2D("ZHANG JINGYANG");

        frame.add(graphPanel);
        frame.setVisible(true);
    }
}
