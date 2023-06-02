package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
            for (byte readByte : input.readAllBytes()) {
                if (filter.test((char) readByte)) {
                    output.append((char) readByte);
                }
            }
        }
        return output.toString();
    }
}