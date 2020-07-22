package application.client;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.Test;

public class ClientTest {
    private Client client;

    @Test(priority = 2)
    public void connectSocketTest() throws IOException {
        client = new Client();
        client.connectSocket();
        assertEquals(client.isConnected(), true);
    }

    @Test(priority = 3)
    public void closeSocket() throws IOException {
        client.closeSocket();
        assertEquals(client.isClosed(), true);
    }
}