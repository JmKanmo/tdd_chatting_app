package application.client.packetbox;

import application.client.Client;
import application.server.Server;
import org.testng.annotations.Test;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Objects;

import static org.testng.Assert.*;

public class PacketBoxType1Test {
    private Client client;
    private Server server;

    public void connectClient() {
        client = new Client();
        client.connectSocket(5002);
    }

    public void startServer() {
        server = new Server();
        server.startServer(5002);
    }

    public void closeClient() {
        client.closeSocket();
    }

    public void stopServer() {
        server.stopServer();
    }

    public void packetBoxType1Test() throws UnknownHostException {
        PacketBoxType1 packetBox = new PacketBoxType1(client.getSocket());
        Map<String, Object> dataMap = packetBox.getDataMap();
        Socket socket = client.getSocket();
        assertEquals(Objects.equals(socket.getLocalPort(), dataMap.get("localPortNumber")), true);
        assertEquals(Objects.equals(socket.getPort(), dataMap.get("destPortNumber")), true);
        assertEquals(Objects.equals(socket.getLocalAddress().getLocalHost().getHostName(), dataMap.get("hostName")), true);
        assertEquals(Objects.equals(socket.getLocalAddress().getHostAddress(), dataMap.get("hostIP")), true);
        assertNotNull(packetBox.getUuid());
    }

    @Test
    public void testPacketBoxType1Test() {
        startServer();
        connectClient();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        assertEquals(server.isBound(), true);
        assertEquals(client.isConnected(), true);

        try {
            packetBoxType1Test();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

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