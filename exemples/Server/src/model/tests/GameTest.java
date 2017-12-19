package model.tests;

import java.util.ArrayList;

import model.Avatar;
import model.Game;
import model.Avatar.Condition;
import junit.framework.TestCase;

public class GameTest extends TestCase {

	public void testAddPlayer() {
		Game g = new Game();
		g.addPlayer("ALIVE", "nom", "post", "DOCTOR");
		assertNotNull(g.getByName("nom"));
		g.addPlayer("ALIVE", "nom2", "", "DOCTOR");
		assertNull(g.getByName("nom2"));
		g.addPlayer("ALIVE", "nom3", "post", null);
		assertNull(g.getByName("nom3"));
	}

	public void testGetDeads() {
		Game g = new Game();
		g.addPlayer("ALIVE", "nom", "post", "DOCTOR");
		g.addPlayer("DEAD", "nom", "post", "DOCTOR");
		g.addPlayer("DEAD", "nom", "post", "DOCTOR");
		g.addPlayer("ALIVE", "nom", "post", "DOCTOR");
		ArrayList<Avatar> list = g.getDeads();
		assertTrue(list.size()==2);
		for(Avatar a : list)
			assertTrue(a.getState()==Condition.DEAD);
	}

	public void testGetAlive() {
		Game g = new Game();
		g.addPlayer("SICK", "nom", "post", "DOCTOR");
		g.addPlayer("DEAD", "nom", "post", "DOCTOR");
		g.addPlayer("DEAD", "nom", "post", "DOCTOR");
		g.addPlayer("ALIVE", "nom", "post", "DOCTOR");
		ArrayList<Avatar> list = g.getAlive();
		assertTrue(list.size()==2);
		for(Avatar a : list)
			assertTrue(a.getState()!=Condition.DEAD);
	}

	public void testGetByName() {
		Game g = new Game();
		g.addPlayer("ALIVE", "nom", "post", "DOCTOR");
		assertNotNull(g.getByName("nom"));
	}

	public void testGetByPosterName() {
		Game g = new Game();
		g.addPlayer("ALIVE", "nom", "post", "DOCTOR");
		assertNotNull(g.getByPosterName("post"));
	}

	public void testSetAvatars() {
		Game g = new Game();
		assertTrue(g.addPlayer("ALIVE", "nom", "post", "DOCTOR"));
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> posts_names = new ArrayList<String>();
		ArrayList<String> states = new ArrayList<String>();
		ArrayList<String> types = new ArrayList<String>();
		g.setAvatars(names, posts_names, types, states);
		assertNull(g.getByName("nom"));
		names.add("fantasque");
		g.setAvatars(names, posts_names, types, states);
		assertNull(g.getByName("fantasque"));
		states.add("fantasque");
		types.add("fantasque");
		posts_names.add("fantasque");
		g.setAvatars(names, posts_names, types, states);
		assertNull(g.getByName("fantasque"));
		
		states.remove("fantasque");
		types.remove("fantasque");
		
		names.add("autre");
		states.add("ALIVE");
		states.add("ALIVE");
		types.add("SORCERER");
		types.add("MADMAN");
		posts_names.add("weird");
		
		g.setAvatars(names, posts_names, types, states);
		assertTrue(g.getAlive().size()==2);
	}

}
