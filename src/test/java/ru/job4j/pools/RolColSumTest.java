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
        Sums[] factResult = asyncSum(matrix);
        Sums[] expectedResult = {
                new Sums(1, 4),
                new Sums(3, 10),
                new Sums(8, 7),
                new Sums(13, 4)
        };
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
        Sums[] factResult = sum(matrix);
        Sums[] expectedResult = {
                new Sums(1, 4),
                new Sums(3, 10),
                new Sums(8, 7),
                new Sums(13, 4)
        };
        assertThat(factResult).isEqualTo(expectedResult);
    }

}