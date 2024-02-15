/*
 * Helper class for converting the file into a list of normalized tokens
 */

import java.io.*;
import java.util.*;

public class FileDictionary {
    private File file;
    private List<String> wordsList;
    private Set<String> uniqueWordsSet;

    public FileDictionary(File file){
        this.file = file;
        analyzeFile();
    }

    /**@return Set of unique words found in a given file*/
    public Set<String> getUniqueWords(){
        return uniqueWordsSet;
    }

    public List<String> getWordsList(){
        return wordsList;
    }

    /** Analyzes given collection:
     * Creates list of all words from a given file,
     * creates set of unique words in the file
     */
    private void analyzeFile() {
        wordsList = new LinkedList<>();

        readWords(file);
        uniqueWordsSet = new LinkedHashSet <>(wordsList);
    }

    /** @return List of all words read from the input file */
    private void readWords(File file){
        StringTokenizer tokenizer;

        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            String delimiters = ",;:()|.!?\"”“‘‘—’'\f1234567890/&*-\\[]{}_\t\n\r ";
            while((line = reader.readLine()) != null){
                tokenizer = new StringTokenizer(line, delimiters);
                while (tokenizer.hasMoreTokens())
                    wordsList.add(tokenizer.nextToken().toLowerCase(Locale.ROOT));
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return uniqueWordsSet.toString();
    }
}
