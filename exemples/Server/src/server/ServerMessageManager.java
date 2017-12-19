package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import model.Avatar;
import model.Avatar.Condition;
import model.Avatar.Type;
import model.Client;
import model.Game;
import model.Ipv4;
import model.LogFaciliter;
import model.Message;
import model.MessageCS;
import model.MessageSG;
import model.MessageSR;
import model.MessageSS;
import model.Player;
import model.LogList.LogListener;
import model.MessageManager;
import model.MessageSR.Turn;
import model.MessageSS.LogType;
import model.exceptions.AvatarSettingException;
import model.exceptions.ClientCreationException;
import model.exceptions.IllegalIpException;

public class ServerMessageManager extends MessageManager implements
		LogFaciliter {
	private final String classe = this.getClass().getName();
	private Ipv4 ip;
	private Integer port;
	private ClientManager cm;
	private LogListener listener;
	private Client client;
	private Socket socket;
	private Game game;

	/**
	 * Constructor of a ServerMessageManager object.
	 * 
	 * @param ip
	 *            the ip of the server instance.
	 * @param port
	 *            the port of the the server instance.
	 * @param l
	 *            the callback class container for logs
	 * @param m
	 *            the clientManager of the server instance.
	 * @param c
	 *            the client we communicate with.
	 * @param s
	 *            the client socket.
	 */
	public ServerMessageManager(Ipv4 ip, Integer port, LogListener l, Game game,
			ClientManager m, Socket s) {
		super();
		this.ip = ip;
		this.port = port;
		this.cm = m;
		this.listener = l;
		this.client = null;
		this.socket = s;
		this.game = game;
	}

	public Client getClient() {
		return client;
	}

	// Private method to send a message.
	private void send(String mess) {
		final String m = "send";
		PrintWriter pw = client.getPrinter();
		pw.println(mess);
		pw.flush();
		if (pw.checkError())
			l(m, "Fail to send:" + mess, LogType.ERROR);
	}

	@Override
	public void onCGChatReceive() {
		l(message);
		cm.sendAll(message);
	}

	// Process of connection.
	public void onCSConnectReceive() {
		final String m = "onCSConnectReceive";

		PrintWriter out = null;
		BufferedReader in = null;
		try {
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream())), true);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (IOException e) {
			l(m, "IOException:" + e.getMessage(), LogType.ERROR);
		}

		String p_name = Message.getParam(message,
				MessageCS.CS_CONNECT_PLAYER_NAME);

		// If the client already exist we send a reconnect
		if ((client = cm.getClientByName(p_name)) != null) {

			try {
				client.setIp(new Ipv4(socket.getInetAddress()));
				client.setPort(socket.getLocalPort());
				client.setPrinter(out);
				client.setListener(in);
			} catch (IllegalIpException e) {
				l(m, e.getMessage(), LogType.ERROR);
			}
			this.sendSSReconnect();
			this.sendSSChat("Vous êtes reconnecté.");

		} else { // Otherwise we connect the new client.
			Float f = Float.parseFloat(Message.getParam(message,
					MessageCS.CS_CONNECT_PLAYER_TIME_BLOCK));
			if (f == 0f || (System.currentTimeMillis() - f) / 1000 > 180) {
				try {
					String a_name = Message.getParam(message,
							MessageCS.CS_CONNECT_AVATAR_NAME);
					String type = Message.getParam(message,
							MessageCS.CS_CONNECT_AVATAR_TYPE);
					Ipv4 ip = new Ipv4(socket.getLocalAddress());
					int port_local = socket.getLocalPort();
					
					Avatar a = new Avatar(a_name, a_name, type, Condition.ALIVE);
					Player p = new Player(p_name);
					client = new Client(ip, port_local, out, in, a, p);
					game.addPlayer("ALIVE",a_name,a_name,"DOCTOR");
					this.sendSGAddAvatar();
					if (client != null) {
						if (!cm.addClient(client))
							l(m, "Add client on list failed.", LogType.ERROR);
						else
							l(m, "Client add to the list", LogType.SUCCESS);
					} else {
						l(m, "The client building has failed.", LogType.ERROR);
					}
				} catch (AvatarSettingException e) {
					l(m, e.getMessage(), LogType.ERROR);
				} catch (IllegalIpException e) {
					l(m, e.getMessage(), LogType.ERROR);
				} catch (ClientCreationException e) {
					l(m, e.getMessage(), LogType.ERROR);
				}
			}
		}
	}

	@Override
	public void onCSDisconnectReceive() {
		final String m = "onCSDisconnectReceive";
		try {
			socket.close();
			client.disconnect();
		} catch (IOException e) {
			l(m, "During disconnect: " + e.getMessage(), LogType.ERROR);
		}
		l(message);
	}

	@Override
	public void onCSReconnectReceive() {
	}

	@Override
	public void onSGChatReceive() {
	}

	@Override
	public void onSPDisconnectReceive() {
	}

	@Override
	public void onSRAvatarsReceive() {
	}

	@Override
	public void onSRDeadsReceive() {
	}

	@Override
	public void onSRPlayersReceive() {
	}

	@Override
	public void onSRPostsNamesReceive() {
	}

	@Override
	public void onSRTimeReceive() {
	}

	@Override
	public void onSRTurnReceive() {
	}
	
	@Override
	public void onSRTypesReceive() {
	}


	@Override
	public void setSGChatSend(Ipv4 ip, Integer port, String message) {
		String mess = MessageSG.chat(ip, port, message);
		send(mess);
	}

	public void sendSGChat(String message) {
		setSGChatSend(ip, port, message);
	}

	@Override
	public void setSPDisconnectSend(Ipv4 ip, Integer port) {
	}

	@Override
	public void setSRAvatarsSend(Ipv4 ip, Integer port,
			ArrayList<String> avatars) {
		String mess = MessageSR.avatars(ip, port, avatars);
		send(mess);
	}
	
	public void setSRAvatars(){
		setSRAvatarsSend(ip, port, game.getAvatarsNames());
	}

	@Override
	public void setSRDeadsSend(Ipv4 ip, Integer port, ArrayList<String> deads) {
	}

	@Override
	public void setSRPlayersSend(Ipv4 ip, Integer port,
			ArrayList<String> players) {
		String mess = MessageSR.players(ip, port, players);
		send(mess);
	}
	
	public void sendSRPlayers(){
		setSRPlayersSend(ip, port, game.getAvatarsNames());
	}

	@Override
	public void setSRPostsNamesSend(Ipv4 ip, Integer port,
			ArrayList<String> posts_names) {
		String mess = MessageSR.posts_names(ip, port, posts_names);
		send(mess);
	}
	
	public void sendSRPostsNames(){
		setSRPostsNamesSend(ip, port, game.getAvatarsPosts());
	}

	@Override
	public void setSRTypesSend(Ipv4 ip, Integer port, ArrayList<String> types) {
		String mess = MessageSR.types(ip, port, types);
		send(mess);
	}
	public void sendSRTypes(){
		setSRTypesSend(ip, port, game.getAvatarsTypes());
	}

	@Override
	public void setSRTimeSend(Ipv4 ip, Integer port, Float time) {
		String mess = MessageSR.time(ip, port, time);
		send(mess);
	}
	public void sendSRTime(){
		setSRTimeSend(ip, port, game.getTime());
	}
	@Override
	public void setSRTurnSend(Ipv4 ip, Integer port, Turn turn) {
		String mess = MessageSR.turn(ip, port, turn);
		send(mess);
	}
	public void sendSRTurn(){
		setSRTurnSend(ip, port, game.getTurn());
	}

	@Override
	public void setSSChatSend(Ipv4 ip, Integer port, String message) {
		String mess = MessageSS.chat(ip, port, message);
		send(mess);
	}

	public void sendSSChat(String mess) {
		setSSChatSend(ip, port, mess);
	}

	@Override
	public void setSSConnectSend(Ipv4 ip, Integer port) {
	}

	@Override
	public void setSSDisconnectSend(Ipv4 ip, Integer port) {
	}

	@Override
	public void setSSLogSend(Ipv4 ip, Integer port, LogType type, String c,
			String f, String message) {
	}

	@Override
	public void setSSReconnectSend(Ipv4 ip, Integer port, String player,
			String avatar, Type type, String poster, Condition state) {
		String mess = MessageSS.reconnect(ip, port, player, avatar, type,
				poster, state);
		send(mess);
	}

	public void sendSSReconnect() {
		setSSReconnectSend(ip, port, client.getPlayer().getName(), client
				.getAvatar().getName(), client.getAvatar().getType(), client
				.getAvatar().getPoster(), client.getAvatar().getState());
	}
	
	@Override
	public void setSGAddAvatar(Ipv4 ip, Integer port, String a_name,
			String a_poster, Type type, Condition c) {
		String mess = MessageSG.addAvatar(ip, port, a_name, a_poster, type, c);
		send(mess);
	}

	public void sendSGAddAvatar(){
		setSGAddAvatar(ip, port, client.getAvatar().getName(), client.getAvatar().getPoster(),
				client.getAvatar().getType(), client.getAvatar().getState());
	}
	
	@Override
	public void setSGRemoveAvatar(Ipv4 ip, Integer port, String a_name) {
		String mess = MessageSG.removeAvatar(ip, port, a_name);
		send(mess);
	}

	public void sendSGRemoveAvatar(){
		setSGRemoveAvatar(ip, port, client.getAvatar().getName());
	}
	
	@Override
	public void l(String fonction, String message, LogType type) {
		listener.logMessage(classe, fonction, message, type);
	}

	@Override
	public void l(String message) {
		listener.logMessage(message);
	}

}
