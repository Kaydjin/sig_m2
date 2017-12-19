package model;

import model.Avatar.Condition;
import model.Avatar.Type;
import utils.ParamCheck;

public class MessageSG {

	//Only here to show the possible type of message.
	public enum ServerGame{ CHAT, ADD_AVATAR, REMOVE_AVATAR };
	
	// The chat game message format for a server.
	public static final String SG_CHAT_NAME = "sg_chat";
	public static final int SG_CHAT_NUM_PARAMS = 1;
	public static final int SG_CHAT_MESSAGE = 1;
	
	public static String chat(Ipv4 ip, Integer port, String message) {
		if(!ParamCheck.notNull(new Object[]{ ip, port, message}))
			return null;
		if(message.trim().equals("")) return null;
		
		String SEPA = Message.new_separator();
		String elem_son = Message.concat(SEPA, new String[] { message });
		String elem_father = Message.createMessage(ip, port,
				SG_CHAT_NAME, elem_son);
		return elem_father;
	
	}
	
	// The add avatar messsage format for a server.
	public static final String SG_ADD_AVATAR_NAME = "sg_add_avatar";
	public static final int SG_ADD_AVATAR_NUM_PARAMS = 4;
	public static final int SG_ADD_AVATAR_AVATAR_NAME = 1;
	public static final int SG_ADD_AVATAR_POST_NAME = 2;
	public static final int SG_ADD_AVATAR_TYPE = 3;
	public static final int SG_ADD_AVATAR_STATE = 4;
	
	public static String addAvatar(Ipv4 ip, Integer port, String a_n, String a_p, Type type, Condition c){
		if(!ParamCheck.notNull(new Object[]{ ip, port, a_n, a_p, type, c}))
			return null;
		if(!ParamCheck.notEmpty(new String[]{ a_n, a_p }))
			return null;
		
		String SEPA = Message.new_separator();
		String elem_son = Message.concat(SEPA, new String[] { a_n, a_p, type.toString(), c.toString() });
		String elem_father = Message.createMessage(ip, port,
				SG_ADD_AVATAR_NAME, elem_son);
		return elem_father;
	}
	
	// The remove avatar message format for a server.
	public static final String SG_REMOVE_AVATAR_NAME = "sg_remove_avatar";
	public static final int SG_REMOVE_AVATAR_NUM_PARAMS = 1;
	public static final int SG_REMOVE_AVATAR_AVATAR_NAME = 1;
	
	public static String removeAvatar(Ipv4 ip, Integer port, String p){
		if(!ParamCheck.notNull(new Object[]{ ip, port, p }))
			return null;
		if(p.trim().equals(""))
			return null;
		
		String SEPA = Message.new_separator();
		String elem_son = Message.concat(SEPA, new String[] { p });
		String elem_father = Message.createMessage(ip, port,
				SG_REMOVE_AVATAR_NAME, elem_son);
		return elem_father;	
		
	}
}
