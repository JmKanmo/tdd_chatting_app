package application.client.packetbox;

import java.net.Socket;
import java.net.UnknownHostException;
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
        try {
            dataMap.put("hostName", socket.getLocalAddress().getLocalHost().getHostName());
            dataMap.put("hostIP", socket.getLocalAddress().getHostAddress());
        } catch (UnknownHostException e) {
            dataMap.put("hostName", "unKnown");
            dataMap.put("hostIP", "unKnown");
        }
    }

    protected String getPublicDataString() {
        return String.format("localPortNumber:%d, destPortNumber:%d, hostName:%s, hostIP:%s", dataMap.get("localPortNumber"), dataMap.get("destPortNumber"),
                dataMap.get("hostName"), dataMap.get("hostIP"));
    }

    public static int createRandomNumber(int min, int max) {
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
