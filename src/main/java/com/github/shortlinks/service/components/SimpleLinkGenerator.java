package com.github.shortlinks.service.components;

import com.github.shortlinks.service.abstracts.LinkGenerator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SimpleLinkGenerator implements LinkGenerator {

    @Override
    public String generate(int linkLength) {
        String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int n = alphabet.length();

        StringBuilder result = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < linkLength; i++) {
            result.append(alphabet.charAt(r.nextInt(n)));
        }
        return result.toString();
    }
}
