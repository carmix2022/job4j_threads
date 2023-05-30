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
        var process = new char[] {'-', '\\', '|', '/'};
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (char charElement : process) {
                    System.out.print("\r load: " + charElement);
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
