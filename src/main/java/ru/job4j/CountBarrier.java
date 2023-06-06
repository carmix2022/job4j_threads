package ru.job4j;

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
            this.count();
        }
    }
}