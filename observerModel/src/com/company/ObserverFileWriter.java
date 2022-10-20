package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ObserverFileWriter {
    String filename;

    public ObserverFileWriter(String filename)
    {
        this.filename = filename;
    }

    public void appendToFile(int count)
    {
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
            String out = Integer.toString(count) + '\n';
            outFileWriter.write(out);
            outFileWriter.close();
        } catch (IOException e) {
            System.out.println("An error ocurred");
            e.printStackTrace();
        }
    }
}
