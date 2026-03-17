package e7;

import java.nio.file.Path;

public class Exercise7_3_Tests {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("java WordCount [test ID] [input dir path] [input file name]");
			System.exit(-1);
		}
		String testID = args[0];
		WordCount.inputDirPathString = args[1];
		Path inputFilePath = Path.of(WordCount.inputDirPathString, args[2]);

		WordCount.setInputFilePathWordCountMap();
		WordCount.setDocFreqMap();
		WordCount wc = WordCount.getWordCountObj(inputFilePath);
		switch (testID) {
		case "ex7-3-1":
			System.out.println(wc.getWordSet().size());
			break;
		case "ex7-3-2":
			int docFreq = WordCount.getDocFreq("the");
			System.out.println(docFreq);
			break;
		case "ex7-3-3":
			double tfidf = wc.calTFIDF(9, 2, 1499, 5);
			System.out.printf("%.3f\n", tfidf);
			break;
		case "ex7-3-4":
			wc.setTFIDFMap();
			tfidf = wc.getTFIDFWeight("auto");
			System.out.printf("%.3f\n", tfidf);
			break;
		case "ex7-3-5":
			wc.setTFIDFMap();
			tfidf = wc.getTFIDFWeight("Hyundai");
			System.out.printf("%.3f\n", tfidf);
			break;
		default:
			System.err.println("java WordCount [test ID] [input dir path] [input file name]");
		}

	}

}
