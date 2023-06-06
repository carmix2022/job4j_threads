package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int queueMaxSize;

    public SimpleBlockingQueue(int queueMaxSize) {
        this.queueMaxSize = queueMaxSize;
    }

    public synchronized void offer(T value) {
        while (this.queue.size() >= this.queueMaxSize) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.queue.offer(value);
        this.notifyAll();
    }

    public synchronized T poll() {
        while (this.queue.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.notifyAll();
        return this.queue.poll();
    }
}
