package ru.job4j.concurrent.producerconsumer;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class SimpleBlockingQueueTest {
    SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
    @Test
    public void when() throws InterruptedException {
        Thread consumer = new Thread(() -> {
            queue.poll();
        });
        Thread producer = new Thread(() -> {
            queue.offer(1000);
        });
        producer.start();
        producer.join();
        consumer.start();
        Assert.assertEquals(Optional.of(1000).get(), queue.poll());
    }
}