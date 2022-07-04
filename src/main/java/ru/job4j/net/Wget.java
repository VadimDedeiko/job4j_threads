package ru.job4j.net;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String fileName;
    private static final int INTERVALBYTES = 1024;
    private static final int SECOND = 1000;

    public Wget(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        long bytesWrited = 0;
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[INTERVALBYTES];
            int bytesRead;
            long now = System.currentTimeMillis();
            long after;
            long deltaTime;
            while ((bytesRead = in.read(dataBuffer, 0, INTERVALBYTES)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                bytesWrited += bytesRead;
                if (bytesWrited >= speed) {
                    after = System.currentTimeMillis();
                    deltaTime = after - now;

                    if (deltaTime < SECOND) {
                        Thread.sleep(SECOND - deltaTime);
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
        String fileName = args[2];
        Thread wget = new Thread(new Wget(url, speed, fileName));
        wget.start();
        wget.join();
    }

    private static void validateArgs(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Enter three arguments "
                    + "- Url, download speed in bytes/second and file name");
        }
        if (Integer.parseInt(args[1]) < 1) {
            throw new IllegalArgumentException("Enter positive speed");
        }
    }
}