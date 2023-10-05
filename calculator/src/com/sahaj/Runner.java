package com.sahaj;

import java.util.Random;

public class Runner {
    private final int arity;
    public Runner(int arity) {
        this.arity = arity;
    }
    public void run(OperationVarArgs lambda) {
        final Random random = new Random();
        final int iterations = random.nextInt(10000,50000);
        for (int i=1; i<=iterations; i++) {
            float[] values = new float[arity];
            for(int j=0;j<arity;j++){values[j] = random.nextFloat();}
            lambda.apply(values);
        }
    }
}
