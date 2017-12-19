package model.tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

import model.Avatar;
import model.Avatar.Condition;
import model.Avatar.Type;
import model.Client;
import model.Ipv4;
import model.Player;
import model.exceptions.ClientCreationException;
import junit.framework.TestCase;

public class ClientTest extends TestCase {

	// Use only to build a not null PrintWriter.
	private static Writer w = new PrintWriter(new BufferedWriter(new Writer() {
		@Override
		public void write(char[] cbuf, int off, int len) throws IOException {
		}

		@Override
		public void flush() throws IOException {
		}

		@Override
		public void close() throws IOException {
		}
	}));

	// Use only to build a not null BufferedReader.
	private static Reader r = new Reader() {

		@Override
		public int read(char[] cbuf, int off, int len) throws IOException {
			return 0;
		}

		@Override
		public void close() throws IOException {
		}

	};

	public void testClient() {
		boolean success = true;
		PrintWriter good_print = null;
		Client client = null;
		try {
			good_print = new PrintWriter(new BufferedWriter(w));
		} catch (Exception i) {
			success = false;
		}
		assertTrue(success);

		BufferedReader br = new BufferedReader(r);

		// Test with a good construction
		success = true;
		try {
			client = new Client(new Ipv4("0.0.0.0"), 1, good_print, br,
					new Avatar("a", "b", Type.DOCTOR, Condition.ALIVE),
					new Player("a"));
		} catch (IllegalArgumentException e) {
			success = false;
		} catch (ClientCreationException e) {
			success = false;
		}
		assertTrue(success);
		if (client != null)
			assertTrue(client.getState() == Client.State.CONNECTED);

		// Test with bad construction on the ip
		success = false;
		try {
			client = new Client(null, 1, good_print, br, new Avatar("a", "b",
					Type.DOCTOR, Condition.ALIVE), new Player("a"));
		} catch (ClientCreationException e) {
			success = true;
		}
		assertTrue(success);

		// Test with bad construction on the port
		success = false;
		try {
			client = new Client(new Ipv4("0.0.0.0"), -2, good_print, br,
					new Avatar("a", "b", Type.DOCTOR, Condition.ALIVE),
					new Player("a"));
		} catch (ClientCreationException e) {
			success = true;
		}
		assertTrue(success);

		// Test with bad construction on the printer
		success = false;
		try {
			client = new Client(new Ipv4("0.0.0.0"), 2, null, br, new Avatar(
					"a", "b", Type.DOCTOR, Condition.ALIVE), new Player("a"));
		} catch (ClientCreationException e) {
			success = true;
		}
		assertTrue(success);

		// test with bad construction on the listener.
		success = false;
		try {
			client = new Client(new Ipv4("0.0.0.0"), 2, good_print, null,
					new Avatar("a", "b", Type.DOCTOR, Condition.ALIVE),
					new Player("a"));
		} catch (ClientCreationException e) {
			success = true;
		}
		assertTrue(success);

		// Test with bad construction on the avatar
		success = false;
		try {
			client = new Client(new Ipv4("0.0.0.0"), 2, good_print, br, null,
					new Player("a"));
		} catch (ClientCreationException e) {
			success = true;
		}
		assertTrue(success);

		// Test with bad construction on the player
		success = false;
		try {
			client = new Client(new Ipv4("0.0.0.0"), 2, good_print, br,
					new Avatar("a", "b", Type.DOCTOR, Condition.ALIVE), null);
		} catch (ClientCreationException e) {
			success = true;
		}
		assertTrue(success);

	}

	public void testDisconnect() {

		boolean success = true;
		PrintWriter good_print = null;
		Client client = null;
		try {
			good_print = new PrintWriter(new BufferedWriter(w));
		} catch (Exception i) {
			success = false;
		}
		assertTrue(success);

		// Test with a good construction
		success = true;

		BufferedReader br = new BufferedReader(r);

		try {
			client = new Client(new Ipv4("0.0.0.0"), 1, good_print, br,
					new Avatar("a", "b", Type.DOCTOR, Condition.ALIVE),
					new Player("a"));
		} catch (IllegalArgumentException e) {
			success = false;
		} catch (ClientCreationException e) {
			success = false;
		}

		assertTrue(success);

		if (client != null) {
			client.disconnect();
			assertEquals(client.getState(), Client.State.DISCONNECTED);
		}
	}

	public void testReconnect() {
		boolean success = true;
		PrintWriter good_print = null;
		Client client = null;
		try {
			good_print = new PrintWriter(new BufferedWriter(w));
		} catch (Exception i) {
			success = false;
		}
		assertTrue(success);

		BufferedReader br = new BufferedReader(r);

		// Test with a good construction
		success = true;
		try {
			client = new Client(new Ipv4("0.0.0.0"), 1, good_print, br,
					new Avatar("a", "b", Type.DOCTOR, Condition.ALIVE),
					new Player("a"));
		} catch (IllegalArgumentException e) {
			success = false;
		} catch (ClientCreationException e) {
			success = false;
		}
		assertTrue(success);

		if (client != null) {
			client.disconnect();
			assertEquals(client.getState(), Client.State.DISCONNECTED);
			client.reconnect();
			assertEquals(client.getState(), Client.State.CONNECTED);
		}
	}

	public void testTime_since_disconnected() {

		boolean success = true;
		PrintWriter good_print = null;
		Client client = null;
		try {
			good_print = new PrintWriter(new BufferedWriter(w));
		} catch (Exception i) {
			success = false;
		}
		assertTrue(success);

		BufferedReader br = new BufferedReader(r);

		// Test with a good construction
		success = true;
		try {
			client = new Client(new Ipv4("0.0.0.0"), 1, good_print, br,
					new Avatar("a", "b", Type.DOCTOR, Condition.ALIVE),
					new Player("a"));
		} catch (IllegalArgumentException e) {
			success = false;
		} catch (ClientCreationException e) {
			success = false;
		}
		assertTrue(success);

		if (client != null) {
			client.disconnect();
			assertEquals(client.getState(), Client.State.DISCONNECTED);
			try {
				Thread.sleep(2 * 1000);
			} catch (InterruptedException e) {
				assertFalse(true); // Musn't be call.
			}
			float time = client.time_since_disconnected();
			assertTrue(time > 0 && time < 10);
		}
	}

}
