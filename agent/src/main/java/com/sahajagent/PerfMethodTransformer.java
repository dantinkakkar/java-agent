package com.sahajagent;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.Arrays;

public class PerfMethodTransformer implements ClassFileTransformer {

    private final String className;
    private final String methodName;
    public static long totalInvocations = 0;
    public static long totalTimeTaken = 0;
    public static long minTimeForOp = Long.MAX_VALUE;
    public static long maxTimeForOp = Long.MIN_VALUE;

    public PerfMethodTransformer(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public byte[] transform(ClassLoader loader, String classNameForTransforming, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        byte[] bytecode = classfileBuffer;
        if (!classNameForTransforming.equals(this.className.replaceAll("\\.", "/"))) {
            return bytecode;
        }
        try {
            ClassPool classPool = ClassPool.getDefault();
            classPool.appendClassPath(new LoaderClassPath(loader));
            CtClass classToProfile = classPool.get(this.className);
            CtMethod methodToProfile = classToProfile.getDeclaredMethod(this.methodName);
            methodToProfile.addLocalVariable("time", CtClass.longType);
            methodToProfile.insertBefore("time=java.time.Instant.now().getNano();");
            methodToProfile.insertBefore("com.sahajagent.PerfMethodTransformer.totalInvocations++;");
            methodToProfile.addLocalVariable("timeTaken", CtClass.longType);
            methodToProfile.insertAfter("timeTaken=java.time.Instant.now().getNano()-time;");
            methodToProfile.insertAfter("com.sahajagent.PerfMethodTransformer.totalTimeTaken+=timeTaken;");
            methodToProfile.insertAfter("com.sahajagent.PerfMethodTransformer.minTimeForOp=Math.min(com.sahajagent.PerfMethodTransformer.minTimeForOp,timeTaken);");
            methodToProfile.insertAfter("com.sahajagent.PerfMethodTransformer.maxTimeForOp=Math.max(com.sahajagent.PerfMethodTransformer.maxTimeForOp,timeTaken);");
            bytecode = classToProfile.toBytecode();
            classToProfile.detach();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytecode;
    }
}
