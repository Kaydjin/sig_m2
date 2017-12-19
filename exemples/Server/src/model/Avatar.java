package model;

import model.exceptions.AvatarSettingException;

public class Avatar {
public enum Type { SORCERER, MADMAN, DOCTOR }
public enum Condition { DEAD, ALIVE, SICK }
private String name;
private String poster_name;
private Type type;
private Condition state;

	public Avatar(String name, String poster, Type type, Condition state){
		this.name = name;
		this.poster_name = poster;
		this.type = type;
		this.state = state;
	}
	
	public Avatar(String name, String poster, String type, String state) throws AvatarSettingException{
		this.name = name;
		this.poster_name = poster;
		setType(type);
		setState(state);
	}
	
	public Avatar(String name, String poster, Type type, String state) throws AvatarSettingException{
		this.name = name;
		this.poster_name = poster;
		this.type = type;
		setState(state);
	}
	
	public Avatar(String name, String poster, String type, Condition state) throws AvatarSettingException{
		this.name = name;
		this.poster_name = poster;
		setType(type);
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public void setType(String type) throws AvatarSettingException{
		if(type.equals(Type.SORCERER.toString()))
			this.type = Type.SORCERER;
		else if(type.equals(Type.MADMAN.toString()))
			this.type = Type.MADMAN;
		else if(type.equals(Type.DOCTOR.toString()))
			this.type = Type.DOCTOR;
		else throw new AvatarSettingException();
	}

	public Condition getState() {
		return state;
	}

	public void setState(Condition state) {
		this.state = state;
	}
	
	public void setState(String state) throws AvatarSettingException{
		if(state.equals(Condition.ALIVE.toString()))
			this.state = Condition.ALIVE;
		else if(state.equals(Condition.DEAD.toString()))
			this.state = Condition.DEAD;
		else if(state.equals(Condition.SICK.toString()))
			this.state = Condition.SICK;
		else throw new AvatarSettingException();
	}

	public String getPoster() {
		return poster_name;
	}

	public void setPoster(String poster_name) {
		this.poster_name = poster_name;
	}
	
}
