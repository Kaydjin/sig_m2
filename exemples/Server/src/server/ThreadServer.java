package server;

import java.net.*;
import java.io.*;

import main.Main;
import model.Client;
import model.Game;
import model.LogFaciliter;
import model.LogList.LogListener;
import model.MessageSS;
import model.MessageSS.LogType;
import model.exceptions.MessageHandlerCreationException;

/**
 * 
 * @author jimmy
 * 
 *         This class take care of the message from the client. And redistribute
 *         them by using the ClientManager.
 */
class ThreadServer extends Thread implements LogFaciliter {

	// The name of the class for the log.
	private final String classe = this.getClass().getName();

	// The socket of the client for the receive.
	private Socket socket;

	// The information about the client.
	private Client client;

	// The log listener to replace exceptions by log in the server process.
	private LogListener listener;

	// The Server message manager which manage the send and receive message.
	private ServerMessageManager mmanager;

	private int port;
	
	/**
	 * Constructor to initialize a thread client. If error the client will not
	 * be add to the chat.
	 * 
	 * @param socket
	 *            The socket of the client.
	 * @param manager
	 *            The client manager of the instance.
	 * @param listener
	 *            The listener to add log of error and success in the instance
	 *            logs.
	 */
	ThreadServer(Integer port, Socket socket, ClientManager manager, Game game, LogListener listener) {
		final String m = "Constructor";
		this.socket = socket;
		this.listener = listener;
		this.port = port;
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream())), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
			
			// We try a connection with a time out.
			String mess = connection(10f, out, in);
			
			//We build the Server Message Manager.
			mmanager = new ServerMessageManager(Main.SERVERIP, port, listener, game, manager, socket);
			
			//We manage the receive message result by the mmanager.
			mmanager.setMessage(mess);
			mmanager.receive_result();
			
			//We keep the new client.
			this.client = mmanager.getClient();
			
			if(client != null)
				this.start();
			
		} catch (MessageHandlerCreationException e){
			l(m, e.getMessage(), LogType.ERROR);
		} catch (IOException e) {
			l(m, e.getMessage(), LogType.ERROR);
		}
	}

	/**
	 * Ask the client for a connection message.
	 * 
	 * @param pw the output of the client
	 * @param br the input of the client
	 * @return the message from the client. Null if no message receive.
	 */
	private String connection(float time_out, PrintWriter pw, BufferedReader br) {

		// First we send a message to the client requesting a connection message
		boolean run = true;
		float time_begin = System.currentTimeMillis(); // Time in ms.
		while (run) {
			pw.println(MessageSS.connect(Main.SERVERIP, port));
			pw.flush();

			// If there is an error in the send, we continue.
			if (!pw.checkError()) {
				run = false;
			} else {

				// If no message go through before the time out we stop.
				if (((System.currentTimeMillis() - time_begin) / 1000) > time_out)
					return null;
			}
		}
		// Then we try to receive an answer:
		time_begin = System.currentTimeMillis(); // Time in ms.
		while (true) {
			try {
				if (br.ready()){
					return br.readLine();
				}else
					//If no message go through before the time out we stop.
					if (((System.currentTimeMillis() - time_begin) / 1000) > time_out)
						return null;
			} catch (IOException e) {
				l("Connection", e.getMessage(), LogType.ERROR);
				return null;
			}
		}
	}

	/**
	 * Thread run of this class. Launch in the constructor. The method wait for
	 * message of the client and take care of distributing them between all the
	 * client.
	 */
	public void run() {
		final String m = "run";
		
		String mess = "";
		boolean running = true;
		try {
			while (running) {
				if (socket.isClosed())
					running = false;
				else
				{
					mess = client.getListener().readLine();
					mmanager.setMessage(mess);
					mmanager.receive_result();
				}
			}

		} catch (IOException e) {
			if(running == true){
				l(m, "IOException:"+e.getMessage(), LogType.ERROR);
				stop_thread();
				running = false;
			}
		} catch (Exception e) {
			if(running == true){
				l(m, "Exception+"+e.getMessage(), LogType.ERROR);
				stop_thread();
			}
		}
	}

	private void stop_thread() {
		final String m = "manage_stoped_thread";
		try {
			socket.close();
			client.disconnect();
		} catch (IOException e) {
			l(m, "During disconnect" + e.getMessage(), LogType.ERROR);
		}
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
