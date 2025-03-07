package com.zyw.blockchainverification.utils;
import java.util.Arrays;
import java.util.Random;

public class RandomNumberGenerator {

    public static int[] generateRandomNumbers(int n, int max) {
        if (n > max) {
            throw new IllegalArgumentException("n must be less than or equal to max");
        }

        int[] arr = new int[n];
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            int num;
            do {
                num = random.nextInt(max) + 1;
            } while (contains(arr, num));
            arr[i] = num;
        }

        return arr;
    }

    private static boolean contains(int[] arr, int num) {
        for (int i : arr) {
            if (i == num) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] arr = generateRandomNumbers(6, 100);
        System.out.println(Arrays.toString(arr));
    }
}


