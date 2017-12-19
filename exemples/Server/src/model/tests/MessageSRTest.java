package model.tests;

import java.util.ArrayList;

import model.Ipv4;
import model.Message;
import model.MessageSR;
import junit.framework.TestCase;

public class MessageSRTest extends TestCase {

	public void testTime() {
		//good message;
		assertNotNull(MessageSR.time(new Ipv4("0.0.0.0"), 1, 2f));
		assertNull(MessageSR.time(null, 1, 2f));
		assertNull(MessageSR.time(new Ipv4("0.0.0.0"), null, 2f));
		assertNull(MessageSR.time(new Ipv4("0.0.0.0"), 1, null));
		assertNull(MessageSR.time(new Ipv4("0.0.0.0"), -1, 2f));
		assertNull(MessageSR.time(new Ipv4("0.0.0.0"), 1, null));
		assertNull(MessageSR.time(new Ipv4("0.0.0.0"), 1, -2f));
		
		String message = MessageSR.time(new Ipv4("0.0.0.0"), 1, 2f);
		assertNull(Message.getParam(message, 0));
		assertTrue(Float.parseFloat(Message.getParam(message, MessageSR.SR_TIME))==2f);
		assertNull(Message.getParam(message, MessageSR.SR_TIME+1));
		
	}

	public void testTurn() {
		
		assertNotNull(MessageSR.turn(new Ipv4("0.0.0.0"), 1, MessageSR.Turn.Day));
		String message = MessageSR.turn(new Ipv4("0.0.0.0"), 1, MessageSR.Turn.Day);
		assertNull(Message.getParam(message, 0));
		assertTrue(Message.getParam(message, MessageSR.SR_TURN).equals(MessageSR.Turn.Day.toString()));
		assertNull(Message.getParam(message, MessageSR.SR_TURN+1));
		
	}

	public void testDeads() {
		String message_empty = MessageSR.deads(new Ipv4("0.0.0.0"), 1, new ArrayList<String>());
		assertNotNull(message_empty);
		assertNull(Message.getListeParam(message_empty));
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("A");
		list.add("B");
		String message = MessageSR.deads(new Ipv4("0.0.0.0"), 1, list);
		assertNotNull(message);
		ArrayList<String> list2 = Message.getListeParam(message);
		assertNotNull(list2);
		for(int i=0; i<list2.size(); i++){
			assertTrue(list2.get(i).toString().equals(list.get(i).toString()));
		}
	}

	public void testTypes() {
		String message_empty = MessageSR.types(new Ipv4("0.0.0.0"), 1, new ArrayList<String>());
		assertNotNull(message_empty);
		assertNull(Message.getListeParam(message_empty));
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("A");
		list.add("B");
		String message = MessageSR.types(new Ipv4("0.0.0.0"), 1, list);
		assertNotNull(message);
		ArrayList<String> list2 = Message.getListeParam(message);
		assertNotNull(list2);
		for(int i=0; i<list2.size(); i++){
			assertTrue(list2.get(i).toString().equals(list.get(i).toString()));
		}
	}

	public void testPlayers() {
		String message_empty = MessageSR.players(new Ipv4("0.0.0.0"), 1, new ArrayList<String>());
		assertNotNull(message_empty);
		assertNull(Message.getListeParam(message_empty));
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("A");
		list.add("B");
		String message = MessageSR.players(new Ipv4("0.0.0.0"), 1, list);
		assertNotNull(message);
		ArrayList<String> list2 = Message.getListeParam(message);
		assertNotNull(list2);
		for(int i=0; i<list2.size(); i++){
			assertTrue(list2.get(i).toString().equals(list.get(i).toString()));
		}
	}

	public void testAvatars() {
		String message_empty = MessageSR.avatars(new Ipv4("0.0.0.0"), 1, new ArrayList<String>());
		assertNotNull(message_empty);
		assertNull(Message.getListeParam(message_empty));
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("A");
		list.add("B");
		String message = MessageSR.avatars(new Ipv4("0.0.0.0"), 1, list);
		assertNotNull(message);
		ArrayList<String> list2 = Message.getListeParam(message);
		assertNotNull(list2);
		for(int i=0; i<list2.size(); i++){
			assertTrue(list2.get(i).toString().equals(list.get(i).toString()));
		}
	}

	public void testPosts_names() {
		String message_empty = MessageSR.posts_names(new Ipv4("0.0.0.0"), 1, new ArrayList<String>());
		assertNotNull(message_empty);
		assertNull(Message.getListeParam(message_empty));
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("A");
		list.add("B");
		String message = MessageSR.posts_names(new Ipv4("0.0.0.0"), 1, list);
		assertNotNull(message);
		ArrayList<String> list2 = Message.getListeParam(message);
		assertNotNull(list2);
		for(int i=0; i<list2.size(); i++){
			assertTrue(list2.get(i).toString().equals(list.get(i).toString()));
		}
	}

}
