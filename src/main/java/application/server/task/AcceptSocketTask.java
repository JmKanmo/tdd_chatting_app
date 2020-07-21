package application.server.task;

import application.client.Client;
import application.server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class AcceptSocketTask implements Runnable {
    private Server server;
    private List<Client> clientList = new ArrayList<>();

    public AcceptSocketTask(Server server) {
        this.server = server;
    }

    public List<Client> getClients() {
        return clientList;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = server.acceptClientSocket();
                server.submitTask(new ReceiveDataTask(socket));
            } catch (IOException e) {
                server.stopServer();
                break;
            }
        }
    }
}
