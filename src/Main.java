/*
* Class for testing TwoWordIndex class on test cases and input provided by user
*/

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        File data = new File("data");
        List<File> fileList = new ArrayList<>();

        if(data.exists() && data.isDirectory()) {
            File[] dataFiles = data.listFiles();
            if(dataFiles != null)
                fileList.addAll(Arrays.asList(dataFiles));
        }

        TwoWordIndex twi = new TwoWordIndex(fileList);
        twi.saveResultsToFile("Two-word index");

        System.out.println(twi.phraseSearch("Harry Potter"));
        System.out.println(twi.phraseSearch("Harry Potter was a highly unusual boy in many ways"));
        System.out.println(twi.phraseSearch("Bilbo Baggins"));
        System.out.println(twi.phraseSearch("were proud to\n" +
                "say that they were perfectly normal, thank you very much"));
        System.out.println(twi.phraseSearch("My dear Prime Minister, are you ever going to tell anybody"));

        System.out.println("Print \"yes\" if you want to search your own phrase in data collection");
        String userAnswer = scanner.nextLine().toLowerCase();
        while(userAnswer.equals("yes")){
            System.out.print("Enter your phrase: ");
            String userPhrase = scanner.nextLine();
            try{
                System.out.println(twi.phraseSearch(userPhrase));
            }catch(IllegalArgumentException ex){
                ex.printStackTrace();
            }

            System.out.println("Print \"yes\" if you want to continue searching your own phrase in data collection");
            userAnswer = scanner.nextLine();
        }
    }
}