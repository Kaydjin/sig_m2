package fenetre;
import java.awt.*;
import javax.swing.*;

import base.Point;
import base.Polyedre;
import base.Segment;

import java.util.*;

public class Panneau extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ArrayList<Point> points = new ArrayList<Point>();
	ArrayList<Segment> segments = new ArrayList<Segment>();
	ArrayList<Segment> segments2 = new ArrayList<Segment>();
	ArrayList<Polyedre> polyedres = new ArrayList<Polyedre>();
	ArrayList<Polyedre> polyedres2 = new ArrayList<Polyedre>();
	Color color_poly2 = Color.red;

	public static final int TAILLEPOINT = 4;
	
	public void add(Point point) {
		points.add(point);
	}

	public void add(Segment segment) {
		segments.add(segment);
	}

	public void add(Polyedre polyedre) {
		polyedres.add(polyedre);
	}
	
	public void add2(Polyedre polyedre, Color color) {
		polyedres2.add(polyedre);
		color_poly2 = color;
	}

	private void draw(Point point, Graphics g) {
		g.drawOval((int) point.x-TAILLEPOINT, (int) point.y-TAILLEPOINT, TAILLEPOINT, TAILLEPOINT);
		g.setColor(Color.red);
		g.fillOval((int) point.x-TAILLEPOINT, (int) point.y-TAILLEPOINT, TAILLEPOINT, TAILLEPOINT);
		g.setColor(Color.black);
	}
	
	private void draw(Segment segment, Graphics g) {
		g.drawLine((int) segment.debut.x, (int) segment.debut.y, (int) segment.fin.x, (int) segment.fin.y);
		
	}

	private void draw(Polyedre polyedre, Graphics g, boolean color) {
		int taille = polyedre.points.length;
		int[] x = new int[taille];
		int[] y = new int[taille];
		
		for (int i = 0; i<taille; i++) {
			x[i] = (int) polyedre.points.get(i).x;
			y[i] = (int) polyedre.points.get(i).y;
		}

		if(color)
			g.setColor(color_poly2);
		else
			g.setColor(Color.black);
		
		g.drawPolygon(x, y, taille);
		//g.setColor(Color.yellow);
		//g.setColor(Color.white);
		//g.fillPolygon(x, y, taille);
	}

	
	public void paint(Graphics g) {
		super.paint(g);

		for (Polyedre poly:polyedres) 
			draw(poly,g, false);
		
		for (Polyedre poly:polyedres2) 
			draw(poly,g, true);
		
		int count=1;
		for (Segment s:segments) {
			draw(s,g);
			g.drawString(""+count++, (int)s.fin.x-10, (int)s.fin.y/2+40);
		}
		for (Segment s:segments2) 
			draw(s,g);

		for (Point p:points) 
			draw(p,g);
	}

	public void add2(Segment s1, Color red) {
		segments2.add(s1);
		color_poly2 = red;
	}
}
