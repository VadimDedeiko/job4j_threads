package ru.job4j.forkjoinpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Search<T> extends RecursiveTask<Integer> {
    private final T[] objects;
    private final T object;
    private final int start;
    private final int finish;
    private static final int STANDART = 10;

    public Search(T[] objects, T object, int start, int finish) {
        this.objects = objects;
        this.object = object;
        this.start = start;
        this.finish = finish;
    }

    @Override
    protected Integer compute() {
        int rsl;
        if ((finish - start) <= STANDART) {
            rsl = founded();
        } else {
            int middle = (start + finish) / 2;
            Search<T> searchFirst = new Search(objects, object, start, middle);
            Search<T> searchSecond = new Search(objects, object, middle + 1, finish);
            searchFirst.fork();
            searchSecond.fork();
            int first = searchFirst.join();
            int second = searchSecond.join();
            rsl = first == 0 ? second : first;
        }
        return rsl;
    }

    public static <T> int execute(T[] numbers, T search, int start, int finish) {
        return new ForkJoinPool().invoke(new Search<T>(numbers, search, start, finish));
    }

    private Integer founded() {
        Integer rsl = -1;
        for (int i = start; i <= finish; i++) {
            if (objects[i] != null && objects[i].equals(object)) {
                rsl = i;
                return rsl;
            }
        }
        return rsl;
    }


    public static void main(String[] args) {
        Integer[] numbers = new Integer[20];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 2;
        }
        System.out.println(execute(numbers, 5, 0, 19));
    }
}

