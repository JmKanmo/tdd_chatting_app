package application.main;

import application.server.Server;

import java.util.Scanner;

class ServerController extends Thread {
    private Server server = new Server();
    private int portNumber;
    private boolean isOn;

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public void stopServer() {
        server.stopServer();
    }

    @Override
    public void run() {
        if (server.startServer(portNumber)) {
            System.out.println("서버가 실행되었습니다.");
            System.out.println("클라이언트 요청수락작업을 시작합니다.");
            server.startAcceptTask();
        } else {
            System.out.println("서버가 실행이 안됩니다. 다시시도해주세요.");
        }
    }
}

public class ServerMain {
    public static void main(String[] args) {
        ServerController serverController = new ServerController();
        Scanner scanner = new Scanner(System.in);

        System.out.println("서버의 포트번호를 입력:");
        int portNumber = scanner.nextInt();
        serverController.setPortNumber(portNumber);
        serverController.start();

        System.out.println("서버를 종료하려면 exit을 입력하세요.");

        while (true) {
            String input = scanner.next();
            if (input.equals("exit")) {
                System.out.println("서버프로그램을 종료합니다.");
                serverController.stopServer();
                break;
            }
        }
    }
}
