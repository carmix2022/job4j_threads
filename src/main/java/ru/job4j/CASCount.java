package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.*;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int current, next;
        do {
            current = count.get();
            next = current + 1;
        } while (!count.compareAndSet(current, next));
    }

    public int get() {
        return count.get();
    }
}