package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * 
 * @author jimmy
 * 
 *         This class allows to know the detail of the interfaces of the machine
 *         running. You can get the broadcast address and the address mask of an
 *         inet address of one of the interface. (Only ipv4)
 */
public class InterfacesProperties {
	private ArrayList<ArrayList<InetAddress>> interfaces;
	private InputStream datas;

	/**
	 * The constructor will clone the informations to not lose them when going
	 * through them the first time.
	 */
	public InterfacesProperties() {

		// First we get the interfaces.
		try {

			Enumeration<NetworkInterface> enumeration = NetworkInterface
					.getNetworkInterfaces();
			interfaces = new ArrayList<ArrayList<InetAddress>>();

			// We iterate over the interfaces to make a double arrays from the
			// enumerations.
			Enumeration<InetAddress> enumeration2 = null;
			ArrayList<InetAddress> address;
			while (enumeration.hasMoreElements()) {
				enumeration2 = enumeration.nextElement().getInetAddresses();

				address = new ArrayList<InetAddress>();
				while (enumeration2.hasMoreElements()) {
					address.add(enumeration2.nextElement());
				}
				interfaces.add(address);

			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	// Private method that clone the data from the ifconfig.
	private void launch_ifconfig() {
		Runtime r = Runtime.getRuntime();
		Process p;
		try {
			p = r.exec("ifconfig");
			p.waitFor();
			datas = p.getInputStream();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The detail of the ipv4 interface with the address inet given in String.
	 * Don't work for the loopback address inet.
	 * 
	 * @return A list of the ipv4 interface and her three parameters in an array
	 *         of three strings with the address inet, the address broadcast and
	 *         the address mask. Null if no inet corresponding.
	 */
	public Ipv4[] getIpv4InterfaceProperties(Ipv4 inet) {

		Ipv4[] res = new Ipv4[3];

		try {

			// We launch the ifconfig each time.
			launch_ifconfig();

			// We get the data from the ifconfig.
			BufferedReader b = new BufferedReader(new InputStreamReader(datas));

			// We iterate over the datas.
			String line = "";
			String inter[];
			while ((line = b.readLine()) != null) {

				// The line we want contains "inet adr:".
				if (line.contains("inet adr:" + inet.toString())) {

					// We remove the bad informations from the line to only let
					// the wanted remains.
					line = line.replace("inet adr:", "").replace("Bcast:", "")
							.replace("Masque:", "");
					inter = line.split(" ");
					res[0] = new Ipv4(inter[inter.length - 5]);
					res[1] = new Ipv4(inter[inter.length - 3]);
					res[2] = new Ipv4(inter[inter.length - 1]);

					return res;
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Give access to the address ipv4 from all the interfaces except the
	 * loopback address.
	 * 
	 * @return The list of address in String format.
	 */
	public ArrayList<Ipv4> getIpv4AddressFromInterfaces() {
		ArrayList<Ipv4> ipv4Addresses = new ArrayList<Ipv4>();

		for (ArrayList<InetAddress> address : interfaces) {
			for (InetAddress inet : address) {
				if (Ipv4.isIpv4String(inet.getHostAddress())
						&& (!inet.isMulticastAddress())
						&& (!inet.isLoopbackAddress())) {
					ipv4Addresses.add(new Ipv4(inet.getHostAddress()));
				}
			}
		}

		return ipv4Addresses;
	}

}