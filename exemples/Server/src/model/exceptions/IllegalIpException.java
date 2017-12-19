package model.exceptions;

/**
 * 
 * @author jimmy
 * An exception throws in the Ipv4 class.
 */
@SuppressWarnings("serial")
public class IllegalIpException extends Exception{
	
	public IllegalIpException(){
		super("Invalid ip address, threre is not four bytes");
	}

}
