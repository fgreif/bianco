package files;

import java.io.*;
import java.util.*;

/**
 * Created by Florian on 08.12.2017.
 *
 * purpose:
 *
 * reads from an input file, removes duplicate lines
 * then writes to output file, finally counts the occurences
 * of each word and puts them into a HashMap. Also turns a file into an array
 * of words.
 *
 *
 * ReadFile(String inputFileName, String outputFileName):
 *      Constructor, does the above
 *
 * void readFile(String inputFileName, String outputFileName) throws IOException:
 *      uses BufferedReader to read input file; in order
 *      to avoid duplicates, safes each line into a HashSet
 *      then writes to output file
 *      will throw an IOException if something goes wrong
 *
 * Map<String, Integer> countByWords():
 *
 *      uses the output file as new input file and maps the number
 *      of each word accordingly.
 *
 * String[] fileToArray():
 *      turns a text file into array
 */
public class ReadFile {

    private String inputFileName;
    private String outputFileName;
    private Map<String, Integer> wordCount;
    private String[] words;

    /**
     * sets fields, executes readFile and makes the output file, sets HashMap to countByWords()
     * @param inputFileName
     * @param outputFileName
     * @throws IOException
     */
    public ReadFile(String inputFileName, String outputFileName) throws IOException {

        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        readFile(getInputFileName(), getOutputFileName());
        this.wordCount = countByWords();
        this.words = fileToArray();
    }

    /**
     * deletes duplicate lines from a file, saves new file in outputFileName
     * @param inputFileName
     * @param outputFileName
     * @throws IOException
     */
    private void readFile(String inputFileName, String outputFileName) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
            Set<String> lines = new HashSet<>(); // maybe should be bigger
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));
            for (String s : lines) {
                writer.write(s);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * counts each occurence of each word, uses the outputfile generated by readFile as input
     * @return
     */
    private Map<String, Integer> countByWords() {
        Map<String, Integer> countByWords = new HashMap<>();
        Scanner s = null;
        try {
            s = new Scanner(new File(getOutputFileName()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (s.hasNext()) {
            String next = s.next();
            Integer count = countByWords.get(next);
            if (count != null) {
                countByWords.put(next, count + 1);
            } else {
                countByWords.put(next, 1);
            }
        }
        s.close();

        return countByWords;
    }

    /**
     *
     * turns a text file into array of words
     * @return
     */
    private String[] fileToArray() {
        List<String> words = new ArrayList<>();
        Scanner s;
        try {
            s = new Scanner(new File(getOutputFileName()));
            while(s.hasNext()) {
                words.add(s.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words.toArray(new String[words.size()]);
    }

    /**
     * getter for input file name
     * @return
     */
    public String getInputFileName() {
        return inputFileName;
    }

    /**
     * getter for output file name
     * @return
     */
    public String getOutputFileName() {
        return outputFileName;
    }

    /**
     * getter for map of word count
     * @return
     */
    public Map<String, Integer> getWordCount() {
        return wordCount;
    }

    /**
     * getter for array of words
     * @return
     */
    public String[] getWords() {
        return words;
    }
}
