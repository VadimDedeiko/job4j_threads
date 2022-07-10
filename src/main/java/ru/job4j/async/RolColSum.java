package ru.job4j.async;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    private static int i;
    private static int j;

    public static void setI(int i) {
        RolColSum.i = i;
    }

    public static void setJ(int j) {
        RolColSum.j = j;
    }

    public static class Sums {
        private int rowSum;
        private int colSum;



        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    /**2. Реализовать последовательную версию программы*/
    public static Sums[] sum(int[][] matrix) {
        Sums sum = new Sums();
        Sums[] sums = new Sums[]{sum};
        int i = RolColSum.i;
        int m = RolColSum.j;
        int resColumn = sum.colSum;
        int resRow = sum.rowSum;
        for (int j = 0; j < matrix[i].length; j++) {
            resColumn += matrix[i][j];
        }

        for (int k = 0; k < matrix.length; k++) {
            resRow += matrix[k][m];
        }
        sum.setColSum(resColumn);
        sum.setRowSum(resRow);
        return sums;
    }

    /**3. Реализовать асинхронную версию программы. i - я задача считает сумму по i столбцу и i строке*/
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums sum = new Sums();
        Sums[] sums = new Sums[]{sum};
        int i = RolColSum.i;
        int m = RolColSum.j;
        int resRow = sum.rowSum;
        CompletableFuture<Integer> resCol = CompletableFuture.supplyAsync(() -> {
            int resColumn = sum.colSum;
            for (int j = 0; j < matrix[i].length; j++) {
                resColumn += matrix[i][j];
            }
            return resColumn;
        });

        for (int k = 0; k < matrix.length; k++) {
            resRow += matrix[k][m];
        }
        sum.setColSum(resCol.get());
        sum.setRowSum(resRow);
        return sums;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RolColSum.setI(2);
        RolColSum.setJ(2);
        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Sums[] sum = RolColSum.sum(array);
        System.out.println(sum[0].getColSum());
        System.out.println(sum[0].getRowSum());
        Sums[] asyncSum = RolColSum.asyncSum(array);
        System.out.println(asyncSum[0].getColSum());
        System.out.println(asyncSum[0].getRowSum());
    }
}