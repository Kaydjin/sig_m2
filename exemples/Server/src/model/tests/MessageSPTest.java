package model.tests;

import model.Ipv4;
import model.MessageSP;
import junit.framework.TestCase;

public class MessageSPTest extends TestCase {

	public void testDisconnect() {
		String message = MessageSP.disconnect(new Ipv4("0.0.0.0"), 1);	
		
		assertNotNull(message);
		assertNull(MessageSP.disconnect(null, 1));
		assertNull(MessageSP.disconnect(null, -1));
		assertNull(MessageSP.disconnect(new Ipv4("0.0.0.0"), null));
	}

}
