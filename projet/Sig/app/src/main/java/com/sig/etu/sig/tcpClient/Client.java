package com.sig.etu.sig.tcpClient;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Wilsigh on 10/01/2018.
 */

public class Client {

    public static String ipAddress = "192.168.7.4";
    public static int port = 1111;

    // variable global
    public static TCPClient tcpClient;

    // terminal
    public static List<String> commandSendRecv;

    static {
        tcpClient = new TCPClient(new TCPClient.Recv() {
            @Override
            public void recv(String str) {
                // on ajoute dans la liste les messages reçu (pour le terminal)
                commandSendRecv.add(str);
            }
        });
        commandSendRecv = new LinkedList<String>();
    }
}
