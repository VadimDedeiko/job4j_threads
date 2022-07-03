package ru.job4j.concurrent;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFileGet {
    private final File file;

    public ParseFileGet(File file) {
        this.file = file;
    }

    public synchronized String getContent(Predicate<Character> filter) throws IOException {
        String output = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = bufferedReader.read()) > -1) {
                if (filter.test((char) data)) {
                    output += (char) data;
                }
            }
        }
        return output;
    }
}