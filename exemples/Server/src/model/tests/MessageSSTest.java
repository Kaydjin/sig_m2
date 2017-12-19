package model.tests;

import model.Avatar.Condition;
import model.Ipv4;
import model.Message;
import model.MessageSS;
import model.Avatar.Type;
import junit.framework.TestCase;

public class MessageSSTest extends TestCase {

	public void testChat() {
		String message = MessageSS.chat(new Ipv4("0.0.0.0"), 1, "message");	
		
		assertNotNull(message);
		assertNull(MessageSS.chat(null, 1, "message"));
		assertNull(MessageSS.chat(null, -1, "message"));
		assertNull(MessageSS.chat(new Ipv4("0.0.0.0"), null, "message"));
		assertNull(MessageSS.chat(new Ipv4("0.0.0.0"), 1, null));
		
		assertNull(Message.getParam(message, 0));
		assertTrue(Message.getParam(message, MessageSS.SS_CHAT_MESSAGE).equals("message"));
		assertNull(Message.getParam(message, MessageSS.SS_CHAT_MESSAGE+1));
	}

	public void testDisconnect() {
		String message = MessageSS.disconnect(new Ipv4("0.0.0.0"), 1);	
		
		assertNotNull(message);
		assertNull(MessageSS.disconnect(null, 1));
		assertNull(MessageSS.disconnect(null, -1));
		assertNull(MessageSS.disconnect(new Ipv4("0.0.0.0"), null));
	}

	public void testConnect() {
		String message = MessageSS.connect(new Ipv4("0.0.0.0"), 1);	
		
		assertNotNull(message);
		assertNull(MessageSS.connect(null, 1));
		assertNull(MessageSS.connect(null, -1));
		assertNull(MessageSS.connect(new Ipv4("0.0.0.0"), null));
	}

	public void testReconnect() {
		Type t = Type.DOCTOR;
		Condition c = Condition.ALIVE;
		assertNotNull(MessageSS.reconnect(new Ipv4("0.0.0.0"), 1, "a", "b", t, "d", c));
		assertNull(MessageSS.reconnect(null, 1, "a", "b", t, "d", c));
		assertNull(MessageSS.reconnect(new Ipv4("0.0.0.0"), -2, "a", "b", t, "d", c));
		assertNull(MessageSS.reconnect(new Ipv4("0.0.0.0"), 1, null, "b", t, "d", c));
		assertNull(MessageSS.reconnect(new Ipv4("0.0.0.0"), 1, "a", null, t, "d", c));
		assertNull(MessageSS.reconnect(new Ipv4("0.0.0.0"), 1, "a", "b", null, "d", c));
		assertNull(MessageSS.reconnect(new Ipv4("0.0.0.0"), 1, "a", "b", t, null, c));
		assertNull(MessageSS.reconnect(new Ipv4("0.0.0.0"), 1, "", "b", t, "d", c));
		assertNull(MessageSS.reconnect(new Ipv4("0.0.0.0"), 1, "a", "", t, "d", c));
		assertNull(MessageSS.reconnect(new Ipv4("0.0.0.0"), 1, "a", "b", t, "", c));

		String message = MessageSS.reconnect(new Ipv4("0.0.0.0"), 1, "a", "b", t, "d", c);
		assertNull(Message.getParam(message, 0));
		assertTrue(Message.getParam(message, MessageSS.SS_RECONNECT_AVATAR_NAME).equals("b"));
		assertTrue(Message.getParam(message, MessageSS.SS_RECONNECT_AVATAR_TYPE).equals(t.toString()));
		assertTrue(Message.getParam(message, MessageSS.SS_RECONNECT_PLAYER_NAME).equals("a"));
		assertTrue(Message.getParam(message, MessageSS.SS_RECONNECT_POST_NAME).equals("d"));
		assertTrue(Message.getParam(message, MessageSS.SS_RECONNECT_STATE).equals(c.toString()));
		assertNull(Message.getParam(message, MessageSS.SS_RECONNECT_STATE+1));
	}
	
	public void testLog() {
		assertNotNull(MessageSS.log(new Ipv4("0.0.0.0"), 1, MessageSS.LogType.ERROR, "b", "c", "d"));
		assertNull(MessageSS.log(null, 1, MessageSS.LogType.ERROR, "b", "c", "d"));
		assertNull(MessageSS.log(new Ipv4("0.0.0.0"), -2, MessageSS.LogType.ERROR, "b", "c", "d"));
		assertNull(MessageSS.log(new Ipv4("0.0.0.0"), 1, null, "b", "c", "d"));
		assertNull(MessageSS.log(new Ipv4("0.0.0.0"), 1, MessageSS.LogType.ERROR, null, "c", "d"));
		assertNull(MessageSS.log(new Ipv4("0.0.0.0"), 1, MessageSS.LogType.ERROR, "b", null, "d"));
		assertNull(MessageSS.log(new Ipv4("0.0.0.0"), 1, MessageSS.LogType.ERROR, "b", "c", null));
		assertNull(MessageSS.log(new Ipv4("0.0.0.0"), 1, MessageSS.LogType.ERROR, "", "c", "d"));
		assertNull(MessageSS.log(new Ipv4("0.0.0.0"), 1, MessageSS.LogType.ERROR, "b", "", "d"));
		assertNull(MessageSS.log(new Ipv4("0.0.0.0"), 1, MessageSS.LogType.ERROR, "b", "c", ""));

		String message = MessageSS.log(new Ipv4("0.0.0.0"), 1, MessageSS.LogType.ERROR, "b", "c", "d");
		assertNull(Message.getParam(message, 0));
		assertTrue(Message.getParam(message, MessageSS.SS_LOG_TYPE).equals(MessageSS.LogType.ERROR.toString()));
		assertTrue(Message.getParam(message, MessageSS.SS_LOG_CLASSE).equals("b"));
		assertTrue(Message.getParam(message, MessageSS.SS_LOG_FONCTION).equals("c"));
		assertTrue(Message.getParam(message, MessageSS.SS_LOG_MESSAGE).equals("d"));
		assertNull(Message.getParam(message, MessageSS.SS_LOG_MESSAGE+1));		
	}

}
