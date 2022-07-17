package ru.job4j.async;

import net.sf.saxon.functions.Sum;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

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

    /**
     * 2. Реализовать последовательную версию программы
     */
    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        int resColumn = 0;
        int resRow = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                resRow += matrix[i][j];
                resColumn += matrix[j][i];
            }
            Sums sum = new Sums();
            sum.setRowSum(resRow);
            sum.setColSum(resColumn);
            sums[i] = sum;
            resColumn = 0;
            resRow = 0;
        }
        return sums;
    }

    /**
     * 3. Реализовать асинхронную версию программы. i - я задача считает сумму по i столбцу и i строке
     */
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
       /* int n = matrix.length;
        int[] sums = new int[2 * n];
        Map<Integer, CompletableFuture<Integer>> futures = new HashMap<>();
        // считаем сумму по главной диагонали
        futures.put(0, getTask(matrix, 0, n - 1, n - 1));
        // считаем суммы по побочным диагоналям
        for (int k = 1; k <= n; k++) {
            futures.put(k, getTask(matrix, 0, k - 1,  k - 1));
            if (k < n) {
                futures.put(2 * n - k, getTask(matrix, n - k, n - 1, n - 1));
            }
        }
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    public static CompletableFuture<Integer> getTask(int[][] data, int startRow, int endRow, int startCol) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            int col = startCol;
            for (int i = startRow; i <= endRow; i++) {
                sum += data[i][col];
                col--;
            }
            return sum;
        });*/
        Sums[] sums = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Integer[]>> futures = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            futures.put(i, getTask(matrix, i));
        }
        for (Integer key : futures.keySet()) {
            Sums sum = new Sums();
            sum.setRowSum(futures.get(key).get()[0]);
            sum.setColSum(futures.get(key).get()[1]);
            sums[key] = sum;
        }
        return sums;
    }

    public static CompletableFuture<Integer[]> getTask(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
            Integer[] res = new Integer[2];
            int resColumn = 0;
            int resRow = 0;
            for (int i = index; i <= index; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    resRow += matrix[index][j];
                    resColumn += matrix[j][index];
                }
                res[0] = resRow;
                res[1] = resColumn;
            }
            return res;
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
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