package task9_4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;



public class Matrix {

    ArrayList<ArrayList<Double>> data;

    public Matrix() {
        data = new ArrayList<>();
    }

    public Matrix(String filename) {
        readFromFile(filename);
    }

    void readFromFile(String filename) {
        data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] values = line.split("\\s+");
                ArrayList<Double> row = new ArrayList<>();
                for (String s : values) {
                    row.add(Double.parseDouble(s));
                }
                data.add(row);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void writeToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (ArrayList<Double> row : data) {
                for (int i = 0; i < row.size(); i++) {
                    pw.print(row.get(i));
                    if (i < row.size() - 1)
                        pw.print(" ");
                }
                pw.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    void writeToFile(String filename, boolean append) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename, append))) {

            for (ArrayList<Double> row : data) {
                for (int i = 0; i < row.size(); i++) {
                    pw.print(row.get(i));
                    if (i < row.size() - 1) {
                        pw.print(" ");
                    }
                }
                pw.println();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void show() {
        for (ArrayList<Double> row : data) {
            for (int i = 0; i < row.size(); i++) {
                System.out.print(row.get(i));
                if (i < row.size() - 1)
                    System.out.print(" ");
            }
            System.out.println();
        }
    }

    int rows() {
        return data.size();
    }

    int cols() {
        return data.get(0).size();
    }
    
    static Matrix add(Matrix a, Matrix b) {
        Matrix result = new Matrix();

        for (int i = 0; i < a.rows(); i++) {
            ArrayList<Double> row = new ArrayList<>();
            for (int j = 0; j < a.cols(); j++) {
                row.add(a.data.get(i).get(j) + b.data.get(i).get(j));
            }
            result.data.add(row);
        }

        return result;
    }

    static Matrix sub(Matrix a, Matrix b) {
        Matrix result = new Matrix();

        for (int i = 0; i < a.rows(); i++) {
            ArrayList<Double> row = new ArrayList<>();
            for (int j = 0; j < a.cols(); j++) {
                row.add(a.data.get(i).get(j) - b.data.get(i).get(j));
            }
            result.data.add(row);
        }

        return result;
    }

    static Matrix mul(Matrix a, Matrix b) {
        Matrix result = new Matrix();

        for (int i = 0; i < a.rows(); i++) {
            ArrayList<Double> row = new ArrayList<>();
            for (int j = 0; j < a.cols(); j++) {
                row.add(a.data.get(i).get(j) * b.data.get(i).get(j));
            }
            result.data.add(row);
        }

        return result;
    }

    static Matrix dot(Matrix a, Matrix b) {
        Matrix result = new Matrix();

        for (int i = 0; i < a.rows(); i++) {
            ArrayList<Double> row = new ArrayList<>();

            for (int j = 0; j < b.cols(); j++) {
                double sum = 0;
                for (int k = 0; k < a.cols(); k++) {
                    sum += a.data.get(i).get(k) * b.data.get(k).get(j);
                }
                row.add(sum);
            }

            result.data.add(row);
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