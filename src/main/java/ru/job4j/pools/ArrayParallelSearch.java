package ru.job4j.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ArrayParallelSearch<T> extends RecursiveAction {

    private static final int THRESHOLD = 10;
    private static int resultIndex;
    private final T[] array;
    private final T searchingObject;
    private final int from;
    private final int to;

    public ArrayParallelSearch(T[] array, T searchingObject, int from, int to) {
        resultIndex = -1;
        this.array = array;
        this.searchingObject = searchingObject;
        this.from = from;
        this.to = to;
    }

    public ArrayParallelSearch(T[] array, T searchingObject) {
        resultIndex = -1;
        this.array = array;
        this.searchingObject = searchingObject;
        this.from = 0;
        this.to = array.length - 1;
    }

    @Override
    protected void compute() {
        if (to - from < THRESHOLD) {
            for (int i = from; i <= to; i++) {
                if (searchingObject.equals(array[i])) {
                    resultIndex = i;
                }
            }
        } else {
                int mid = (from + to) / 2;
                invokeAll(new ArrayParallelSearch<>(array, searchingObject, from, mid),
                        new ArrayParallelSearch<>(array, searchingObject, mid + 1, to));
        }
    }

    public int indexOf() {
        this.invoke();
        return resultIndex;
    }
}
