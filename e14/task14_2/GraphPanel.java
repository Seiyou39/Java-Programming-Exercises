//15824071 ZHANG JINGYANG
package task14_2;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphPanel extends JPanel implements ActionListener {

    private boolean display_plot = false;
    private DataPanel datapanel;
    private JFrame frame;

    private final int[] padding = {75, 30, 30, 30};
    private final Color grid_color = new Color(200, 200, 200, 200);
    private final int point_width = 4;
    private final int margin = 8;
    private final int num_x_divisions = 10;
    private final int num_y_divisions = 10;

    public GraphPanel(JFrame newFrame) {
        frame = newFrame;
        datapanel = new DataPanel(frame);
        frame.getContentPane().add(this, "Center");
    }

    public void actionPerformed(ActionEvent e) {
        if (!datapanel.isInitialized()) return;
        datapanel.refreshData();
        display_plot = true;
        repaint();
        frame.pack();
        frame.setVisible(true);
    }

    public ActionListener getDataPanel() {
        return datapanel;
    }
    
    private int toScreenX(float x, float xLower, float xUpper, int plotW) {
        return Math.round(padding[0] + (x - xLower) * plotW / (xUpper - xLower));
    }

    private int toScreenY(float y, float yLower, float yUpper, int plotH) {
        return Math.round(padding[2] + (yUpper - y) * plotH / (yUpper - yLower));
    }
    
    private void drawEigenLine(Graphics2D g2d,
            double a, double b, double c, double d, double lambda,
            float xLower, float xUpper, float yLower, float yUpper, int plotW, int plotH) {

        double p = a - lambda;
        double q = b;

        double vx, vy;
        if (Math.abs(q) > 1e-12) {
            vx = 1.0;
            vy = -p / q;
        } else {
            vx = 0.0;
            vy = 1.0;
        }

        double n = Math.sqrt(vx*vx + vy*vy);
        vx /= n;
        vy /= n;

        double T = Math.max(xUpper - xLower, yUpper - yLower) * 2.0;

        float x1 = (float)(-T * vx);
        float y1 = (float)(-T * vy);
        float x2 = (float)( T * vx);
        float y2 = (float)( T * vy);

        int sx1 = toScreenX(x1, xLower, xUpper, plotW);
        int sy1 = toScreenY(y1, yLower, yUpper, plotH);
        int sx2 = toScreenX(x2, xLower, xUpper, plotW);
        int sy2 = toScreenY(y2, yLower, yUpper, plotH);

        g2d.drawLine(sx1, sy1, sx2, sy2);
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!display_plot || datapanel == null || !datapanel.isInitialized()) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));

        float min_x = datapanel.getXLower(), max_x = datapanel.getXUpper();
        float min_y = datapanel.getYLower(), max_y = datapanel.getYUpper();

        float dx = max_x - min_x;
        float dy = max_y - min_y;
        if (dx == 0 || dy == 0) return;

        float x_scale = (getWidth() - (padding[0] + padding[1])) / dx;
        float y_scale = (getHeight() - (padding[2] + padding[3])) / dy;
        
        float xLower = min_x, xUpper = max_x;
        float yLower = min_y, yUpper = max_y;

        float xInterval = datapanel.getXInterval();
        float yInterval = datapanel.getYInterval();
        
        if (xInterval <= 0 || yInterval <= 0) return;

        int plotW = getWidth() - (padding[0] + padding[1]);
        int plotH = getHeight() - (padding[2] + padding[3]);

        FontMetrics m = g2d.getFontMetrics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(
            padding[0], padding[2],
            getWidth() - (padding[0] + padding[1]),
            getHeight() - (padding[2] + padding[3])
        );
        
        String title = datapanel.getTitle();
        if (title != null && !title.isEmpty()) {
            FontMetrics tm = g2d.getFontMetrics();
            int plotT = getWidth() - (padding[0] + padding[1]);
            int tx = padding[0] + plotT / 2 - tm.stringWidth(title) / 2;
            int ty = padding[2] - margin;
            g2d.setColor(Color.BLACK);
            g2d.drawString(title, tx, ty - 5);
        }

        for (float yVal = yLower; yVal <= yUpper + 1e-6f; yVal += yInterval) {
            int y = Math.round(padding[2] + (yUpper - yVal) * plotH / (yUpper - yLower));

            g2d.setColor(grid_color);
            g2d.drawLine(padding[0], y, padding[0] + plotW, y);

            g2d.setColor(Color.BLACK);
            String yLabel = String.format("%.1f", yVal);
            g2d.drawString(yLabel, padding[0] - m.stringWidth(yLabel) - margin, y + m.getHeight() / 2);
        }

        for (float xVal = xLower; xVal <= xUpper + 1e-6f; xVal += xInterval) {
            int x = Math.round(padding[0] + (xVal - xLower) * plotW / (xUpper - xLower));

            g2d.setColor(grid_color);
            g2d.drawLine(x, padding[2], x, padding[2] + plotH);

            g2d.setColor(Color.BLACK);
            String xLabel = String.format("%.1f", xVal);
            g2d.drawString(xLabel, x - m.stringWidth(xLabel) / 2, padding[2] + plotH + m.getHeight() + margin);
        }

        g2d.setColor(Color.BLACK);
        g2d.drawLine(padding[0], getHeight() - padding[3], padding[0], padding[2]);
        g2d.drawLine(padding[0], getHeight() - padding[3], getWidth() - padding[1], getHeight() - padding[3]);

        Matrix A = datapanel.getMatrix();
        int scale = datapanel.getScale();
        if (scale <= 0) scale = 1;

        float step = 1.0f / scale;

        g2d.setColor(Color.BLUE);

        for (float x = xLower; x <= xUpper + 1e-6f; x += step) {
            for (float y = yLower; y <= yUpper + 1e-6f; y += step) {

                double ax = A.get(0,0) * x + A.get(0,1) * y;
                double ay = A.get(1,0) * x + A.get(1,1) * y;

                int x1 = toScreenX(x, xLower, xUpper, plotW);
                int y1 = toScreenY(y, yLower, yUpper, plotH);
                int x2 = toScreenX((float)ax, xLower, xUpper, plotW);
                int y2 = toScreenY((float)ay, yLower, yUpper, plotH);

                g2d.drawLine(x1, y1, x2, y2);
            }
        }

        double a = A.get(0,0), b = A.get(0,1), c = A.get(1,0), d = A.get(1,1);
        double tr = a + d;
        double det = a*d - b*c;
        double disc = tr*tr - 4*det;

        if (disc >= 0) {
            double s = Math.sqrt(disc);
            double l1 = (tr + s) / 2.0;
            double l2 = (tr - s) / 2.0;

            g2d.setColor(Color.RED);
            drawEigenLine(g2d, a, b, c, d, l1, xLower, xUpper, yLower, yUpper, plotW, plotH);
            drawEigenLine(g2d, a, b, c, d, l2, xLower, xUpper, yLower, yUpper, plotW, plotH);
        }
        }
    }

