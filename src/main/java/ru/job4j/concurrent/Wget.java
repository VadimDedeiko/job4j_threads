package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread loading = new Thread(() -> {
            System.out.println("Start loading ... ");
            for (int index = 0; index < 101; index++) {
                try {
                    System.out.print("\rLoading : " + index  + "%");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(System.lineSeparator() + "Loading has finished");
        });
        loading.start();
    }
}
