package application.server.task;

import application.client.Client;
import application.server.Server;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.ref.Cleaner;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AcceptSocketTaskTest {
    private ExecutorService executorService;
    private ServerSocket serverSocket;
    private Socket connectServerSocket;
    private Socket connectClientSocket;

    @BeforeMethod
    public void init(){
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    void connectServer() throws IOException {
        try {
            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            serverSocket = new ServerSocket(5001);
        } catch (IOException e1) {
            e1.printStackTrace();
            closeServer();
            return;
        }
    }

    void connectClient() {
        client = new Client();
        client.connectSocket();
    }

    void closeClient() {
        client.closeSocket();
    }

    void closeServer() {
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
    }

    @Test
    public void runTest() {
        connectServer();
        connectClient();
        closeClient();
    }
}