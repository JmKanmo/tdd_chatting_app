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
    private BufferedOutputStream bufferedOutputStream;

    public SendDataTask(Socket socket, int number) {
        this.socket = socket;
        this.dummyDataTask = new DummyDataTask(number);
    }

    private void sendData() throws Exception {
        try {
            bufferedOutputStream.write("임시데이터 입니다.".getBytes("UTF-8"));
            bufferedOutputStream.flush();
        } catch (Exception exception) {
            throw exception;
        }
    }

    @Override
    public void run() {
        try {
            bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());

            while (true) {
                try {
                    sendData();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
