package ru.job4j.concurrent.producerconsumer;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class SimpleBlockingQueueTest {
    SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
    @Test
    public void when() throws InterruptedException {
        Thread consumer = new Thread(() -> {
            try {
                queue.poll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread producer = new Thread(() -> queue.offer(1000));
        producer.start();
        producer.join();
        consumer.start();
        Assert.assertEquals(Optional.of(1000).get(), queue.poll());
    }
}