package intersectionSegment;

import base.Point;
import base.Segment;
import fenetre.Fenetre;
import fenetre.Panneau;
import java.util.*;


public class Main {
	static float[][][] donnees = {
			{{200,300},{400,200}},
			{{200,400},{400,200}},
			{{200,400},{400,200}},
			{{200,300},{400,300}},
			{{200,200},{400,100}},
			{{400,400},{0,0}
			}
	};
	
	public static void main(String[] arg) {
		Segment[] segment = new Segment[donnees.length];
		for (int i=0; i<donnees.length; i++) {
			segment[i] = new Segment(new Point(donnees[i][0][0],donnees[i][0][1]), 
					new Point(donnees[i][1][0],donnees[i][1][1]));
		};
		
		Panneau panneau = new Panneau();
		for (Segment s : segment)
			panneau.add(s);

		AlgoIntersectionSegment algo = new AlgoIntersectionSegment(segment);
		ArrayList<Point> res = algo.findIntersection();
		
		for (Point p: res)
			panneau.add(p);
		
		System.out.println(res);
		
		Fenetre fenetre = new Fenetre(panneau);
		fenetre.setVisible(true);
	}

}
