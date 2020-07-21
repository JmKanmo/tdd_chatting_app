package application.server.task;

import application.client.Client;
import application.server.Server;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

class StateCheckThread extends Thread {
    AcceptSocketTask acceptSocketTask;

    public StateCheckThread(AcceptSocketTask acceptSocketTask) {
        this.acceptSocketTask = acceptSocketTask;
    }

    @Override
    public void run() {
        while (true) {
            if (Thread.interrupted()) break;
            try {
                List<Socket> socketList = this.acceptSocketTask.getSocketList();
                Thread.sleep(2000);

                if (socketList.isEmpty()) {
                    System.out.println("socket이 비었습니다.");
                } else {
                    socketList.forEach(socket -> {
                        System.out.print(socket.toString() + ", ");
                    });
                    System.out.println();
                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }
}

public class AcceptSocketTaskTest {
    private Server server;
    private Client client;
    private Thread thread;

    void connectServer() throws IOException {
        server = new Server();
        server.startServer();
        thread = new StateCheckThread(server.getAcceptSocketTask());
        thread.start();
    }

    void connectClient() {
        client = new Client();
        client.connectSocket();
    }

    void closeClient() {
        client.closeSocket();
    }

    void closeServer() {
        server.stopServer();
    }

    @Test
    public void runTest() throws IOException, InterruptedException {
        connectServer();
        connectClient();
        connectClient();
        Thread.sleep(6000);
        closeClient();
        closeServer();
        Thread.sleep(6000);

        // closeServer -> run method throw new IOException 발생 -> stopServer 호출여부 확인
        assertEquals(server.getServerSocket().isClosed(), true);
        assertEquals(server.getExecutorService().isShutdown(), true);
    }
}