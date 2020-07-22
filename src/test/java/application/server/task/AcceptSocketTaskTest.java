package application.server.task;

import application.client.Client;
import application.server.Server;
import application.statechecker.AcceptSocketTaskStateCheck;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

public class AcceptSocketTaskTest {
    private Server server;
    private Client client;
    private Thread thread;

    void connectServer() throws IOException {
        server = new Server();
        server.startServer();
        thread = new AcceptSocketTaskStateCheck(server.getAcceptSocketTask());
        thread.start();
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