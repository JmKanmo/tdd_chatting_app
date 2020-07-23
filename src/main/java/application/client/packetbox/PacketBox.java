package application.client.packetbox;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class PacketBox {
    private Socket socket;
    private Map<String, Object> dataMap = new HashMap<>();

    public PacketBox(Socket socket) {
        this.socket = socket;
        generateData();
    }

    public void generateData() {
        dataMap.put("localPortNumber", socket.getLocalPort());
        dataMap.put("destPortNumber", socket.getPort());
        dataMap.put("hostName", socket.getLocalAddress().getHostName());
        dataMap.put("hostIP", socket.getLocalAddress().getHostAddress());
    }

    protected String getPublicDataString() {
        return String.format("localPortNumber:%d, destPortNumber:%d, hostName:%s, hostIP:%s", dataMap.get("localPortNumber"), dataMap.get("destPortNumber"),
                dataMap.get("hostName"), dataMap.get("hostIP"));
    }

    protected int createRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    @Override
    public String toString() {
        return String.format("PacketBox:[%s]", getPublicDataString());
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }
}
