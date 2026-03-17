//15824071 ZHANG JINGYANG
package task13_3;

public class LeastSquares {
	public static Vector minimize(Matrix points, int n) {
		
		Matrix augMatrix = new Matrix(n + 1, n + 2);
		
		for (int i = 0; i <= n; i++) {
		    for (int j = 0; j <= n; j++) {
		        double sum = 0.0;
		        for (int k = 0; k < points.rows(); k++) {
		            double x = points.get(k, 0);
		            sum += Math.pow(x, i + j);
		        }
		        augMatrix.set(i, j, sum);
		    }
		}
		
		for (int i = 0; i <= n; i++) {
		    double sum = 0.0;
		    for (int k = 0; k < points.rows(); k++) {
		        double x = points.get(k, 0);
		        double y = points.get(k, 1);
		        sum += y * Math.pow(x, i);
		    }
		    augMatrix.set(i, n + 1, sum);
		}
		
		LinearSolver solver = new LinearSolver(augMatrix);
		Vector coeffs = solver.solve();
		
		return coeffs;
	}

}
