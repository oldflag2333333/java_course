package com.example.course04;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author oldFlag
 * @since 2022/3/13
 */
public class HttpServer02 {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8802);

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("接收请求！");
                new Thread(() -> Service.handle(socket)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
