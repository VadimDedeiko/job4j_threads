package ru.job4j.concurrent.producerconsumer;


import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final int size = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() {
        for (int i = 1; i <= size; i++) {
            while (!Thread.currentThread().isInterrupted()) {
                Thread thread = new Thread(() -> {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
                threads.add(thread);
            }
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool();
        try {
            for (int i = 0; i < 5; i++) {
                int num = i;
                threadPool.work(() -> System.out.println("ThreadPool.main"));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}