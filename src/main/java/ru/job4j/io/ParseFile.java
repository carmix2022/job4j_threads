package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Integer> filter) throws IOException {
        String output;
        try (InputStream i = new FileInputStream(file)) {
            output = "";
            int data;
            while ((data = i.read()) > 0) {
                if (filter.test(data)) {
                    output += (char) data;
                }
            }
        }
        return output;
    }
}