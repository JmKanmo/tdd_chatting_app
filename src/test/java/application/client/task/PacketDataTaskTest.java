package application.client.task;

import application.client.Client;
import application.client.packetbox.PacketBox;
import application.client.packetbox.PacketBoxType1;
import application.server.Server;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class PacketDataTaskTest {
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

    public void packetDataTaskTest() {
        PacketDataTask packetDataTask = new PacketDataTask(client.getSocket());
        String data = packetDataTask.getDummyData();
        System.out.println(data + "," + client.getSocket().toString());
        assertNotNull(data);
    }

    public void simulationTest() {
        server.startAcceptTask();
        client.startSendDataTask();
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

        packetDataTaskTest();
//        simulationTest();

        try {
            Thread.sleep(6000);
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
