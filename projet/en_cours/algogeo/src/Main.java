import fenetre.Fenetre;
import fenetre.Panneau;

import java.awt.Color;
import java.util.ArrayList;

import base.ListCirculairePoints;
import base.Point;
import base.Polyedre;
import base.Segment;


public class Main {
	public static void main(String[] arg) {

		Point[] test = {
				new Point(10, 10),
				new Point(160, 80),
				new Point(180, 60),
				new Point(180, 140),
				new Point(315, 90),
				new Point(150, 195),
				new Point(260, 280),
				new Point(100, 185),
				new Point(150, 325),
				new Point(80, 200),
				new Point(10, 350),
				new Point(80, 70)
		};

		Polyedre poly=new Polyedre(test);
		
		Point p6 = new Point(80,80);
		
		Panneau panneau = new Panneau();
		panneau.add(poly);
		
		System.out.println(poly.contains(p6));
		
		Polyedre test2 = poly.enveloppe_convexe();
		//panneau.add2(test2, Color.red);
		
		ArrayList<Polyedre> list_triangles = Polyedre.triangularisation(poly);
		/*for(Polyedre p : list_triangles)
		panneau.add(p);*/
		
		
		ListCirculairePoints lcp = poly.circuitPolaire(p6);
		/*Polyedre p_new = new Polyedre(lcp);
		panneau.add(p_new);*/
		
		Fenetre fenetre = new Fenetre(panneau);
		fenetre.setVisible(true);

	}
	
}
