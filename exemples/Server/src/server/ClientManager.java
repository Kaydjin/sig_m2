package server;

import java.io.PrintWriter;
import java.util.Vector;

import model.Client;
import model.LogList.LogListener;
import model.MessageSS.LogType;
/**
 * 
 * @author jimmy
 * 
 *         A list of clients with synchronized methods for multi-thread access.
 */
public class ClientManager implements model.LogFaciliter {

	// The name of the class for the log.
	private final String classe = this.getClass().getName();

	// The list of clients.
	private Vector<Client> tabClients = new Vector<Client>();

	// The number of clients.
	private int nbClients = 0;

	// The log listener to add log.
	private LogListener listener;

	/**
	 * Constructor
	 * 
	 * @param listener
	 *            The listener implemented to add log.
	 */
	public ClientManager(LogListener listener) {
		this.listener = listener;
	}

	/**
	 * Synchronized method that send a message with the Message format to all
	 * the clients.
	 * 
	 * @param message
	 */
	synchronized public void sendAll(String message) {
		final String m = "sendAll";

		PrintWriter out;
		boolean erreur = false;

		for (int i = 0; i < nbClients; i++) {
			out = tabClients.get(i).getPrinter();
			if (out == null) {
				l(m, "PrintWriter null, message not send.", LogType.ERROR);
				erreur = true;
			} else {
				out.println(message);
				out.flush();
				if (out.checkError()) {
					l(m, "Error while sending a message.", LogType.ERROR);
					erreur = true;
				}
			}
		}
		if (!erreur)
			l(m, "Sending complete.", LogType.SUCCESS);
	}

	/**
	 * Synchronized method that delete a output from a client from the list.
	 * 
	 * @param i
	 *            The client number corresponding to his output in the list.
	 * @return
	 */
	synchronized public boolean delClient(int i) {
		final String m = "delClient";
		PrintWriter out;
		if (tabClients.get(i) != null) {
			if ((out = tabClients.remove(i).getPrinter()) != null) {
				out.close();
				nbClients--;
				return true;
			} else {
				l(m, "Fail to delete an output.", LogType.ERROR);
				return false;
			}
		}
		l(m, "This output client doesn't exist.", LogType.ERROR);
		return false;
	}

	/**
	 * Synchronized method that add an output of a client to the list.
	 * @param c the client to add
	 * @return true if the client has been add, false otherwise.
	 */
	synchronized public boolean addClient(Client c) {
		if (tabClients.add(c)) {
			c.setNumClient(nbClients);
			nbClients++;
			return true;
		}
		return false;
	}

	/**
	 * Synchronized method that return a client of the list matching the parameters.
	 * @param player_name the name of the client.
	 * @return the client if he is in the list, null otherwise.
	 */
	synchronized public Client getClientByName(String player_name){
		for(Client c : tabClients){
			if(c.getPlayer().getName().equals(player_name))
				return c;
		}
		return null;
	}
	
	/**
	 * Synchronized method that return the number of clients in the list.
	 * 
	 * @return the number of clients
	 */
	synchronized public int getNbClients() {
		return nbClients;
	}
	@Override
	public void l(String message) {
		listener.logMessage(message);
	}

	@Override
	public void l(String fonction, String message, LogType type) {
		// TODO Auto-generated method stub
		
	}

}
