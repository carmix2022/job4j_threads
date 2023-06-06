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

    public synchronized void offer(T value) throws InterruptedException {
        while (this.queue.size() >= this.queueMaxSize) {
            this.wait();
        }
        this.queue.offer(value);
        this.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (this.queue.isEmpty()) {
            this.wait();
        }
        this.notifyAll();
        return this.queue.poll();
    }
}
