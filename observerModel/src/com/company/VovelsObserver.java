package com.company;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class VovelsObserver extends ObserverFileWriter implements IObserver{

    public VovelsObserver() {
        super("vowelCount.txt");
    }

    @Override
    public void newSentence(String sentence) {
        int vowelCount = 0;
        Set<Character> vowels = new HashSet<>(Arrays.asList('a', 'o', 'e', 'y', 'i', 'u'));
        for (int i=0; i < sentence.length(); i++)
        {
            if (vowels.contains(sentence.charAt(i))) {
                vowelCount++;
            }
        }

        appendToFile(vowelCount);
    }
}
