package model;

import model.MessageSS.LogType;

/**
 * @author jimmy 
 * 
 *  This class is use to make the code more readable by making the
 *         log less important.
 */
public interface LogFaciliter {

	//To add a sucess or error log.
	public void l(String fonction, String message, LogType type);

	//To add a Message log.
	public void l(String message);

}
