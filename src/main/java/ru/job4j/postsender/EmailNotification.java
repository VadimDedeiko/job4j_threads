package ru.job4j.postsender;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private ExecutorService pool;

    public EmailNotification(ExecutorService pool) {
        this.pool = pool;
    }

    public synchronized void emailTo(User user) {
        String subject = String.format("Notification %s to email %s", user.getUsername(), user.getEmail());
        String body = String.format("Add a new event to %s", user.getEmail());
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
        String subject = null;
        String body = null;
        pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        pool.submit(() -> send(subject, body, new User("name", "email").getEmail()));
    }
}
