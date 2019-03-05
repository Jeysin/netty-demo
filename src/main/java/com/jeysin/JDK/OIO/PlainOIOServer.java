package com.jeysin.JDK.OIO;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: Jeysin
 * @Date: 2019/3/4 23:26
 * @Desc:
 */

public class PlainOIOServer {
    public void server(Integer port) throws Exception{
        final ServerSocket serverSocket = new ServerSocket(port);
        while(true){
            final Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection from " + clientSocket);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OutputStream out = null;
                    try{
                        out = clientSocket.getOutputStream();
                        out.write("Hi, Jeysin!".getBytes("UTF-8"));
                        out.flush();
                    } catch (Exception e){
                        e.printStackTrace();
                    } finally {
                        try {
                            if (out != null) {
                                out.close();
                            }
                            clientSocket.close();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        PlainOIOServer plainOIOServer = new PlainOIOServer();
        try {
            plainOIOServer.server(10002);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
