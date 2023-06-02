package ru.job4j.io;

import java.io.*;
import java.nio.file.Files;

public final class WritingToFile {
    private final File file;

    public WritingToFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        Files.writeString(this.file.toPath(), content);
    }
}