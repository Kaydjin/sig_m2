package model;

import java.io.BufferedReader;
import java.io.PrintWriter;

import model.exceptions.ClientCreationException;

/**
 * 
 * @author jimmy
 * This class represent the client and all the possible informations about him.
 */
public class Client {
	
	//Enumeration of the possible state of the client.
	public enum State{ DISCONNECTED, CONNECTED};

	//Connection informations.
private Ipv4 ip;
private Integer port;

	//Send and receive actors.
private PrintWriter printer;
private BufferedReader listener;

	//State of the client in the server.
private State state;
private float last_connect_state;
public static int ten9 = 1000000000;

	//Data from the game about the client.
private Avatar avatar;
private Player player;
private Integer num_client;

	public Client(){
		this.printer = null;
		this.listener = null;
		this.avatar = null;
		this.player = null;
		this.port = null;
		this.ip = null;
		this.num_client = null;
	}
	
	/**	Only constructor of the client class.
	 * @param ip the ip of the client.
	 * @param port the port in which the client send and receive.
	 * @param printer the output to send message to the client.
	 * @param br the input to listen from the client.
	 * @param avatar the detail about the avatar of the client.
	 * @param player the detail about the player informations of the client.
	 * @throws ClientCreationException if one of the parameter is null or impossible.
	 */
	public Client(Ipv4 ip, int port, PrintWriter printer, BufferedReader br, Avatar avatar, Player player) throws ClientCreationException{
		if(ip == null ||port < 0 || printer == null || br == null || avatar == null || player == null)
			throw new ClientCreationException();
		
		this.ip = ip;
		this.port = port;
		this.printer = printer;
		this.listener = br;
		this.avatar = avatar;
		this.player = player;
		this.state = State.CONNECTED;
		this.num_client = null;
	}

	/**
	 * Must be call when the client disconnect.
	 */
	public void disconnect(){
		this.state = State.DISCONNECTED;
		last_connect_state = System.nanoTime();
 	}
	
	/**
	 * Must be call when the client reconnect.
	 */
	public void reconnect(){
		this.state = State.CONNECTED;
	}
	
	/**
	 * Inform about the time since the client disconnect.
	 * @return the time since the client disconnect in seconds.
	 */
	public float time_since_disconnected(){
		if(this.state == State.CONNECTED)
			return 0;
		return (System.nanoTime()-last_connect_state)/ten9;
	}

	public Ipv4 getIp() {
		return ip;
	}

	public void setIp(Ipv4 ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public PrintWriter getPrinter() {
		return printer;
	}

	public void setPrinter(PrintWriter printer) {
		this.printer = printer;
	}
	
	public BufferedReader getListener() {
		return listener;
	}

	public void setListener(BufferedReader listener) {
		this.listener = listener;
	}

	public State getState() {
		return state;
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Integer getNumClient(){
		return num_client;
	}
	
	public void setNumClient(Integer i){
		num_client = i;
	}

}
