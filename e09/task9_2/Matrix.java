//15824071 ZHANG JINGYANG
package task9_2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Matrix {
    // Member variable to store a 2D matrix
	ArrayList<ArrayList<Integer>> data = new ArrayList<>();

    // Constructor that takes filename and read its content
	public Matrix(String filename) {
        readFromFile(filename);
    }

    // Method to read a text file and store its content to data
    void readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;

            while ((line = br.readLine()) != null) {
                
                String[] values = line.split("[\\s]+", 0);
                ArrayList<Integer> row = new ArrayList<>();
                for (String s : values) {
                    row.add(Integer.parseInt(s));
                }
                data.add(row);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Method to write the content of data to a text file
    void writeToFile(String filename) {
    	try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {

            for (ArrayList<Integer> row : data) {
                for (int i = 0; i < row.size(); i++) {
                    pw.print(row.get(i));
                    if (i < row.size() - 1){
                    	pw.print(" ");
                    }
                }
                pw.println();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Method to print the content of data to the standard output (System.out)
    void show() {
    	for (ArrayList<Integer> row : data) {
            for (int i = 0; i < row.size(); i++) {
                System.out.print(row.get(i));
                if (i < row.size() - 1) {
                	System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    // (optional) Getter method to return the number of rows
    int rows() {
        return data.size();
    }

    // (optional) Getter method to return the number of columns
    int cols() {
        return data.get(0).size();
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