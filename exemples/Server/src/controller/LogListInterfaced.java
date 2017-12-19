package controller;

import java.util.ArrayList;

import model.Log;
import model.LogList;
import model.exceptions.LogCreationException;

/**
 * 
 * @author jimmy
 * 
 *         Extends LogList and add a callback interface to implement and call to
 *         intercept the log from the application.
 */
public class LogListInterfaced extends LogList {
	private LogAddListener listener;

	/**
	 * Constructor of the LogListInterfaced class.
	 * 
	 * @param mere
	 *            The LogList before improving it to a LogListInterfaced logs
	 *            list.
	 * @param listener
	 *            The interface implemented to call back.
	 */
	public LogListInterfaced(LogList mere, LogAddListener listener) {
		if (mere.listes != null)
			this.listes = mere.listes;
		else
			this.listes = new ArrayList<Log>();
		this.listener = listener;
	}

	/**
	 * The same method from his parent but he notifies his controller when a log
	 * is done.
	 */
	@Override
	synchronized public boolean add(String message) {
		Log l;
		try {
			l = new Log(message);
		} catch (LogCreationException e) {
			return false;
		}
		
		boolean b = listes.add(l);
		if(b)
			listener.logAdd(l);
		else
			return false;
		
		return true;
	}	

	/**
	 * 
	 * @author jimmy
	 * 
	 *         A log listener.
	 */
	public interface LogAddListener {
		public void logAdd(Log log);
	}

}
