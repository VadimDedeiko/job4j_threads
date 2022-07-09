package ru.job4j.postsender;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private volatile String subject;
    private volatile String body;
    private volatile User user;
    private ExecutorService pool;

    public synchronized void emailTo(User user) {
        this.subject = String.format("Notification %s to email %s", user.getUsername(), user.getEmail());
        this.body = String.format("Add a new event to %s", user.getEmail());
    }

    public void close() throws InterruptedException {
        pool.shutdown();
        while (!pool.isTerminated()) {
            Thread.sleep(500);
        }
    }

    public synchronized void send(String subject, String body, String email) {

    }

    private synchronized void excute() {
        pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        pool.submit(() -> send(subject, body, user.getEmail()));
    }
}
