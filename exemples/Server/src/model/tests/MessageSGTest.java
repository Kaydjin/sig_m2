package model.tests;

import model.Avatar.Condition;
import model.Avatar.Type;
import model.Ipv4;
import model.Message;
import model.MessageSG;
import junit.framework.TestCase;

public class MessageSGTest extends TestCase {

	public void testChat() {
		String message = MessageSG.chat(new Ipv4("0.0.0.0"), 1, "message");	
		
		assertNotNull(message);
		assertNull(MessageSG.chat(null, 1, "message"));
		assertNull(MessageSG.chat(null, -1, "message"));
		assertNull(MessageSG.chat(new Ipv4("0.0.0.0"), null, "message"));
		assertNull(MessageSG.chat(new Ipv4("0.0.0.0"), 1, null));
		
		assertNull(Message.getParam(message, 0));
		assertTrue(Message.getParam(message, MessageSG.SG_CHAT_MESSAGE).equals("message"));
		assertNull(Message.getParam(message, MessageSG.SG_CHAT_MESSAGE+1));
	}

	
	public void testAddAvatar() {
		String message = MessageSG.addAvatar(new Ipv4("0.0.0.0"), 1, "name", "name", Type.DOCTOR, Condition.ALIVE);
		assertNotNull(message);
		
		assertNull(Message.getParam(message, 0));
		assertTrue(Message.getParam(message, MessageSG.SG_ADD_AVATAR_AVATAR_NAME).equals("name"));
		assertTrue(Message.getParam(message, MessageSG.SG_ADD_AVATAR_POST_NAME).equals("name"));
		assertEquals(Message.getParam(message, MessageSG.SG_ADD_AVATAR_TYPE), Type.DOCTOR.toString());
		assertEquals(Message.getParam(message, MessageSG.SG_ADD_AVATAR_STATE), Condition.ALIVE.toString());
		assertNull(Message.getParam(message, MessageSG.SG_ADD_AVATAR_STATE+1));
	}

	public void testRemoveAvatar() {
		String message = MessageSG.removeAvatar(new Ipv4("0.0.0.0"), 1, "name");	
		
		assertNotNull(message);
		assertNull(MessageSG.chat(null, 1, "name"));
		assertNull(MessageSG.chat(null, -1, "name"));
		assertNull(MessageSG.chat(new Ipv4("0.0.0.0"), null, "name"));
		assertNull(MessageSG.chat(new Ipv4("0.0.0.0"), 1, null));
		
		assertNull(Message.getParam(message, 0));
		assertTrue(Message.getParam(message, MessageSG.SG_REMOVE_AVATAR_AVATAR_NAME).equals("name"));
		assertNull(Message.getParam(message, MessageSG.SG_REMOVE_AVATAR_AVATAR_NAME+1));
	}
	
}
