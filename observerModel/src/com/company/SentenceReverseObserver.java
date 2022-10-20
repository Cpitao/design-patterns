package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SentenceReverseObserver implements IObserver{

    String filename = "reversedSentences.txt";


    private String reverseWord(String word) {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i < word.length(); i++)
        {
            builder.append(word.charAt(word.length() - 1 - i));
        }

        return builder.toString();
    }

    @Override
    public void newSentence(String sentence) {
        String[] words = sentence.split(" ");
        for (int i=0; i < words.length; i++)
        {
            words[i] = reverseWord(words[i]);
        }

        appendWordsToFile(words);
    }

    private void appendWordsToFile(String[] words) {
        try {
            File outFile = new File(filename);
            outFile.createNewFile();
        } catch (IOException e) {
            System.out.println("Exception while creating a file");
            e.printStackTrace();
        }

        try {
            File outFile = new File(filename);
            FileWriter outFileWriter = new FileWriter(outFile, true);
            StringBuilder outBuilder = new StringBuilder();
            for (String word: words)
            {
                outBuilder.append(word).append(' ');
            }
            outBuilder.append('\n');
            outFileWriter.write(outBuilder.toString());
            outFileWriter.close();
        } catch (IOException e) {
            System.out.println("An error ocurred");
            e.printStackTrace();
        }
    }
}
