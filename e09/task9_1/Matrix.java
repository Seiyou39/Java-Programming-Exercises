//15824071 ZHANG JINGYANG
package task9_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Matrix {
    // Member variable to store a 2D matrix
    int[][] data;

    // Constructor that takes filename and read its content
    Matrix(String filename) {
        readFromFile(filename);
    }

    // Method to read a text file and store its content to data
    void readFromFile(String filename) {
        int rows = 0;
        int columns = 0;

        ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = br.readLine()) != null) {
                rows++;
                ArrayList<Integer> col = new ArrayList<Integer>();
                
                String[] values = line.split("[\\s]+", 0);

                if (columns == 0) {
                    columns = values.length;
                } else if (columns != values.length) {
                    System.out.println("Inconsistent column size at row: " + rows);
                    System.exit(1);
                }

                for (int i = 0; i < columns; i++) {
                    col.add(Integer.parseInt(values[i]));
                }

                a.add(col);
            }

            br.close();

        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }

        data = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                data[i][j] = a.get(i).get(j);
            }
        }
    }

    // Method to write the content of data to a text file
    void writeToFile(String filename) {
    	 try {
    	        PrintWriter pw = new PrintWriter(new FileWriter(filename));

    	        for (int i = 0; i < data.length; i++) {
    	            for (int j = 0; j < data[i].length; j++) {
    	                pw.print(data[i][j]);
    	                if (j < data[i].length - 1) pw.print(" ");
    	            }
    	            pw.println();
    	        }

    	        pw.close();
    	    } catch (Exception e) {
    	        System.out.println("Error: cannot write file " + filename);
    	    }
    }

    // Method to print the content of data to the standard output (System.out)
    void show() {
    	for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j]);
                if (j < data[i].length - 1) System.out.print(" ");
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


    public static void main(String[] args) {
    	// Initialize a Matrix instance with the first command line argument
    	Matrix matrix = new Matrix(args[0]);

    	// Print the content of the Matrix instance to the standard output
    	matrix.show();

    	// Output the content of the Matrix instance to
    	// a text file specified by the second command line argument
    	matrix.writeToFile(args[1]);
    }
}