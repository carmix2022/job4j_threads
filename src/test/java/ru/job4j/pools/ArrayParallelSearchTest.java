package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ArrayParallelSearchTest {
    @Test
    void whenIntegerArrayWithGreatLengthThenCorrectWork() {
        Integer[] ar = {11, 1, 2, 3, 5, 6, 7, 11, 9, 10, 11, 12};
        ArrayParallelSearch<Integer> aps = new ArrayParallelSearch<>(ar, 1);
        Integer expectedIndex = 1;
        Integer factIndex = aps.indexOf();
        assertThat(factIndex).isEqualTo(expectedIndex);
    }

    @Test
    void whenIntegerArrayWithShortLengthThenCorrectWork() {
        Integer[] ar = {2, 3, 11, 5, 6, 1, 11, 9};
        ArrayParallelSearch<Integer> aps = new ArrayParallelSearch<>(ar, 1);
        Integer expectedIndex = 5;
        Integer factIndex = aps.indexOf();
        assertThat(factIndex).isEqualTo(expectedIndex);
    }

    @Test
    void whenStringArrayThenCorrectWork() {
        String[] ar = {"ab", "abc", "jjj", "f", "ff", "ldfd"};
        ArrayParallelSearch<String> aps = new ArrayParallelSearch<>(ar, "ff");
        Integer expectedIndex = 4;
        Integer factIndex = aps.indexOf();
        assertThat(factIndex).isEqualTo(expectedIndex);
    }

    @Test
    void whenElementIsNotInTheArrayThenEmptyResult() {
        Integer[] ar = {1, 2, 3, 1, 5, 6, 7, 1, 9, 10, 11};
        ArrayParallelSearch<Integer> aps = new ArrayParallelSearch<>(ar, 0);
        Integer expectedIndex = -1;
        Integer factIndex = aps.indexOf();
        assertThat(factIndex).isEqualTo(expectedIndex);
    }

}