package application.client.packetbox;

import java.net.Socket;
import java.util.Stack;

public class PacketBoxType2 extends PacketBox {
    private int randomNumber;
    private String binaryString;
    private static final int RANDOM_MIN = 0;
    private static final int RANDOM_MAX = 999;

    public PacketBoxType2(Socket socket) {
        super(socket);
        randomNumber = super.createRandomNumber(RANDOM_MIN, RANDOM_MAX);
        binaryString = getBinaryNumber(randomNumber);
    }

    public String getBinaryNumber(int number) {
        Stack<Integer> stack = new Stack<>();
        StringBuilder stringBuilder = new StringBuilder();

        do {
            stack.add(number % 2);
            number /= 2;
        } while (number != 1);

        stack.add(number);

        while (stack.isEmpty() != true) {
            stringBuilder.append(String.valueOf(stack.pop()));
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return String.format("PacketBoxType2:[%s, randomNumber:%d, binaryNumber:%s]", super.getPublicDataString(), randomNumber, binaryString);
    }

    public int getRandomNumber() {
        return randomNumber;
    }

    public String getBinaryString() {
        return binaryString;
    }
}
