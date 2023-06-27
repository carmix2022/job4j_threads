package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @Test
    public void whenTwoThreadsThenCorrectWork() throws InterruptedException {
        CASCount casCount = new CASCount();
        int stepsNumber = 3;
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < stepsNumber; i++) {
                        casCount.increment();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < stepsNumber; i++) {
                        casCount.increment();
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(casCount.get()).isEqualTo(2 * stepsNumber);
    }

}