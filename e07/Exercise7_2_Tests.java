package e7;

import java.nio.file.Path;

public class Exercise7_2_Tests {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("java WordCount [input dir path] [input file name] [output file path]");
			System.exit(-1);
		}
		WordCount.inputDirPathString = args[0];
		Path inputFilePath = Path.of(WordCount.inputDirPathString, args[1]);
		Path outputFilePath = Path.of(args[2]);

		WordCount.setInputFilePathWordCountMap();
		WordCount wc = WordCount.getWordCountObj(inputFilePath);
		wc.writeTopNFrequentWords(5, outputFilePath);
	}

}
