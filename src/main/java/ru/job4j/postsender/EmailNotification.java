package ru.job4j.postsender;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private volatile String subject;
    private volatile String body;
    private volatile User user;

    public synchronized void emailTo(User user) {
        this.subject = String.format("Notification %s to email %s", user.getUsername(), user.getEmail());
        this.body = String.format("Add a new event to %s", user.getEmail());
    }

    public synchronized void close() {

    }

    public synchronized void send(String subject, String body, String email) {

    }

    private synchronized void excute() {
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        pool.submit(() -> send(subject, body, user.getEmail()));
    }
}
