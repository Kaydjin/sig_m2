package test;

import static org.junit.Assert.*;

import org.junit.Test;

import base.Point;
import base.Polyedre;

public class PolyedreTest {

	@Test
	public void testContains() {
		Point[] vraipoly = {
				new Point(100, 100),
				new Point(150, 50),
				new Point(300, 100),
				new Point(300, 300),
				new Point(350, 100),
				new Point(350, 350),
				new Point(150, 350),
				new Point(100, 300)
		};
		Polyedre poly=new Polyedre(vraipoly);
		
		Point p1 = new Point(500,250);
		Point p2 = new Point(25,25);
		Point p3 = new Point(10,350);
		Point p4 = new Point(250,360);
		Point p5 = new Point(100,180);
		Point p6 = new Point(100,300);	
		Point p7 = new Point(150,155);
		Point p8 = new Point(325,155);
		
		if(poly.contains(p1))
			fail("Ne contient pas, un point externe a droite");
		if(poly.contains(p2))
			fail("Ne contient pas, un point externe ayant x=y");
		if(poly.contains(p3))
			fail("Ne contient pas, un point externe en bas a gauche.");
		if(poly.contains(p4))
			fail("Ne contient pas, un point en dessous");
		if(!poly.contains(p5))
			fail("Contient un point confondu avec un point d'un segment du polygone.");
		if(!poly.contains(p6))
			fail("Contient un point confondu avec un sommet du polygone.");
		if(!poly.contains(p7))
			fail("Contient un point contenu dans le polygone.");
		if(poly.contains(p8))
			fail("Ne contient pas, un point entre deux parti du polygone.");
		
	}

	@Test
	public void testEnveloppe_convexe() {
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
		
		if(poly.enveloppe_convexe() == null)
			fail("Ne contient pas, le polygone est viable");
		poly=new Polyedre(new Point[] {});
		if(poly.enveloppe_convexe() != null)
			fail("L'enveloppe convexe n'existe pas pour un polyedre inexistant");
		poly=new Polyedre(new Point[] {new Point(20,20), new Point(40, 40), new Point(0,40)});
		if(poly.enveloppe_convexe() == null)
			fail("L'enveloppe convexe pour un triangle devrait exister");
	}

	@Test
	public void testTriangularisation() {
		fail("Not yet implemented");
	}

}
