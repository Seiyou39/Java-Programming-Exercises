//15824071 ZHANG JINGYANG
package task14_2;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DataPanel implements ActionListener{
    private boolean initialized;
    private int titleIndex, xTitleIndex, yTitleIndex;
    private int xLowerIndex, xUpperIndex, xIntervalIndex;
    private int yLowerIndex, yUpperIndex, yIntervalIndex;
    private JFrame frame;
    private JPanel panel;
    private String title;
    private String xTitle;
    private String yTitle;
    private int scaleIndex, scale;
    private float xLower, xUpper, xInterval;
    private float yLower, yUpper, yInterval;
    private Point2D.Float[] points;
    private JLabel[] paramLabels;
    private JTextField[] paramFields;
    private JTextField[] dataFields;
    
    private Matrix A;

    DataPanel(JFrame newFrame) {
        initialized = false;
        titleIndex = 0;
        xTitleIndex = 1;
        yTitleIndex = 2;
        xLowerIndex = 3;
        xUpperIndex = 4;
        xIntervalIndex = 5;
        yLowerIndex = 6;
        yUpperIndex = 7;
        yIntervalIndex = 8;
        scaleIndex = 9;
        paramLabels = new JLabel[10];
        paramLabels[titleIndex] = new JLabel("Title");
        paramLabels[xTitleIndex] = new JLabel("X Axis Title");
        paramLabels[yTitleIndex] = new JLabel("Y Axis Title");
        paramLabels[xLowerIndex] = new JLabel("X lower bound");
        paramLabels[xUpperIndex] = new JLabel("X upper bound");
        paramLabels[xIntervalIndex] = new JLabel("X tick interval");
        paramLabels[yLowerIndex] = new JLabel("Y lower bound");
        paramLabels[yUpperIndex] = new JLabel("Y upper bound");
        paramLabels[yIntervalIndex] = new JLabel("Y tick interval");
        paramLabels[scaleIndex] = new JLabel("Scale");
        paramFields = new JTextField[10];
        paramFields[titleIndex] = new JTextField("Least Squares");
        paramFields[xTitleIndex] = new JTextField("X");
        paramFields[yTitleIndex] = new JTextField("Y");
        paramFields[xLowerIndex] = new JTextField("-10");
        paramFields[xUpperIndex] = new JTextField("120");
        paramFields[xIntervalIndex] = new JTextField("10");
        paramFields[yLowerIndex] = new JTextField("10");
        paramFields[yUpperIndex] = new JTextField("110");
        paramFields[yIntervalIndex] = new JTextField("20");
        paramFields[scaleIndex] = new JTextField("1");
        frame = newFrame;
        panel = new JPanel(new FlowLayout());
        frame.getContentPane().add(panel, "West");
    }

    public void actionPerformed(ActionEvent e) {
        JFrame fileFrame = new JFrame();
        JPanel filePanel = new JPanel();
        JFileChooser fileChooser = new JFileChooser();
        fileFrame.getContentPane().add(filePanel);
        filePanel.add(fileChooser);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(filePanel);
        if (result != JFileChooser.APPROVE_OPTION) {
            JLabel msg = new JLabel("No file selected");
            panel.add(msg);
            return;
        }

        File datafile = fileChooser.getSelectedFile();
        initialized = readFile(datafile);
        panel.update(panel.getGraphics());
        frame.pack();
        frame.setVisible(true);
    }

    // The data file contains two sections.
    // The first section contains parameters used for configuring the graph.
    // Each line starts with a single word identifier followed by a space.
    // The rest of the line is the value.
    // It is terminated by a line containing the word "Data".
    // The second section contains the data pairs that will be graphed.
    private boolean readFile(File datafile) {
        int numAllocated = 10;
        int numRead = 0;
        int numDataPoints = 0;
        String[] dataStrings = new String[numAllocated];
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(datafile));
            String text;
            while ((text = reader.readLine()) != null) {
                if (numRead >= numAllocated) {
                    numAllocated = 2 * numAllocated;
                    String[] temp = dataStrings;
                    dataStrings = new String[numAllocated];
                    System.arraycopy(temp, 0, dataStrings, 0, numRead);
                }

                dataStrings[numRead] = text;
                numRead = numRead + 1;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
        try {
            if (reader != null)
                reader.close();
        } catch (IOException e) {
            System.out.println("IO Exception on close");
        }
        
        try {
            int got = 0;
            double[][] tmp = new double[2][2];

            for (int i = 0; i < numRead && got < 2; i++) {
                String line = dataStrings[i];
                if (line == null) continue;
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] seg = line.split("\\s+");
                if (seg.length >= 2 && isNumber(seg[0]) && isNumber(seg[1])) {
                    tmp[got][0] = Double.parseDouble(seg[0]);
                    tmp[got][1] = Double.parseDouble(seg[1]);
                    got++;
                }
            }

            if (got == 2) {
                A = new Matrix(2,2);
                A.set(0,0,tmp[0][0]); A.set(0,1,tmp[0][1]);
                A.set(1,0,tmp[1][0]); A.set(1,1,tmp[1][1]);
                
                initialized = true;

                dataFields = new JTextField[4];
                dataFields[0] = new JTextField("" + tmp[0][0]);
                dataFields[1] = new JTextField("" + tmp[0][1]);
                dataFields[2] = new JTextField("" + tmp[1][0]);
                dataFields[3] = new JTextField("" + tmp[1][1]);

                points = new Point2D.Float[2];
                points[0] = new Point2D.Float((float)tmp[0][0], (float)tmp[0][1]);
                points[1] = new Point2D.Float((float)tmp[1][0], (float)tmp[1][1]);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        int thisCase = -2;
        int thisDataPoint = 0;
        for (int i = 0; i < numRead; i++) {
        	String line = dataStrings[i].trim();
        	if (line.isEmpty()) continue;
        	String[] segments = line.split("\\s+");
            if (segments[0].equals("Title")) {
                thisCase = titleIndex;
            } else if (segments[0].equals("xTitle")) {
                thisCase = xTitleIndex;
            } else if (segments[0].equals("yTitle")) {
                thisCase = yTitleIndex;
            } else if (segments[0].equals("xLower")) {
                thisCase = xLowerIndex;
            } else if (segments[0].equals("xUpper")) {
                thisCase = xUpperIndex;
            } else if (segments[0].equals("xInterval")) {
                thisCase = xIntervalIndex;
            } else if (segments[0].equals("yLower")) {
                thisCase = yLowerIndex;
            } else if (segments[0].equals("yUpper")) {
                thisCase = yUpperIndex;
            } else if (segments[0].equals("yInterval")) {
                thisCase = yIntervalIndex;
            } else if (segments[0].equals("Scale")) {
                thisCase = scaleIndex;
            } /*else if (segments[0].equals("Data")) {
                thisCase = -1;
                numDataPoints = numRead - i - 1;
                dataFields = new JTextField[2 * numDataPoints];
                points = new Point2D.Float[numDataPoints];
            }*/ else if (thisCase != -1) {
                thisCase = -2;
            }

            if (thisCase >= 0 && segments.length > 1) {
                StringBuilder temp = new StringBuilder(segments[1]);
                for (int j = 2; j < segments.length; j++)
                    temp.append(" ").append(segments[j]);
                paramFields[thisCase].setText(temp.toString());
                thisCase = -2;
            }/* else if (thisCase == -1 && !segments[0].equals("Data")
                && thisDataPoint < numDataPoints) {
            	if (segments.length < 2) continue;
                dataFields[2 * thisDataPoint] = new JTextField(segments[0]);
                dataFields[2 * thisDataPoint + 1] = new JTextField(segments[1]);
                thisDataPoint++;
            }*/
            /*else if (thisCase == -2 && segments.length >= 2) {

               if (dataFields == null) {
                   dataFields = new JTextField[2 * numRead];
                   points = new Point2D.Float[numRead];
                   thisDataPoint = 0;
               }

               dataFields[2 * thisDataPoint] = new JTextField(segments[0]);
               dataFields[2 * thisDataPoint + 1] = new JTextField(segments[1]);
               thisDataPoint++;
           }*/
        }


        frame.getContentPane().remove(panel);
        int showPoints = 2;
        
        panel = new JPanel(new GridLayout(10 + showPoints, 2));
        for (int i = 0; i < 10; i++) {
            panel.add(paramLabels[i]);
            panel.add(paramFields[i]);
        }
        for (int i = 0; i < showPoints; i++) {
            panel.add(dataFields[2 * i]);
            panel.add(dataFields[2 * i + 1]);
        }
        frame.getContentPane().add(panel, "West");

        return true;
    }

    // Read data from panel in case user made any changes
    void refreshData() {
        if (!initialized) return;

        title = paramFields[titleIndex].getText();
        xTitle = paramFields[xTitleIndex].getText();
        yTitle = paramFields[yTitleIndex].getText();
        xLower = Float.parseFloat(paramFields[xLowerIndex].getText());
        xUpper = Float.parseFloat(paramFields[xUpperIndex].getText());
        xInterval = Float.parseFloat(paramFields[xIntervalIndex].getText());
        yLower = Float.parseFloat(paramFields[yLowerIndex].getText());
        yUpper = Float.parseFloat(paramFields[yUpperIndex].getText());
        yInterval = Float.parseFloat(paramFields[yIntervalIndex].getText());
        scale = Integer.parseInt(paramFields[scaleIndex].getText());

        double a00 = Double.parseDouble(dataFields[0].getText());
        double a01 = Double.parseDouble(dataFields[1].getText());
        double a10 = Double.parseDouble(dataFields[2].getText());
        double a11 = Double.parseDouble(dataFields[3].getText());

        if (A == null) A = new Matrix(2, 2);
        A.set(0, 0, a00); A.set(0, 1, a01);
        A.set(1, 0, a10); A.set(1, 1, a11);

        if (points == null) points = new Point2D.Float[2];
        points[0] = new Point2D.Float((float)a00, (float)a01);
        points[1] = new Point2D.Float((float)a10, (float)a11);
    }

    boolean isInitialized() {
        return initialized;
    }
    
    boolean isNumber(String s) {
        try { Double.parseDouble(s); return true; }
        catch (Exception e) { return false; }
    }

    String getTitle() {
        return title;
    }

    String getXTitle() {
        return xTitle;
    }

    String getYTitle() {
        return yTitle;
    }

    float getXLower() {
        return xLower;
    }

    float getXUpper() {
        return xUpper;
    }

    float getXInterval() {
        return xInterval;
    }

    float getYLower() {
        return yLower;
    }

    float getYUpper() {
        return yUpper;
    }

    float getYInterval() {
        return yInterval;
    }

    int getNumberOfPoints() {
        return points.length;
    }
    
    int getScale() { 
    	return scale;
    }

    Point2D.Float getPoint(int i) {
        if (i < 0) {
            i = 0;
        } else if (i >= points.length) {
            i = points.length - 1;
        }
        return points[i];
    }
    
    Matrix getMatrix() { 
    	return A; }
}
