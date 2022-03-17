package com.example.course04;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author oldFlag
 * @since 2022/3/13
 */
public class HttpServer03 {


    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 2);
        final ServerSocket serverSocket = new ServerSocket(8803);
        while (true) {
            try {
                final Socket socket = serverSocket.accept();
                System.out.println("收到请求");
                executorService.execute(() -> Service.handle(socket, "jet"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
