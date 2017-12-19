package server.tests;


public class Main2 {

	public static void main(String args[]) {
		
		for(int i=0; i<1000; i++){
			new TCPClient(new TCPClient.OnMessageReceived() {
				@Override
				// here the messageReceived method is implemented
				public void messageReceived(String message) {
				}
			}, i).start();
		}
	}
}