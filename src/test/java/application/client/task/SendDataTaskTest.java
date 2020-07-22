package application.client.task;

import application.client.Client;
import application.server.Server;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SendDataTaskTest {
    private Client client;
    private Server server;

    public void connectClient() {
        client = new Client();
        client.connectSocket();
    }

    public void startServer() {

    }


    @Test
    public void sendDataTest() {

    }
}