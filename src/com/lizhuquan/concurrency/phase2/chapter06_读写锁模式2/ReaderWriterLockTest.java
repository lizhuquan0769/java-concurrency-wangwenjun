package com.lizhuquan.concurrency.phase2.chapter06_读写锁模式2;

import java.util.Random;

public class ReaderWriterLockTest {

    public static void main(String[] args) {
        final ShareData data = new ShareData(10);

        new ReaderWorker(data).start();
        new ReaderWorker(data).start();

        new WriterWorker(data, "qwertyuiop").start();
        new WriterWorker(data, "QWERTYUIOP").start();
    }

    public static class WriterWorker extends Thread {

        private static final Random random = new Random();

        private final ShareData data;

        private final String filler;

        private int index;

        public WriterWorker(ShareData data, String filler) {
            this.data = data;
            this.filler = filler;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    char c = nextChar();
                    data.write(c);
                    Thread.sleep(random.nextInt(1000));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private char nextChar() {
            char c = filler.charAt(index);
            index++;
            if (index >= filler.length()) {
                index = 0;
            }
            return c;
        }
    }

    public static class ReaderWorker extends Thread {

        private static final Random random = new Random();
        private final ShareData data;

        public ReaderWorker(ShareData data) {
            this.data = data;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    data.read();
                    Thread.sleep(random.nextInt(100));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
