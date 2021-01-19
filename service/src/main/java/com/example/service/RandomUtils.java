package com.example.service;

import java.util.Random;

public class RandomUtils {

    private static Random random = new Random();

    public static int randomInt() {
        return random.nextInt();
    }

    public static int randomInt(int bound) {
        return random.nextInt(bound);
    }
}
