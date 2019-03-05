package com.jeysin.JDK.OIO;

import java.io.InputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.Charset;

/**
 * @Author: Jeysin
 * @Date: 2019/3/4 23:41
 * @Desc:
 */

public class PlainOIOClient {

    public void server(String host, Integer port) throws Exception{
        Socket socket = new Socket(host, port);
        InputStream in = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int num = in.read(bytes, 0, 1024);
        System.out.println(num);
        System.out.println(new String(bytes, "UTF-8"));
        socket.close();
    }

    public static void main(String[] args){
        PlainOIOClient plainOIOClient = new PlainOIOClient();
        try {
            plainOIOClient.server("localhost", 10002);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
