package ru.job4j.net;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final int intervalBytes = 1024;
    private final int second = 1000;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        long bytesWrited = 0;
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.txt")) {
            byte[] dataBuffer = new byte[intervalBytes];
            int bytesRead;
            long now = System.currentTimeMillis();
            long after;
            long deltaTime;
            while ((bytesRead = in.read(dataBuffer, 0, intervalBytes)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                bytesWrited += bytesRead;
                if (bytesWrited >= speed) {
                    after = System.currentTimeMillis();
                    deltaTime = after - now;

                    if (deltaTime <= second) {
                        Thread.sleep(second - deltaTime);
                    }
                    bytesWrited = 0;
                    now = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validateArgs(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }

    private static void validateArgs(String[] args) {
        if (!isURL(args[0])) {
            throw new IllegalArgumentException("Invalid URL");
        }
        if (args.length != 2) {
            throw new IllegalArgumentException("Enter two arguments - Url and download speed in bytes/second");
        }
        if (Integer.parseInt(args[1]) < 1) {
            throw new IllegalArgumentException("Enter positive speed");
        }
    }

    private static boolean isURL(String url) {
        try {
            (new java.net.URL(url)).openStream().close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}