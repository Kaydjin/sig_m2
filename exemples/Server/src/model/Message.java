package model;

import java.util.ArrayList;

import utils.ParamCheck;
import utils.RandomUtil;

/**
 * 
 * @author jimmy
 * 
 *         This class use method to extract and build messages.
 */
public class Message {
	
	// The format specific strings:
	private static final int POSITION_IP = 1;
	private static final int POSITION_PORT = 2;
	private static final int POSITION_TYPE = 3;
	private static final int POSITION_PARAMS = 4;


	//Initialize the separators.
	public static String new_separator(){
		char[] values = new char[9];
		for(int i=0; i<3; i++){
			values[i] = RandomUtil.randomDigit();
			values[i+3] = RandomUtil.randomLetter();
			values[i+6] = RandomUtil.randomDigit();
		}
		return String.valueOf(values);
	}
	
	public static String concat(String SEPA, String[] strings){
		if(strings.length == 0)
			return null;
		
		String res = SEPA;
		for(String s : strings){
			if(s != null)
				res = res + s + SEPA;
		}
		return res;
	}
	
	public static String concat(String SEPA, ArrayList<String> list){
		if(list.size()==0)
			return null;
		
		String res = SEPA;
		for(String s : list){
			if(s != null)
				res = res + s + SEPA;
		}
		return res;		
	}
	
	//Return the separator from the beginning of the string.
	private static String recup_separation(String s){
		if(s.length() < 10)
			return null;
		return s.substring(0, 9);
	}
	
	public static String createMessage(Ipv4 ip, Integer port, String type, String params){
		if(!ParamCheck.notNull(new Object[]{ ip, port, type}))
			return null;
		
		if(port < 0)return null;
		
		String sepa_son="";
		String SEPA;
		if(params != null){ //no parameters is possible.
			sepa_son = recup_separation(params);
			if(sepa_son == null) return null;
			//We check that we don't have two time the same separator.
			while((SEPA = new_separator()).equals(sepa_son));
		}else{
			SEPA = new_separator();
		}
		
		String ipstring = ip.toString();
		String portstring = Integer.toString(port);
		
		return concat(SEPA, new String[]{ipstring, portstring, type, params});
	}
	

	/**
	 * Return the sender's ipv4 address.
	 * 
	 * @param message
	 *            a Message format string.
	 * @return the ip from the Message.
	 */
	public static Ipv4 getIpv4(String message) {
		if (message == null)
			return null;

		String SEPA = recup_separation(message);
		if(SEPA == null)return null;
		Ipv4 ip;
		try {
			message = message.split(SEPA)[POSITION_IP];
			ip = new Ipv4(message);
		} catch (ArrayIndexOutOfBoundsException a) {
			return null;
		} catch (IllegalArgumentException e){
			return null;
		}

		return ip;
	}

	/**
	 * Return the sender's port.
	 * 
	 * @param message
	 *            a Message format string.
	 * @return the sender's port
	 */
	public static Integer getPort(String message) {
		if (message == null)
			return null;

		String SEPA = recup_separation(message);
		if(SEPA == null)return null;
		try {
			message = message.split(SEPA)[POSITION_PORT];
		} catch (ArrayIndexOutOfBoundsException a) {
			return null;
		}

		return Integer.parseInt(message);
	}

	/**
	 * Return the sender's type of message
	 * 
	 * @param message
	 *            a Message format string.
	 * @return the type of the message
	 */
	public static String getTypeMessage(String message) {
		if (message == null || message.trim().equals(""))
			return null;

		String SEPA = recup_separation(message);
		if(SEPA == null)return null;
		try {
			message = message.split(SEPA)[POSITION_TYPE];
		} catch (ArrayIndexOutOfBoundsException a) {
			return null;
		}

		return message;
	}
	
	
	/**
	 * Return the parameters of the type of message.
	 * 
	 * @param message
	 *            a Message format string.
	 * @return the message from the Message.
	 */
	private static String getParams(String message) {
		if (message == null)
			return null;

		String SEPA = recup_separation(message);
		if(SEPA == null)return null;
		String res;
		try {
			res = message.split(SEPA)[POSITION_PARAMS];
		} catch (ArrayIndexOutOfBoundsException a) {
			return null;
		}

		if(res.trim().equals(""))return null;
		
		return res;
	}
	
	private static int getTailleTypeMessage(String type){
		if(type.equals(MessageCG.CG_CHAT_NAME))
			return MessageCG.CG_CHAT_NUM_PARAMS;
		if(type.equals(MessageCS.CS_CONNECT_NAME))
			return MessageCS.CS_CONNECT_NUM_PARAMS;
		if(type.equals(MessageCS.CS_DISCONNECT_NAME))
			return MessageCS.CS_DISCONNECT_NUM_PARAMS;
		if(type.equals(MessageCS.CS_RECONNECT_NAME))
			return MessageCS.CS_RECONNECT_NUM_PARAMS;
		if(type.equals(MessageSG.SG_CHAT_NAME))
			return MessageSG.SG_CHAT_NUM_PARAMS;
		if(type.equals(MessageSP.SP_DISCONNECT_NAME))
			return MessageSP.SP_DISCONNECT_NUM_PARAMS;
		if(type.equals(MessageSR.SR_TIME_NAME))
			return MessageSR.SR_TIME_NUM_PARAMS;
		if(type.equals(MessageSR.SR_TURN_NAME))
			return MessageSR.SR_TURN_NUM_PARAMS;
		if(type.equals(MessageSS.SS_CHAT_NAME))
			return MessageSS.SS_CHAT_NUM_PARAMS;
		if(type.equals(MessageSS.SS_CONNECT_NAME))
			return MessageSS.SS_CONNECT_NUM_PARAMS;
		if(type.equals(MessageSS.SS_DISCONNECT_NAME))
			return MessageSS.SS_DISCONNECT_NUM_PARAMS;
		if(type.equals(MessageSS.SS_RECONNECT_NAME))
			return MessageSS.SS_RECONNECT_NUM_PARAMS;
		if(type.equals(MessageSS.SS_LOG_NAME))
			return MessageSS.SS_LOG_NUM_PARAMS;
		if(type.equals(MessageSG.SG_ADD_AVATAR_NAME))
			return MessageSG.SG_ADD_AVATAR_NUM_PARAMS;
		if(type.equals(MessageSG.SG_REMOVE_AVATAR_NAME))
			return MessageSG.SG_REMOVE_AVATAR_NUM_PARAMS;
		return 0;
	}
	
	private static boolean is_parameter(String message, Integer pos){
		if(pos == null || pos <= 0)
			return false;
		
		String type = getTypeMessage(message);
		
		if(type == null) return false;
		
		Integer taille = getTailleTypeMessage(type);
		
		if(pos <= taille)
			return true;
		
		return false;
	}
	
	/**
	 * 
	 */
	public static String getParam(String message, Integer position){
		if(position == null || !is_parameter(message, position))
			return null;
		
		String params = getParams(message);
		
		String SEPA = recup_separation(params);
		if(SEPA == null)return null;
		String res;
		try {
			res = params.split(SEPA)[position];
		} catch (ArrayIndexOutOfBoundsException a) {
			return null;
		}

		return res;
	}
	
	public static boolean isMessage(String message){
		if(Message.getIpv4(message)==null
				|| Message.getPort(message)==null
				|| Message.getTypeMessage(message)==null)
			return false;
		
		if(!isNotTypeListeParam(message)&&!isTypeListeParam(message))
			return false;
		
		if(isNotTypeListeParam(message)){
			int taille = Message.getTailleTypeMessage(Message.getTypeMessage(message));
			
			String param;
			for(int i=1; i<=taille; i++){
				param = Message.getParam(message, i);
				if(param == null)return false;
			}
		}
			
		return true;
	}
	
	public static boolean isNotTypeListeParam(String message){
		String type = getTypeMessage(message);
		if(type == null) return false;
		
		if(type.equals(MessageCG.CG_CHAT_NAME)
				|| type.equals(MessageCS.CS_CONNECT_NAME)
				|| type.equals(MessageCS.CS_DISCONNECT_NAME)
				|| type.equals(MessageCS.CS_RECONNECT_NAME)
				|| type.equals(MessageSG.SG_CHAT_NAME)
				|| type.equals(MessageSP.SP_DISCONNECT_NAME)
				|| type.equals(MessageSR.SR_TIME_NAME)
				|| type.equals(MessageSR.SR_TURN_NAME)
				|| type.equals(MessageSS.SS_CHAT_NAME)
				|| type.equals(MessageSS.SS_DISCONNECT_NAME)
				|| type.equals(MessageSS.SS_LOG_NAME)
				|| type.equals(MessageSS.SS_RECONNECT_NAME)
				|| type.equals(MessageSS.SS_CONNECT_NAME)
				|| type.equals(MessageSG.SG_ADD_AVATAR_NAME)
				|| type.equals(MessageSG.SG_REMOVE_AVATAR_NAME))
			return true;
		
		return false;			
	}
	
	public static boolean isTypeListeParam(String message){
		String type = getTypeMessage(message);
		if(type == null) return false;
		
		if(type.equals(MessageSR.SR_AVATARS_NAME)
				|| type.equals(MessageSR.SR_DEADS_NAME)
				|| type.equals(MessageSR.SR_PLAYERS_NAME)
				|| type.equals(MessageSR.SR_TYPES_NAME)
				|| type.equals(MessageSR.SR_POSTS_NAMES_NAME))
			return true;
		
		return false;
	}
	
	public static ArrayList<String> getListeParam(String message){
		if(message == null || !isTypeListeParam(message))
			return null;
		
		String params = getParams(message);
		
		if(params == null || params.trim().equals(""))return null;

		ArrayList<String> res = new ArrayList<String>();
		String SEPA = recup_separation(params);
		if(SEPA == null)return null;
		try {
			int taille = params.split(SEPA).length;
			for(int i=1; i<taille; i++){
				res.add(params.split(SEPA)[i]);
			}
		} catch (ArrayIndexOutOfBoundsException a) {
			return null;
		}
		return res;
	}

}
