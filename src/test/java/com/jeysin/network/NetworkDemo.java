package com.jeysin.network;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author: Jeysin
 * @Date: 2019/4/8 17:32
 * @Desc:
 */

public class NetworkDemo {

    private static void URLTest(){
        try{
            URL url = new URL("http://www.baidu.com");
            URLConnection urlConnection = url.openConnection();
            InputStream input = urlConnection.getInputStream();
            byte[] bytes = new byte[1024];
            int length = 0;
            while ((length = input.read(bytes)) != -1) {
                System.out.println(new String(bytes, 0, length));
            }
            input.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        URLTest();
    }
}
