package utils;

import junit.framework.TestCase;

public class RandomUtilTest extends TestCase {

	public void testRandom() {
		int random;
		for(int i=0; i<100; i++){
			random = RandomUtil.random(5,10);
			assertTrue(random < 11 && random > 4);
		}
	}

}
