package com.lizhuquan.concurrency.chapter06;

/**
 * 通过执行线程开启守护线程的方式，强制关闭长时间未工作完毕的守护线程
 */
public class _02ThreadCloseForce {

    private static class TaskWrapper {
        private Thread executeThread;
        private boolean finish;

        public void execute(Runnable task) {
            executeThread = new Thread(){
                @Override
                public void run() {
                    Thread workThread = new Thread(task);
                    workThread.setDaemon(true);
                    workThread.start();

                    try {
                        workThread.join();
                        finish = true;
                    } catch (InterruptedException e) {
                        System.out.println(">> execute thread 被打断");
                    }
                }
            };
            executeThread.start();
        }

        public void shutdown(long mills) {
            long currMills = System.currentTimeMillis();
            while (!finish) {
                if (System.currentTimeMillis() - currMills >= mills) {
                    System.out.println(">> 任务超时，打断execute thread");
                    finish = true;
                    executeThread.interrupt();
                    break;
                }

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public static void main(String[] args) {
            TaskWrapper taskWrapper = new TaskWrapper();
            taskWrapper.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(">> 开始读取大文件");
                    try {
                        Thread.sleep(100_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(">> 读取结束，完成任务");
                }
            });

            long start = System.currentTimeMillis();
            taskWrapper.shutdown(10_000);
            System.out.println("总耗时：" + (System.currentTimeMillis() - start));
        }
    }
}
