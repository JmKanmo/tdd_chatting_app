package application.client.packetbox;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class PacketBox {
    private Socket socket;
    private Map<String, String> dataMap = new HashMap<>();

    public PacketBox(Socket socket) {
        this.socket = socket;
    }

    public void generateData(){

    }

    @Override
    public String toString() {
        return String.format("PacketBox [localPortNumber:%d, destPortNumber:%d, hostName:%s, hostIP:%s]", "");
    }

    protected int getRandomNumber(int min, int max) {
        return (int) (Math.random() * max) + min;
    }
}
