package model.tests;

import model.Avatar.Type;
import model.Ipv4;
import model.Message;
import model.MessageCG;
import model.MessageCS;
import model.Avatar.Condition;
import junit.framework.TestCase;

public class MessageCSTest extends TestCase {

	public void testDisconnect() {
		Condition c = Condition.ALIVE;
		//good message;
		assertNotNull(MessageCS.disconnect(new Ipv4("0.0.0.0"), 1, "a", "b", c, "post"));
		assertNull(MessageCS.disconnect(null, 1, "a", "b", c, "post"));
		assertNull(MessageCS.disconnect(new Ipv4("0.0.0.0"), -2, "a", "b", c, "post"));
		assertNull(MessageCS.disconnect(new Ipv4("0.0.0.0"), 1, null, "b", c, "post"));
		assertNull(MessageCS.disconnect(new Ipv4("0.0.0.0"), 1, "a", null, c, "post"));
		assertNull(MessageCS.disconnect(new Ipv4("0.0.0.0"), 1, "a", "b", null, "post"));
		assertNull(MessageCS.disconnect(new Ipv4("0.0.0.0"), 1, "a", "b", c, null));
		assertNull(MessageCS.disconnect(new Ipv4("0.0.0.0"), 1, "", "b", c, "post"));
		assertNull(MessageCS.disconnect(new Ipv4("0.0.0.0"), 1, "a", "", c, "post"));
		assertNull(MessageCS.disconnect(new Ipv4("0.0.0.0"), 1, "a", "b", c, ""));
		
		String message = MessageCS.disconnect(new Ipv4("0.0.0.0"), 1, "a", "b", c, "post");
		assertNull(Message.getParam(message, 0));
		assertTrue(Message.getParam(message, MessageCG.CG_CHAT_AVATAR_NAME).equals("b"));
		assertTrue(Message.getParam(message, MessageCG.CG_CHAT_AVATAR_STATE).equals(c.toString()));
		assertTrue(Message.getParam(message, MessageCG.CG_CHAT_PLAYER_NAME).equals("a"));
		assertTrue(Message.getParam(message, MessageCG.CG_CHAT_POST_NAME).equals("post"));
		assertNull(Message.getParam(message, MessageCG.CG_CHAT_POST_NAME+1));
	}


	public void testConnect() {
		Type t = Type.DOCTOR;
		//good message;
		assertNotNull(MessageCS.connect(new Ipv4("0.0.0.0"), 1, "a", 2f, "name", t));
		assertNull(MessageCS.connect(null, 1, "a", 2f, "name", t));
		assertNull(MessageCS.connect(new Ipv4("0.0.0.0"), -2, "a", 2f, "name", t));
		assertNull(MessageCS.connect(new Ipv4("0.0.0.0"), 1, null, 2f, "name", t));
		assertNull(MessageCS.connect(new Ipv4("0.0.0.0"), 1, "a", null, "name", t));
		assertNull(MessageCS.connect(new Ipv4("0.0.0.0"), 1, "a", 2f, null, t));
		assertNull(MessageCS.connect(new Ipv4("0.0.0.0"), 1, "a", 2f, "name", null));
		assertNull(MessageCS.connect(new Ipv4("0.0.0.0"), 1, "", 2f, "name", t));
		assertNull(MessageCS.connect(new Ipv4("0.0.0.0"), 1, "a", -2f, "name", t));
		assertNull(MessageCS.connect(new Ipv4("0.0.0.0"), 1, "a", 2f, "", t));
		
		String message = MessageCS.connect(new Ipv4("0.0.0.0"), 1, "a", 2f, "name", t);
		assertNull(Message.getParam(message, 0));
		assertTrue(Message.getParam(message, MessageCS.CS_CONNECT_AVATAR_NAME).equals("name"));
		assertTrue(Message.getParam(message, MessageCS.CS_CONNECT_AVATAR_TYPE).equals(t.toString()));
		assertTrue(Message.getParam(message, MessageCS.CS_CONNECT_PLAYER_NAME).equals("a"));
		assertTrue(Float.parseFloat(Message.getParam(message, MessageCS.CS_CONNECT_PLAYER_TIME_BLOCK))==2f);
		assertNull(Message.getParam(message, MessageCS.CS_CONNECT_AVATAR_TYPE+1));
	}

	public void testReconnect() {
		String message = MessageCS.reconnect(new Ipv4("0.0.0.0"), 1);	
		
		assertNotNull(message);
		assertNull(MessageCS.reconnect(null, 1));
		assertNull(MessageCS.reconnect(null, -1));
		assertNull(MessageCS.reconnect(new Ipv4("0.0.0.0"), null));

		
	}

}
