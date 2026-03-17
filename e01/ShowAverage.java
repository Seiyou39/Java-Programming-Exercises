//15824071 ZHANG JINGYANG 
package e1;

public class ShowAverage {
	public static void main(String[] args) {
		double[][] scores = {{ 53, 68, 88 }, { 73, 81, 70 }, { 91, 90, 62 }};
	    String[] subjects = { "English", "Mathematics", "Science" };
	
	    for (int i = 0; i < scores.length; i++) {
        double sum = 0;
        for (int j = 0; j < scores[i].length; j++) {
                sum += scores[i][j];
            }
            System.out.printf("Average score over the 3 subjects for student %d: %.1f\n",i + 1, sum / scores[i].length);
        }

	    System.out.println("\n");
	    
        for (int j = 0; j < scores[0].length; j++) {
            double sum = 0;
            for (int i = 0; i < scores.length; i++) {
                sum += scores[i][j];
            }
            System.out.printf("Average score over the 3 students for %s: %.1f\n",subjects[j], sum / scores.length);
        }
    
	}
}