//15824071 ZHANG JINGYANG
package task11_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class LinearSolver {
	
	Matrix A;
	Vector b;
	Matrix aug_matrix;
	String filename;
	
	LinearSolver(String filename) {
        this.filename = filename;
        readFromFile();
    }
	
	void readFromFile() {
        ArrayList<ArrayList<Double>> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                	continue;
                }
                String[] tokens = line.split("\\s+");
                ArrayList<Double> values = new ArrayList<>();
                for (int i = 0; i < tokens.length; i++) {
                    values.add(Double.parseDouble(tokens[i]));
                }
                lines.add(values);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        int file_rows = lines.size();//ファイル中の行の数
        int file_cols = lines.get(0).size();//ファイル中の列の数
        
        
        //行列を読み込む
        A = new Matrix(file_rows - 1, file_cols);
        for (int i = 0; i < file_rows - 1; i++) {
            for (int j = 0; j < file_cols; j++) {
                A.data[i][j] = lines.get(i).get(j);
            }
        }
        
        //ベクトルを読み込む
        int b_size = lines.get(file_rows - 1).size();
        double[] b_data = new double[b_size];
        for (int i = 0; i < b_size; i++) {
            b_data[i] = lines.get(file_rows - 1).get(i);
        }
        b = new Vector(b_data);
        
        
        //拡大行列を作る
        int rows = file_rows - 1;
        int cols = file_cols + 1;

        aug_matrix = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < file_cols; j++) {
                aug_matrix.data[i][j] = A.data[i][j];
            }
            aug_matrix.data[i][file_cols] = b_data[i];
        }
    }
	
	Matrix A() {
	    return A;
	}
	
	Vector b() {
	    return b;
	}
	
	Matrix getAugmentedMatrix() {
	    return aug_matrix;
	}

	public static void main(String[] args) {
	    LinearSolver solver = new LinearSolver(args[0]);
	    solver.A().show("A");
	    solver.b().show("b");
	    solver.getAugmentedMatrix().show("Augmented Matrix");

	    solver.A().writeToFile(args[1]);
	    solver.b().writeToFile(args[1], true);
	}

}
