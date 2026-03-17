//15824071 ZHANG JINGYANG
package task12_4;

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
	
	NonLinearSolver(){
		this.maxIter = 30;
	}
	
	double f(double x) {
		return (Math.exp(x) - Math.sin((Math.PI * x) / 2.0));
	}
	
	double df(double x) {
		return (Math.exp(x) - (Math.PI / 2.0) * Math.cos((Math.PI * x) / 2.0));
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
	
	public double solveSecant(double i0, double i1) {
	    double x0 = i0;
	    double x1 = i1;

	    for (int n = 0; n < maxIter; n++) {
	        double f0 = f(x0);
	        double f1 = f(x1);

	        double denom = (f1 - f0);
	        if (Math.abs(denom) < EPS) {
	            throw new ArithmeticException(
	                "Secant method didn't converge after " + maxIter + " iteration."
	            );
	        }

	        double x2 = x1 - f1 * (x1 - x0) / denom;

	        if (Double.isNaN(x2) || Double.isInfinite(x2)) {
	            throw new ArithmeticException(
	                "Secant method didn't converge after " + maxIter + " iteration."
	            );
	        }

	        if (Math.abs(x2 - x1) <= EPS) {
	            return x2;
	        }

	        x0 = x1;
	        x1 = x2;
	    }

	    throw new ArithmeticException(
	        "Secant method didn't converge after " + maxIter + " iteration."
	    );
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
