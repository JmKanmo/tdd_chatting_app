package application.client;

import application.client.task.SendDataTask;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private Thread thread;

    public boolean connectSocket(int portNumber) {
        try {
            socket = new Socket("localhost", portNumber);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void startSendDataTask() {
        this.thread = new Thread(new SendDataTask(socket));
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

    public boolean isConnected() {
        return socket.isConnected();
    }

    public boolean isClosed() {
        return socket.isClosed();
    }

    public Socket getSocket() {
        return socket;
    }
}
