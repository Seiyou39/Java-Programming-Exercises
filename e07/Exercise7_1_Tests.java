package e7;

import java.nio.file.Path;

public class Exercise7_1_Tests {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("java WordCount [test ID] [input dir path] [input file name]");
			System.exit(-1);
		}
		String testID = args[0];
		WordCount.inputDirPathString = args[1];
		Path inputFilePath = Path.of(WordCount.inputDirPathString, args[2]);

		switch (testID) {
		case "ex7-1-1":
			WordCount wc = new WordCount(inputFilePath);
			int wordFreq = wc.getWordFreq("test");
			System.out.println(wordFreq);
			break;
		case "ex7-1-2":
			wc = new WordCount(inputFilePath);
			wc.countWordFreq("test");
			wc.countWordFreq("Test");
			wordFreq = wc.getWordFreq("test");
			System.out.println(wordFreq);
			break;
		case "ex7-1-3":
			wc = new WordCount(inputFilePath);
			System.out.println(wc.getInputFilePath());
			break;
		case "ex7-1-4":
			wc = new WordCount(inputFilePath);
			wordFreq = wc.getWordFreq("the");
			System.out.println(wordFreq);
			break;
		case "ex7-1-5":
			WordCount.setInputFilePathWordCountMap();
			wc = WordCount.getWordCountObj(inputFilePath);
			wordFreq = wc.getWordFreq("to");
			System.out.println(wordFreq);
			break;
		default:
			System.err.println("java WordCount [test ID] [input dir path] [input file name]");
		}
	}
}
