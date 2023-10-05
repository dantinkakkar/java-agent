package com.sahaj;

import java.util.Random;
import java.util.function.Function;

public class Runner {
    public void run(Function2<Float, Float, Float> operation) {
        final Random random = new Random();
        final int iterations = random.nextInt(10000,50000);
        for (int i=1; i<=iterations; i++) {
            float x = random.nextFloat();
            float y = random.nextFloat();
            operation.apply(x,y);
        }
    }
}
