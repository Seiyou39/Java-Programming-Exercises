//15824071 ZHANG JINGYANG
package e7;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map.Entry;
import java.util.*;
import java.io.*;

// Exercise 7
public class WordCount {
	protected Path inputFilePath;
	public static String inputDirPathString;// 入力ディレクトリのパス
	private Map<String, Integer> wordFreqMap;// 単語の頻度のマップ
	private Map<String, Double> tfIdfMap;// 単語のTF-IDF値のマップ
	private static Map<String, Integer> docFreqMap = new HashMap<>();// 単語が何個の文書に出現したかのマップ
	private static Map<Path, WordCount> inputFilePathWordCountMap = new HashMap<>();

	/**
	 * Exercise 7-1: Initializes the inputFilePath, wordFreqMap, and tfIdfMap fields.
	 * Calls the readFile() method.
	 */
	WordCount(Path inputFilePath) {//ファイルパスを受け取り，マップを初期化し，ファイル読み込み
		this.inputFilePath = inputFilePath;
		this.wordFreqMap = new HashMap<>();
		this.tfIdfMap = new HashMap<>();
		readFile();	
		
	}

	/**
	 * Exercise 7-1: Increments the frequency of the word specified by its argument.
	 */
	void countWordFreq(String word) {
		word = word.toLowerCase(); // 小文字にし
	    wordFreqMap.put(word, wordFreqMap.getOrDefault(word, 0) + 1);// 頻度マップに追加
	}

	/**
	 * Exercise 7-1: Reads text from the input file, converts all words to
	 * lowercase, and counts the frequency of each word.
	 * 
	 */
	void readFile() {
		try {
			List<String> lines = Files.readAllLines(inputFilePath, StandardCharsets.UTF_8); // UTF-8 でファイルを読み込み，全行を取得
			for(String line:lines) {
				if(!line.isEmpty()) {
					String[] token = line.split("\\s+");// 空白で単語に分割
					for(String w:token) { // それぞれの単語の頻度をカウント
						countWordFreq(w);
					}
				}
			}
		}catch(Exception e) {
         e.printStackTrace();
		}
	}

	/**
	 * Exercise 7-1: Gets the input file path.
	 */
	Path getInputFilePath() {
		return inputFilePath;
	}

	/**
	 * Exercise 7-1: Sets inputFilePathWordCountMap, which stores pairs of an
	 * inputFilePath (key) and a WordCount instance (value).
	 */
	static void setInputFilePathWordCountMap() {
		try {
	        File dir = new File(inputDirPathString);//Practice7-1の方法で入力ディレクトリ内の全ファイルを読み
	        File[] files = dir.listFiles();
	        for (File file : files) {
                WordCount wc = new WordCount(file.toPath());
                inputFilePathWordCountMap.put(file.toPath(), wc);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * Exercise 7-1: Gets the WordCount instance associated with the input file path.
	 */
	static WordCount getWordCountObj(Path inputFilePath) {
		return inputFilePathWordCountMap.get(inputFilePath);
	}

	/**
	 * Exercise 7-2: Writes the top n words ranked by frequency, along with their
	 * frequencies, to the specified output file path.
	 */
	void writeTopNFrequentWords(int n, Path outputFilePath) { //  出現頻度トップnの単語を書き出す
		try {//Practice7-3の方法で降順で単語を書き出す
			List<Entry<String,Integer>>entries = new ArrayList<>(wordFreqMap.entrySet());// entriesという可変長リストを作り、Mapの中身を全部 Entry（key,value ペア）として ArrayList に移す
			Collections.sort(entries, new Comparator<>() {  // 出現頻度の降順でソート
			    public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2) {
			        return obj2.getValue().compareTo(obj1.getValue());
			    }
			});
			
			BufferedWriter bw = Files.newBufferedWriter(outputFilePath, StandardCharsets.UTF_8);
	        int count = 0;
	        bw.write("Top " + n + " words in frequency");
	        bw.newLine();
	        for (Entry<String, Integer> entry : entries) {
	        	count++;
	            bw.write(count +": " + entry.getKey() +" ("+ entry.getValue() +")");
	            bw.newLine();
	            if (count >= n) break;
	        }
	        bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Exercise 7-3: Gets the frequency of the word specified by its argument.
	 */
	int getWordFreq(String word) {
		word = word.toLowerCase();
	    return wordFreqMap.getOrDefault(word, 0);
	}

	/**
	 * Exercise 7-3: Gets the set of words stored in its instance.
	 */
	Set<String> getWordSet() {
		return wordFreqMap.keySet();
	}

	/**
	 * Exercise 7-3: Calculates the total word count from wordFreqMap.
	 */
	int getTotalWordCount() {//文書内の総単語数を計算して返す
		int total = 0;
		for(int count:wordFreqMap.values()) {
			total += count;
		}
		
		 return total;
	}

	/**
	 * Exercise 7-3: Sets doc_freq that stores the pairs of a word (key) and the
	 * number of documents in which the word appears (value).
	 */
	static void setDocFreqMap() {//各単語が何個の文書に出現したかをdocFreqMapにセットする
		docFreqMap.clear();
		for (WordCount wc : inputFilePathWordCountMap.values()) {
	        for (String word : wc.getWordSet()) {
	            docFreqMap.put(word, docFreqMap.getOrDefault(word, 0) + 1);
	        }
	    }
	}

	/**
	 * Exercise 7-3: Gets the number of documents in which the input word appears.
	 */
	static int getDocFreq(String word) {
		word = word.toLowerCase();
		return docFreqMap.getOrDefault(word, 0);
	}

	/**
	 * Exercise 7-3: Calculates the TF-IDF weight of each word stored in its
	 * instance.
	 */
	double calTFIDF(int wf, int df, int totalNumOfWords, int totalNumOfDocs) {// TF-IDF を計算
		double tf,idf;
		tf = (double) wf/totalNumOfWords;
		idf = Math.log((double) totalNumOfDocs/df);
		return tf*idf;
		
	}

	/**
	 * Exercise 7-3: Calculates the TF-IDF weight of each word stored in the
	 * wordFreqMap. Set tfidfMap that stores the pairs of a word (key) and the
	 * TF-IDF weight.
	 */
	void setTFIDFMap() { // 文書内の単語についてcalTFIDF関数を利用し、TF-IDFを計算し、tfIdfMapに保存
		tfIdfMap.clear();
		for (String w : wordFreqMap.keySet()) {
		    int wf = getWordFreq(w);
		    int df = getDocFreq(w);
		    double tfidf = calTFIDF(wf, df, getTotalWordCount(), inputFilePathWordCountMap.size());
		    tfIdfMap.put(w, tfidf);
		}
		
	}

	/**
	 * Exercise 7-3: Gets the TF-IDF weight of the specified word.
	 */
	double getTFIDFWeight(String word) {
		word = word.toLowerCase();
		return tfIdfMap.getOrDefault(word,0.0);
	}

	/**
	 * Exercise 7-4: Writes the top n words ranked by TF-IDF weight, along with their
	 * weights, to the specified output file path.
	 */
	void writeTopNTfIdfWords(int n, Path outputFilePath) {// TF-IDFトップn単語を書き出す
		try {
			List<Entry<String,Double>>entries = new ArrayList<>(tfIdfMap.entrySet());//　ここでEntryは<String,Double>のセット
			Collections.sort(entries, new Comparator<>() {// 降順ソート
			    public int compare(Entry<String, Double> obj1, Entry<String, Double> obj2) {
			        return obj2.getValue().compareTo(obj1.getValue());
			    }
			});
			
			BufferedWriter bw = Files.newBufferedWriter(outputFilePath, StandardCharsets.UTF_8);
	        int count = 0;
	        bw.write("Top " + n + " words in TF-IDF weight");
	        bw.newLine();
	        for (Entry<String, Double> entry : entries) {
	        	count++;
	            bw.write(count +": " + entry.getKey() +" ("+ String.format("%.3f", entry.getValue()) +")");
	            bw.newLine();
	            if (count >= n) break;
	        }
	        bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
