package model.tests;

import java.util.ArrayList;

import model.Avatar.Condition;
import model.Avatar.Type;
import model.Ipv4;
import model.Log;
import model.MessageCG;
import model.MessageCS;
import model.MessageSG;
import model.MessageSP;
import model.MessageSR;
import model.MessageSS;
import model.MessageSR.Turn;
import model.MessageSS.LogType;
import model.exceptions.LogCreationException;
import junit.framework.TestCase;

public class LogTest extends TestCase {

	public void testLog() {
		Ipv4 ip = new Ipv4("0.0.0.0");
		int port = 1;
		String p_name = "player";
		String a_name = "avatar";
		Type a_type = Type.DOCTOR;
		Condition a_state = Condition.DEAD;
		String post_name = "post";
		String message = "message";
		LogType type = LogType.ERROR;
		Turn turn = Turn.Day;
		String classe = "classe";
		String fonction = "fonction";
		ArrayList<String> list = new ArrayList<String>();
		Float time = (float) System.currentTimeMillis();

		String message1 = MessageCG.chat(ip, port, p_name, a_name, a_state,
				post_name, message);
		String message2 = MessageCS.connect(ip, port, p_name, time, a_name,
				a_type);
		String message3 = MessageCS.disconnect(ip, port, p_name, a_name,
				a_state, post_name);
		String message4 = MessageCS.reconnect(ip, port);
		String message5 = MessageSG.chat(ip, port, message);
		String message6 = MessageSP.disconnect(ip, port);
		String message7 = MessageSR.avatars(ip, port, list);
		String message8 = MessageSR.deads(ip, port, list);
		String message9 = MessageSR.players(ip, port, list);
		String message10 = MessageSR.posts_names(ip, port, list);
		String message11 = MessageSR.time(ip, port, time);
		String message12 = MessageSR.turn(ip, port, turn);
		String message13 = MessageSR.types(ip, port, list);
		String message14 = MessageSS.chat(ip, port, message);
		String message15 = MessageSS.connect(ip, port);
		String message16 = MessageSS.disconnect(ip, port);
		String message17 = MessageSS.log(ip, port, type, classe, fonction,
				message);
		String message18 = MessageSS.reconnect(ip, port, p_name, a_name,
				a_type, post_name, a_state);

		Log[] l = new Log[18];

		try {
			l[0] = new Log(message1);
			l[1] = new Log(message2);
			l[2] = new Log(message3);
			l[3] = new Log(message4);
			l[4] = new Log(message5);
			l[5] = new Log(message6);
			l[6] = new Log(message7);
			l[7] = new Log(message8);
			l[8] = new Log(message9);
			l[9] = new Log(message10);
			l[10] = new Log(message11);
			l[11] = new Log(message12);
			l[12] = new Log(message13);
			l[13] = new Log(message14);
			l[14] = new Log(message15);
			l[15] = new Log(message16);
			l[16] = new Log(message17);
			l[17] = new Log(message18);

		} catch (LogCreationException e) {
			assertFalse(true);
		}
		assertNotNull(l[0]);
		assertNotNull(l[1]);
		assertNotNull(l[2]);
		assertNotNull(l[3]);
		assertNotNull(l[4]);
		assertNotNull(l[5]);
		assertNotNull(l[6]);
		assertNotNull(l[7]);
		assertNotNull(l[8]);
		assertNotNull(l[9]);
		assertNotNull(l[10]);
		assertNotNull(l[11]);
		assertNotNull(l[12]);
		assertNotNull(l[13]);
		assertNotNull(l[14]);
		assertNotNull(l[15]);
		assertNotNull(l[16]);
		assertNotNull(l[17]);
	}

}
