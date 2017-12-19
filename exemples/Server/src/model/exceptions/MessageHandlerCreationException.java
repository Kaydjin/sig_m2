package model.exceptions;

@SuppressWarnings("serial")
public class MessageHandlerCreationException extends Exception {

	public MessageHandlerCreationException(){
		super("The parameter doesn't respect the format of a Message.");
	}
}
