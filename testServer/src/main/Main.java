package main;

import model.Ipv4;
import controller.ServerChatControlInterface;

public class Main {

	private static final int SERVERPORT = 4444;
	public static final Ipv4 SERVERIP = new Ipv4("192.168.43.102");
	public static void main(String args[]) {

		ServerChatControlInterface serv = new ServerChatControlInterface(SERVERPORT);
		serv.start();
		
	}
	
}