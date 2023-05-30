package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }

    @Override
    public void run() {
        try {
            var process = new char[] {'-', '\\', '|', '/'};
            for (int i = 0; !Thread.currentThread().isInterrupted(); i = (i + 1) % process.length) {
                System.out.print("\r load: " + process[i]);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
