package ru.job4j.forkjoinpool;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SearchTest {
    @Test
    public void whenArrayLengthLess10() {
        String[] array = new String[]{"pavel", "olga", "petr", "andrey", "oleg"};
        assertThat(1, is(Search.execute(array, "olga", 0, array.length - 1)));
    }

    @Test
    public void whenArrayLength1() {
        String[] array = new String[]{"pavel"};
        assertThat(0, is(Search.execute(array, "pavel", 0, 0)));
    }

    @Test
    public void whenArrayNoElementToFind() {
        String[] array = new String[]{"pavel", "olga", "petr", "andrey", "oleg"};
        assertThat(-1, is(Search.execute(array, "dima", 0, array.length - 1)));
    }

    @Test
    public void whenArrayLengthMoreThen10() {
        String[] array = new String[]{"pavel", "olga", "petr", "andrey", "oleg", "dima", "ilya", "platon", "fedor", "evgeniy", "yana"};
        assertThat(7, is(Search.execute(array, "platon", 0, array.length - 1)));
    }

    @Test
    public void whenArrayLengthMoreThen10NoElementToFind() {
        String[] array = new String[]{"pavel", "olga", "petr", "andrey", "oleg", "dima", "ilya", "platon", "fedor", "evgeniy", "yana"};
        assertThat(-1, is(Search.execute(array, "platonishe", 0, array.length - 1)));
    }

    @Test
    public void whenIntegerArrayLengthMore10AndFindElement() {
        Integer[] array = new Integer[]{2, 5, 89, 26, 12, 34, 3, 7, 58, 27, 91, 15, 73};
        assertThat(-1, is(Search.execute(array, 91, 0, array.length - 1)));
    }

    @Test
    public void whenIntegerArrayLengthMOre10AndNoElementToFind() {
        Integer[] array = new Integer[]{2, 5, 89, 26, 12, 34, 3, 7, 58, 27, 91, 15, 73};
        assertThat(-1, is(Search.execute(array, 93, 0, array.length - 1)));
    }
}