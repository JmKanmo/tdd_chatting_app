package application.server;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import org.mockito.Mockito;

public class ServerTest extends Mockito {
    private Server server;

    @Test(priority = 4)
    public void stopServerTest() throws IOException {
        server.stopServer();
        assertEquals(server.getExecutorService().isShutdown(), true);
        assertEquals(server.getServerSocket().isClosed(), true);
    }

    @Test(priority = 1)
    public void startServerTest() throws Exception {
        server = new Server();
        server.startServer();
        assertEquals(server.getServerSocket().isBound(), true);
    }
}
