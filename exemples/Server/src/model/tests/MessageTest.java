package model.tests;

import java.util.ArrayList;

import model.Ipv4;
import model.Message;
import model.MessageSR;
import junit.framework.TestCase;

public class MessageTest extends TestCase {

	public void testNew_separator() {
		String SEPA = Message.new_separator();
		assertTrue(SEPA.length()==9);
	}

	public void testConcatStringStringArray() {
		String strings[] = { "A", "B", "C" };
		String SEPA = Message.new_separator();
		String res = Message.concat(SEPA, strings);
		
		String param="";
		try {
			param = res.split(SEPA)[1];
		} catch (ArrayIndexOutOfBoundsException a) {
			assertFalse(true);
		}
		assertTrue(param.equals(strings[0]));
		
		try {
			param = res.split(SEPA)[2];
		} catch (ArrayIndexOutOfBoundsException a) {
			assertFalse(true);
		}
		assertTrue(param.equals(strings[1]));

		try {
			param = res.split(SEPA)[3];
		} catch (ArrayIndexOutOfBoundsException a) {
			assertFalse(true);
		}
		assertTrue(param.equals(strings[2]));
		
	}

	public void testConcatStringArrayListOfString() {
		ArrayList<String> array = new ArrayList<String>();
		array.add("A");
		array.add("B");
		array.add("C");
		String SEPA = Message.new_separator();
		String res = Message.concat(SEPA, array);

		String param="";
		try {
			param = res.split(SEPA)[1];
		} catch (ArrayIndexOutOfBoundsException a) {
			assertFalse(true);
		}
		assertTrue(param.equals(array.get(0)));
		
		try {
			param = res.split(SEPA)[2];
		} catch (ArrayIndexOutOfBoundsException a) {
			assertFalse(true);
		}
		assertTrue(param.equals(array.get(1)));

		try {
			param = res.split(SEPA)[3];
		} catch (ArrayIndexOutOfBoundsException a) {
			assertFalse(true);
		}
		assertTrue(param.equals(array.get(2)));
	}

	public void testCreateMessage() {
		String mess = null;
		String sepa = Message.new_separator();
		String params = Message.concat(sepa, new String[]{ "1" });
		
		//A good build.
		try{
			mess = Message.createMessage(new Ipv4("0.0.0.0"), 5, MessageSR.SR_TIME_NAME, params);
		}catch(IllegalArgumentException e){
			assertTrue(false);
		}
		assertTrue(mess != null);
		
		//Message creation without a separator on the parameters.
		mess = Message.createMessage(new Ipv4("0.0.0.0"), 5, MessageSR.SR_TIME_NAME, "1");
		assertTrue(mess == null);
		
		//Bad ip
		mess = Message.createMessage(null, 5, MessageSR.SR_TIME_NAME, "1");
		assertTrue(mess == null);
		
		//Bad port.
		mess = Message.createMessage(new Ipv4("0.0.0.0"), -2, MessageSR.SR_TIME_NAME, params);
		assertTrue(mess == null);
		
	}

	public void testGetIpv4() {
		String mess = null;
		String sepa = Message.new_separator();
		String params = Message.concat(sepa, new String[]{ "1" });
		
		//A good build.
		try{
			mess = Message.createMessage(new Ipv4("0.0.0.0"), 5, MessageSR.SR_TIME_NAME, params);
		}catch(IllegalArgumentException e){
			assertTrue(false);
		}
		assertTrue(mess != null);
		
		assertTrue(Message.getIpv4(mess).toString().equals(new Ipv4("0.0.0.0").toString()));
	}

	public void testGetPort() {
		String mess = null;
		String sepa = Message.new_separator();
		String params = Message.concat(sepa, new String[]{ "1" });
		
		//A good build.
		try{
			mess = Message.createMessage(new Ipv4("0.0.0.0"), 5, MessageSR.SR_TIME_NAME, params);
		}catch(IllegalArgumentException e){
			assertTrue(false);
		}
		assertTrue(mess != null);
		
		assertTrue(Message.getPort(mess)==5);
	}

	public void testGetTypeMessage() {
		String mess = null;
		String sepa = Message.new_separator();
		String params = Message.concat(sepa, new String[]{ "1" });
		
		//A good build.
		try{
			mess = Message.createMessage(new Ipv4("0.0.0.0"), 5, MessageSR.SR_TIME_NAME, params);
		}catch(IllegalArgumentException e){
			assertTrue(false);
		}
		assertTrue(mess != null);
		
		assertTrue(Message.getTypeMessage(mess).equals(MessageSR.SR_TIME_NAME));
	}

	public void testGetParam() {
		String mess = null;
		String sepa = Message.new_separator();
		String params = Message.concat(sepa, new String[]{ "1" });
		
		//A good build.
		try{
			mess = Message.createMessage(new Ipv4("0.0.0.0"), 5, MessageSR.SR_TIME_NAME, params);
		}catch(IllegalArgumentException e){
			assertTrue(false);
		}
		assertTrue(mess != null);
		
		assertTrue(Message.getParam(mess, MessageSR.SR_TIME).equals("1"));
		assertTrue(Message.getParam(mess, 0)==null);
		assertTrue(Message.getParam(mess, 2)==null);
		
	}
	
	public void testIsMessage() {
		String mess = null;
		String sepa = Message.new_separator();
		String params = Message.concat(sepa, new String[]{ "1" });
		
		mess = Message.createMessage(new Ipv4("0.0.0.0"), 5, MessageSR.SR_TIME_NAME, params);
		
		assertTrue(mess != null);
		assertTrue(Message.isMessage(mess));
		assertFalse(Message.isMessage("abdfqe"));
		
	}

}
