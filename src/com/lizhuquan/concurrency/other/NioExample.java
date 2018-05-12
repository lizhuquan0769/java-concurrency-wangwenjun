package com.lizhuquan.concurrency.other;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by lizhuquan on 2018/5/12.
 */
public class NioExample {

    public static void main(String[] args) throws IOException {
        File file = new File("c:/spring-tool-suite-3.9.2.RELEASE-e4.7.2-win32-x86_64.zip");
        FileInputStream fin = new FileInputStream(file);
        FileChannel fcIn = fin.getChannel();
        ByteBuffer byteBuff = ByteBuffer.allocate(1024 << 8);

        FileOutputStream fout = new FileOutputStream(new File("d:/fout.out"));
        FileChannel fcOut = fout.getChannel();

        long b = System.currentTimeMillis();
        while(fcIn.read(byteBuff) > -1) {
            byteBuff.flip();
            // 通过FileOutputStream写入
            fout.write(byteBuff.array(), 0, byteBuff.limit());
            // 通过FileChannel写入
//            fcOut.write(byteBuff);
            byteBuff.clear();
        }
        long e = System.currentTimeMillis();
        System.out.println("span " + (e - b) + " ms");

        fcIn.close();
        fcOut.close();
        fin.close();
        fout.close();
    }
}
