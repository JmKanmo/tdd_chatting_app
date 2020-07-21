package application.server;

import application.server.task.AcceptSocketTask;
import application.server.task.ReceiveDataTask;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ExecutorService executorService;
    private ServerSocket serverSocket;
    private AcceptSocketTask acceptSocketTask;

    public ServerSocket geServertSocket() {
        return this.serverSocket;
    }

    public AcceptSocketTask getAcceptSocketTask() {
        return this.acceptSocketTask;
    }

    public void startServer() {
        try {
            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            serverSocket = new ServerSocket(5001);
        } catch (IOException e1) {
            e1.printStackTrace();
            stopServer();
            return;
        }
        this.acceptSocketTask = new AcceptSocketTask(this);
        executorService.submit(this.acceptSocketTask);
    }

    public void stopServer() {
        if (serverSocket != null && serverSocket.isClosed() != true) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (executorService != null && executorService.isShutdown() != true) {
            executorService.shutdown();
        }
        this.acceptSocketTask = null;
    }

    public Socket acceptClientSocket() throws IOException {
        return serverSocket.accept();
    }

    public void submitTask(ReceiveDataTask receiveTask) {
        this.executorService.submit(receiveTask);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
