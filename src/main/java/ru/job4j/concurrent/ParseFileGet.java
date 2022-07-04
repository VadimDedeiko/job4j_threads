package ru.job4j.concurrent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Predicate;

public final class ParseFileGet {
    private final File file;

    public ParseFileGet(File file) {
        this.file = file;
    }

    public synchronized String content(Predicate<Character> filter) {
        String output = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = bufferedReader.read()) != -1) {
                if (filter.test((char) data)) {
                    output += (char) data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public String getContent() {
        return content(e -> true);
    }

    public String getContentWithoutUnicode() {
        return content(e -> e < 0x80);
    }
}