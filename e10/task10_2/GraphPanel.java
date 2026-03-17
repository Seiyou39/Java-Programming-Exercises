//15824071 ZHANG JINGYANG
package task10_2;

import java.awt.*;
import java.util.*;

import javax.swing.*;

// Inherit JPanel
public class GraphPanel extends JPanel {
    // Member variable to store 2D point coordinates
    private Matrix points;

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

    public GraphPanel(Matrix points) {
        super();

        // Set the panel size to 800 x 400
        setPreferredSize(new Dimension(800, 400));

        // Set the argument "points" to "this.points"
        this.points = points;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Set antialiasing to remove artifacts (VALUE_ANTIALIAS_ON)
        // g2.setRenderingHint(...);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculate the minimum and maximum values of x and y coordinates
        double min_x = Double.MAX_VALUE, max_x = Double.MIN_VALUE;
        double min_y = Double.MAX_VALUE, max_y = Double.MIN_VALUE;
        for(int i = 0; i < points.rows(); i++) {
        	if(min_x >= points.data[i][0]) {
        		min_x = points.data[i][0];
        	}
        	
        	if(max_x <= points.data[i][0]) {
        		max_x = points.data[i][0];
        	}
        	
        	if(min_y >= points.data[i][1]) {
        		min_y = points.data[i][1];
        	}
        	
        	if(max_y <= points.data[i][1]) {
        		max_y = points.data[i][1];
        	}   	
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
            
            g2.drawString(label, padding-5, y+5);
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
            
            g2.drawString(label,x-5, b_y + b_height + 15);
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
        for (int i = 0; i < points.rows(); i++) {

            double x_val = points.data[i][0];
            double y_val = points.data[i][1];

            int x = (int)(b_x + (x_val - min_x) * b_width / (max_x - min_x));

            int y = (int)(b_y + b_height - (y_val - min_y) * b_height / (max_y - min_y));

            graph_points.add(new Point(x, y));
        }

        // Draw lines between points (hint: setStroke, drawLine)
        g2.setColor(line_color);
        g2.setStroke(graph_stroke);
        for (int i = 0; i < graph_points.size() - 1; i++) {
            Point p1 = graph_points.get(i);
            Point p2 = graph_points.get(i + 1);

            g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        // Plot points (hint: fillOval)
        g2.setColor(point_color);

        for (Point p : graph_points) {
            g2.fillOval(p.x - point_width/2, p.y - point_width/2, point_width, point_width);
        }
    }
}
