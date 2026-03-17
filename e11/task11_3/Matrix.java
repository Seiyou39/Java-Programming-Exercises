//15824071 ZHANG JINGYANG
package task11_3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Matrix {
	
	
	static final double EPS = 1e-10;
	
    // Member variable to store a 2D matrix
    double[][] data;

    // Constructor that takes filename and read its content
    Matrix(String filename) {
        readFromFile(filename);
    }

    // Added: Constructor that takes the number of rows and columns
    // and allocate memory to data
   

    // Method to read a text file and store its content to data
    // Note that data is now double type
    void readFromFile(String filename) {
    	ArrayList<ArrayList<Double>> a = new ArrayList<>();
        int rows = 0, cols = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.equals("")) continue;
                String[] values = line.split("\\s+");
                if (cols == 0) cols = values.length;
                ArrayList<Double> row = new ArrayList<>();
                for (int i = 0; i < cols; i++) {
                    row.add(Double.parseDouble(values[i]));
                }
                a.add(row);
                rows++;
            }
            br.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        data = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                data[i][j] = a.get(i).get(j);
    }

    // Method to write the content of data to a text file
    void writeToFile(String filename) {
    	writeToFile(filename, false);
    }

    // Overloaded method to write the content of data to a text file
    // If append is true, append the content of data to the end of the existing file
    // Otherwise, overwrite the existing file and store the content of data to the new file
    void writeToFile(String filename, boolean append) {
    	 try {
             PrintWriter pw = new PrintWriter(new FileWriter(filename, append));
             for (int i = 0; i < data.length; i++) {
                 for (int j = 0; j < data[i].length; j++) {
                     pw.print(data[i][j]);
                     if (j < data[i].length - 1) {
                    	 pw.print(" ");
                     }
                 }
                 pw.println();
             }
             pw.close();
         } catch (Exception e) {
             System.out.println(e);
         }
    }

    // Method to print the content of data to the standard output (System.out)
    void show() {
    	
    	for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j]);
                if (j < data[i].length - 1) {
                	System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
    
    void show(String description) {
    	System.out.println(description);
    	for (int i = 0; i < data.length; i++) {
    		System.out.print(" ");
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j]);
                if (j < data[i].length - 1) {
                	System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
    
    //行列式detの算出
    double det() {
    	int n = data.length;

        double[][] a = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = data[i][j];
            }
        }

        double det = 1.0;
        int swap = 1; //行交換回数
        double amax;
        int ip, i, j, k;

      //Pivot軸を選択
        for (k = 0; k < n - 1; k++) {

            ip = k;
            amax = Math.abs(a[k][k]);

            for (i = k + 1; i < n; i++) {
                if (Math.abs(a[i][k]) > amax) {
                    amax = Math.abs(a[i][k]);
                    ip = i;
                }
            }

            if (amax < EPS) {
                return 0.0;
            }

            
          //行交換
            if (ip != k) {
                for (j = k; j < n; j++) {
                    double tmp = a[k][j];
                    a[k][j] = a[ip][j];
                    a[ip][j] = tmp;
                }
                swap = -swap;//行交換ごとに-1を掛け
            }

            //上三角行列を作る
            for (i = k + 1; i < n; i++) {
                double factor = a[i][k] / a[k][k];
                for (j = k; j < n; j++) {
                    a[i][j] -= a[k][j] * factor;
                }
            }
        }

        // det = 上三角行列対角部分の乗積
        for (i = 0; i < n; i++) {
            det *= a[i][i];
        }

        return swap * det;
    	
    }
     
    
    public static void main(String[] args) {
        Matrix m = new Matrix(args[0]);
        double det = m.det();
        m.show();
        System.out.println("Det: " + det);
        
        try {
        	BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
        	writer.write(String.valueOf(det));
        	writer.close();
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
    
}
