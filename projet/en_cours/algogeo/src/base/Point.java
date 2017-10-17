package base;


public class Point implements Comparable<Point>{
	public float x;
	public float y;
	
	//Optional variable use in algorithm or as attribute.
	public boolean convexe;
	public float valeur;
	public float valeur2;
	
	public Point(float x, float y) {
		super();
		this.x = x;
		this.y = y;
		convexe = false;
		valeur = -1;
		valeur2 = -1;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}
	
	public boolean egal(Point p){
		if(p.x!=this.x)
			return false;
		if(p.y!=this.y)
			return false;
		return true;
	}



	public Point translation(Segment s) {
		return new Point(x+s.fin.x-s.debut.x,y+s.fin.y-s.debut.y);
	}

	/**
	 * Augment the length of a segment by a factor d and slightly modify the direction.
	 * @param s : the segment to modify
	 * @param d : the factor 
	 * @return the point corresponding to the end of the new segment.
	 */
	public Point amplidecal(Segment s,float d) {
		
		//If x and y are the same we must change the angle so epsilon is *2 for x.
		return new Point((x+(s.fin.x-s.debut.x)*d)+EPSILON*2*d,(y+(s.fin.y-s.debut.y)*d)+EPSILON*d);
	}
	
	public Point ampli(Segment s, float d) {
		return new Point((x+(s.fin.x-s.debut.x)*d),(y+(s.fin.y-s.debut.y)*d));
	}
	
	public Point homothetie(float lambdax, float lambday) {
		return new Point(lambdax*x,lambday*y);
	}	
	public Point homothetie(float lambda) {
		return new Point(lambda*x,lambda*y);
	}
	
	private static final float EPSILON = (float) 0.001;
	
	private static boolean proche(float x, float y) {
		return (x-y < EPSILON) && (y-x < EPSILON);
	}

	public double distance(Point p){
		return Math.sqrt(Math.pow(this.x-p.x,2)+Math.pow(this.y-p.y,2));

	}

	
	public int compareTo(Point p) {
		if (proche (x,p.x) && proche (y,p.y))
			return 0;

		if (y < p.y)
			return 1;
		else if (y>p.y)
			return -1;
		else if (x < p.x)
			return -1;
		else if (x>p.x)
			return 1;
		else 
			return 0;
	}
	
	public Point copy() {
		return new Point(this.x, this.y);
	}
}