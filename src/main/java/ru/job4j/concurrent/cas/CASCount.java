package ru.job4j.concurrent.cas;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        Integer check;
        do {
            check = count.get();
        } while (!count.compareAndSet(check, check + 1));
    }

    public int get() {
        return count.get();
    }
}