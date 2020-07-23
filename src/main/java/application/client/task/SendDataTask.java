package application.client.task;

import application.client.Client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SendDataTask implements Runnable {
    private Socket socket;
    private DummyDataTask dummyDataTask;

    public SendDataTask(Socket socket, int number) {
        this.socket = socket;
        this.dummyDataTask = new DummyDataTask(number);
    }

    @Override
    public void run() {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());) {
            while (true) {
                bufferedOutputStream.write("임시데이터 입니다.".getBytes("UTF-8"));
                bufferedOutputStream.flush();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public DummyDataTask getDummyDataTask() {
        return dummyDataTask;
    }
}
