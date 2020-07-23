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
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private ServerSocket serverSocket;
    private AcceptSocketTask acceptSocketTask;

    public void startServer(int portNumber) {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }
        acceptSocketTask = new AcceptSocketTask(this);
        executorService.submit(acceptSocketTask);
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
        acceptSocketTask = null;
    }

    public Socket acceptClientSocket() throws IOException {
        return serverSocket.accept();
    }

    public void submitTask(ReceiveDataTask receiveTask) {
        executorService.submit(receiveTask);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public boolean isBound() {
        return serverSocket.isBound();
    }

    public boolean isClosed() {
        return serverSocket.isClosed();
    }

    public boolean isShutDown() {
        return executorService.isShutdown();
    }

    public ServerSocket geServertSocket() {
        return serverSocket;
    }

    public AcceptSocketTask getAcceptSocketTask() {
        return acceptSocketTask;
    }

}
