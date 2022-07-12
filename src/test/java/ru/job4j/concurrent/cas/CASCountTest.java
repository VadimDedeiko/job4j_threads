package ru.job4j.concurrent.cas;

import org.junit.Assert;
import org.junit.Test;

public class CASCountTest {
    @Test
    public void when() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                casCount.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                casCount.increment();
            }
        });
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                casCount.increment();
            }
        });
        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                casCount.increment();
            }
        });
        thread1.start();
        thread2.start();
        thread2.join();
        thread3.start();
        thread3.join();
        thread4.start();
        thread4.join();
        Assert.assertEquals(240, casCount.get());
    }

}