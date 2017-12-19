package utils;

public class RandomUtil {

	public static int random(int lower, int higher){

		return (int)(Math.random() * (higher-lower)) + lower;
	}
	
	public static char randomLetter(){
		return (char) random(97, 122);
	}
	
	public static char randomDigit(){
		return Character.forDigit(random(0, 9), 10);
	}
	
	public static void main(String args[]){
		char[] values = new char[9];
		for(int i=0; i<3; i++){
			values[i] = RandomUtil.randomDigit();
			values[i+3] = RandomUtil.randomLetter();
			values[i+6] = RandomUtil.randomDigit();
		}
		String fin = String.valueOf(values);
		System.out.println(fin);
	}
}