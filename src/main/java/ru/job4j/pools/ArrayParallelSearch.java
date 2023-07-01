package ru.job4j.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ArrayParallelSearch<T> extends RecursiveAction {

    private static final int THRESHOLD = 10;
    private final T[] array;
    private final T searchingObject;
    private static final List<Integer> RESULT_INDEXES = new ArrayList<>();
    private final int from;
    private final int to;

    public ArrayParallelSearch(T[] array, T searchingObject, int from, int to) {
        this.array = array;
        this.searchingObject = searchingObject;
        this.from = from;
        this.to = to;
    }

    public ArrayParallelSearch(T[] array, T searchingObject) {
        this.array = array;
        this.searchingObject = searchingObject;
        this.from = 0;
        this.to = array.length - 1;
    }

    @Override
    protected void compute() {
        if (this.array.length <= THRESHOLD) {
            for (int i = 0; i < this.array.length; i++) {
                if (this.searchingObject.equals(this.array[i])) {
                    RESULT_INDEXES.add(i);
                }
            }
        } else {
            if (this.from == this.to && this.array[from].equals(this.searchingObject)) {
                RESULT_INDEXES.add(this.from);
            }
            if (this.from != this.to) {
                int mid = (this.from + this.to) / 2;
                invokeAll(new ArrayParallelSearch<T>(this.array, this.searchingObject, this.from, mid),
                        new ArrayParallelSearch<T>(this.array, this.searchingObject, mid + 1, this.to));
            }
        }
    }

    public List<Integer> indexOf() {
        this.invoke();
        List<Integer> rsl = List.copyOf(RESULT_INDEXES);
        RESULT_INDEXES.clear();
        return rsl;
    }
}
