package ru.job4j.concurrent.producerconsumer;

import com.google.errorprone.annotations.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    private final int capacity;
    private volatile int size;
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void offer(T value) {
        if (size <= capacity) {
            queue.offer(value);
            notifyAll();
            size++;
        }
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new InterruptedException();
            }
        }
        notifyAll();
        return queue.poll();
    }


    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
