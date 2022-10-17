package com.github.shortlinks.service.components;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SimpleLinkGenerator implements LinkGenerator {

    private static final Random RAND = new Random();

    @Override
    public String generate(int linkLength) {
        String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int n = alphabet.length();

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < linkLength; i++) {
            result.append(alphabet.charAt(RAND.nextInt(n)));
        }
        return result.toString();
    }
}
