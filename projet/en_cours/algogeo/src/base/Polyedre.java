package base;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

public class Polyedre {
	public ListCirculairePoints points;

	public Polyedre(Point[] points) {
		super();
		this.points = new ListCirculairePoints(points);
	}
	public Polyedre(PointLier[] points) {
		this((Point[])points);
	}
	public Polyedre(ListCirculairePoints p) {
		this.points = p;
	}
	
	
	public ListCirculairePoints circuitPolaire(Point p) {
		if(!this.contains(p))
			return null;
		
		Point p2_droite_y=new Point(p.x, p.y-10);
		Segment droite_y = new Segment(p, p2_droite_y);
		
		ListCirculairePoints l = this.points.clone();
		
		Segment inter;
		Point p_inter;
		float d1;
		float d2 = droite_y.distance();
		
		for(int i=0; i<l.length; i++) {
			p_inter = l.get(i);
			inter = new Segment(p, p_inter);
			d1 = inter.distance();
			p_inter.valeur = (float) Math.acos(droite_y.produitScalaire(inter)/(d1*d2));
			if(droite_y.produitVectoriel(inter)>0)
				p_inter.valeur = 360 - (float) (p_inter.valeur*360/(2.0*Math.PI));
			else
				p_inter.valeur = (float) (p_inter.valeur*360/(2.0*Math.PI));
			
			p_inter.valeur2 = d1;
		}
		
		l.setPoints(Fusion.trie_fusion_by_valeur(l.getPoints()));
			
		return l;
	}
	
	public boolean contains(Point p) {

		//On crée un segment en fonction du point le plus loin
		//de notre point p appartenant au polygone.
		Point loin=null;
		Iterator<PointLier> it = points.iterator();
		Point p_temp=null;
		while(it.hasNext()) {
			p_temp = it.next();
			if (loin==null||p_temp.distance(p)>loin.distance(p)) loin=p_temp;
		}
		
		//Ce segment est légérement décalé et allongé.
		loin=loin.amplidecal(new Segment(p,loin),(float)0.5);
		Segment s=new Segment(p,loin);

		Point p1;
		Point p2;
		boolean change=true;
		int ncollisions=0;
		//Tant que il y a un changement de la droite on recommence.
		while (change) {
			change=false;
			ncollisions=0;
			//On teste pour tous les segments du polygone.
			for (int i = 0; i < points.length; i++) {
				
				p1 = points.get(i);
				p2 = points.get(points.suivant(i));
				
				Segment temp = new Segment(p1, p2);
				
				//Si le point initial appartient au segment on renvoit true;
				if(temp.contains(p))
					return true;
					
				//Si le segment s est parralèle au segment, on
				//modifie s et on recommence le test à zero.
				if (s.produitVectoriel(temp) == 0){ 
					change=true;
				}else{
					//Sinon On récupère le point d'intersection 
					Point intersect = temp.intersection(s);
					//Si il y a une intersection.
					if (intersect != null) {
						//On vérifie si l'intersection  est sur un point du segment
						//considéré.
						if (intersect.equals(p1) || intersect.equals(p2)){
							change = true;
						}else
							//Sinon on continue et on ajoute une collision.
							ncollisions++;
					}
					
				}
				
				//On doit changer la droite.
				if(change){
					loin = loin.amplidecal(new Segment(p, loin),(float)0.5);
					s = new Segment(p, loin);
					break;
				}
			}
		}
		return ncollisions%2==1;
	}
	
	public Polyedre enveloppe_convexe(){
		if(this.points.length<3)
			return null;
		
		ListCirculairePoints ordonnee = points.clone();
		//Point temp = null;
		
		ordonnee.setPoints(Fusion.trie_fusion(ordonnee.getPoints()));
		/*
		//On trie en fonction des abcisses.
		int count=0;
		for(int i=0; i<this.points.length-1; i++){
			for(int j=i+1; j<this.points.length; j++){
				
				//Dans un cas égal on veut garder le plus haut.
				if(ordonnee.get(i).x==ordonnee.get(j).x
				&& ordonnee.get(i).y< ordonnee.get(j).y){
					temp = ordonnee.get(i).copy();
					ordonnee.setValue(ordonnee.get(j), i);
					ordonnee.setValue(temp, j);				
				}
				//Sinon on veut garder le plus a gauche.
				if(ordonnee.get(i).x>ordonnee.get(j).x){
					temp = ordonnee.get(i).copy();
					ordonnee.setValue(ordonnee.get(j), i);
					ordonnee.setValue(temp, j);	
				}
				count++;
			}
		}
		System.out.println(count);*/
		ArrayDeque<Point> res = new ArrayDeque<Point>();
		ArrayDeque<Point> en_cours = new ArrayDeque<Point>();
		res.add(ordonnee.get(0));
		
		//De gauche à droite.
		for(int i=1; i<ordonnee.length; i++){
			//Si il est plus bas que notre point de la liste convexe
			//forcément on l'intégre.
			if(ordonnee.get(i).y <= res.getLast().y){
				res.add(ordonnee.get(i));
				en_cours.clear();
			}else{
				//Si il n'est pas plus bas que nos points de la liste
				//convexe, on supprime tous les points précédent qui
				//était plus haut que lui dans la liste en cours.
				if(!en_cours.isEmpty()&&en_cours.getLast().y>=ordonnee.get(i).y)
					while(!en_cours.isEmpty()&&en_cours.getLast().y>=ordonnee.get(i).y)
						en_cours.removeLast();
				
				//Dans tous les cas on l'ajoute à la liste en cours.
				en_cours.add(ordonnee.get(i));
			}
		}
		//Quand on a finit on doit rajouter à res la liste en cours.
		res.addAll(en_cours);
		//On réinitialise.
		en_cours.clear();
		
		//De droite à gauche.
		for(int i=ordonnee.length-2; i>=0; i--){
			//Si il est plus haut que notre point de la liste convexe
			//forcément on l'intégre.
			if(ordonnee.get(i).y >= res.getLast().y){
				res.add(ordonnee.get(i));
				en_cours.clear();
			}else{
				//Si il n'est pas plus bas que nos points de la liste
				//convexe, on supprime tous les points précédent qui
				//était plus bas que lui dans la liste en cours.
				if(!en_cours.isEmpty()&&en_cours.getLast().y<=ordonnee.get(i).y)
					while(!en_cours.isEmpty()&&en_cours.getLast().y<=ordonnee.get(i).y)
						en_cours.removeLast();
				
				//Dans tous les cas on l'ajoute à la liste en cours.
				en_cours.add(ordonnee.get(i));
			}			
		}
		//Quand on a finit on doit rajouter à res la liste en cours.
		res.addAll(en_cours);
		
		Iterator<Point> it = res.iterator();
		Point[] nouveau = new Point[res.size()];
		int i=0;
		while(it.hasNext()){
			nouveau[i++]=it.next();
		}
		
		return new Polyedre(nouveau);
	}
	
	private static boolean testOreille(Polyedre poly, PointLier p){
		
		Point p0 = p.precedent;
		Point p2 = p.suivant;
		Point p_inter;
		Polyedre inter = new Polyedre(new Point[] {p0, p ,p2});
		
		for(int j=0; j<poly.points.length; j++) {
			p_inter = poly.points.get(j);
			if(p_inter != p0 && p_inter != p && p_inter != p2
					&& inter.contains(poly.points.get(j)))
				return false;
		}
		return true;
	}
	
	/**
	 * Certify that a vertex is convex in a polyhedron.
	 * @param poly the polyhedron which contains the vertex.
	 * @param pos the position of the vertex in the polyhedron.
	 * @return a boolean true if convex.
	 */
	private static boolean testConvexe(Polyedre poly, Integer pos) {
		
		Point p0 = poly.points.get(poly.points.precedent(pos));
		Point p2 = poly.points.get(poly.points.suivant(pos));
		
		Segment s1 = new Segment(p0, poly.points.get(pos));
		Segment s2 = new Segment(poly.points.get(pos), p2);
	
		if(s1.produitVectoriel(s2)>0) return true;
		
		return false;
	}
	
	public static ArrayList<Polyedre> triangularisation(Polyedre p){
		
		ArrayList<Polyedre> res = new ArrayList<Polyedre>();
		ArrayList<PointLier> oreilles = new ArrayList<PointLier>();
		Polyedre poly = new Polyedre(p.points.clone());
		
		//We search all the ears and we test if vertex are convex.
		for(int i=0; i<poly.points.length; i++){
			
			if(poly.points.get(i).convexe=Polyedre.testConvexe(poly, i)) {
				if(Polyedre.testOreille(poly, poly.points.get(i))) {
					poly.points.get(i).valeur=i;
					oreilles.add(poly.points.get(i));
				}
			}
		}
		
		PointLier p_oreille=null;
		PointLier p_previous=null;
		PointLier p_next=null;
		
		//Remove ears one by one, building the resulting triangle at the same time.
		while(!oreilles.isEmpty()){
				
			//Remove the first index.
			p_oreille = oreilles.remove(0);
			p_previous = p_oreille.precedent;
			p_next = p_oreille.suivant;
			
			//Remove the point in the list.
			poly.points.remove(p_oreille);
			
			//If the point left and right are concave we must test if they have change.
			if(!p_previous.convexe && Polyedre.testOreille(poly, p_previous)) {
				p_previous.convexe = true;
				oreilles.add(p_previous);
			}
			if(!p_next.convexe && Polyedre.testOreille(poly, p_next)) {
				p_next.convexe = true;
				oreilles.add(p_next);
			}
			
			//Add the new triangle to the result.
			res.add(new Polyedre(new Point[]{p_previous,p_oreille,p_next}));
			
		}
		
		return res;
	}

	
}
