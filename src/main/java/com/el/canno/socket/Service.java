package com.el.canno.socket;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Service {

    public static void main(String[] args) throws Exception {
        // 监听指定的端口
        int port = 9991;
        ServerSocket server = new ServerSocket(port);
        boolean a = true;
        while (a) {
            // server将一直等待连接的到来
            System.out.println("server将一直等待连接的到来");
            Socket socket = server.accept();
            // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            try {
                while ((len = inputStream.read(bytes)) != -1) {
                    //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                    sb.append(new String(bytes, 0, len, "UTF-8"));
                    System.out.println("get message from client: " + sb);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("get message from client: " + sb);
            inputStream.close();
            socket.close();
        }

        server.close();
    }
}
