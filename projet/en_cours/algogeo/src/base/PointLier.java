package base;

public class PointLier extends Point{
public PointLier precedent;
public PointLier suivant;

	public PointLier(float x, float y, PointLier precedent, PointLier suivant) {
		super(x, y);
		this.precedent = precedent;
		this.suivant = suivant;
	}
	public PointLier(float x, float y) {
		super(x, y);
		this.precedent = null;
		this.suivant = null;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

}
