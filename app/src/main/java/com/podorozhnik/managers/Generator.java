package com.podorozhnik.managers;

import java.util.Random;

public class Generator {
    private static Random random = new Random();

    public static int generateId(int length){
        return random.nextInt(Integer.MAX_VALUE);
    }
}
