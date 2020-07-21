package application.server.task;

import application.client.Client;
import application.server.Server;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.ref.Cleaner;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class StateCheckThread extends Thread {
    AcceptSocketTask acceptSocketTask;

    public StateCheckThread(AcceptSocketTask acceptSocketTask) {
        this.acceptSocketTask = acceptSocketTask;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);
                System.out.println(this.acceptSocketTask.getClients());
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }
}

public class AcceptSocketTaskTest {
    private Server server;
    private Client client;
    private Thread thread;

    void connectServer() throws IOException {
        server = new Server();
        server.startServer();
        new StateCheckThread(server.getAcceptSocketTask()).start();
    }

    void connectClient() {
        client = new Client();
        client.connectSocket();
    }

    void closeClient() {
        client.closeSocket();
    }

    void closeServer() {
        server.stopServer();
    }

    @Test
    public void runTest() throws IOException, InterruptedException {
        connectServer();
        connectClient();
        Thread.sleep(10000);
        closeClient();
        closeServer();
    }
}