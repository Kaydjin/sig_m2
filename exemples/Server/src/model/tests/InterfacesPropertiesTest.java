package model.tests;

import java.util.ArrayList;

import model.InterfacesProperties;
import model.Ipv4;

//A simple main to test the InterfacesProperties class methods.
public class InterfacesPropertiesTest {

	public static void main(String args[]) {
		InterfacesProperties info = new InterfacesProperties();
		ArrayList<Ipv4> address = info.getIpv4AddressFromInterfaces();
		Ipv4[] tab;
		for (Ipv4 ip : address) {
			tab = info.getIpv4InterfaceProperties(ip);
			if (tab != null) {
				for (int i = 0; i < tab.length; i++) {
					System.out.println(tab[i].toString());
				}
			}
		}
	}
}
