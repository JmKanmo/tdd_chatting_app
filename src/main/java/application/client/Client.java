package application.client;

import application.client.task.SendDataTask;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private Thread thread;

    public void connectSocket() {
        try {
            socket = new Socket("localhost", 5001);
        } catch (IOException e) {
            closeSocket();
            e.printStackTrace();
        }
    }

    public void sendData(int number) {
        this.thread = new Thread(new SendDataTask(socket, number));
        thread.start();
    }

    public void closeSocket() {
        try {
            if (socket != null && !socket.isClosed())
                socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
