package ru.job4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    private final SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(5);
    private int countProducer = 0;
    private int countConsumer = 0;
    private final Thread producer = new Thread(
            () -> {
                for (int i = 1; i < 7; i++) {
                    countProducer++;
                    try {
                        sbq.offer(i);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
    );
    private final Thread consumer = new Thread(
            () -> {
                for (int i = 1; i < 7; i++) {
                    countConsumer++;
                    try {
                        sbq.poll();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
    );

    @Test
    void checkNumberOfMethodsHits() throws InterruptedException {
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(countConsumer).isEqualTo(6);
        assertThat(countProducer).isEqualTo(6);
    }


}