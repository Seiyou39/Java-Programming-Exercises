//15824071 ZHANG JINGYANG
package task12_2;

public class NonLinearSolver {
	
	static final double EPS=1e-10;
	private int maxIter;
	
	private double A;
	private double B;
	
	NonLinearSolver(long studentNo){
		this.maxIter = 30;
		
		//Student number rule:
		long entry = (studentNo / 1000) % 100; //entry year
		long id3 = studentNo % 1000; //last 3 digits ID
		this.A = 0.80 + 0.01 * (id3 % 100);
		long bIndex = (entry *10 + (id3 / 100)) % 100;
		this.B = -0.30 + 0.006 *bIndex;	
	}
	
	double f(double x) {
		return (x - Math.exp(-A * x) - B);
	}
	
	double df(double x) {
		return (1.0 + A * Math.exp(-A * x));
	}
	
	public double solveNewton(double x) {
		int n = 0;
		double d;
		
		do {
			double dfx = df(x);
			if(dfx == 0) {
				return Double.NaN;
			}
			
			
			d = -f(x) / df(x);
			x = x + d;
			n++;
			
			if(Double.isNaN(x)) {
				throw new ArithmeticException(
						"Newton's method didn't converge after " + n + " iteration."
						);
			}
			
		}while( Math.abs(d) > EPS && n <maxIter);
		
		if(n == maxIter  ) {
			throw new ArithmeticException(
					"Newton's method didn't converge after " + maxIter + " iteration."
					);
		}
		else {
			return x;
		}
	}
		
	
	
	public static void main(String[] args) {
		if(args.length < 2) {
			System.out.println("Usage: ");
			System.exit(1);
		}
		
		long studentNo;
		studentNo = Long.parseLong(args[0]);
		
		NonLinearSolver solver = new NonLinearSolver(studentNo);
		boolean allfail = false;
		
		for (int i = 1; i < args.length; i++) {
	        try {
	            double ans = solver.solveNewton(Double.parseDouble(args[i]));
	            System.out.println("searching x from " + Double.parseDouble(args[i]) + ", solution is " + ans);
	            allfail = true;
	        } catch (ArithmeticException e) {
	            System.out.println("searching x from " + Double.parseDouble(args[i]) + ": " + e);
	        }
	    }

	    if (!allfail) {
	        System.exit(1);
	    }
	}

}
