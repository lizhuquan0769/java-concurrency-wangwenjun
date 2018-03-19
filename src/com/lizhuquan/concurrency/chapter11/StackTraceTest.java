package com.lizhuquan.concurrency.chapter11;

public class StackTraceTest {

    private static class A {
        public void testA() {
            new B().testB();
        }
    }

    private static class B {
        public void testB() {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for (int i = 0; i < stackTrace.length; i++) {
                StackTraceElement e = stackTrace[i];
                System.out.println(e.getClassName() + ":" + e.getMethodName() + ":" + e.getLineNumber());
            }
        }
    }

    public static void main(String[] args) {
        new A().testA();
    }
}
