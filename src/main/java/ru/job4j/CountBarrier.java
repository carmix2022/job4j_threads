package ru.job4j;

import javax.swing.text.TableView;

public class CountBarrier {
    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public synchronized void count() {
        count++;
        this.notifyAll();
    }

    public synchronized void await() {
        while (this.count < this.total) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier cb = new CountBarrier(10);
        Thread thread1 = new Thread(
                () -> {
                    cb.await();
                    System.out.println("конец");
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    for (int i = 1; i < 11; i++) {
                        cb.count();
                    }
                }
        );
        thread1.start();
        thread2.start();
    }
}