package intersectionSegment;

import base.*;
import java.util.*;

public class AlgoIntersectionSegment {
	Segment[] segment;
	EventQueue queue = new EventQueue();
	LigneFlottante ligne = new LigneFlottante();
	ArrayList<Point> resultat = new ArrayList<Point>();

	public AlgoIntersectionSegment(Segment[] segment) {
		this.segment = segment;
	}

	public ArrayList<Point> findIntersection() {
		/* initialisation de la queue des Žvnements */
		for (Segment s : segment) {
			EventQueue.Event e = queue.add(s.debut);
			e.addDebut(s);
			e = queue.add(s.fin);
			e.addFin(s);
		}

		while (!queue.isEmpty()) {
			EventQueue.Event e = queue.poolFirstEntry();
			handleEventPoint(e);
		}

		return resultat;
	}

	private void handleEventPoint(EventQueue.Event e) {

		/* Suppression des fins */
		if (e.finSize() != 0)
			for (Segment s : e.fin)
				ligne.remove(s);
		
		/* calcul des segments passant par e.point */
		e.milieu = ligne.coupure(e.point);

		/* Suppression des segments debut et milieu dans la ligne flottante */
		if (e.milieuSize() != 0)
			for (Segment s : e.milieu)
				ligne.remove(s);

		/*
		 * modification du point dans la ligne flottante et ajout des segments
		 * debut et milieu
		 */
		ligne.point = e.point;

		/* ajout des points commencant par e.point */
		if (e.debutSize() != 0)
			for (Segment s : e.debut)
				ligne.add(s);

		if (e.milieuSize() != 0)
			for (Segment s : e.milieu)
				ligne.add(s);

		/* detection d'une intersection */
		if (e.debutSize() + e.finSize() + e.milieuSize() > 1)
			resultat.add(e.point);

		/* Calcul des nouvelles intersections */
		if (e.debutSize() + e.milieuSize() == 0) {
			Segment sl = ligne.plusPetit();
			Segment sr = ligne.plusGrand();
			insertNewEvent(sl, sr, e.point);
		} else {
			{
				Segment sl = ligne.plusPetit();
				Segment sr = ligne.avant();
				insertNewEvent(sl, sr, e.point);
			}
			{
				Segment sl = ligne.plusGrand();
				Segment sr = ligne.apres();
				insertNewEvent(sl, sr, e.point);
			}
		}
	}

	private void insertNewEvent(Segment sl, Segment sr, Point p) {
		if (sl != null && sr != null) {
			Point newP = sl.intersection(sr);
			if (newP != null) {
				if (newP.y < p.y || (newP.y == p.y && newP.x >= p.x)) {
					EventQueue.Event e = queue.add(newP);
				}
			}
		}
	}
}
