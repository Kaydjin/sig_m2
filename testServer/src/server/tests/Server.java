package server.tests;

import main.Main;
import model.*;
import model.MessageSS.LogType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * @author jimmy
 * 
 *         The server class.
 */
public class Server extends Thread implements LogFaciliter {

	// The name of the class for the log.
	private final String classe = this.getClass().getName();

	// The log list.
	protected LogList logs;

	// The print mangager which contains the list of clients and
	// the synchronized methods.
	protected ClientManager manager;

	// The server main object, his socket.
	protected ServerSocket ss;

	// The broadcast ip, meaning one by interface.
	protected ArrayList<Ipv4> broadcasts = new ArrayList<Ipv4>();
	
	// The informations about the game.
	protected Game game;
	

	// Some values for putting the server to sleep or stop him.
	private int port;
	private final int TIME_OUT = 5 * 1000; // 5sec before sleeping 2sec at max.
	private final int SLEEP_TIME_CONNECTION_DONE = 500; // 500ms.
	private final int SLEEP_TIME_CONNECTION_NOT_DONE = 2 * 1000; // 2sec.
	private boolean running = true; // The boolean for the run. If put to false,
									// the server is stop.
	private boolean first_client = false; // Put to true when the first client
											// connect.

	/**
	 * The constructor.
	 * 
	 * @param port
	 *            the port for the connection to the server. Send and receive.
	 */
	public Server(int port) {

		// Initialize the log which will contains the messages from the chat and
		// the server possible errors.
		logs = new LogList();

		// Initialize the server socket.
		init_server(port);

		// Initialize the PrintWriterManager
		init_print_writer();

		// Initialize the broadcasts listes.
		init_broadcast_list();
		
		this.port = port;

	}

	/**
	 * Initialize the server. We try to connect the ServerSocket to the
	 * specified port.
	 * 
	 * @param port
	 *            The port in which connect the ServerSocket.
	 */
	protected void init_server(int port) {
		final String m = "init_server";
		if (port <= 0)
			l(m, "Port " + port + " inexistant.", LogType.ERROR);
		else {
			try {
				ss = new ServerSocket(port);
				l(m, "The server is initialized.", LogType.SUCCESS);
			} catch (Exception e) {
				l(m, "Server initialization failed.", LogType.ERROR);
			}
		}
	}

	/**
	 * Initialize the print writer manager with a listener so that he log
	 * message when necessary
	 */
	protected void init_print_writer() {
		manager = new ClientManager(new LogList.LogListener() {

			@Override
			public void logMessage(String message) {
				logs.add(message);
			}

			@Override
			public void logMessage(String classe, String fonction,
					String message, LogType type) {
				String log = MessageSS.log(Main.SERVERIP, port, type, classe, fonction, message);
				logs.add(log);
			}


		});
	}

	/**
	 * Initialize the broadcast list.
	 */
	protected void init_broadcast_list() {
		InterfacesProperties iProp = new InterfacesProperties();
		ArrayList<Ipv4> address = iProp.getIpv4AddressFromInterfaces();
		for (int i = 0; i < address.size(); i++)
			broadcasts.add(iProp.getIpv4InterfaceProperties(address.get(i))[1]);
	}

	/**
	 * The server implementation of Runnable. The server connect new client and
	 * create a new thread for each one.
	 */
	@Override
	public void run() {
		final String m = "run";

		// We don't try to add client if the serverSocket didn't connect to the
		// port.
		if (ss != null) {
			l(m, "Service launched.", LogType.SUCCESS);
			game = new Game();
			// Running stop in the add_new_thread method when nobody is
			// connected.
			while (running) {

				try {
					l(m, "Waiting for a new connection.", LogType.SUCCESS);
					/*
					 * if(_nbClients < 1){ for(int i=0; i<broadcasts.size();
					 * i++){ new ThreadBroadcast(broadcasts.get(i), "hi");
					 * ///////TODO broadcast. } }
					 */

					add_new_thread();

				} catch (IOException e) {
					l(m, e.getMessage(), LogType.ERROR);
				}
			}

		} else {
			l(m, "Service not launched. Service socket not initialized.",
					LogType.ERROR);
		}
	}

	/**
	 * Launch a new thread client.
	 * 
	 * @throws IOException
	 */
	private void add_new_thread() throws IOException {
		final String m = "add_new_thread";

		// Set the time to wait for a new client.
		ss.setSoTimeout(TIME_OUT);

		try {

			// We wait for a new client and implement is logListener callback
			// methods.
			new ThreadServer(port, ss.accept(), manager, game, new LogList.LogListener() {

				@Override
				public void logMessage(String message) {
					logs.add(message);
				}

				@Override
				public void logMessage(String classe, String fonction,
						String message, LogType type) {
					String log = MessageSS.log(Main.SERVERIP, port, type, classe, fonction, message);
					logs.add(log);
				}
			});

			// If we are here one client have been add so we set to true the
			// first client boolean if not already done
			if (!first_client)
				first_client = true;

			// We connected someone but we put the server to sleep for some
			// time.
			try {
				Thread.sleep(SLEEP_TIME_CONNECTION_DONE);
			} catch (InterruptedException e1) {
				l(m, "The sleep of the thread has been interrupted.",
						LogType.ERROR);
			}

		} catch (SocketTimeoutException e) {
			// This exception is throws when no client is add in the period
			// fixed.

			// If a client has already been connected but there is no client we
			// stop the server.
			if (first_client && manager.getNbClients() == 0) {
				l(m, "Serveur fini.", LogType.SUCCESS);
				running = false;
				ss.close();
			}

			// We didn't connected someone and we put the server to sleep for
			// some time.
			try {
				Thread.sleep(SLEEP_TIME_CONNECTION_NOT_DONE);
			} catch (InterruptedException e1) {
				l(m, "Sleeping of the thread interruption.", LogType.ERROR);
			}

		}
	}

	@Override
	public void l(String fonction, String message, LogType type) {
		String log = MessageSS.log(Main.SERVERIP, port, type, classe, fonction, message);
		logs.add(log);
	}

	@Override
	public void l(String message) {
		logs.add(message);
	}

}
