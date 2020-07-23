package application.client.task;

import org.testng.annotations.Test;

import java.net.Socket;

public class PacketDataTaskTest {
    @Test
    public void packetDataTaskTest(){
        PacketDataTask packetDataTask = new PacketDataTask(new Socket());
    }
}
