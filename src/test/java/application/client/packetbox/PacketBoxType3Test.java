package application.client.packetbox;

import application.client.Client;
import application.server.Server;
import org.testng.annotations.Test;

import java.net.Socket;
import java.util.Map;
import java.util.Objects;

import static org.testng.Assert.*;

public class PacketBoxType3Test {
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

    @Test
    public void packetBoxType3Test() throws Exception {
        PacketBoxType3 packetBox = new PacketBoxType3(client.getSocket());
        Map<String, Object> dataMap = packetBox.getDataMap();
        Socket socket = client.getSocket();

        assertEquals(Objects.equals(socket.getLocalPort(), dataMap.get("localPortNumber")), true);
        assertEquals(Objects.equals(socket.getPort(), dataMap.get("destPortNumber")), true);
        assertEquals(Objects.equals(socket.getLocalAddress().getHostName(), dataMap.get("hostName")), true);
        assertEquals(Objects.equals(socket.getLocalAddress().getHostAddress(), dataMap.get("hostIP")), true);

        assertNotEquals(packetBox.getUrl(), null);
        assertNotEquals(packetBox.getHeaderInfo().isEmpty(), true);
        assertNotNull(packetBox.getHeaderInfo());
        assertNotNull(packetBox.toString());
        System.out.println(packetBox.toString());
    }

    @Test
    public void totalTest() throws Exception {
        startServer();
        connectClient();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        assertEquals(server.isBound(), true);
        assertEquals(client.isConnected(), true);

        packetBoxType3Test();

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