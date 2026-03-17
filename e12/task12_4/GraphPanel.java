//15824071 ZHANG JINGYANG
package task12_4;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;

// Inherit JPanel
public class GraphPanel extends JPanel {
    // Member variable to store 2D point coordinates
    private Matrix points;
    private double x1,x2,d;
    private ArrayList<Double> x_points = new ArrayList<>();
    private ArrayList<Double>  y_points = new ArrayList<>();
    private NonLinearSolver solver = new NonLinearSolver();

    // settings
    private int padding = 30;
    private int label_padding = 30;
    private Color line_color = new Color(44, 102, 230, 180);
    private Color point_color = new Color(100, 100, 100, 180);
    private Color grid_color = new Color(200, 200, 200, 200);
    private Stroke graph_stroke = new BasicStroke(2f);
    private int point_width = 4;
    private int num_x_divisions = 10;
    private int num_y_divisions = 10;
    
    public GraphPanel(double x1, double x2, double d) {
        this.x1 = x1;
        this.x2 = x2;
        this.d = d;
        setPreferredSize(new Dimension(600, 600));
        
        double h = (x2 - x1) / d;

        for (int i = 0; i <= d; i++) {
            double x = x1 + i * h;
            x_points.add(x);
            y_points.add(solver.f(x));
        }
    }

   /* public GraphPanel(Matrix points) {
        super();

        // Set the panel size to 800 x 400
        setPreferredSize(new Dimension(800, 400));

        // Set the argument "points" to "this.points"
        this.points = points;
    }*/
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Set antialiasing to remove artifacts (VALUE_ANTIALIAS_ON)
        // g2.setRenderingHint(...);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculate the minimum and maximum values of x and y coordinates
        double min_x = Double.MAX_VALUE, max_x = Double.MIN_VALUE;
        double min_y = Double.MAX_VALUE, max_y = Double.MIN_VALUE;
        for(int i = 0; i < x_points.size(); i++) {
        	min_x = Math.min(min_x, x_points.get(i));
        	max_x = Math.max(max_x, x_points.get(i));
        	min_y = Math.min(min_y, y_points.get(i));
        	max_y = Math.max(max_y, y_points.get(i));
        }

        // Draw white background (hint: fillRect)
        g2.setColor(Color.white);
        int b_x = padding + label_padding;
        int b_y = padding;
        int b_width = getWidth() - (2 * padding + label_padding);
        int b_height = getHeight() - (2 * padding + label_padding);
        g2.fillRect(b_x,b_y,b_width,b_height);

        // Draw grid, label, and tick along y axis (hint: drawLine, drawString)
        
        g2.setColor(grid_color);
        for(int i = 1; i <=num_x_divisions;i++) {
        	int g_x0 = b_x + (i * b_width / num_x_divisions);
        	int g_y1 = b_y;
        	int g_y2 = b_y + b_height;
        	g2.drawLine(g_x0,g_y1,g_x0,g_y2);
        }
        
        g2.setColor(Color.black);
        for(int i = 1; i <=num_x_divisions;i++) {
        	int g_x0 = b_x + (i * b_width / num_x_divisions);
        	int g_y2 = b_y + b_height;
        	g2.drawLine(g_x0,g_y2-5,g_x0,g_y2);
        }
        
        for (int i = 0; i <= num_y_divisions; i++) {
        	
            int y = padding + (i * b_height / num_y_divisions);
            double value = max_y - (i * (max_y - min_y) / num_y_divisions);
            String label = String.format("%.1f", value);
            
            FontMetrics fm = g2.getFontMetrics();
            int labelWidth = fm.stringWidth(label);

            g2.drawString(label,b_x - 5 - labelWidth, y + 5 );
        }

        // Draw grid, label, and tick along x axis (hint: drawLine, drawString)
        g2.setColor(grid_color);
        for(int i = 1; i <=num_y_divisions;i++) {
        	int g_x1 = b_x;
        	int g_x2 = b_x + b_width;
        	int g_y0 = b_y + (i * b_height / num_y_divisions);
        	g2.drawLine(g_x1,g_y0,g_x2,g_y0);
        }
        
        g2.setColor(Color.black);
        for(int i = 0; i <num_y_divisions;i++) {
        	int g_x1 = b_x;
        	int g_y0 = b_y + (i * b_height / num_y_divisions);
        	g2.drawLine(g_x1,g_y0,g_x1 + 5,g_y0);
        }
        
        for (int i = 0; i <= num_y_divisions; i++) {
        	
            int x = b_x + (i * b_width / num_x_divisions);
            double value = min_x + (i * (max_x - min_x) / num_x_divisions);
            String label = String.format("%.1f", value);
            
            g2.drawString(label,x-10, b_y + b_height + 15);
        }

        // Draw x and y axes (hint: drawLine)
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2f));
        g2.drawLine(b_x, b_y, b_x, b_y + b_height);
        g2.drawLine(b_x, b_y + b_height, b_x + b_width, b_y + b_height);


        // Scale factors to point-panel coordinate conversion
        double x_scale = ((double) getWidth() - 2 * padding - label_padding)
                        / (max_x - min_x);
        double y_scale = ((double) getHeight() - 2 * padding - label_padding)
                        / (max_y - min_y);
        
        

        ArrayList<Point> graph_points = new ArrayList<Point>();
        // Scale the point coordinates into graph coordinates and store them in graph_points
        for (int i = 0; i < x_points.size(); i++) {

            double x_val = x_points.get(i);
            double y_val = y_points.get(i);

            int x = (int)(b_x + (x_val - min_x) * x_scale);
            int y = (int)(b_y + b_height - (y_val - min_y) * y_scale);


            graph_points.add(new Point(x, y));
        }

        // Draw lines between points (hint: setStroke, drawLine)
        
        for (int i = 0; i < graph_points.size() - 1; i++) {
        	if (y_points.get(i) * y_points.get(i+1) < 0) {
                g2.setColor(new Color(140, 40, 140, 255));
            } else {
                g2.setColor(new Color(30, 30, 30, 180));
            }
            g2.setStroke(graph_stroke);
            Point p1 = graph_points.get(i);
            Point p2 = graph_points.get(i + 1);

            g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
        if (0 >= min_y && 0 <= max_y) {
            int y0 = (int)(b_y + b_height - (0 - min_y) * y_scale);
            g2.setColor(new Color(35, 75, 35, 255));
            g2.drawLine(b_x, y0, b_x + b_width, y0);
        }

        // Plot points (hint: fillOval)
        for (int i = 0; i < graph_points.size(); i++) {
            if (y_points.get(i) > 0){
            	g2.setColor(Color.RED);
            }
            else{
            	g2.setColor(Color.BLUE);
            }
            Point p = graph_points.get(i);
            g2.fillOval(p.x - point_width/2, p.y - point_width/2, point_width, point_width);
        }
        for (int i = 0; i < x_points.size() - 1; i++) {
            if (y_points.get(i) * y_points.get(i + 1) < 0) {
            	
                double xm = (x_points.get(i) + x_points.get(i + 1)) / 2.0;

                try {
                    double iv = solver.solveSecant(x_points.get(i),x_points.get(i + 1));
                    double ym = solver.f(iv); 

                    int px = (int)(b_x + (iv - min_x) * x_scale);
                    int py = (int)(b_y + b_height - (ym - min_y) * y_scale);

                    g2.setColor(Color.GREEN);
                    g2.fillOval(px - point_width/2, py - point_width/2, point_width, point_width);

                } catch (ArithmeticException e) {
                	
                }
            }
        }
    }
    
    
    
    
    public void saveImage(String filename) {
        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2 = img.createGraphics();
        this.paint(g2);
        g2.dispose();
        try {
            ImageIO.write(img, "png", new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
