package application.server.task;

import application.server.log.LogController;

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
                byte[] bArr = new byte[1024];
                int readByCnt = bufferedInputStream.read(bArr);

                if (readByCnt == -1) {
                    throw new IOException();
                }
                // Client로부터 받아온 데이터 -> 서버의 로그파일에 기록하도록 한다.
                String data = new String(bArr, 0, readByCnt, "UTF-8");
                LogController.logging(data);
            }
        } catch (IOException e) {
            if (socket.isClosed() != true) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }
}
