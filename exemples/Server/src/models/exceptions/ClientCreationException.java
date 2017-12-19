package models.exceptions;

@SuppressWarnings("serial")
public class ClientCreationException extends Exception {

	public ClientCreationException(){
		super("The creation of the client fail, one of the parameter is not authorized.");
	}
	
}
