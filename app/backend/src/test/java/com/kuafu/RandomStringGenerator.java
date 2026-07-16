package com.kuafu;

import java.security.SecureRandom;

public class RandomStringGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 32;
    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomString() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String randomString = generateRandomString();
        System.out.println("生成的32位字符串: " + randomString);
    }
}
