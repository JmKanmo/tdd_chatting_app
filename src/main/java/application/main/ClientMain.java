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
        while (!isStop) {
            if (!isOn) {
                int ret = client.connectSocket(portNumber);

                if (ret > 0) {
                    isOn = true;
                    System.out.println("서버로의 데이터 전송을 시작합니다.");
                    client.startSendDataTask();
                } else {
                    if (ret < 0) {
                        System.out.println("해당포트의 서버가 닫혀있습니다, 재접속을 시도합니다.");
                    } else {
                        System.out.println("기타 다른 오류가 발생, 내용확인요함");
                    }
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        client.closeSocket();
        isOn = false;
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
