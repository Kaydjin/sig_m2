package utils;

public class ParamCheck {

	public static boolean notNull(Object[] list){
		for(Object o : list){
			if(o == null)
				return false;
		}
		return true;
	}
	
	public static boolean notEmpty(String[] list){
		for(String s : list){
			if(s.trim().equals(""))
				return false;
		}
		return true;
	}
}
