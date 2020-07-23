package application.client.task;

import application.client.packetbox.PacketBox;
import application.client.packetbox.PacketBoxType1;
import application.client.packetbox.PacketBoxType2;
import application.client.packetbox.PacketBoxType3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class PacketDataTask {
    private Socket socket;
    private String dummyData;

    public PacketDataTask(Socket socket) {
        this.socket = socket;
        initializePacketData();
    }

    public void initializePacketData() {
        PacketBox packetBox = createPacketBox();
        if (packetBox != null)
            dummyData = packetBox.toString();
    }

    public String getDummyData() {
        return dummyData;
    }

    public PacketBox createPacketBox() {
        int packetNumber = PacketBox.createRandomNumber(1, 3);
        PacketBox packetBox = null;

        switch (packetNumber) {
            case 1:
                packetBox = new PacketBoxType1(socket);
                break;

            case 2:
                packetBox = new PacketBoxType2(socket);
                break;
            case 3:
                packetBox = new PacketBoxType3(socket);
                break;
        }
        return packetBox;
    }
}
