package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;
import java.util.*;
import java.util.stream.*;

public class ThreadPool {
    private final List<Thread> threads;
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);
    private final int size = Runtime.getRuntime().availableProcessors();

    public ThreadPool() {
        threads = IntStream.range(0, size)
                .mapToObj(i -> new Thread(new WorkInPool()))
                .collect(Collectors.toCollection(LinkedList::new));
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            if (!thread.isInterrupted()) {
                thread.interrupt();
            }
        }
    }

    public class WorkInPool implements Runnable {
        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    tasks.poll();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}