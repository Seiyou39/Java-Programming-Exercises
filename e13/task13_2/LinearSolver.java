//15824071 ZHANG JINGYANG
package task13_2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class LinearSolver {
	static final double EPS = 1e-10;
	Matrix aug_matrix;
	
	LinearSolver(Matrix aug_matrix){
		this.aug_matrix = aug_matrix;	
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
	
	Matrix getAugmentedMatrix() {
	    return aug_matrix;
	}


}
