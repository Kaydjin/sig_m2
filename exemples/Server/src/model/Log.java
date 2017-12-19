package model;

import model.exceptions.LogCreationException;

/**
 * 
 * @author jimmy
 * 
 *         This class can represent every possible message.
 *         It is uses to keep message, success and error logs as well.
 */
public class Log {

	private Ipv4 ip;
	private Integer port;
	private String type;
	private String message;

	/**
	 * Constructor
	 * @param message the message in a format defined by the Message class.
	 */
	public Log(String message) throws LogCreationException{
		if(!Message.isMessage(message))
			throw new LogCreationException();
		else
		{
			this.ip = Message.getIpv4(message);
			this.port = Message.getPort(message);
			this.type = Message.getTypeMessage(message);
			this.message = message;
		}
	}

	public Ipv4 getIp() {
		return ip;
	}

	public void setIp(Ipv4 ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
