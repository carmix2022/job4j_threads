package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ArrayParallelSearchTest {
    @Test
    void whenIntegerArrayWithGreatLengthThenCorrectWork() {
        Integer[] ar = {1, 2, 3, 1, 5, 6, 7, 1, 9, 10, 11, 12};
        ArrayParallelSearch<Integer> aps = new ArrayParallelSearch<>(ar, 1);
        List<Integer> expectedList = List.of(3, 7, 0);
        List<Integer> factList = aps.indexOf();
        assertThat(factList).hasSameSizeAs(expectedList).hasSameElementsAs(factList);
    }

    @Test
    void whenIntegerArrayWithShortLengthThenCorrectWork() {
        Integer[] ar = {1, 2, 3, 1, 5, 6, 7, 1, 9};
        ArrayParallelSearch<Integer> aps = new ArrayParallelSearch<>(ar, 1);
        List<Integer> expectedList = List.of(0, 3, 7);
        List<Integer> factList = aps.indexOf();
        assertThat(factList).hasSameSizeAs(expectedList).hasSameElementsAs(factList);
    }

    @Test
    void whenStringArrayThenCorrectWork() {
        String[] ar = {"ab", "abc", "jjj", "f", "ff", "ldfd"};
        ArrayParallelSearch<String> aps = new ArrayParallelSearch<>(ar, "f");
        List<Integer> expectedList = List.of(3);
        List<Integer> factList = aps.indexOf();
        assertThat(factList).hasSameSizeAs(expectedList).hasSameElementsAs(factList);
    }

    @Test
    void whenElementIsNotInTheArrayThenEmptyResult() {
        Integer[] ar = {1, 2, 3, 1, 5, 6, 7, 1, 9, 10, 11};
        ArrayParallelSearch<Integer> aps = new ArrayParallelSearch<>(ar, 0);
        List<Integer> factList = aps.indexOf();
        assertThat(factList).isEmpty();
    }

}