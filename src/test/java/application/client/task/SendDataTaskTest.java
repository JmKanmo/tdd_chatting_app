package application.client.task;

import application.client.Client;
import application.server.Server;
import application.statechecker.AcceptSocketTaskStateCheck;
import org.testng.annotations.Test;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class SendDataTaskTest {
    private List<Client> clientList = new ArrayList<>();
    private Server server;
    private Thread thread;

    public void connectClient() {
        Client client = new Client();
        clientList.add(client);
        client.connectSocket(5001);
    }

    public void startServer() {
        server = new Server();
        server.startServer(5001);
        server.startAcceptTask();
        thread = new AcceptSocketTaskStateCheck(server.getAcceptSocketTask());
        thread.start();
    }

    public void sendDataTaskTest(int idx) throws InterruptedException, IOException {
        clientList.get(idx).startSendDataTask();
    }

    public void closeClientSocket() {
        for (Client client : clientList) {
            client.closeSocket();
        }
        clientList.clear();
    }

    public void stopServer() {
        server.stopServer();
    }

    @Test
    public void sendDataTest() throws IOException, InterruptedException {
        startServer();
        connectClient();
        connectClient();
        connectClient();

        try {
            Thread.sleep(500);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        assertEquals(server.isBound(), true);

        for (Client client : clientList) {
            assertEquals(client.isConnected(), true);
        }

        for (int i = 0; i < clientList.size(); i++) {
            sendDataTaskTest(i);
        }

        Thread.sleep(2000);

        List<String> lines = Files.readAllLines(Paths.get("D:\\tdd_chatting_app\\src\\main\\java\\application\\server\\log\\server.log"));
        String lastLine = lines.get(lines.size() - 1).split(":")[1].trim();
        assertEquals(lastLine.equals("임시데이터 입니다."), true);
        lines.clear();

        closeClientSocket();
        stopServer();

        Thread.sleep(1500);

        assertEquals(server.isClosed(), true);

        for (Client client : clientList) {
            assertEquals(client.isConnected(), true);
        }
    }
}