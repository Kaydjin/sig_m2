package model;

import java.util.ArrayList;

public class MessageSR {

	//Only here to show the possible type of message.
	public enum ServerRappel{ TIME, TOUR, MORTS, TYPES, PLAYERS, AVATARS, POSTS_NAMES }

	// The time recall message format for a server.
	public static final String SR_TIME_NAME = "sr_time";
	public static final int SR_TIME_NUM_PARAMS = 1;
	public static final int SR_TIME = 1;
	
	public static String time(Ipv4 ip, Integer port, Float time) {
		if (ip == null || port == null || time == null)
			return null;
		
		if(time < 0) return null;

		String SEPA = Message.new_separator();
		String stime = Float.toString(time);
		String elem_son = Message.concat(SEPA, new String[] { stime });
		String elem_father = Message.createMessage(ip, port,
				SR_TIME_NAME, elem_son);
		return elem_father;

	}
	
	// The tour recall message format for a server.
	public static final String SR_TURN_NAME = "sr_turn";
	public enum Turn { Day, Night};
	public static final int SR_TURN_NUM_PARAMS = 1;
	public static final int SR_TURN = 1;
	
	public static String turn(Ipv4 ip, Integer port, Turn turn) {
		if (ip == null || port == null || turn == null)
			return null;

		String SEPA = Message.new_separator();
		String elem_son = Message.concat(SEPA, new String[] { turn.toString() });
		String elem_father = Message.createMessage(ip, port,
				SR_TURN_NAME, elem_son);
		return elem_father;

	}
	
	// The dead recall message format for a server.
	public static final String SR_DEADS_NAME = "sr_deads";
	
	public static String deads(Ipv4 ip, Integer port, ArrayList<String> names_deads) {
		if (ip == null || port == null)
			return null;

		String elem_son=null;
		if(names_deads != null && names_deads.size()>0){
			String SEPA = Message.new_separator();
			elem_son = Message.concat(SEPA, names_deads);
		}
		
		String elem_father = Message.createMessage(ip, port,
				SR_DEADS_NAME, elem_son);
		return elem_father;

	}
	
	// The types recall message format for a server.
	public static final String SR_TYPES_NAME = "sr_types";
	
	public static String types(Ipv4 ip, Integer port, ArrayList<String> types) {
		if (ip == null || port == null || types == null)
			return null;

		String elem_son=null;
		if(types.size()>0){
			String SEPA = Message.new_separator();
			elem_son = Message.concat(SEPA, types);
		}
		String elem_father = Message.createMessage(ip, port,
				SR_TYPES_NAME, elem_son);
		return elem_father;

	}
	
	// The players recall message format for a server.
	public static final String SR_PLAYERS_NAME = "sr_players";

	public static String players(Ipv4 ip, Integer port, ArrayList<String> players) {
		if (ip == null || port == null || players == null)
			return null;

		String elem_son=null;
		if(players.size()>0){
			String SEPA = Message.new_separator();
			elem_son = Message.concat(SEPA, players);
		}
		String elem_father = Message.createMessage(ip, port,
				SR_PLAYERS_NAME, elem_son);
		return elem_father;

	}
	
	// The avatars recall message format for a server.
	public static final String SR_AVATARS_NAME = "sr_avatars";

	public static String avatars(Ipv4 ip, Integer port, ArrayList<String> avatars) {
		if (ip == null || port == null || avatars == null)
			return null;

		String elem_son=null;
		if(avatars.size()>0){
			String SEPA = Message.new_separator();
			elem_son = Message.concat(SEPA, avatars);
		}
		String elem_father = Message.createMessage(ip, port,
				SR_AVATARS_NAME, elem_son);
		return elem_father;

	}
	
	// The posts_names recall message format for a server.
	public static final String SR_POSTS_NAMES_NAME = "sr_posts_names";

	public static String posts_names(Ipv4 ip, Integer port, ArrayList<String> posts_names) {
		if (ip == null || port == null || posts_names == null)
			return null;

		String elem_son=null;
		if(posts_names.size()>0){
			String SEPA = Message.new_separator();
			elem_son = Message.concat(SEPA, posts_names);
		}
		String elem_father = Message.createMessage(ip, port,
				SR_POSTS_NAMES_NAME, elem_son);
		return elem_father;

	}
	
}
