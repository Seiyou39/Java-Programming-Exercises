//ZHANG JINGYANG 15824071
package score;

public class SubjectScore {
	private String studentName;
	protected int num;
	private String[] subject;
	private int[] point;
	static final int MaxNum = 10;
	

	SubjectScore(String s, int i){
		studentName = s;
		
		if(i > 0 && i <= MaxNum) {
			num = i;
		}
		else num = MaxNum;
		
		subject = new String[num];
	    point = new int[num];

	}
	
	SubjectScore(int i){
		this("unknown", i);
	}
	
	SubjectScore(String s){
		this(s, 5);
	}
	
	
	SubjectScore(){
		this("unknown", 5);
	}
	
	int getNum() {
		return num;
	}
	
	String getStudentName() {
		return studentName;
	}
	
	void setStudentName(String s) {
		studentName = s;
	}
	
	String getSubject(int i)throws Exception{
		if(i<0 || i >= subject.length) {
			throw new Exception(i + " is a wrong index.");
		} 
		return subject[i];
		
	}
	
	int getScore(int i)throws Exception{
		if(i<0 || i >= point.length) {
			throw new Exception(i + " is a wrong index.");
		} 
		return point[i];
	}
	
	void setSubject(int i, String s)throws Exception{
		if(i<0 || i >= subject.length) {
			throw new Exception(i + " is a wrong index.");
		}
		subject[i] = s;
	}
	
	void setSubject(int i) {
		subject[i] = "unknown";
	}
	
	/* e3 Extend */
	void setSubjects(String[] s) {
		if(s.length > subject.length) {
			for(int i = 0; i < subject.length;i++) {
				subject[i] = s[i];
			}
		}
		for(int i = 0; i < s.length;i++) {
			subject[i] = s[i];
		}
		
	}
	
	void setScore(int i, int j)throws Exception{
		if(i<0 || i >= point.length) {
			throw new Exception(i + " is a wrong index.");
		}
		if(j<0 || j>100) {
			throw new Exception(j + " is out of the range of 0 to 100.");
		}
		point[i] = j;
	}
	
	void setScore(int i) {
		point[i] = 60;
	}
	/* e3 Extend */
	void setScores(int[] p) {
		if(p.length > point.length) {
			for(int i = 0; i < point.length;i++) {
				point[i] = p[i];
			}
		}
		for(int i = 0; i < p.length;i++) {
			point[i] = p[i];
	    }
	}
	
	double calAverage() {
		int sum = 0;
	    int count = 0;

	    for (int i = 0; i < num; i++) {
	        if (subject[i] != null) {
	            sum += point[i];
	            count++;
	        }
	    }
	    return (double) sum / count; 
	}
	
	/* e3 Extend */
	double calAverage(int m) {
		int sum = 0;
		int count = 0;
		if(m > num) {
			return calAverage();
		}
		for (int i = 0; i < m; i++) {
	        if (subject[i] != null) {
	            sum += point[i];
	            count++;
	        }
	    }
		return (double) sum / count;
	}
	
	int getMaxScore() {
		int Maxscore = 0;
		
		for (int i = 0; i < num; i++) {
			if(Maxscore < point[i] && subject[i] != null) {
				Maxscore = point[i];
			}
	    }
		return Maxscore;
	}

	int getMinScore() {
		int Minscore = 100;
		
		for (int i = 0; i < num; i++) {
			if(Minscore > point[i] && subject[i] != null){
				Minscore = point[i];
			}
	    }
		return Minscore;
	}
	
	String getMaxSubject() {
	    int Maxscore = getMaxScore();
	    for (int i = 0; i < num; i++) {
	        if (point[i] == Maxscore) {
	            return subject[i];
	        }
	    }
	    return null;
	}
	
	String getMinSubject() {
	    int Minscore = getMinScore();
	    for (int i = 0; i < num; i++) {
	        if (point[i] == Minscore) {
	            return subject[i];
	        }
	    }
	    return null;
	}
	
	/* e3 Extend */
	void copyScores(int[] p) {
		for(int i = 0; i < point.length; i++) {
			p[i] = point[i];
		}
	}
	
	void copySubjects(String [] s) {
		for(int i = 0; i < subject.length; i++) {
			s[i] = subject[i];
		}
	}
	
	public static void main(String[] args) {
		
	}
}
