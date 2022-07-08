package ru.job4j.concurrent.cas;

import org.junit.Assert;
import org.junit.Test;

public class CacheTest {
    @Test(expected = OptimisticException.class)
    public void whenVersionsNotEqual() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        cache.add(base1);
        cache.update(base1);
        cache.update(base1);
    }

    @Test
    public void when() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        Assert.assertTrue(cache.add(base1));
    }
}