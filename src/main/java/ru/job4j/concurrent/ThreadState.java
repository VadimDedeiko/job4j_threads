package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName()));
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName()));
        first.start();
        second.start();
        System.out.println(first.getState());
        System.out.println(second.getState());

        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getState());
            System.out.println(second.getState());
        }
        System.out.println(first.getState() + " Нить 1 завершила работу");
        System.out.println(second.getState() + " Нить 2 завершила работу");
        System.out.println("работа MAIN завершена");
    }
}