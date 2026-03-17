//15824071 ZHANG JINGYANG
package task12_4;

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
        if (args.length < 3) {
            System.exit(1);
        }

        double x1 = Double.parseDouble(args[0]);
        double x2 = Double.parseDouble(args[1]);
        double d  = Double.parseDouble(args[2]);

        if (x1 >= x2) {
            System.exit(1);
        }

        JFrame frame = new JFrame("ZHANG JINGYANG");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        GraphPanel panel = new GraphPanel(x1, x2, d);
        frame.add(panel);

        frame.setVisible(true);
    }
    
}
