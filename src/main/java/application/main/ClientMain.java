package application.main;

import application.client.Client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

class ClientController extends Thread {
    private int portNumber;
    private boolean isStop;
    private boolean isOn;
    private Client client = new Client();

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public void stopClient() {
        this.isStop = true;
    }

    @Override
    public void run() {
        while (!isStop && !isOn) {
            if (client.connectSocket(portNumber)) {
                System.out.println("서버와 연결되었습니다.");
                System.out.println("서버로의 데이터 전송을 시작합니다.");
                isOn = true;
                client.startSendDataTask();
            } else {
                System.out.println("서버와의 연결이 불안정, 재접속 시도 중...");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("프로그램을 종료합니다.");
        client.closeSocket();
    }
}

public class ClientMain {
    public static void main(String[] args) throws IOException {
        ClientController clientController = new ClientController();
        Scanner scanner = new Scanner(System.in);

        System.out.println("접속할 포트번호입력:");

        int portNumber = scanner.nextInt();
        clientController.setPortNumber(portNumber);
        clientController.start();

        System.out.println("접속프로그램을 종료하려면 exit을 누르고 포트번호를 변경하려면 change를 입력하세요.");

        while (true) {
            String command = scanner.next();
            if (command.equals("exit")) {
                System.out.println("프로그램을 종료합니다.");
                clientController.stopClient();
                break;
            } else if (command.equals("change")) {
                portNumber = scanner.nextInt();
                clientController.setPortNumber(portNumber);
            }
        }
    }
}
