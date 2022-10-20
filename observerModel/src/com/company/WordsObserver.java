package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class WordsObserver extends ObserverFileWriter implements IObserver {

    public WordsObserver() {
        super("wordCount.txt");
    }

    @Override
    public void newSentence(String sentence) {
        int wordCount = sentence.split(" ").length;
        appendToFile(wordCount);
    }
}
