package application.server.task;

import application.server.log.LogController;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ReceiveDataTask implements Runnable {
    private Socket socket;
    private BufferedInputStream bufferedInputStream;

    public ReceiveDataTask(Socket socket) {
        this.socket = socket;
    }

    private void receiveData() throws IOException {
        byte[] bArr = new byte[1024];
        int readByCnt = bufferedInputStream.read(bArr);

        if (readByCnt == -1) {
            throw new IOException();
        }
        // Client로부터 받아온 데이터 -> 서버의 로그파일에 기록하도록 한다.
        String data = new String(bArr, 0, readByCnt, "UTF-8");
        LogController.getInstance().logging(data);
    }

    @Override
    public void run() {
        try {
            bufferedInputStream = new BufferedInputStream(socket.getInputStream());

            while (true) {
                receiveData();
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
