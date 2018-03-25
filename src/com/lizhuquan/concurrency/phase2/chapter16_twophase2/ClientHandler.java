package com.lizhuquan.concurrency.phase2.chapter16_twophase2;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;

    private volatile boolean running = true;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             PrintWriter pw = new PrintWriter(outputStream);)
        {
            while (running) {
                String message = br.readLine();
                if (message == null) {
                    break;
                }
                System.out.println(">>> come from client: " + message);
                pw.write("echo: " + message + "\n");
                pw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.running = false; //可能客户端自己断开链接， 而服务端依然在执行
        } finally {
            this.stop();
        }

    }

    public void stop() {
        if (!running) {
            return;
        }
        this.running = false;
        try {
            System.out.println(socket + " stoping...");
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
