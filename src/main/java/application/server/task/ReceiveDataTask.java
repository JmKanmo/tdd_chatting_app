package application.server.task;

import application.server.log.LogController;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.List;

public class ReceiveDataTask implements Runnable {
    private Socket socket;
    private List<Socket> socketList;

    public ReceiveDataTask(Socket socket, List<Socket> socketList) {
        this.socket = socket;
        this.socketList = socketList;
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
            System.out.println(String.format("%d번 클라이언트의 응답이 없어 데이터교환 소켓을 닫습니다.", socket.getPort()));
            socketList.remove(socket);
        }
    }
}
