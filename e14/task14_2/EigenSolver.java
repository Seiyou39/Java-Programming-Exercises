//15824071 ZHANG JINGYANG
package task14_2;

public class EigenSolver {
	private Vector eigen_values;
	private Matrix eigen_vectors; 
	
	private int max_iter = 100;
	static final double EPS = 1e-10;
	
	
	void solve(Matrix matrix) {
		
		if (!isSymmetric(matrix)) {
            System.out.println("Input matrix is not symmetric.");
            System.exit(1);
        }
		 
	        int n = matrix.rows();
	        Matrix a = new Matrix(n, n);
	        for (int i = 0; i < n; i++) {
	            for (int j = 0; j < n; j++) {
	                a.set(i, j, matrix.get(i, j));
	            }
	        }
	        
	        if (a.rows() != a.cols()) {
	            System.out.println("Input matrix is not symmetric.");
	            System.exit(1);
	        }
	        
	        double[] a_row_k = new double[n];
	        double[] a_row_m = new double[n];
	        double[] a_col_k = new double[n];
	        double[] a_col_m = new double[n];

	        Matrix r = Matrix.eye(n);


	        //repeat until all no diagonal elements are zero
	        for (int it = 1; it <= max_iter; it++) {
	            int k = 0, m = 0;	            
	   		 	double s = EPS;
	            for (int i = 0; i < n; i++) {
	                for (int j = i + 1; j < n; j++) {
	                    double v = Math.abs(a.get(i, j));
	                    if (v > s) {
	                        s = v;
	                        k = i;
	                        m = j;
	                    }
	                }
	            }

	            if (s == EPS) {
	                break;
	            }

	            //calculate rotation parameter t(theta),cos(t),and sin(t)
	            double akk = a.get(k, k);
	            double amm = a.get(m, m);
	            double akm = a.get(k, m);
	            double cos_t, sin_t;
	            if (Math.abs(akk - amm) < EPS) {
	                cos_t = 1.0 / Math.sqrt(2.0);
	                sin_t = (akm > 0.0) ? (1.0 / Math.sqrt(2.0)) : (-1.0 / Math.sqrt(2.0));
	            } else {
	                double t = (2.0 * akm) / (akk - amm);
	                double u = Math.sqrt(1.0 + t * t);
	                cos_t = Math.sqrt((1.0 + u) / (2.0 * u));
	                sin_t = Math.sqrt((u - 1.0) / (2.0 * u)) * (t > 0.0 ? 1.0 : -1.0);
	            }

	            //apply R' * A * R
	            for (int j = 0; j < n; j++) {
	                double akj = a.get(k, j);
	                double amj = a.get(m, j);
	                a_row_k[j] = akj * cos_t + amj * sin_t;
	                a_row_m[j] = -akj * sin_t + amj * cos_t;
	            }

	            for (int i = 0; i < n; i++) {
	                double aik = a.get(i, k);
	                double aim = a.get(i, m);
	                a_col_k[i] = aik * cos_t + aim * sin_t;
	                a_col_m[i] = -aik * sin_t + aim * cos_t;
	            }

	            a_col_k[k] = a.get(k, k) * cos_t * cos_t
	                       + a.get(k, m) * cos_t * sin_t
	                       + a.get(m, k) * cos_t * sin_t
	                       + a.get(m, m) * sin_t * sin_t;
	            a_col_m[m] = a.get(k, k) * sin_t * sin_t
	                       - a.get(m, k) * cos_t * sin_t
	                       - a.get(k, m) * cos_t * sin_t
	                       + a.get(m, m) * cos_t * cos_t;
	            a_col_k[m] = 0.0;
	            a_col_m[k] = 0.0;
	            for (int j = 0; j < n; j++) {
	                a.set(k, j, a_row_k[j]);
	                a.set(m, j, a_row_m[j]);
	            }

	            for (int i = 0; i < n; i++) {
	                a.set(i, k, a_col_k[i]);
	                a.set(i, m, a_col_m[i]);
	            }
	            
	            
	            //apply X * R
	            for (int i = 0; i < n; i++) {
	                double rik = r.get(i, k);
	                double rim = r.get(i, m);
	                a_col_k[i] = rik * cos_t + rim * sin_t;
	                a_col_m[i] = -rik * sin_t + rim * cos_t;
	            }
	            for (int i = 0; i < n; i++) {
	                r.set(i, k, a_col_k[i]);
	                r.set(i, m, a_col_m[i]);
	            }
	        }

	        
	        double[] ev = new double[n];
	        for (int i = 0; i < n; i++) { 
	        	ev[i] = a.get(i, i);
	        }

	        eigen_values = new Vector(ev);
	        eigen_vectors = r;
	    }

	
	Vector getEigenValues() {
		return eigen_values;
	}
	
	Matrix getEigenVectors() {
		return eigen_vectors;
	}

	double getEigenValue(int i) {
		return eigen_values.getter(i);
	}
	
	Vector getEigenVector(int i) {
		Vector v = new Vector(eigen_vectors.rows());
		
		for(int j = 0; j < eigen_vectors.rows(); j++) {
			v.setter(j, eigen_vectors.get(j, i));
		}
		return v;
	}
	
	 private boolean isSymmetric(Matrix A) {
	        if (A.rows() != A.cols()) return false;
	        int n = A.rows();
	        for (int i = 0; i < n; i++) {
	            for (int j = i + 1; j < n; j++) {
	                if (Math.abs(A.get(i, j) - A.get(j, i)) > EPS) return false;
	            }
	        }
	        return true;
	    }
	
	public static void main(String[] args) {
	    Matrix A = new Matrix(args[0]);
	    EigenSolver solver = new EigenSolver();

	    solver.solve(A);

	    Matrix eye = Matrix.eye(A.rows());

	    for (int i = 0; i < A.rows(); ++i) {
	        System.out.println("===========     " + "lambda" + (i + 1) + "     ===========");

	        Matrix m = Matrix.sub(A, Matrix.mul(solver.getEigenValue(i), eye));
	        m.show("A - lambda * I");

	        System.out.println("det(A - lambda * I)=" + m.det());

	        Vector d = Matrix.dot(m, solver.getEigenVector(i));
	        d.show("(A - lambda * I)x");
	    }

	    solver.getEigenValues().writeToFile(args[1]);
	    solver.getEigenVectors().writeToFile(args[1], true);

	}
}
