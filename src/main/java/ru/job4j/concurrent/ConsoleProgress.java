package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        char[] process = new char[] {'-', '\\', '|', '/'};
        int counter = 0;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\rLoading: " + process[counter]);
                counter++;
                if (counter == 4) {
                    counter = 0;
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();
    }
}
