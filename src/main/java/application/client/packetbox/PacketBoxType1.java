package application.client.packetbox;

import java.util.UUID;

public class PacketBoxType1 extends PacketBox {
    private String uuId;

    public PacketBoxType1(int destPortNumber, int localPortNumber, String hostName, String hostIP) {
        super(destPortNumber, localPortNumber, hostName, hostIP);
        this.uuId = UUID.randomUUID().toString().replaceAll("-", "");
    }

    public String toString() {
        return String.format("PacketBoxType1 [localPortNumber:%d, destPortNumber:%d, hostName:%s, hostIP:%s, uuID:%s]", super.localPortNumber, super.destPortNumber, super.hostName, super.hostIP, this.uuId);
    }
}
