package model;

import java.util.ArrayList;

import utils.ParamCheck;
import model.Avatar.Condition;
import model.MessageSR.Turn;
import model.exceptions.AvatarSettingException;

public class Game {
	private ArrayList<Avatar> avatars;
	private float time;
	private Turn turn;

	/**
	 * Constructor of a game, no parameter specified.
	 */
	public Game() {
		avatars = new ArrayList<Avatar>();
		setTime(0);
		setTurn(Turn.Day);
	}

	/**
	 * Save the time when the game started.
	 */
	public void startGame() {
		setTime(System.currentTimeMillis());
	}

	/**
	 * Add a player to the list.
	 * 
	 * @param state the condition of the avatar
	 * @param name the name of the avatar
	 * @param post the poster name of the avatar
	 * @param type the type of the avatar
	 * @return true if the avatar added to the list.
	 */
	public boolean addPlayer(String state, String name, String post,
			String type) {
		if (!ParamCheck.notNull(new Object[] { state, name, post, type }))
			return false;
		if (!ParamCheck.notEmpty(new String[] { name, post }))
			return false;

		try{
			if (avatars.add(new Avatar(name, post, type, state))) {
				return true;
			}
		}catch(AvatarSettingException e){
			return false;
		}
		return false;
	}

	/**
	 * Return the list of the dead avatars. 
	 * @return a list of avatar.
	 */
	public ArrayList<Avatar> getDeads() {
		ArrayList<Avatar> l = new ArrayList<Avatar>();
		for (Avatar a : avatars)
			if (a.getState() == Condition.DEAD)
				l.add(a);

		return l;
	}
	
	/**
	 * Return the list of the avatars alive.
	 * @return a list of avatar
	 */
	public ArrayList<Avatar> getAlive() {
		ArrayList<Avatar> l = new ArrayList<Avatar>();
		for(Avatar a : avatars)
			if(a.getState() != Condition.DEAD)
				l.add(a);
		return l;
	}
	
	/**
	 * Get an avatar with a name matching the parameter
	 * @param name the name of the avatar to get
	 * @return an avatar if present otherwise null.
	 */
	public Avatar getByName(String name){
		for(Avatar a : avatars)
			if(a.getName().equals(name))
				return a;
		
		return null;
	}
	
	/**
	 * Get an avatar with a poser name matching the parameter
	 * @param poster the poster name of the avatar to get
	 * @return an avatar if present otherwise null
	 */
	public Avatar getByPosterName(String poster){
		for(Avatar a : avatars)
			if(a.getPoster().equals(poster))
				return a;
		
		return null;
	}
	

	/**
	 * Generate the avatars list by building them with the parameters.
	 * @param names the names of the avatars
	 * @param posts the posts_names of the avatars
	 * @param types the types of the avatars.
	 * @param states the condition of the avatars.
	 */
	public void setAvatars(ArrayList<String> names, ArrayList<String> posts,
			ArrayList<String> types, ArrayList<String> states) {
		this.avatars = new ArrayList<Avatar>();
		
		for(int i=0; i<names.size(); i++){
			try{
				addPlayer(states.get(i), names.get(i), posts.get(i), types.get(i));
			}catch(IndexOutOfBoundsException e){
				return ;
			}
		}
	}
	
	/**
	 * Return the list of the avatars types.
	 */
	public ArrayList<String> getAvatarsTypes(){
		ArrayList<String> list = new ArrayList<String>();
		for(Avatar a : avatars)
			list.add(a.getType().toString());
		return list;
	}
	
	/**
	 * Return the list of the avatars names.
	 */
	public ArrayList<String> getAvatarsNames(){
		ArrayList<String> list = new ArrayList<String>();
		for(Avatar a : avatars)
			list.add(a.getName());
		return list;
	}
	
	/**
	 * Return the list of the avatars states.
	 */
	public ArrayList<String> getAvatarsStates(){
		ArrayList<String> list = new ArrayList<String>();
		for(Avatar a : avatars)
			list.add(a.getState().toString());
		return list;
	}
	
	/**
	 * Return the list of the avatars posts_names.
	 */
	public ArrayList<String> getAvatarsPosts(){
		ArrayList<String> list = new ArrayList<String>();
		for(Avatar a : avatars)
			list.add(a.getPoster());
		return list;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public Turn getTurn() {
		return turn;
	}

	public void setTurn(Turn turn) {
		this.turn = turn;
	}
	
	

}
