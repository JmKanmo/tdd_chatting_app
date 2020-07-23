package application.client.packetbox;

public class PacketBox {
    protected int destPortNumber;
    protected int localPortNumber;
    protected String hostName;
    protected String hostIP;

    public PacketBox(int destPortNumber, int localPortNumber, String hostName, String hostIP) {
        this.destPortNumber = destPortNumber;
        this.localPortNumber = localPortNumber;
        this.hostName = hostName;
        this.hostIP = hostIP;
    }

    @Override
    public String toString() {
        return String.format("PacketBox [localPortNumber:%d, destPortNumber:%d, hostName:%s, hostIP:%s]", localPortNumber, destPortNumber, hostName, hostIP);
    }

    protected int getRandomNumber(int min, int max) {
        return (int) (Math.random() * max) + min;
    }
}
