package application.server.task;

import application.server.log.LogController;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class ReceiveDataTask implements Runnable {
    private Socket socket;

    public ReceiveDataTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());) {

            while (true) {
                byte[] bArr = new byte[1024];
                int readByCnt = bufferedInputStream.read(bArr);

                if (readByCnt == -1) {
                    throw new IOException();
                }

                String data = new String(bArr, 0, readByCnt, "UTF-8");
                LogController.getInstance().logging(data);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("클라이언트와의 데이터교환 없음");
        }
    }
}
