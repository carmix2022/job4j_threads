package ru.job4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(6);
        Thread producer = new Thread(
                () -> IntStream.range(0, 10).forEach(
                        (i) -> {
                            try {
                                queue.offer(i);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).isEqualTo(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

}