package com.example.course04;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author oldFlag
 * @since 2022/3/13
 */
public class HttpServer01 {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8801);

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Service.handle(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
