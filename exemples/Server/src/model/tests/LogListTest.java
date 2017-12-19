package model.tests;

import java.util.ArrayList;

import model.Ipv4;
import model.Log;
import model.LogList;
import model.Message;
import model.MessageSP;
import model.exceptions.LogCreationException;
import junit.framework.TestCase;

/**
 * 
 * @author jimmy
 * 
 * Test for the LogList class.
 */
public class LogListTest extends TestCase {

	//Test for the simple constructor.
	public void testLogList() {
		LogList list = new LogList();
		assertTrue(list != null);
	}

	//Test for the constructor with an array.
	public void testLogListArrayListOfLog() {
		Ipv4 ip = new Ipv4("0.0.0.0");
		
		ArrayList<Log> liste = new ArrayList<Log>();
		String message = MessageSP.disconnect(ip, 1);
		try {
			liste.add(new Log(message));
			liste.add(new Log(message));
		} catch (LogCreationException e) {
			assertFalse(true);
		}
		
		LogList list = new LogList(liste);
		assertEquals(list.get(0).getType(), MessageSP.SP_DISCONNECT_NAME);
		assertTrue(list.get(1).getPort()==1);
	}

	//Test the add for a message log type.
	public void testAddString() {
		LogList list = new LogList();
		
		//Case the case type null;
		assertFalse(list.add("message"));
		

		assertFalse(list.add(null));
		
		Ipv4 ip = new Ipv4("0.0.0.0");
		String message = Message.createMessage(ip, 1, MessageSP.SP_DISCONNECT_NAME, null);
		
		//Good case
		assertTrue(list.add(message));
	}

	//Test the remove method.
	public void testRemove() {
		Ipv4 ip = new Ipv4("0.0.0.0");
		
		ArrayList<Log> liste = new ArrayList<Log>();
		String message = MessageSP.disconnect(ip, 1);
		String message2 = MessageSP.disconnect(ip, 2);
		try {
			liste.add(new Log(message));
			liste.add(new Log(message2));
		} catch (LogCreationException e) {
			assertFalse(true);
		}
		
		LogList l = new LogList(liste);
		
		//We remove two times the first element.
		assertTrue(l.remove(0).getPort()==1);
		assertTrue(l.remove(0).getPort()==2);
	}

}
