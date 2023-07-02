package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;
import static ru.job4j.pools.RolColSum.asyncSum;
import static ru.job4j.pools.RolColSum.sum;

class RolColSumTest {

    @Test
    void whenAsyncSumThenCorrectWork() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 0, 0, 0},
                {1, 2, 0, 0},
                {1, 4, 3, 0},
                {1, 4, 4, 4}
        };
        String factResult = Arrays.toString(asyncSum(matrix));
        String expectedResult = "[Sums{rowSum=1, colSum=4}, Sums{rowSum=3, colSum=10}, "
                + "Sums{rowSum=8, colSum=7}, Sums{rowSum=13, colSum=4}]";
        assertThat(factResult).isEqualTo(expectedResult);
    }

    @Test
    void whenSyncSumThenCorrectWork() {
        int[][] matrix = {
                {1, 0, 0, 0},
                {1, 2, 0, 0},
                {1, 4, 3, 0},
                {1, 4, 4, 4}
        };
        String factResult = Arrays.toString(sum(matrix));
        String expectedResult = "[Sums{rowSum=1, colSum=4}, Sums{rowSum=3, colSum=10}, "
                + "Sums{rowSum=8, colSum=7}, Sums{rowSum=13, colSum=4}]";
        assertThat(factResult).isEqualTo(expectedResult);
    }

}