package model;

import java.util.ArrayList;

import model.Avatar.Condition;
import model.Avatar.Type;
import model.MessageSR.Turn;
import model.MessageSS.LogType;
import model.exceptions.MessageHandlerCreationException;

public class MessageManager implements OnMessageReceive, SetMessageSend{
protected String message;

	public MessageManager() {
		message = null;
	}

	public void setMessage(String message) throws MessageHandlerCreationException{
		if(!Message.isMessage(message))
			throw new MessageHandlerCreationException();
		this.message = message;
	}
			
	public void receive_result(){
		if(message == null)
			return;
		
		if(Message.getTypeMessage(message).equals(MessageCG.CG_CHAT_NAME))
			onCGChatReceive();
		if(Message.getTypeMessage(message).equals(MessageCS.CS_CONNECT_NAME))
			onCSConnectReceive();
		if(Message.getTypeMessage(message).equals(MessageCS.CS_DISCONNECT_NAME))
			onCSDisconnectReceive();
		if(Message.getTypeMessage(message).equals(MessageCS.CS_RECONNECT_NAME))
			onCSReconnectReceive();
		if(Message.getTypeMessage(message).equals(MessageSG.SG_CHAT_NAME))
			onSGChatReceive();
		if(Message.getTypeMessage(message).equals(MessageSP.SP_DISCONNECT_NAME))
			onSPDisconnectReceive();
		if(Message.getTypeMessage(message).equals(MessageSR.SR_AVATARS_NAME))
			onSRAvatarsReceive();
		if(Message.getTypeMessage(message).equals(MessageSR.SR_DEADS_NAME))
			onSRDeadsReceive();
		if(Message.getTypeMessage(message).equals(MessageSR.SR_PLAYERS_NAME))
			onSRPlayersReceive();
		if(Message.getTypeMessage(message).equals(MessageSR.SR_POSTS_NAMES_NAME))
			onSRPostsNamesReceive();
		if(Message.getTypeMessage(message).equals(MessageSR.SR_TIME_NAME))
			onSRTimeReceive();
		if(Message.getTypeMessage(message).equals(MessageSR.SR_TURN_NAME))
			onSRTurnReceive();
		if(Message.getTypeMessage(message).equals(MessageSR.SR_TYPES_NAME))
			onSRTypesReceive();
		if(Message.getTypeMessage(message).equals(MessageSS.SS_CHAT_NAME))
			onSSChatReceive();
		if(Message.getTypeMessage(message).equals(MessageSS.SS_CONNECT_NAME))
			onSSConnectReceive();
		if(Message.getTypeMessage(message).equals(MessageSS.SS_DISCONNECT_NAME))
			onSSDisconnectReceive();
		if(Message.getTypeMessage(message).equals(MessageSS.SS_LOG_NAME))
			onSSLogReceive();
		if(Message.getTypeMessage(message).equals(MessageSS.SS_RECONNECT_NAME))
			onSSReconnectReceive();
		
	}

	@Override
	public void onCGChatReceive() {}

	@Override
	public void onCSConnectReceive() {}

	@Override
	public void onCSDisconnectReceive() {}

	@Override
	public void onCSReconnectReceive() {}

	@Override
	public void onSGChatReceive() {}

	@Override
	public void onSPDisconnectReceive() {}

	@Override
	public void onSRAvatarsReceive() {}

	@Override
	public void onSRDeadsReceive() {}

	@Override
	public void onSRPlayersReceive() {}

	@Override
	public void onSRPostsNamesReceive() {}

	@Override
	public void onSRTimeReceive() {}

	@Override
	public void onSRTurnReceive() {}

	@Override
	public void onSSChatReceive() {}

	@Override
	public void onSSConnectReceive() {}

	@Override
	public void onSSDisconnectReceive() {}

	@Override
	public void onSSLogReceive() {}

	@Override
	public void onSSReconnectReceive() {}

	@Override
	public void onSRTypesReceive() {}

	@Override
	public void onSGAddAvatar() { }

	@Override
	public void onSGRemoveAvatar() { }
	
	@Override
	public void setCGChatSend(Ipv4 ip, Integer port, String player,
			String avatar, Condition state, String poster, String message) {}

	@Override
	public void setCSConnectSend(Ipv4 ip, Integer port, String player,
			Float time, String avatar, Type type) {}

	@Override
	public void setCSDisconnectSend(Ipv4 ip, Integer port, String player,
			String avatar, Condition state, String poster) {}

	@Override
	public void setCSReconnectSend(Ipv4 ip, Integer port) {}

	@Override
	public void setSGChatSend(Ipv4 ip, Integer port, String message) {}

	@Override
	public void setSPDisconnectSend(Ipv4 ip, Integer port) {}

	@Override
	public void setSRAvatarsSend(Ipv4 ip, Integer port,
			ArrayList<String> avatars) {}

	@Override
	public void setSRDeadsSend(Ipv4 ip, Integer port, ArrayList<String> deads) {}

	@Override
	public void setSRPlayersSend(Ipv4 ip, Integer port,
			ArrayList<String> players) {}

	@Override
	public void setSRPostsNamesSend(Ipv4 ip, Integer port,
			ArrayList<String> posts_names) {}

	@Override
	public void setSRTypesSend(Ipv4 ip, Integer port, ArrayList<String> types) {}

	@Override
	public void setSRTimeSend(Ipv4 ip, Integer port, Float time) {}

	@Override
	public void setSRTurnSend(Ipv4 ip, Integer port, Turn turn) {}

	@Override
	public void setSSChatSend(Ipv4 ip, Integer port, String message) {}

	@Override
	public void setSSConnectSend(Ipv4 ip, Integer port) {}

	@Override
	public void setSSDisconnectSend(Ipv4 ip, Integer port) {}

	@Override
	public void setSSLogSend(Ipv4 ip, Integer port, LogType type, String c,
			String f, String message) {}

	@Override
	public void setSSReconnectSend(Ipv4 ip, Integer port, String player,
			String avatar, Type type, String poster, Condition state) {}

	@Override
	public void setSGAddAvatar(Ipv4 ip, Integer port, String a_name,
			String a_poster, Type type, Condition c) {}

	@Override
	public void setSGRemoveAvatar(Ipv4 ip, Integer port, String a_name) {}

}