package ru.job4j.pools;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class RolColSum {
    public static Sums[] sum(int[][] matrix) {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int column = i;
            rsl[i] = new Sums(
                    Arrays.stream(matrix[i]).sum(),
                    Arrays.stream(matrix).mapToInt(ints -> ints[column]).sum()
            );
        }
        return rsl;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        List<CompletableFuture<Integer[]>> rowColSumFutures = IntStream.range(0, matrix.length)
                .mapToObj(k -> getTask(matrix, k)).toList();
        CompletableFuture<List<Integer[]>> allFutures =
                CompletableFuture.allOf(rowColSumFutures.toArray(new CompletableFuture[0]))
                        .thenApply(v -> rowColSumFutures.stream()
                                .map(CompletableFuture::join)
                                .collect(Collectors.toList()));
        CompletableFuture<Sums[]> sumsArrayFuture = allFutures.thenApply(allSums ->
                allSums.stream()
                        .map(t -> new Sums(t[0], t[1]))
                        .toArray(Sums[]::new));
        return sumsArrayFuture.get();

    }

    private static CompletableFuture<Integer[]> getTask(int[][] matrix, int rowCol) {
        return CompletableFuture.supplyAsync(() -> {
            Integer[] rsl = new Integer[2];
            rsl[0] = Arrays.stream(matrix[rowCol]).sum();
            rsl[1] = Arrays.stream(matrix).mapToInt(ints -> ints[rowCol]).sum();
            return rsl;
        });
    }
}
