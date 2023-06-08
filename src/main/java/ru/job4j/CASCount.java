package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.*;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        count.incrementAndGet();

    }

    public int get() {
        return count.get();
    }
}