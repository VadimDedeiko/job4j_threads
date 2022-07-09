package ru.job4j.forkjoinpool;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Search extends RecursiveTask<String> {
    private final Object[] objects;
    private final Object object;

    public Search(Object[] objects, Object object) {
        this.objects = objects;
        this.object = object;
    }

    @Override
    protected String compute() {
        if (objects.length <= 10) {
            for (int i = 0; i < objects.length; i++) {
                if (objects[i] != null && objects[i].equals(object)) {
                    return String.valueOf(i);
                }
            }
        }
        int middle = objects.length / 2;
        Search searchFirst = new Search(
                Arrays.copyOfRange(objects, 0, middle), object
        );
        Search searchSecond = new Search(
                Arrays.copyOfRange(objects, middle, objects.length), object
        );
        searchFirst.fork();
        searchSecond.fork();
        String first = searchFirst.join();
        String second = searchSecond.join();
        StringBuilder stringBuilder = new StringBuilder();
        return String.valueOf(stringBuilder.append(first).append(", ").append(second));
    }

    public static void main(String[] args) {
        Integer[] numbers = new Integer[20];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 2;
        }
        Search search = new Search(numbers, 5);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.out.println(forkJoinPool.invoke(search));
    }
}

