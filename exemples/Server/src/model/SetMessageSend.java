package model;

import java.util.ArrayList;

import model.Avatar.Condition;
import model.Avatar.Type;
import model.MessageSR.Turn;
import model.MessageSS.LogType;

public interface SetMessageSend{
	
	public void setCGChatSend(Ipv4 ip, Integer port, String player, String avatar, Condition state, String poster, String message);
	public void setCSConnectSend(Ipv4 ip, Integer port, String player, Float time, String avatar, Type type);
	public void setCSDisconnectSend(Ipv4 ip, Integer port, String player, String avatar, Condition state, String poster);
	public void setCSReconnectSend(Ipv4 ip, Integer port);
	public void setSGChatSend(Ipv4 ip, Integer port, String message);
	public void setSGAddAvatar(Ipv4 ip, Integer port, String a_name, String a_poster, Type type, Condition c);
	public void setSGRemoveAvatar(Ipv4 ip, Integer port, String a_name);
	public void setSPDisconnectSend(Ipv4 ip, Integer port);
	public void setSRAvatarsSend(Ipv4 ip, Integer port, ArrayList<String> avatars);
	public void setSRDeadsSend(Ipv4 ip, Integer port, ArrayList<String> deads);
	public void setSRPlayersSend(Ipv4 ip, Integer port, ArrayList<String> players);
	public void setSRPostsNamesSend(Ipv4 ip, Integer port, ArrayList<String> posts_names);
	public void setSRTypesSend(Ipv4 ip, Integer port, ArrayList<String> types);
	public void setSRTimeSend(Ipv4 ip, Integer port, Float time); 
	public void setSRTurnSend(Ipv4 ip, Integer port, Turn turn);
	public void setSSChatSend(Ipv4 ip, Integer port, String message);
	public void setSSConnectSend(Ipv4 ip, Integer port);
	public void setSSDisconnectSend(Ipv4 ip, Integer port);
	public void setSSLogSend(Ipv4 ip, Integer port, LogType type, String c, String f, String message);
	public void setSSReconnectSend(Ipv4 ip, Integer port, String player, String avatar, Type type, String poster, Condition state);
	
}
