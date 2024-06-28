package org.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        String[] texts = new String[100];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateRoute("RLRFR", 100);
        }

        for (String text : texts) {
            threadPool.submit(() -> {
                int count = 0;
                for (int k = 0; k < text.length(); k++) {
                    if (text.charAt(k) == 'R') {
                        count++;
                    }
                }
                System.out.println(text.substring(0, 100) + "   " + count);

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(count)) {
                        sizeToFreq.put(count, sizeToFreq.get(count) + 1);
                    } else {
                        sizeToFreq.put(count, 1);
                    }
                }
            });
        }

        threadPool.shutdown();

        int max = (Collections.max(sizeToFreq.values()));

        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (max == entry.getValue()) {
                System.out.println(entry.getKey() + " " + max);
            }
        }
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getValue() != max) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
