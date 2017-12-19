package model;

import model.Avatar.Condition;
import model.Avatar.Type;
import utils.ParamCheck;

public class MessageCS {

	// Only here to show the possible type of message.
	public enum ClientService {
		DISCONNECT, CONNECT, RECONNECT
	};

	// The disconnect message format for a client.
	public static final String CS_DISCONNECT_NAME = "cs_disconnect";
	public static final int CS_DISCONNECT_NUM_PARAMS = 4;
	public static final int CS_DISCONNECT_PLAYER_NAME = 1;
	public static final int CS_DISCONNECT_AVATAR_NAME = 2;
	public static final int CS_DISCONNECT_AVATAR_STATE = 3;
	public static final int CS_DISCONNECT_POST_NAME = 4;

	public static String disconnect(Ipv4 ip, Integer port, String p_name,
			String a_name, Condition a_state, String post_name) {
		if (!ParamCheck.notNull(new Object[] { ip, port, p_name, a_name,
				a_state, post_name }))
			return null;
		if (!ParamCheck.notEmpty(new String[] { p_name, a_name, post_name }))
			return null;

		String SEPA = Message.new_separator();
		String elem_son = Message.concat(SEPA, new String[] { p_name, a_name,
				a_state.toString(), post_name });
		String elem_father = Message.createMessage(ip, port,
				CS_DISCONNECT_NAME, elem_son);
		return elem_father;

	}

	// The connect message format for a client.
	public static final String CS_CONNECT_NAME = "cs_connect";
	public static final int CS_CONNECT_NUM_PARAMS = 4;
	public static final int CS_CONNECT_PLAYER_NAME = 1;
	public static final int CS_CONNECT_PLAYER_TIME_BLOCK = 2;
	public static final int CS_CONNECT_AVATAR_NAME = 3;
	public static final int CS_CONNECT_AVATAR_TYPE = 4;

	public static String connect(Ipv4 ip, Integer port, String p_name,
			Float time, String a_name, Type a_type) {
		if (!ParamCheck.notNull(new Object[] { ip, port, p_name, time, a_name,
				a_type }))
			return null;
		if (!ParamCheck.notEmpty(new String[] { p_name, a_name }))
			return null;
		if(time < 0)
			return null;

		String SEPA = Message.new_separator();
		String stime = Float.toString(time);
		String elem_son = Message.concat(SEPA, new String[] { p_name, stime,
				a_name, a_type.toString() });
		String elem_father = Message.createMessage(ip, port, CS_CONNECT_NAME,
				elem_son);
		return elem_father;

	}

	// The parameters for a reconnect message from the client doesn't exist.
	public static final String CS_RECONNECT_NAME = "cs_reconnect";
	public static final int CS_RECONNECT_NUM_PARAMS = 0;

	public static String reconnect(Ipv4 ip, Integer port) {
		if (!ParamCheck.notNull(new Object[] { ip, port }))
			return null;

		String elem_father = Message.createMessage(ip, port, CS_RECONNECT_NAME, null);
		return elem_father;
	}
}
