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
                .mapToObj(i -> new Thread(() -> {
                    try {
                        while (!Thread.currentThread().isInterrupted()) {
                            tasks.poll().run();
                        }
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                    }
                }))
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

    public static void main(String[] args) {
        ThreadPool tp = new ThreadPool();
        try {
            for (int i = 0; i < 10; i++) {
                tp.work(() -> System.out.printf("Task execute in thread %s%n", Thread.currentThread().getName()));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}