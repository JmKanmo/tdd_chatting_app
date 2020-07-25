package application.client;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import application.server.Server;
import org.testng.annotations.Test;

public class ClientTest {
    private Client client;
    private Server server;

    @Test
    public void clientTest(){
        server = new Server();
        server.startServer(5010);
        client = new Client();
        client.connectSocket(5010);
        assertEquals(client.isConnected(),true);
        server.stopServer();
        client.closeSocket();
        assertEquals(client.isClosed(),true);
    }
}