/*
* Implements Two-word index data structure and implements a phrase search in it*/

import java.io.*;
import java.util.*;

public class TwoWordIndex {
    final List<File> fileList;
    TreeMap<String, List<String>> twoWordIndex = new TreeMap<>();

    /**Constructor creates two-word index for a given collections of files*/
    public TwoWordIndex(List<File> fileList){
        this.fileList = fileList;
        createTwoWordIndex();
    }

    /**Creates two-word index*/
    private void createTwoWordIndex(){
        twoWordIndex = new TreeMap<>();
        FileDictionary fd;
        for(File file : fileList){
            fd = new FileDictionary(file);
            LinkedList <String> wordsList = (LinkedList<String>) fd.getWordsList();
            if (wordsList.isEmpty()) continue; // Skip empty sets

            Iterator<String> iterator = wordsList.iterator();
            String word = iterator.next();

            while(iterator.hasNext()){
                String nextWord = iterator.next();
                String phrase = word + " " + nextWord;
                if(twoWordIndex.containsKey(phrase)){
                    // if word phrase is in two-word index, add to its list a new filename
                    twoWordIndex.get(phrase).add(file.getName());
                } else {
                    // if phrase isn't in two-word index, put it there along with array list
                    // containing a file where it was found
                    List<String> filenameList = new ArrayList<>();
                    filenameList.add(file.getName());
                    twoWordIndex.put(phrase, filenameList);
                }
                word = nextWord;
            }
        }
    }

    /**Searches for a given phrase in a file collection using two-word index
     * @return set of filenames containing given phrase*/
    public Set<String> phraseSearch(String phrase){
        // Create list of all word pairs in the phrase
        String delimiters = ",;:()|.!?\"”“‘‘—’'`\f\\{}[]()1234567890/&*-©–™_\t\n\r ";
        StringTokenizer tokenizer = new StringTokenizer(phrase.toLowerCase(), delimiters);

        List<String> wordPairs = new LinkedList<>();
        String word = tokenizer.nextToken();

        if(!tokenizer.hasMoreTokens()){
            throw new IllegalArgumentException("Can't process phrases consisting of one word!");
        }

        while(tokenizer.hasMoreTokens()){
            String nextWord = tokenizer.nextToken();
            wordPairs.add(word + " " + nextWord);
            word = nextWord;
        }


        Set<String> filesContainingPhrase = new HashSet<>();
        for(String wordPair : wordPairs) {
            Set<String> filesContainingWordPair = new HashSet<>();
            if (twoWordIndex.containsKey(wordPair)) {
                filesContainingWordPair.addAll(twoWordIndex.get(wordPair));
                if (!filesContainingPhrase.isEmpty()) {
                    filesContainingPhrase.retainAll(filesContainingWordPair);
                } else {
                    filesContainingPhrase.addAll(filesContainingWordPair);
                }
            }else{
                filesContainingPhrase.clear();
                filesContainingPhrase.add("There is no file containing phrase "+phrase);
                break;
            }
        }
        return filesContainingPhrase;
    }



    @Override
    public String toString(){
        return twoWordIndex.toString();
    }

    /**Saves results to a file named two-word index*/
    public void saveResultsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Two-word index:\n");
            for (Map.Entry<String, List<String>> entry : twoWordIndex.entrySet()) {
                String line = entry.getKey() + " = " + entry.getValue();
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
