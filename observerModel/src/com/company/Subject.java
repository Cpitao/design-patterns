package com.company;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

public class Subject {
    private ArrayList<IObserver> observers = new ArrayList<>();

    public void readFile(String filename)
    {
        try {
            InputStream in = new FileInputStream(filename);
            Reader reader = new InputStreamReader(in, Charset.defaultCharset());
            Reader buffer = new BufferedReader(reader);
            int r;
            boolean sentenceBegan = false;
            StringBuilder sentence = new StringBuilder();
            while ((r = buffer.read()) != -1)
            {
                if (r != '.') {
                    sentenceBegan = true;
                    sentence.append((char) r);
                }
                else if (sentenceBegan)
                {
                    sentenceBegan = false;
                    notify(sentence.toString());
                    sentence = new StringBuilder();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addObserver(IObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(IObserver observer) {

    }

    public void notify(String sentence) {
        for (IObserver observer: observers)
        {
            observer.newSentence(sentence);
        }
    }
}
