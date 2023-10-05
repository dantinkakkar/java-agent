package com.sahajagent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

import static com.sahajagent.PerfMethodTransformer.*;

public class PerfAgent {
    public static void premain(String args, Instrumentation instrumentation) {
        final String className = args.split("::")[0];
        final String methodName = args.split("::")[1];
        addTransformerToMethod(className, methodName, instrumentation);
        Runnable shutDownHook = () -> {
            System.out.println("Total invocations: " + totalInvocations);
            System.out.println("Total time taken: " + (double) (totalTimeTaken/1000000) + " milliseconds");
            System.out.println("Minimum operation time: " + minTimeForOp + " nanoseconds");
            System.out.println("Maximum operation time: " + maxTimeForOp + " nanoseconds");
            System.out.println("Mean operation time: " + ((double) totalTimeTaken)/((double) (totalInvocations*1000)) + " nanoseconds");
        };
        Runtime.getRuntime().addShutdownHook(new Thread(shutDownHook));
    }

    private static void addTransformerToMethod(String className, String methodName, Instrumentation instrumentation) {
        try {
            Class<?> clazz = Class.forName(className);
            PerfMethodTransformer perfMethodTransformer = new PerfMethodTransformer(className, methodName);
            instrumentation.addTransformer(perfMethodTransformer, true);
            instrumentation.retransformClasses(clazz);
        } catch (ClassNotFoundException | UnmodifiableClassException e) {
            throw new RuntimeException(e);
        }
    }
}