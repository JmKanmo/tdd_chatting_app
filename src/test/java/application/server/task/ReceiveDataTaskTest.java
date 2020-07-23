package application.server.task;

import application.client.Client;
import application.server.Server;
import application.statechecker.AcceptSocketTaskStateCheck;
import org.testng.annotations.Test;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.testng.Assert.*;

public class ReceiveDataTaskTest {
    private List<Client> clientList = new ArrayList<>();
    private String[] msgList = {"엑셈입니다", "MFJ데몬팀", "코딩이 재밌습니다.", "여기는어디? 나는 누구?", "치킨이 먹고싶어요"};
    private Server server;
    private Thread stateCheckThread;

    void connectClient() {
        Client client = new Client();
        clientList.add(client);
        client.connectSocket(5001);
    }

    void connectClient(String msg) {
        Client client = new Client();
        clientList.add(client);
        client.connectSocket(5001);
        sendDataClientToServer(clientList.size() - 1, msg);
    }

    void startServer() {
        server = new Server();
        server.startServer(5001);
        server.startAcceptTask();
        stateCheckThread = new AcceptSocketTaskStateCheck(server.getAcceptSocketTask());
        stateCheckThread.start();
    }

    @Test
    void closeClient() {
        for (Client client : clientList) {
            client.closeSocket();
            assertEquals(client.isClosed(), true);
        }
        clientList.clear();
    }

    void stopServer() {
        server.stopServer();
    }

    void sendDataClientToServer(int idx, String msg) {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(clientList.get(idx).getSocket().getOutputStream());) {
            bufferedOutputStream.write(msg.getBytes("UTF-8"));
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkLogMessageLines() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("D:\\tdd_chatting_app\\src\\main\\java\\application\\server\\log\\server.log"));
            lines = lines.subList(lines.size() - msgList.length * 2, lines.size());
            List<String> infos = new ArrayList<>();

            for (int i = 0; i < lines.size(); i++) {
                if (i % 2 != 0) {
                    String info = lines.get(i).split(":")[1].trim();
                    infos.add(info);
                }
            }

            Arrays.sort(msgList);

            infos.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });

            for (int i = 0; i < msgList.length; i++) {
                assertEquals(msgList[i].equals(infos.get(i)), true);
            }

            infos.clear();
            lines.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = false)
    public void connectAndStopTest() throws InterruptedException {
        startServer();

        for (String msg : msgList) {
            connectClient();
        }

        assertEquals(server.isBound(), true);

        for (Client client : clientList) {
            assertEquals(client.isConnected(), true);
        }

        Thread.sleep(5000);
        closeClient();
        stopServer();

        assertEquals(server.isClosed(), true);
        assertEquals(clientList.isEmpty(), true);
    }


    @Test(enabled = true)
    public void receiveDataTaskTest() {
        startServer();

        for (String msg : msgList) {
            connectClient(msg);
        }

        assertEquals(server.isBound(), true);

        for (Client client : clientList) {
            assertEquals(client.isConnected(), true);
        }

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        checkLogMessageLines();
        closeClient();
        stopServer();

        assertEquals(server.isClosed(), true);
        assertEquals(clientList.isEmpty(), true);
    }
}