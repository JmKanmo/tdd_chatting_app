package application.client.task;

import application.client.Client;
import application.server.Server;
import application.statechecker.AcceptSocketTaskStateCheck;
import org.testng.annotations.Test;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.testng.Assert.*;

public class SendDataTaskTest {
    private Client client;
    private Server server;
    private Thread thread;

    public void connectClient() {
        client = new Client();
        client.connectSocket();
    }

    public void startServer() {
        server = new Server();
        server.startServer();
        thread = new AcceptSocketTaskStateCheck(server.getAcceptSocketTask());
        thread.start();
    }

    @Test
    public void sendDataTaskTest() throws InterruptedException, IOException {
        client.sendData(3);
        List<String> lines = Files.readAllLines(Paths.get("D:\\tdd_chatting_app\\src\\main\\java\\application\\server\\log\\server.log"));
        String lastLine = lines.get(lines.size() - 1).split(":")[1].trim();
        assertEquals(lastLine.equals("임시데이터 입니다."), true);
        lines.clear();
    }

    public void closeClientSocket() {
        client.closeSocket();
    }

    public void stopServer() {
        server.stopServer();
    }

    @Test
    public void sendDataTest() throws IOException, InterruptedException {
        startServer();
        connectClient();

        try {
            Thread.sleep(500);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        assertEquals(server.getServerSocket().isBound(), true);
        assertEquals(client.getSocket().isBound(), true);

        sendDataTaskTest();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        closeClientSocket();
        stopServer();
        Thread.sleep(2000);
        assertEquals(server.getServerSocket().isClosed(), true);
        assertEquals(client.getSocket().isClosed(), true);
    }
}