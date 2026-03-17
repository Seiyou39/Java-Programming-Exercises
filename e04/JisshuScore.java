//ZANG JINGYANG 15824071
package score;

public class JisshuScore extends SubjectScore {
	private int []report;
	
	JisshuScore(){
		super();
		report = new int[num];
	}
	
	JisshuScore(String s){
		super(s);
		report = new int[num];
	}
	
	JisshuScore(int i){
		super(i);
		report = new int[num];
	}
	
	JisshuScore(String s, int i){
		super(s,i);
		report = new int[num];
	}
	
	void setReportScore(int i, int j) throws Exception{
		if(i<0 || i >= report.length) {
			throw new Exception(i + " is a wrong index.");
		} 
		if(j<0 || j>100) {
			throw new Exception(j + " is out of the range of 0 to 100.");
		}
		report[i] = j;
	}
	
	int getReportScore(int i) throws Exception{
		if(i<0 || i >= report.length) {
			throw new Exception(i + " is a wrong index.");
		} 
		return report[i];
	}
	
	double calAverage(int i) {
		if(i == 0) {
			return calAverage();
		}
		else {
			int sum = 0;
		    int count = 0;

		    for (int idx = 0; idx < num; idx++) {
		    	try{
		    		String s =  getSubject(idx);
			        if (s != null) {
			            sum += report[idx];
			            count++;
			        }
		    		}catch(Exception e) {
		    			e.printStackTrace();
		    	}
		  }
		    return (double) sum / count; 
		}
	}
}
