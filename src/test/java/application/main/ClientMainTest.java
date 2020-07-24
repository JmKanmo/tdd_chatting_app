package application.main;

import application.client.Client;
import application.server.Server;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ClientMainTest {
    @Test
    public void clientMainTest() {
        Server server = new Server();
        server.startServer(5001);
        Client client = new Client();
        try{
            client.connectSocket(-32);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        assertEquals(client.getSocket(),null);
        client.closeSocket();
        server.stopServer();
        client.connectSocket(5001);
        assertEquals(client.isClosed(), false);
    }
}