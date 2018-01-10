package com.sig.etu.sig.tcpClient;

/**
 * Created by Wilsigh on 10/01/2018.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPClient {

    // réception de message
    private Recv recv;

    // socket de connexion
    private Socket socket;

    // réception des messages
    private DataInputStream dataInputStream;
    // envoie de message
    private DataOutputStream dataOutputStream;

    // si on est connecté à l'esp
    private boolean connected = false;


    public TCPClient(Recv recv) {
        this.recv = recv;
    }

    /**
     * Constructeur de TCPClient
     * @param address adresse IP de l'esp
     * @param port port utilisé de l'esp
     */
    public TCPClient(String address, int port, Recv recv) throws IOException {
        this.recv = recv;
        connect(address, port);
    }

    /**
     * Connexion avec l'esp
     * @param address adresse IP de l'esp
     * @param port port de l'esp
     */
    public void connect(final String address, final int port) throws IOException {
        socket = new Socket(address, port);

        // on récupère les inputstream et outputstram du socket
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());

        // gestion des réceptions des messages
        recv();

        // on indique que le socket est bien connecté à l'esp
        connected = true;

        Client.commandSendRecv.add("esp8266 connected");
    }

    /**
     * Gestion des réceptions des messages
     */
    private void recv() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = "";

                try {
                    // boucle continue qui s'arrête lorsqu'on ferme la connexion
                    while (connected) {
                        // si on reçois des messages, on ajoute dans la chaine de caractère
                        while (dataInputStream.available() > 0) {
                            str += (char) dataInputStream.readByte();
                        }

                        // si on a reçu un message, on appelle la méthode recv de l'interface
                        if (str.length() != 0) {
                            recv.recv(str);
                        }

                        // on supprime le message
                        str = "";
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Testes la connexion
     * @return vrai si on est connecté à l'esp, faux sinon
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Envoie de message à l'esp
     * @param message
     */
    public void send(final String message) {
        if (isConnected()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // on écrit le message dans le buffer
                        dataOutputStream.writeBytes(" " + message);     // on ajoute un espace au début
                        // sinon ça plante à la réception
                        // vidage du buffer
                        dataOutputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * Déconnexion avec l'esp
     */
    public void disconnect() {
        if (isConnected()) {
            connected = false;
            Client.commandSendRecv.add("esp8266 disconnected");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // on ferme les inputstream et outputstream
                        dataInputStream.close();
                        dataOutputStream.close();
                        // fermeture du socket
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * Change la méthode de réception des message
     * @param recv
     */
    public void setRecv(Recv recv) {
        this.recv = recv;
    }

    public interface Recv {

        public void recv(String str);
    }
}
