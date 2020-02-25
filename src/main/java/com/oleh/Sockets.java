package com.oleh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Sockets {
    public static void main(String[] args) {
        while (true) occupyPort();
    }

    // java.net.SocketException: No buffer space available (maximum connections reached?): NET_Bind
    static HashMap<Integer, ServerSocket> sockets = new HashMap<>(65536); // 16271

    static void occupyPort() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(0);
            int localPort = serverSocket.getLocalPort();
            if (sockets.put(localPort, serverSocket) != null) {
                System.out.println("we are done");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String listenPort() {
        StringBuilder sb = new StringBuilder();
        try (ServerSocket server = new ServerSocket(1010);
             Socket client = server.accept();
             InputStream input = client.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
