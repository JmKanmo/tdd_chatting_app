package application.client.packetbox;

import java.net.Socket;
import java.util.UUID;

public class PacketBoxType1 extends PacketBox {
    private String uuid;

    public PacketBoxType1(Socket socket) {
        super(socket);
        uuid = UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    public String toString() {
        return String.format("PacketBoxType1:[%s,uuid:%s]", super.getPublicDataString(), uuid);
    }

    public String getUuid() {
        return uuid;
    }
}
