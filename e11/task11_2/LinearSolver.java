//15824071 ZHANG JINGYANG
package task11_2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class LinearSolver {
	
	Matrix A;
	Vector b;
	Matrix aug_matrix;
	String filename;
	
	
	static final double EPS = 1e-10;
	
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
        
        int rows = file_rows - 1;
        int cols = file_cols + 1;

        
        //拡大行列を作る
        aug_matrix = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < file_cols; j++) {
                aug_matrix.data[i][j] = A.data[i][j];
            }
            aug_matrix.data[i][file_cols] = b_data[i];
        }
    }
	
	
	//部分ピボット軸選択法
	Vector solve() {
		double amax;
		double[][] a = getAugmentedMatrix().data;
		int k, ip = 0, i, j;
		int n = aug_matrix.data.length;
		
		for(k = 0; k < n - 1; k++) {
			ip = k;
			amax = Math.abs(a[k][k]);
			
			//Pivot軸を選択
			for(i = k+1; i < n ;i++) {
				if(Math.abs(a[i][k]) > amax){
					amax = Math.abs(a[i][k]);
					ip = i;
				}
			}
			if(amax < EPS) {
				throw new ArithmeticException("Matrix is not regular");
			}
			
			
			//行交換
			if (ip != k) {
	            for (j = k; j <= n; j++) {
	            	amax = a[k][j];
	            	a[k][j] = a[ip][j];
	            	a[ip][j] = amax;
	            }
	        }
			
			
			//前進消去
			for(j = k, amax = a[k][k]; j <= n; j++) {
				a[k][j] /= amax;
			}
			for(i = k + 1; i < n; i++) {
				amax = a[i][k];
				for(j = k; j <= n; j++) {
					a[i][j] -= a[k][j] *amax;
				}
			}
		}
		
		
		//後退代入
		double[] x = new double[n];

	    for (i = n - 1; i >= 0; i--) {
	        x[i] = a[i][n];
	        for (j = i + 1; j < n; j++) {
	            x[i] -= a[i][j] * x[j];
	        }
	        x[i] /= a[i][i];
	    }

	    return new Vector(x);
		
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

	    Vector solution = solver.solve();
	    solution.show("solution");
	    Vector.sub(solver.A().dot(solution), solver.b()).show("Ax-b");
	    solution.writeToFile(args[1]);
	}

}
