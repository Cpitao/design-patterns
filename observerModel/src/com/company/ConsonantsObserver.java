package com.company;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ConsonantsObserver extends ObserverFileWriter implements IObserver{

    public ConsonantsObserver() {
        super("consonantCount.txt");
    }

    @Override
    public void newSentence(String sentence) {
        int consonantsCount = 0;
        Set<Character> consonants = new HashSet<>();
        for (char c: "bcdfghjklmnpqrstvwxz".toCharArray())
        {
            consonants.add(c);
        }

        for (int i=0; i < sentence.length(); i++)
        {
            if (consonants.add(sentence.charAt(i)))
            {
                consonantsCount++;
            }
        }

        appendToFile(consonantsCount);
    }
}
