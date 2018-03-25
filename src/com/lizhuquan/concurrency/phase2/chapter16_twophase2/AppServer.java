package com.lizhuquan.concurrency.phase2.chapter16_twophase2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppServer extends Thread {

    private final static int DEFAULT_SERVER_PORT = 8899;

    private int port = DEFAULT_SERVER_PORT;

    private volatile boolean start = true;

    private List<ClientHandler> clientHandlers = new ArrayList<>();

    private final ExecutorService executors = Executors.newFixedThreadPool(10);

    private ServerSocket serverSocket;

    public AppServer() {
        this(DEFAULT_SERVER_PORT);
    }

    public AppServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while (start) {
                Socket accept = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(accept);
                executors.submit(clientHandler);
                clientHandlers.add(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.dispose();
        }

    }

    public void shutdown() throws IOException {
        this.start = false;
        this.interrupt();
        this.serverSocket.close();
    }

    private void dispose() {
        clientHandlers.stream().forEach(ClientHandler::stop);
        executors.shutdown();
    }

}
