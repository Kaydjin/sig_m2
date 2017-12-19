package model.tests;

import java.net.InetAddress;
import java.net.UnknownHostException;

import model.Ipv4;
import model.exceptions.IllegalIpException;
import junit.framework.TestCase;

/**
 * 
 * @author jimmy
 * Test for the ipv4 class.
 */
public class Ipv4Test extends TestCase {
	
	//Some bad ip.
	private final String bad_ip_too_much_number = "122.1152.215.252";
	private final String bad_ip_too_high_number = "122.125.260.252";
	private final String bad_ip_too_long = "125.122.0152.215.252";
	private final String good_ip = "125.1.216.32";

	//test for the constructor with inetaddress in parameter.
	public void testIpv4InetAddress() {
		boolean erreur1 = false;
		boolean erreur2 = false;
		boolean erreur3 = false;
		boolean erreur4 = false;
		
		@SuppressWarnings("unused")
		InetAddress address;
		try {
			address =InetAddress.getByName(bad_ip_too_much_number);
		} catch (UnknownHostException e) {
			erreur1 = true;
		}	
		try {
			address =InetAddress.getByName(bad_ip_too_high_number);
		} catch (UnknownHostException e) {
			erreur2 = true;
		}		
		try {
			address =InetAddress.getByName(bad_ip_too_long);
		} catch (UnknownHostException e) {
			erreur3 = true;
		}		
		try {
			address =InetAddress.getByName(good_ip);
		} catch (UnknownHostException e) {
			erreur4 = true;
		}
		assertTrue(erreur1);
		assertTrue(erreur2);
		assertTrue(erreur3);
		assertFalse(erreur4);
	}
	
	//Test for the constructor with a string in parameter.
	public void testIpv4String() {
		@SuppressWarnings("unused")
		Ipv4 ip;
		boolean test = true;
		try {
			ip = new Ipv4(bad_ip_too_much_number);
		} catch (IllegalArgumentException e) {
			test = false;
		}
		assertFalse(test);
		test = true;
		try {
			ip = new Ipv4(bad_ip_too_high_number);
		} catch (IllegalArgumentException e) {
			test = false;
		}
		assertFalse(test);
		test = true;
		try {
			ip = new Ipv4(bad_ip_too_long);
		} catch (IllegalArgumentException e) {
			test = false;
		}
		assertFalse(test);
		test = true;
		try {
			ip = new Ipv4(good_ip);
		} catch (IllegalArgumentException e) {
			test = false;
		}
		assertTrue(test);
	}

	//Test .toString() fonction.
	public void testToString() {
		assertEquals(new Ipv4(good_ip).toString(), good_ip);
	}
	

	//Test the setter method with an inetaddress in parameter.
	public void testSetRawIp() {
		InetAddress address=null;
		InetAddress address2=null;
		boolean erreur = false;
		Ipv4 ip=null;
		try {
			address = InetAddress.getByName(good_ip);
			address2 = InetAddress.getByName("121.123.124.125");
		} catch (UnknownHostException e) {
			erreur = true;
		}
		try {
			ip = new Ipv4(address);
		} catch (IllegalIpException e) {
			erreur = true;
		}
		assertFalse(erreur);
		try {
			ip.setRawIp(address2);
		} catch (IllegalIpException e) {
			erreur = true;
		}
		assertFalse(erreur);
		assertEquals(ip.toString(), address2.getHostName());
	}

}
