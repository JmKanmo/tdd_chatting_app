package application.server.task;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReceiveDataTask implements Runnable {
    private Socket socket;

    public ReceiveDataTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            while (true) {
                throw new IOException();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
