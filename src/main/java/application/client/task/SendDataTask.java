package application.client.task;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class SendDataTask implements Runnable {
    private Socket socket;
    private PacketDataTask packetDataTask;

    public SendDataTask(Socket socket) {
        this.socket = socket;
        this.packetDataTask = new PacketDataTask(socket);
    }

    @Override
    public void run() {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());) {
            while (true) {
                if (socket.isClosed()) {
                    throw new IOException();
                }
                bufferedOutputStream.write("임시데이터 입니다.".getBytes("UTF-8"));
                bufferedOutputStream.flush();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("소켓이 닫혔습니다.");
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public PacketDataTask getDummyDataTask() {
        return packetDataTask;
    }
}
