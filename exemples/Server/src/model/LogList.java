package model;

import java.util.ArrayList;

import model.MessageSS.LogType;
import model.exceptions.LogCreationException;

/**
 * 
 * @author jimmy
 * 
 *         A lig of log class.
 */
public class LogList {

	public ArrayList<Log> listes;

	/**
	 * Constructor
	 */
	public LogList() {
		listes = new ArrayList<Log>();
	}

	/**
	 * Constructor with data already existing. 
	*/
	public LogList(ArrayList<Log> listes) {
		this.listes = listes;
	}

	/**
	 * Synchronized method to get a log from the list.
	 * 
	 * @param i
	 *            the index of the element to get.
	 * @return the element wanted in the list. Null otherwise.
	 */
	synchronized public Log get(Integer i) {
		if (i < 0 || i >= listes.size())
			return null;

		return listes.get(i);
	}

	/**
	 * Synchronized method to add a message log type.
	 * 
	 * @param message
	 * @return true if the log added;
	 */
	synchronized public boolean add(String message) {
		if(message == null)return false;
		
		Log l;
		try {
			l = new Log(message);
		} catch (LogCreationException e) {
			return false;
		}
		return listes.add(l);
	}

	/**
	 * Synchronized method to remove a log.
	 * 
	 * @param i
	 *            The index of the log to remove from the list.
	 * @return The log remove from the list.
	 */
	synchronized public Log remove(Integer i) {
		return listes.remove((int) i);
	}

	/**
	 * 
	 * @author jimmy Listener use as a callback to replace all exceptions and
	 *         success messages in the server by logs.
	 */
	public interface LogListener {
		public void logMessage(String message);

		public void logMessage(String classe, String fonction,
				String message, LogType type);
	}

}
