package ru.job4j.concurrent.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        Integer incremented = count.get() + 1;
        Integer check;
        do {
            check = count.get();
        } while (!count.compareAndSet(check, incremented));
    }

    public int get() {
        Integer get = count.get();
        if (get == null) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        return get;
    }
}