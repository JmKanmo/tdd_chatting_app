package application.statechecker;

import application.server.task.AcceptSocketTask;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.net.Socket;
import java.util.List;

public class AcceptSocketTaskStateCheck extends Thread {
    AcceptSocketTask acceptSocketTask;

    public AcceptSocketTaskStateCheck(AcceptSocketTask acceptSocketTask) {
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

    @Test
    public void testAcceptSocketTaskStateCheck() {
        //default test
    }
}