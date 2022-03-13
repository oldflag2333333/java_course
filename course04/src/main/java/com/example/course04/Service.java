package com.example.course04;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author oldFlag
 * @since 2022/3/13
 */
public class Service {


    public static void handle(Socket socket) {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello,man";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
