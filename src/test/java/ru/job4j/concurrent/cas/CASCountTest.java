package ru.job4j.concurrent.cas;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CASCountTest {
    @Test
    public void when() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread thread1 = new Thread(casCount::increment);
        Thread thread2 = new Thread(casCount::increment);
        thread1.start();
        thread2.start();
        thread2.join();
        Assert.assertEquals(2, casCount.get());
    }

}