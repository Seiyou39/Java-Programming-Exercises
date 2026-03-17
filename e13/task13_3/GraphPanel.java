//15824071 ZHANG JINGYANG
package task13_3;

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

        g2d.setColor(Color.RED);
        int n = datapanel.getNumberOfPoints();
        for (int i = 0; i < n; i++) {
            float dataX = datapanel.getPoint(i).x;
            float dataY = datapanel.getPoint(i).y;

            int px = Math.round(padding[0] + (dataX - xLower) * plotW / (xUpper - xLower));
            int py = Math.round(padding[2] + (yUpper - dataY) * plotH / (yUpper - yLower));
            
            g2d.fillOval(px - point_width / 2, py - point_width / 2, point_width, point_width);
        }

        int order = datapanel.getOrder();
        Matrix pm = new Matrix(n, 2);
        for (int i = 0; i < n; i++) {
            pm.set(i, 0, datapanel.getPoint(i).x);
            pm.set(i, 1, datapanel.getPoint(i).y);
        }
        
        

        Vector coeffs = LeastSquares.minimize(pm, order);
        g2d.setColor(new Color(30, 30, 30, 180));

        int sampleN = 200;
        int prevPx = 0, prevPy = 0;
        boolean hasPrev = false;

        for (int i = 0; i < sampleN; i++) {
            float x = min_x + dx * i / (sampleN - 1f);

            double y = 0, xp = 1;
            for (int j = 0; j <= order; j++) {
                y += coeffs.getter(j) * xp;
                xp *= x;
            }

            int px = Math.round(padding[0] + (x - xLower) * plotW / (xUpper - xLower));
            int py = Math.round(padding[2] + (yUpper - (float) y) * plotH / (yUpper - yLower));
            
            if (hasPrev) g2d.drawLine(prevPx, prevPy, px, py);
            prevPx = px;
            prevPy = py;
            hasPrev = true;
        }
    }
}
