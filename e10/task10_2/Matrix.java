//15824071 ZHANG JINGYANG
package task10_2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Matrix {
    // Member variable to store a 2D matrix
    double[][] data;

    // Constructor that takes filename and read its content
    Matrix(String filename) {
        readFromFile(filename);
    }

    // Added: Constructor that takes the number of rows and columns
    // and allocate memory to data
    Matrix(int rows, int cols){
        data = new double[rows][cols];
    }

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

    // (optional) Getter method to return the number of rows
    int rows() {
        return data.length;
    }

    // (optional) Getter method to return the number of columns
    int cols() {
        return data[0].length;
    }
    static Matrix add(Matrix a, Matrix b) {
        // Initialize a matrix storing the result
        Matrix result = new Matrix(a.rows(), a.cols());
        for (int i = 0; i < a.rows(); i++)
            for (int j = 0; j < a.cols(); j++)
                result.data[i][j] = a.data[i][j] + b.data[i][j];
        return result;
    }

    // Static method that takes Matrix a and b and returns the difference between them
    static Matrix sub(Matrix a, Matrix b) {
        // Initialize a matrix storing the result
        Matrix result = new Matrix(a.rows(), a.cols());
        for (int i = 0; i < a.rows(); i++)
            for (int j = 0; j < a.cols(); j++)
                result.data[i][j] = a.data[i][j] - b.data[i][j];
        return result;
    }

    // Static method that takes Matrix a and b and returns the element-wise product of them
    static Matrix mul(Matrix a, Matrix b) {
        // Initialize a matrix storing the result
        Matrix result = new Matrix(a.rows(), a.cols());
        for (int i = 0; i < a.rows(); i++)
            for (int j = 0; j < a.cols(); j++)
                result.data[i][j] = a.data[i][j] * b.data[i][j];
        return result;
    }

    // Static method that takes Matrix a and b and returns the dot product of them
    static Matrix dot(Matrix a, Matrix b) {
        // Initialize a matrix storing the result
    	int rows = a.rows();
        int cols = b.cols();
        int mid = a.cols();

        Matrix result = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double sum = 0;
                for (int k = 0; k < mid; k++) {
                    sum += a.data[i][k] * b.data[k][j];
                }
                result.data[i][j] = sum;
            }
        }

        return result;
    }
    
    public static void main(String[] args) {
        Matrix a = new Matrix(args[0]);
        Matrix b = new Matrix(args[1]);
        String output = args[2];

        Matrix sum = Matrix.add(a, b);
        System.out.println("a+b");
        sum.show();
        sum.writeToFile(output);

        Matrix sub = Matrix.sub(a, b);
        System.out.println("a-b");
        sub.show();
        sub.writeToFile(output, true);

        Matrix mul = Matrix.mul(a, b);
        System.out.println("a*b");
        mul.show();
        mul.writeToFile(output, true);

        Matrix dot = Matrix.dot(a, b);
        System.out.println("a·b");
        dot.show();
        dot.writeToFile(output, true);
    }
}
