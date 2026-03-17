//ZANG JINGYANG 15824071
package score;

import java.lang.String;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.PrintWriter;

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
			System.out.print((i + 1) + " is a wrong index.\n");
		} 
		if(j<0 || j>100) {
			System.out.println(j + "_is_out_of_the_range_of_0_to_100.");
			report[i] = 0;
			return;
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
			double sum = 0;
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
	//方法1
	void readScores1(Path inputFilePath) {
	    try(BufferedReader br = Files.newBufferedReader(inputFilePath, StandardCharsets.UTF_8)) {
	    	String s;
        	int i=0;
            while ((s = br.readLine()) != null) {//最後まで読み込む
                String[] token = s.split("\\s+");
                setSubject(i, token[0]);
                setScore(i, Integer.parseInt(token[1]));
                setReportScore(i, Integer.parseInt(token[2]));
                i++;
            	}
       }catch (Exception e) {
            	System.out.println("Exception: " + e);
            	e.printStackTrace();
        }
	}
	//方法2
	void readScores2(Path inputFilePath) {
	    try(BufferedReader br = Files.newBufferedReader(inputFilePath, StandardCharsets.UTF_8)) {
	    	String s1,s2,s3;
	    	s1=br.readLine();//1行目を読み込む
	    	String[] token1 = s1.split("\\s+");
	    	s2=br.readLine();//2行目を読み込む
	    	String[] token2 = s2.split("\\s+");
	    	s3=br.readLine();//3行目を読み込む
	    	String[] token3 = s3.split("\\s+");
	    	
	    	int n = getNum();//科目数
	    	for (int i = 0; i < n; i++) {//科目数以内のデータを読み込む
	            if (i < token1.length) setSubject(i, token1[i]);//1行目のデータが科目名に設定
	            if (i < token2.length) setScore(i, Integer.parseInt(token2[i]));//2行目のデータがpointに設定
	            if (i < token3.length) setReportScore(i, Integer.parseInt(token3[i]));//3行目のデータがreportに設定
	        }

	        for (int i = n; i < token1.length; i++) {//オーバーの部分はそのindexを示す
	            System.out.print(i + " is a wrong index.\n");
	        }

	        for (int i = n; i < token2.length; i++) {//オーバーの部分はそのindexを示す
	            System.out.print(i + " is a wrong index.\n");
	        }
	        for (int i = n; i < token3.length; i++) {//オーバーの部分はそのindexを示す
	            System.out.print(i + " is a wrong index.\n");
	        }
	   
        }catch (Exception e) {
        	
        }
	}
	
	void writeScores(Path outputFilePath) {
		try(BufferedWriter bw = Files.newBufferedWriter(outputFilePath, StandardCharsets.UTF_8)){
			PrintWriter pw = new PrintWriter(bw);
			pw.printf("The average score of %s: %.2f%n", getStudentName(), calAverage());
	        pw.printf("The average report score of %s: %.2f%n", getStudentName(), calAverage(1));
	        pw.printf("The maximum score of %s: %d [%s]%n", getStudentName(), getMaxScore(), getMaxSubject());
	        pw.printf("The minimum score of %s: %d [%s]%n", getStudentName(), getMinScore(), getMinSubject());
		}catch (Exception e) {
            System.out.println("Exception: " + e);
            e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
        if (args.length == 2) {
            Path inputFilePath = Path.of(args[0]);
            Path outputFilePath = Path.of(args[1]);
            switch (inputFilePath.toString()) {
                case "TaroInput.txt":
                    JisshuScore taro = new JisshuScore("Taro", 9);
                    taro.readScores1(inputFilePath);
                    taro.writeScores(outputFilePath);
                    break;
                case "HanakoInput.txt":
                    JisshuScore hanako = new JisshuScore("Hanako", 6);
                    hanako.readScores1(inputFilePath);
                    hanako.writeScores(outputFilePath);
                    break;
                case "JiroInput.txt":
                    JisshuScore jiro = new JisshuScore("Jiro", 5);
                    jiro.readScores2(inputFilePath);
                    jiro.writeScores(outputFilePath);
                    break;
                case "SaburoInput.txt":
                    JisshuScore saburo = new JisshuScore("Saburo", 3);
                    saburo.readScores2(inputFilePath);
                    saburo.writeScores(outputFilePath);
                    break;
                default:
                    System.err.println("The input file is not found.");
                    break;
            }
        } else {
            System.err.println("java JisshuScore [input_file] [output_file].");
        }
    }
}
