package com.sahaj;

import java.util.function.BiFunction;

public class Calculator {
    public float add(float... values) {
        return op(0, Float::sum, values);
    }

    public float subtract(float... values) {
        return op(values[0], (a, b) -> a - b, values);
    }

    public float multiply(float... values) {
        return op(1f, (a, b) -> a * b, values);
    }

    public float divide(float... values) {
        return op(values[0], (a, b) -> a / b, values);
    }

    private static float op(float initial, BiFunction<Float,Float,Float> operandLambda, float... values) {
        float res = initial;
        for (float value : values) {
            res = operandLambda.apply(res, value);
        }
        return res;
    }
}