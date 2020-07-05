package com.bortni.service.util;

import java.util.Random;

public class GameStringGenerator {

    private final static int CHARS_NUMBER = 10;

    public String generate() {
        int leftLimit = 48;
        int rightLimit = 122;
        Random random = new Random();

        return random.ints(CHARS_NUMBER, leftLimit, rightLimit+1)
                .filter(i -> (i <= 57 || i>=65) && (i<=90 || i >=97))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
