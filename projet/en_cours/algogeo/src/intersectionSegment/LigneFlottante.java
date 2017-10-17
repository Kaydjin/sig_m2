package intersectionSegment;

import java.util.*;
import base.*;

public class LigneFlottante {
	TreeSet<SegmentFlottant> tree = new TreeSet<SegmentFlottant>();
	Point point = new Point(Float.MAX_VALUE/2,-Float.MAX_VALUE/2);

	@Override
	public String toString() {
		StringBuffer res = new StringBuffer("{ ");
		for (SegmentFlottant s : tree)
			res.append(s.toString() + " ");
		res.append("}");
		return "LigneFlottante [tree=" + res.toString() + ", point=" + point
				+ "]";
	}

	private static final float EPSILON = (float) 0.001;
	
	private static boolean proche(float x, float y) {
		return (x-y < EPSILON) && (y-x < EPSILON);
	}
	
	public class SegmentFlottant implements Comparable<SegmentFlottant> {
		Segment segment;

		SegmentFlottant(Segment s) {
			this.segment = s;
		}

		@Override
		public String toString() {
			return segment.toString();
		}

		private int compareDirection(SegmentFlottant s) {
			float tmp = segment.produitVectoriel(s.segment);
			if (tmp > 0)
				return -1;
			else if (tmp < 0)
				return 1;
			else
				return 0;
		}

		private float intersectionLigne() {
			if (point.equals(segment.debut))
				return point.x;
			else {
				Point p = segment.intersectionHorizontale(point.y);
				if (segment.debut.y == segment.fin.y)
					return point.x;
				assert p != null;
				return p.x;
			}
		}


		
		public int compareTo(SegmentFlottant s) {
			if (s==this)
				return 0;
			
			float p0 = intersectionLigne();
			float p1 = s.intersectionLigne();

			if (proche(p0,p1)) {					
				// traiter le cas artificiel
				// [point,(max,max)] ou [point,(-max,max]
				if (segment.debut.equals(point) && segment.fin.y==Float.MAX_VALUE)
					return (segment.fin.x>0)?1:-1;
				else if (s.segment.debut.equals(point) && s.segment.fin.y==Float.MAX_VALUE)
					return (s.segment.fin.x>0)?-1:1;

				// autres cas !
				int d = this.compareDirection(s);
				if (d != 0)
					return (p0 <= point.x) ? d : -d;
				else if (segment.fin.x == s.segment.fin.x)
					return 0;
				else 
					return (segment.fin.x < s.segment.fin.x) ? -1 : 1;
			}
			else if (p0 < p1)
				return -1;
			else // if (p0 > p1)
				return 1;
		}
	}

	public void remove(Segment s) {
		tree.remove(new SegmentFlottant(s));
	}

	public void add(Segment s) {
		tree.add(new SegmentFlottant(s));
	}

	/*
	 * Plus grand élément avant p
	 */
	public Segment avant() {
		// [point,(max,max)] ou [point,(-max,max]
		SegmentFlottant s = new SegmentFlottant(new Segment(point, new Point(
				-Float.MAX_VALUE, Float.MAX_VALUE)));
		SegmentFlottant res = tree.lower(s);
		return (res == null) ? null : res.segment;
	}

	/*
	 * Plus petit élément contenant p
	 */
	public Segment plusPetit() {
		SegmentFlottant s = new SegmentFlottant(new Segment(point, new Point(
				-Float.MAX_VALUE, Float.MAX_VALUE)));
		SegmentFlottant res = tree.ceiling(s);
		return (res == null) ? null : res.segment;
	}

	/*
	 * Plus petit élément après p
	 */
	public Segment apres() {
		SegmentFlottant s = new SegmentFlottant(new Segment(point, new Point(
				Float.MAX_VALUE, Float.MAX_VALUE)));
		SegmentFlottant res = tree.higher(s);
		return (res == null) ? null : res.segment;
	}

	/*
	 * Plus grand élément contenant p
	 */
	public Segment plusGrand() {
		SegmentFlottant s = new SegmentFlottant(new Segment(point, new Point(
				Float.MAX_VALUE, Float.MAX_VALUE)));
		SegmentFlottant res = tree.floor(s);
		return (res == null) ? null : res.segment;
	}


	public ArrayList<Segment> coupure(Point p) {
		Point tmp = point;
		point = p;
		ArrayList<Segment> res = new ArrayList<Segment>();
		SegmentFlottant limite = new SegmentFlottant(new Segment(point, new Point(
				-Float.MAX_VALUE, Float.MAX_VALUE)));
		for (SegmentFlottant s:tree.tailSet(limite)) {
			float p0 = s.intersectionLigne();
			if (proche(p0,p.x))
				res.add(s.segment);
			else
				break;
		}
		point = tmp;
		return res;
				
	}

}
