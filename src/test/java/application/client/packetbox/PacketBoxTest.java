package application.client.packetbox;

import application.client.Client;
import application.server.Server;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;

import static org.testng.Assert.*;

public class PacketBoxTest {
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

    @Test(enabled = false)
    public void packetBoxTest() {
        PacketBox packetBox = new PacketBox(client.getSocket());
        Map<String, Object> dataMap = packetBox.getDataMap();
        Socket socket = client.getSocket();

        for (int i = 1; i <= 5000; i++) {
            int min = 10, max = 55;
            int randomNumber = packetBox.createRandomNumber(min, max);
            assertEquals(randomNumber >= min && randomNumber <= max, true);
        }

        assertEquals(Objects.equals(socket.getLocalPort(), dataMap.get("localPortNumber")), true);
        assertEquals(Objects.equals(socket.getPort(), dataMap.get("destPortNumber")), true);
        assertEquals(Objects.equals(socket.getLocalAddress().getHostName(), dataMap.get("hostName")), true);
        assertEquals(Objects.equals(socket.getLocalAddress().getHostAddress(), dataMap.get("hostIP")), true);
    }

    public void packetBoxPolymorphismTest(){
        PacketBox packetBox = new PacketBoxType1(client.getSocket());
        assertNotNull(packetBox.toString());
        assertNotEquals(packetBox.toString(),new PacketBox(client.getSocket()).toString());
    }

    @Test
    public void testPacketBoxTest () {
        startServer();
        connectClient();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        assertEquals(server.isBound(), true);
        assertEquals(client.isConnected(), true);

        packetBoxPolymorphismTest();

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