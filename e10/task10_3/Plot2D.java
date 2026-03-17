//15824071 ZHANG JINGYANG
package task10_3;

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
    	if (args.length == 2) {
            String input = args[0];
            String output = args[1];

            Matrix points = new Matrix(input);
            GraphPanel panel = new GraphPanel(points);

            Plot2D frame = new Plot2D("Hidden");
            frame.add(panel);
            frame.pack(); 

            panel.saveImage(output);

            System.out.println("Image saved as " + output);
            System.exit(0);
        }
        
    	if (args.length == 1) {
            Matrix points = new Matrix(args[0]);

            Plot2D frame = new Plot2D("ZHANG JINGYANG");
            GraphPanel panel = new GraphPanel(points);

            frame.add(panel);
            frame.setVisible(true);
    	}
    }
}
