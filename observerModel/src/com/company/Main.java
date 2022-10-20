package com.company;

import java.util.concurrent.CopyOnWriteArrayList;

public class Main {

    public static void main(String[] args) {
        Subject subject = new Subject();
        subject.addObserver(new WordsObserver());
        subject.addObserver(new VovelsObserver());
        subject.addObserver(new ConsonantsObserver());
        subject.addObserver(new SentenceReverseObserver());
        subject.readFile("example.txt");
    }
}
