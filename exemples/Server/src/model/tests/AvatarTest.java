package model.tests;

import model.Avatar;
import model.Avatar.Condition;
import model.Avatar.Type;
import model.exceptions.AvatarSettingException;
import junit.framework.TestCase;

public class AvatarTest extends TestCase {

	public void testSetTypeString() {
		Avatar a = new Avatar("a", "b", Type.DOCTOR, Condition.ALIVE);
		try{
			a.setType("MADMAN");
		}catch(AvatarSettingException e){
			assertFalse(true);
		}
		assertTrue(a.getType()==Type.MADMAN);
	}

	public void testSetStateString() {
		Avatar a = new Avatar("a", "b", Type.DOCTOR, Condition.ALIVE);
		try{
			a.setState("DEAD");
		}catch(AvatarSettingException e){
			assertFalse(true);
		}
		assertTrue(a.getState()==Condition.DEAD);
	}

}
