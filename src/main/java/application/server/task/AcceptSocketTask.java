package application.server.task;

import application.client.Client;
import application.server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class AcceptSocketTask implements Runnable {
    private Server server;
    private List<Socket> socketList = new ArrayList<>();

    public AcceptSocketTask(Server server) {
        this.server = server;
    }

    public List<Socket> getSocketList() {
        return socketList;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = server.acceptClientSocket();
                this.socketList.add(socket);
                server.submitTask(new ReceiveDataTask(socket));
            } catch (IOException e) {
                server.stopServer();
                socketList.clear();
                break;
            }
        }
    }
}
