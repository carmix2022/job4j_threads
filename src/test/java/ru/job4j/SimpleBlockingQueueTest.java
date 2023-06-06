package ru.job4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    private final SimpleBlockingQueue sbq = new SimpleBlockingQueue(5);
    private int countProducer = 0;
    private int countConsumer = 0;
    private Thread producer = new Thread(
            () -> {
                for (int i = 1; i < 7; i++) {
                    countProducer++;
                    sbq.offer(i);
                }
            }
    );
    private Thread consumer = new Thread(
            () -> {
                for (int i = 1; i < 7; i++) {
                    countConsumer++;
                    sbq.poll();
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