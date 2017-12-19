package model;

public interface OnMessageReceive {

	public void onCGChatReceive();
	public void onCSConnectReceive();
	public void onCSDisconnectReceive();
	public void onCSReconnectReceive();
	public void onSGChatReceive();
	public void onSGAddAvatar();
	public void onSGRemoveAvatar();
	public void onSPDisconnectReceive();
	public void onSRAvatarsReceive();
	public void onSRDeadsReceive();
	public void onSRPlayersReceive();
	public void onSRPostsNamesReceive();
	public void onSRTypesReceive();
	public void onSRTimeReceive();
	public void onSRTurnReceive();
	public void onSSChatReceive();
	public void onSSConnectReceive();
	public void onSSDisconnectReceive();
	public void onSSLogReceive();
	public void onSSReconnectReceive();
	
}
