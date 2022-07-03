package ru.job4j.concurrent;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFileSave {
    private final File file;

    public ParseFileSave(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) throws IOException {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                bufferedOutputStream.write(content.charAt(i));
            }
        }
    }
}