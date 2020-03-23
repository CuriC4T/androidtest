package com.example.privatechat.Connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MainClient extends Thread{
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public MainClient(){

    }

    void connect() { // 연결
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();

            System.out.println(inetAddress);
            socket = new Socket("www.nyannyan.kro.kr", 7777);

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         new Thread(this).start();
        } catch (Exception e) {

        }
    }
    public BufferedReader getBufferedReader(){
        return reader;
    }
    public PrintWriter getPrintWriter(){
        return writer;
    }


}
