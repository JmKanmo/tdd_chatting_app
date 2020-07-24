package application.client.packetbox;

import application.client.Client;
import application.server.Server;
import org.testng.annotations.Test;

import java.net.Socket;
import java.util.Map;
import java.util.Objects;

import static org.testng.Assert.*;

public class PacketBoxType2Test {
    private Client client;
    private Server server;

    public void connectClient() {
        client = new Client();
        client.connectSocket(5001);
    }

    public void startServer() {
        server = new Server();
        server.startServer(5001);
    }

    public void closeClient() {
        client.closeSocket();
    }

    public void stopServer() {
        server.stopServer();
    }

    public void packetBoxType2Test() {
        int min = 5;
        int max = 10;
        PacketBoxType2 packetBox = new PacketBoxType2(client.getSocket());
        Map<String, Object> dataMap = packetBox.getDataMap();
        Socket socket = client.getSocket();

        assertEquals(Objects.equals(socket.getLocalPort(), dataMap.get("localPortNumber")), true);
        assertEquals(Objects.equals(socket.getPort(), dataMap.get("destPortNumber")), true);
        assertEquals(Objects.equals(socket.getLocalAddress().getHostName(), dataMap.get("hostName")), true);
        assertEquals(Objects.equals(socket.getLocalAddress().getHostAddress(), dataMap.get("hostIP")), true);
        assertNotEquals(packetBox.getRandomNumber(), 0);
        assertNotNull(packetBox.getBinaryString());
        int randomNumber = packetBox.getRandomNumber();
        assertEquals(randomNumber >= 0 && randomNumber <= 999, true);
        System.out.println(packetBox.toString());
    }

    @Test
    public void totalTest() {
        startServer();
        connectClient();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        assertEquals(server.isBound(), true);
        assertEquals(client.isConnected(), true);


        packetBoxType2Test();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        stopServer();
        closeClient();

        assertEquals(server.isClosed(), true);
        assertEquals(server.isShutDown(), true);
        assertEquals(client.isClosed(), true);
    }
}