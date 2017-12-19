package model;

import model.Avatar.Condition;
import utils.ParamCheck;

public class MessageCG {
	
	//Only here to show the possible type of message.
	public enum ClientGame{ CHAT };
	
	// The chat message format for a client.
	public static final String CG_CHAT_NAME = "cg_chat";
	public static final int CG_CHAT_NUM_PARAMS = 5;
	public static final int CG_CHAT_PLAYER_NAME = 1;
	public static final int CG_CHAT_AVATAR_NAME = 2;
	public static final int CG_CHAT_AVATAR_STATE = 3;
	public static final int CG_CHAT_POST_NAME = 4;
	public static final int CG_CHAT_MESSAGE = 5;
	
	public static String chat(Ipv4 ip, Integer port, String p_name,
			String a_name, Condition a_state, String post_name, String message) {
		
		if (!ParamCheck.notNull(new Object[]{ip, port, p_name, a_name, a_state, post_name, message}))
			return null;
		if (!ParamCheck.notEmpty(new String[]{a_name, post_name, p_name, message}))
			return null;

		String SEPA = Message.new_separator();
		String elem_son = Message.concat(SEPA, new String[] { p_name, a_name,
				a_state.toString(), post_name, message});
		String elem_father = Message.createMessage(ip, port,
				CG_CHAT_NAME, elem_son);
		
		return elem_father;

	}
	
}
