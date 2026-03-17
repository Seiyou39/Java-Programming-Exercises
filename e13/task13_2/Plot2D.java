//15824071 ZHANG JINGYANG
package task13_2;

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
        if (args.length < 4) {
            System.exit(1);
        }
        Matrix points = new Matrix(args[0]);
        int order_n = Integer.parseInt(args[1]);
        int number_of_interpolation_points = Integer.parseInt(args[2]);
        
        GraphPanel panel = new GraphPanel(points, number_of_interpolation_points, order_n);

        JFrame frame = new JFrame("ZHANG JINGYANG");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        
        panel.saveImage(args[3]);
    }
    
}
