package application.server.task;

import application.client.Client;
import application.server.Server;
import application.statechecker.AcceptSocketTaskStateCheck;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AcceptSocketTaskTest {
    private Server server;
    private List<Client> clientList = new LinkedList<>();
    private Thread thread;

    void connectServer() throws IOException {
        server = new Server();
        server.startServer();
        thread = new AcceptSocketTaskStateCheck(server.getAcceptSocketTask());
        thread.start();
    }

    void connectClient() {
        Client client = new Client();
        clientList.add(client);
        client.connectSocket();
    }

    void closeClient() {
        for (Client client : clientList) {
            client.closeSocket();
        }
        clientList.clear();
    }

    void closeServer() {
        server.stopServer();
    }

    @Test
    public void runTest() throws IOException, InterruptedException {
        connectServer();
        connectClient();
        connectClient();
        Thread.sleep(6000);
        closeClient();
        closeServer();
        Thread.sleep(6000);

        // closeServer -> run method throw new IOException 발생 -> stopServer 호출여부 확인
        assertEquals(server.getServerSocket().isClosed(), true);
        assertEquals(server.getExecutorService().isShutdown(), true);
    }
}