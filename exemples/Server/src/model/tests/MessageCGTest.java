package model.tests;

import model.Avatar.Condition;
import model.Ipv4;
import model.Message;
import model.MessageCG;
import junit.framework.TestCase;

public class MessageCGTest extends TestCase {

	public void testChat() {
		Condition c = Condition.ALIVE;
		//good message;
		assertNotNull(MessageCG.chat(new Ipv4("0.0.0.0"), 1, "a", "b", c, "post", "m"));
		assertNull(MessageCG.chat(null, 1, "a", "b", c, "post", "m"));
		assertNull(MessageCG.chat(new Ipv4("0.0.0.0"), -2, "a", "b", c, "post", "m"));
		assertNull(MessageCG.chat(new Ipv4("0.0.0.0"), 1, null, "b", c, "post", "m"));
		assertNull(MessageCG.chat(new Ipv4("0.0.0.0"), 1, "a", null, c, "post", "m"));
		assertNull(MessageCG.chat(new Ipv4("0.0.0.0"), 1, "a", "b", null, "post", "m"));
		assertNull(MessageCG.chat(new Ipv4("0.0.0.0"), 1, "a", "b", c, null, "m"));
		assertNull(MessageCG.chat(new Ipv4("0.0.0.0"), 1, "a", "b", c, "post", null));
		assertNull(MessageCG.chat(new Ipv4("0.0.0.0"), 1, "", "b", c, "post", "m"));
		assertNull(MessageCG.chat(new Ipv4("0.0.0.0"), 1, "a", "", c, "post", "m"));
		assertNull(MessageCG.chat(new Ipv4("0.0.0.0"), 1, "a", "b", c, "", "m"));
		assertNull(MessageCG.chat(new Ipv4("0.0.0.0"), 1, "a", "b", c, "post", ""));
		
		String message = MessageCG.chat(new Ipv4("0.0.0.0"), 1, "a", "b", c, "post", "m");
		assertNull(Message.getParam(message, 0));
		assertNotNull(Message.getParam(message, MessageCG.CG_CHAT_AVATAR_NAME));
		assertNotNull(Message.getParam(message, MessageCG.CG_CHAT_AVATAR_STATE));
		assertNotNull(Message.getParam(message, MessageCG.CG_CHAT_MESSAGE));
		assertNotNull(Message.getParam(message, MessageCG.CG_CHAT_PLAYER_NAME));
		assertNotNull(Message.getParam(message, MessageCG.CG_CHAT_POST_NAME));
		assertNull(Message.getParam(message, MessageCG.CG_CHAT_MESSAGE+1));
	}

}
