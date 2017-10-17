package base;

import java.util.ArrayList;
import java.util.Iterator;

public class ListCirculairePoints{
private ArrayList<PointLier> points;
public Integer length;

	public ListCirculairePoints() {
		super();
		points = new ArrayList<PointLier>();
		length = 0;
	}
	public ListCirculairePoints(Point[] pointstab) {
		super();
		points = new ArrayList<PointLier>();
		length = 0;
		for(int i=0; i<pointstab.length; i++) {
			this.add(pointstab[i]);
		}
	}
	
	private ListCirculairePoints(ArrayList<PointLier> al) {
		super();
		points = new ArrayList<PointLier>();
		length = 0;
		for(PointLier p : al)
			this.add(p);
	}


	public ArrayList<PointLier> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<PointLier> points) {
		this.points = points;
	}

	public int suivant(int pos) {
		if(length< 2 || pos > length-1)
			return -1;
		
		if(pos==length-1) return 0;
		
		return pos+1;
	}
	
	public int precedent(int pos) {
		if(length < 2 || pos > length-1)
			return -1;
		
		if(pos==0) return  length-1;
		
		return pos-1;
	}
	
	public PointLier nextP(int pos) {
		if(length < 2)
			return null;

		return points.get(suivant(pos));
	}
	
	public Point prevP(int pos) {
		if(length < 2)
			return null;

		return points.get(precedent(pos));
	}
	
	public boolean add(Point p) {
		if(length==0) {
			points.add(new PointLier(p.x, p.y));
			length++;
			return true;
		}
		if(length==1) {
			PointLier unique = points.get(0);
			PointLier p_inter = new PointLier(p.x, p.y, unique, unique);
			unique.suivant = p_inter;
			unique.precedent = p_inter;
			points.add(p_inter);
			length++;
			return true;
		}
		PointLier fin = points.get(length-1);
		PointLier premier = points.get(0);
		PointLier p_inter = new PointLier(p.x, p.y, fin, premier);
		premier.precedent = p_inter;
		fin.suivant = p_inter;
		points.add(p_inter);
		length++;
		return true;
	}
	
	public PointLier get(int pos) {
		return points.get(pos);
	}
	
	public boolean setValue(Point p, int pos) {
		if(pos<length && pos>=0) {
			PointLier pl = points.get(pos);
			pl.x = p.x;
			pl.y = p.y;
			return true;
		}
		return false;
	}
	
	public int indexOf(PointLier p) {
		return points.indexOf(p);
	}
	
	public boolean isEmpty() {
		return points.isEmpty();
	}
	
	public Iterator<PointLier> iterator() {
		return points.iterator();
	}
	
	public boolean remove(PointLier p) {
		Integer index = points.indexOf(p);
		if(index != -1) {
			PointLier prec = p.precedent;
			PointLier suiv = p.suivant;
			prec.suivant = suiv;
			suiv.precedent = prec;
			length--;
			return points.remove(p);
		}
		return false;
	}
	
	public PointLier remove(int pos) {
		PointLier p = points.get(pos);
		PointLier prec = p.precedent;
		PointLier suiv = p.suivant;
		prec.suivant = suiv;
		suiv.precedent = prec;
		points.remove(p);
		length--;
		return p;
	}
	
	public int size() {
		return length;
	}
	
	public ListCirculairePoints clone() {
		ArrayList<PointLier> al = this.getPoints();
		ListCirculairePoints list = new ListCirculairePoints(al);
		
		return list;
	}
	
}
