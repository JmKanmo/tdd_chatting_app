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
        PacketBox packetBox = selectPacketBox();

        if (packetBox != null) {
            dummyData = packetBox.toString();
        }
    }

    public String getDummyData() {
        return dummyData;
    }

    public PacketBox selectPacketBox() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));) {
            System.out.println("전송할 패킷데이터타입 입력:");
            int number = Integer.parseInt(bufferedReader.readLine());
            PacketBox packetBox = null;

            switch (number) {
                case 1: {
                    packetBox = new PacketBoxType1(socket);
                    break;
                }
                case 2: {
                    int min = 0, max = 0;
                    System.out.println("min, max값 입력:");
                    String[] inputs = bufferedReader.readLine().split(" ");
                    min = Integer.parseInt(inputs[0]);
                    max = Integer.parseInt(inputs[1]);
                    packetBox = new PacketBoxType2(socket, min, max);
                    break;
                }
                case 3: {
                    packetBox = new PacketBoxType3(socket);
                    break;
                }
            }
            return packetBox;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
