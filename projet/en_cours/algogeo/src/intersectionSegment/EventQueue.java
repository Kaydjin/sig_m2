package intersectionSegment;

import java.util.*;
import base.*;

public class EventQueue {
	TreeMap<Point, Event> queue = new TreeMap<Point, Event>();

	public static class Event {
		Point point;
		ArrayList<Segment> debut;
		ArrayList<Segment> milieu;
		ArrayList<Segment> fin;

		Event(Point p) {
			point = p;
		}

		private static int taille(ArrayList<Segment> s) {
			return (s == null) ? 0 : s.size();
		}

		public int debutSize() {
			return taille(debut);
		}

		public int finSize() {
			return taille(fin);
		}

		public int milieuSize() {
			return taille(milieu);
		}

		public void addDebut(Segment s) {
			if (debut == null)
				debut = new ArrayList<Segment>();
			debut.add(s);
		}

		public void addMilieu(Segment s) {
			if (milieu == null)
				milieu = new ArrayList<Segment>();
			milieu.add(s);
		}

		public void addFin(Segment s) {
			if (fin == null)
				fin = new ArrayList<Segment>();
			fin.add(s);
		}
	}

	public Event add(Point p) {
		Event e = queue.get(p);
		if (e == null) {
			e = new Event(p);
			queue.put(p,e);
		}
		return e;
	}

	public Event poolFirstEntry() {
		return queue.pollFirstEntry().getValue();
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}
}
