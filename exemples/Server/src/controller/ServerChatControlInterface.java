package controller;

import graphic.ServerInterface;

import javax.swing.JFrame;

import server.Server;
import main.Main;
import model.Log;
import model.Message;
import model.MessageCG;
import model.MessageCS;
import model.MessageSG;
import model.MessageSS;
import model.MessageSS.LogType;

/**
 * 
 * @author jimmy
 * 
 *         The extension of the serverChat. A swing interface is add and the
 *         LogList is transform in a LogListInterfaced.
 * 
 */
public class ServerChatControlInterface extends Server {

	/**
	 * Constructor
	 * 
	 * @param port
	 *            The port uses by the server.
	 */
	public ServerChatControlInterface(final int port) {
		super(port);

		// We construct the Swing interface.
		final ServerInterface frame = new ServerInterface(
				new ServerInterface.ServerSendListener() {

					@Override
					public void messageAdd(String message) {
						message = MessageSG.chat(Main.SERVERIP, port, message);
						manager.sendAll(message);
					}

				});

		// We improved the LogList in an LogListInterfaced class.
		logs = new LogListInterfaced(logs,
				new LogListInterfaced.LogAddListener() {

					@Override
					public void logAdd(Log log) {
						String t = log.getType();
						String message = log.getMessage();
						String ip = log.getIp().toString();
						if (t.equals(MessageCG.CG_CHAT_NAME)) {
							String player = Message.getParam(message,
									MessageCG.CG_CHAT_PLAYER_NAME);
							String avatar = Message.getParam(message,
									MessageCG.CG_CHAT_AVATAR_NAME);
							String mess = Message.getParam(message,
									MessageCG.CG_CHAT_MESSAGE);
							frame.add_message(Main.SERVERIP.toString(), player,
									avatar, mess, frame.STYLE_MESSAGE_SIMPLE);
						}
						if (t.equals(MessageCS.CS_DISCONNECT_NAME)) {
							String player = Message.getParam(message,
									MessageCS.CS_DISCONNECT_PLAYER_NAME);
							String avatar = Message.getParam(message,
									MessageCS.CS_DISCONNECT_AVATAR_NAME);
							String mess = "Disconnect";
							frame.add_message(ip, player, avatar, mess,
									frame.STYLE_MESS_SPE_SIMPLE);
						}
						if (log.getType().equals(MessageSS.SS_LOG_NAME)) {
							String classe = Message.getParam(message,
									MessageSS.SS_LOG_CLASSE);
							String fonction = Message.getParam(message,
									MessageSS.SS_LOG_FONCTION);
							String mess = Message.getParam(message,
									MessageSS.SS_LOG_MESSAGE);
							String type = Message.getParam(message,
									MessageSS.SS_LOG_TYPE);
							if (type.equals(LogType.ERROR.toString())) {
								frame.add_message(Main.SERVERIP.toString(),
										classe, fonction, mess,
										frame.STYLE_ERREUR_SIMPLE);
							}
							if (type.equals(LogType.SUCCESS.toString())) {
								frame.add_message(Main.SERVERIP.toString(),
										classe, fonction, mess,
										frame.STYLE_SUCCESS_SIMPLE);
							}
						}
					}

				});

		// We make the swing interface visible.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
