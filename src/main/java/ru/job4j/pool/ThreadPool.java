package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;
import java.util.*;
import java.util.stream.*;

public class ThreadPool {
    private final List<ThreadInPool> threads;
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);
    private final int size = Runtime.getRuntime().availableProcessors();

    public ThreadPool() {
        threads = IntStream.range(0, size)
                .mapToObj(i -> new ThreadInPool())
                .collect(Collectors.toCollection(LinkedList::new));
        threads.forEach(ThreadInPool::run);
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (ThreadInPool thread : threads) {
            if (!thread.isInterrupted()) {
                thread.interrupt();
            }
        }
    }

    public class ThreadInPool extends Thread implements Runnable {
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