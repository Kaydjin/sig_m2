package base;

public class Segment {
	public Point debut;
	public Point fin;

	public Segment(Point debut, Point fin) {
		super();
		this.debut = debut;
		this.fin = fin;
	}

	@Override
	public String toString() {
		return "[" + debut + ", " + fin + "]";
	}

	public int superHashCode() {
		return super.hashCode();
	}

	public Segment trie() {
		assert !debut.equals(fin);
		if (debut.y > fin.y || (debut.y == fin.y && debut.x <= fin.x))
			return new Segment(debut, fin);
		else
			return new Segment(fin, debut);

	}

	public Point intersectionHorizontale(float y) {
		if ((debut.y - y) * (fin.y - y) > 0) // debut, fin du m�me cot�
			return null;
		else if (debut.y == fin.y) // segment horizontal 
			return null;
		else {
			float x = ((fin.y - y) * debut.x + (y - debut.y)*fin.x)
					/ (fin.y - debut.y);
			return new Point(x, y);
		}
	}

	public float produitScalaire(Segment s) {
		return (fin.x - debut.x) * (s.fin.x - s.debut.x) + (fin.y - debut.y)
				* (s.fin.y - s.debut.y);
	} 
	
	public float produitVectoriel(Segment s) {
		return (fin.x - debut.x) * (s.fin.y - s.debut.y) - (fin.y - debut.y)
				* (s.fin.x - s.debut.x);
	}

	public Segment homothetie(float lambdax, float lambday) {
		return new Segment(debut,fin.homothetie(lambdax, lambday));
	}
	public Segment homothetie(float lambda) {
		return new Segment(debut.homothetie(lambda),fin.homothetie(lambda));
	}
	
	public boolean contains(Point p) {
		Segment s = new Segment(debut,p);
		return (produitVectoriel(s) == 0
				&& produitScalaire(s) >= 0
				&& s.produitScalaire(s) <= produitScalaire(this));
	}

	public Point projectionHorizontale(Point p) {
		Point pointHorizon = new Point(p.x+1,p.y);
		Segment demiHorizon = new Segment(p,pointHorizon);
		if (produitVectoriel(demiHorizon) == 0)
				return null;
		else {
			float lambda = - produitVectoriel(new Segment(debut,p)) / produitVectoriel(demiHorizon);
			return p.translation(demiHorizon.homothetie(lambda));
		}
	}

	public boolean pointadroite(Point p){
		Segment temp=new Segment(debut,p);


		return temp.produitScalaire(this)<0;

	}
	
	public Point intersection(Segment s) {

		if (s.produitVectoriel(this) == 0)
			return null;

		float a = (s.produitVectoriel(new Segment(debut, s.debut)))
				/ s.produitVectoriel(this);
		float b = (this.produitVectoriel(new Segment(s.debut, this.debut)))
				/ this.produitVectoriel(s);

		
		if (a < 0 || a > 1)
			return null;
		else if (b < 0 || b > 1)
			return null;
		else
			return new Point(debut.x + a * (fin.x - debut.x), debut.y + a
					* (fin.y - debut.y));
	}
	
	
	public float distance() {
		return (float) Math.sqrt(Math.pow(fin.x-debut.x,2)+Math.pow(fin.y-debut.y, 2));
	}
	
}
