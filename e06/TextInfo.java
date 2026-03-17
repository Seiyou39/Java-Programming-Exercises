//15824071 ZHANG JINGYANG
package e6;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class TextInfo {

    private String sourceName; // variable to store the name of the information source (input file name)
    private int total; // variable to store the number of words
    private HashMap<String, Integer> wordFreq; // HashMap to store the frequency of each word

    // Constructor that takes the name of the information source as its argument
    TextInfo(String fn) {
    	this.sourceName = fn;// 入力ファイル名を保存
    	this.total = 0;//単語の総数を 0 に初期化
    	this.wordFreq = new HashMap<>();// 単語と出現回数を保存するための HashMap を生成
    	//全てのデータを初期化する
    }

    // Getter method to return the value of sourceName
    String getSourceName() {
        return sourceName;//sourceNameを返すgetterメソッド
    }

    // Setter method to set the name of the information source (file name)
    void setSourceName(String s) {
        sourceName = s;//sでsourceNameを設定するSetterメソッド
    }

    // Method to increment the frequency of a word specified by its argument
    private void countFreq(String word) {
    	if(wordFreq.containsKey(word)) {//既に存在する単語なら、出現回数を +1
    		wordFreq.put(word,wordFreq.get(word) + 1);
    	}
    	else {
    		wordFreq.put(word,1);//初めて出現する単語なら、回数を 1 に設定
    	}
    	total++;
    }

    // Method to read text from the information source (input file) and count the frequency of each word
    void readFile() {
    	try {
    		BufferedReader br = new BufferedReader(new FileReader(sourceName));// 入力ファイルを読み込み、単語ごとの出現回数を数えるメソッド
    		String s;
    		while ((s = br.readLine()) != null) {
    			String[] token = s.split("\\s+");//空白を消す
    			for(String w:token) {
    				countFreq(w);//出現回数をカウント
    			}
    		}
    		br.close();
    	}catch(Exception e) {
    		 System.out.println("Exception: " + e);
             e.printStackTrace();
    	}
    }

    // Method to return the frequency of a word specified by its argument
    int getFreq(String word) {
    	if(wordFreq.containsKey(word)) {//もしHashMaoの中にこの単語があれば
    		return wordFreq.get(word);//この単語のValueを返す
    	}else {//なければ0を返す
    		return 0;
    	}
    }

    // Method to output of the frequency of every word
    void writeAllFreq(String outputFilePath) {// すべての単語の出現回数を出力ファイルに書き出すメソッド
    	try {
    		BufferedWriter bw =new BufferedWriter(new FileWriter(outputFilePath));	
    		bw.write("Information source: " + getSourceName());
            bw.newLine();
            for (String word : wordFreq.keySet()) {// 各単語とその出現回数を出力
                bw.write("Frequency of " + "\"" + word + "\": " + wordFreq.get(word));
                bw.newLine();
    		}	
            bw.close();
    	}catch(Exception e) {
    		 System.out.println("Exception: " + e);
             e.printStackTrace();
    	}
    }

    // Return the self-information of the specified word
    double calSelfInfo(String word) {// 指定された単語の自己情報量Self-Informationを計算するメソッド
    	int count = getFreq(word);
        if(count != 0) {
        	double px = (double)count/total;
        	return -(Math.log(px) / Math.log(2.0));//底の変換公式で計算し、自己情報量を返す
        }else {
        	return 0;
        }
    }

    // Method to output the self-information of every word
    void writeAllSelfInfo(String outputFilePath) { //すべての単語の自己情報量を出力ファイルに書き出すメソッド
    	try {
    		BufferedWriter bw =new BufferedWriter(new FileWriter(outputFilePath));	
    		bw.write("Information source: " + getSourceName());
            bw.newLine();
            for (String word : wordFreq.keySet()) { // 各単語の自己情報量を出力
                bw.write("Self-Information of " + "\"" + word + "\": " + String.format("%.3f", calSelfInfo(word)));
                bw.newLine();
    		}
            bw.write("Information of \"" + getSourceName() + "\": " + String.format("%.3f", calInfo())); // ファイル全体の平均情報量（エントロピー）を出力
            bw.close();
    	}catch(Exception e) {
    		 System.out.println("Exception: " + e);
             e.printStackTrace();
    	}
    }

    // Method to return the entropy of the information source
    double calInfo() {  //情報源全体の平均情報量（エントロピー）を計算するメソッド
    	double Entropy = 0.0;
        for(String word : wordFreq.keySet()) {
        	int count = getFreq(word);
        	double px = (double)count/total;
        	Entropy += px * calSelfInfo(word);
        }
        return Entropy;
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println(
                "java TextInfo [freq|self-info] [input file path] [output file path]"
            );
            System.exit(-1);
        }
        String type = args[0];
        String inputFilePath = args[1];
        String outputFilePath = args[2];
        TextInfo source = new TextInfo(inputFilePath);
        source.readFile();
        switch (type) {
            case "freq":
                source.writeAllFreq(outputFilePath);
                break;
            case "self-info":
                source.writeAllSelfInfo(outputFilePath);
                break;
            default:
                System.err.println(
                    "java TextInfo [freq|self-info] [input file path] [output file path]"
                );
        }

        /*
         * If you implement your own functions, uncomment the following two lines, and
         * make a code to test your own functions under those lines.
         */
        // System.out.println("\n Hereafter, the results of my own functions");
        // System.out.println("=========================================");
    }
}
