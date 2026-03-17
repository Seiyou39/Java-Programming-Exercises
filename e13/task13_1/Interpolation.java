//15824071 ZHANG JINGYANG
package task13_1;

public class Interpolation {
	static double lagrange(Matrix points, double x) {
		
		double y = 0.0;
		int N = points.rows();
		
		for(int i = 0; i < N; i++ ) {
			double li = 1.0;
			double xi = points.get(i, 0);
			double yi = points.get(i, 1);
			
			for(int k = 0; k < N; k++) {
				if(k != i) {
					double xk = points.get(k, 0);
					li *= (x - xk) / (xi - xk); 
				}
			}
			y += li * yi;
		}
		return y;
	}

}
