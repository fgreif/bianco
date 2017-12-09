package files;

import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileAnalyser {

	/**
	 * USAGE EXAMPLE
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FileAnalyser fh = new FileAnalyser("okcool.txt");
			fh.analyse();

			for (Entry<String, Long> f : fh.getWordsWithCount()) {
				System.out.println(f.getKey() + " " + f.getValue());
			}
		} catch (Exception e) {
			System.err.println("FUUUUUUUUUUUUUUUUUUUUUU!");
			e.printStackTrace();
		}
	}
	
	private Path path = null;
	private ConcurrentMap<String, Long> analysedWords;

	public FileAnalyser(String path) {
		try {
			this.path = Paths.get(path);
			if (!this.path.toFile().exists()) {
				throw new FileNotFoundException("This file could not be found!" + path);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Please enter a valid filepath!", e);
		}
	}

	/**
	 * Analyses a file
	 * 
	 * @param path
	 * @return true if successfull, false otherwise
	 */
	public boolean analyse() {
		try {
			analysedWords = Files.lines(path, Charset.forName("Cp1252"))
					.flatMap(Pattern.compile(" ")::splitAsStream)
					.map(s -> s.replaceAll("[^a-zA-Z0-9üצה]", ""))
					.filter(x -> filterOutThisShit(x))
					.collect(Collectors.groupingByConcurrent(Function.identity(), Collectors.<String>counting()));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	/**
	 * Use this method to define, which strings are valid for counting, and
	 * which should be kicked out while processing
	 * 
	 * @param x
	 *            the string to be checked
	 * @return true if is valid, false otherwise
	 */
	private boolean filterOutThisShit(String x) {
		return x.length() > 1; // TODO maybe implement something intelligent
		//for now just filtering parsing errors with the length of one
	}

	/**
	 * @return a String array containing all words of the analysed file
	 */
	public String[] getWords() {
		return analysedWords.keySet().toArray(new String[analysedWords.size()]);
	}

	/**
	 * @return the number of different words in the input files
	 */
	public int getTotalNumOfWords() {
		return analysedWords.size();
	}

	/**
	 * @return an entry set containing the words with their respective count
	 */
	public Set<Entry<String, Long>> getWordsWithCount() {
		return analysedWords.entrySet();
	}

}
