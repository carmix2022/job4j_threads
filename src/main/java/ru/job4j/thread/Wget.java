package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(this.url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(
                     this.url.substring(this.url.lastIndexOf('/') + 1)
             )
        ) {
            int bytesRead;
            int downloadedBytesCount = 0;
            byte[] dataBuffer = new byte[1024];
            long startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long downloadTime = System.currentTimeMillis() - startTime;
                downloadedBytesCount += bytesRead;
                if (downloadedBytesCount > this.speed * downloadTime) {
                        Thread.sleep((long) (downloadedBytesCount * 1.0 / this.speed) - downloadTime);
                }
                downloadedBytesCount = 0;
                startTime = System.currentTimeMillis();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private static void validArgs(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Incorrect number of arguments");
        }
        if (!validURL(args[0])) {
            throw new IllegalArgumentException(String.format("Error: This argument '%s' is not correct URL", args[0]));
        }
        if (!args[1].matches("\\d+")) {
            throw new IllegalArgumentException(String.format("Error: This argument '%s' must be a number", args[1]));
        }
    }

    private static boolean validURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validArgs(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}