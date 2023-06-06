package ru.job4j;

import javax.swing.text.TableView;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private volatile int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            this.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (this.count < this.total) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier cb = new CountBarrier(10);
        Thread thread = new Thread(
                () -> {
                    cb.await();
                    System.out.println("конец");
                }
        );
        for (int i = 1; i < 11; i++) {
            cb.count();
        }
        thread.start();
    }
}