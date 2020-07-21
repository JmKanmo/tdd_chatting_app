package application.server.task;

import application.client.Client;
import application.server.Server;
import org.testng.annotations.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.testng.Assert.*;

public class ReceiveDataTaskTest {
    private Client client;
    private Server server;

    void clientConnect() {
        client = new Client();
        client.connectSocket();
    }

    void serverStart() {
        server = new Server();
        server.startServer();
    }

    void clientClose() {
        client.closeSocket();
    }

    void sererStop() {
        server.stopServer();
    }

    void sendDataClientToServer(String msg) {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(client.getSocket().getOutputStream());) {
            bufferedOutputStream.write(msg.getBytes("UTF-8"));
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void receiveDataTaskTest() {
        serverStart();
        clientConnect();

        assertEquals(server.getServerSocket().isBound(), true);
        assertEquals(client.getSocket().isBound(), true);

        String msg = "안녕하세요. 반갑습니다. 테스트코드입니다";
        sendDataClientToServer(msg);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        clientClose();
        sererStop();

        assertEquals(server.getServerSocket().isClosed(), true);
        assertEquals(client.getSocket().isClosed(), true);

        try {
            List<String> lines = Files.readAllLines(Paths.get("D:\\tdd_chatting_app\\src\\main\\java\\application\\server\\log\\server.log"));
            String[] splited = lines.get(lines.size() - 1).split(":");
            String checked = splited[splited.length - 1].trim();
            assertEquals(checked.equals(msg), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}