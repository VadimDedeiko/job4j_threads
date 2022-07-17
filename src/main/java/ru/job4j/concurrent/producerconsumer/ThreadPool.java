package ru.job4j.concurrent.producerconsumer;


import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(Runtime.getRuntime().availableProcessors());

    public ThreadPool(int numberThreads) {
        for (int i = 0; i < numberThreads; i++) {
            try {
                Thread thread = new Thread(tasks.poll());
                threads.add(thread);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        while (!Thread.currentThread().isInterrupted()) {
            for (Thread thread : threads) {
                thread.start();
            }
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) {
        try {
            for (int i = 0; i < 5; i++) {
                int num = i;
                new ThreadPool(4).work(() -> System.out.println(num));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}