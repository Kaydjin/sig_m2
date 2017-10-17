package base;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Fusion {

	/*entrée ː un tableau T
	sortie ː une permutation triée de T
	fonction triFusion(T[1, …, n])
	      si n ≤ 1
	              retourner T
	      sinon
	              retourner fusion(triFusion(T[1, …, n/2]), triFusion(T[n/2 + 1, …, n]))

	entrée ː deux tableaux triés A et B
	sortie : un tableau trié qui contient exactement les éléments des tableaux A et B
	fonction fusion(A[1, …, a], B[1, …, b])
	      si A est le tableau vide
	              retourner B
	      si B est le tableau vide
	              retourner A
	      si A[1] ≤ B[1]
	              retourner A[1] :: fusion(A[2, …, a], B)
	      sinon
	              retourner B[1] :: fusion(A, B[2, …, b])*/
	
	
	public static ArrayList<PointLier> trie_fusion(ArrayList<PointLier> al){
		ArrayDeque<PointLier> ad = new ArrayDeque<PointLier>();
		for(PointLier p : al)
			ad.addLast(p);

		ArrayDeque<PointLier> vertex = trie_fusion(ad);
		ArrayList<PointLier>res = new ArrayList<PointLier>();
		for(PointLier p : vertex)
			res.add(p);
		return res;
	}
	
	public static ArrayDeque<PointLier> trie_fusion(ArrayDeque<PointLier> al) {
		if(al.size()<=1)
			return al;
		else {
			int milieu = al.size()/2;
			ArrayDeque<PointLier> po = new ArrayDeque<PointLier>();
			
			for(int i=0; i<milieu; i++) 
				po.addLast(al.removeFirst());
			return fusion(trie_fusion(po), trie_fusion(al));
		}
	}
	
	private static ArrayDeque<PointLier> fusion(ArrayDeque<PointLier>l1, 
			ArrayDeque<PointLier> l2){
		if(l1.isEmpty())
			return l2;
		if(l2.isEmpty())
			return l1;
		PointLier p_inter=l1.getFirst();
		PointLier p_inter2=l2.getFirst();
		ArrayDeque<PointLier> ad_inter;

		if(p_inter.x < p_inter2.x || (p_inter.x == p_inter2.x && p_inter.y > p_inter2.y)) {
			l1.removeFirst();
			ad_inter = fusion(l1, l2);
			ad_inter.addFirst(p_inter);
			return ad_inter;
		}else {
			l2.removeFirst();
			ad_inter = fusion(l1, l2);
			ad_inter.addFirst(p_inter2);
			return ad_inter;	
		}
	}
	
	
	public static ArrayList<PointLier> trie_fusion_by_valeur(ArrayList<PointLier> al){
		ArrayDeque<PointLier> ad = new ArrayDeque<PointLier>();
		for(PointLier p : al)
			ad.addLast(p);

		ArrayDeque<PointLier> vertex = trie_fusion_by_valeur(ad);
		ArrayList<PointLier>res = new ArrayList<PointLier>();
		for(PointLier p : vertex)
			res.add(p);
		return res;
	}
	
	public static ArrayDeque<PointLier> trie_fusion_by_valeur(ArrayDeque<PointLier> al) {
		if(al.size()<=1)
			return al;
		else {
			int milieu = al.size()/2;
			ArrayDeque<PointLier> po = new ArrayDeque<PointLier>();
			
			for(int i=0; i<milieu; i++) 
				po.addLast(al.removeFirst());
			return fusion_by_valeur(trie_fusion_by_valeur(po), trie_fusion_by_valeur(al));
		}
	}
	
	private static ArrayDeque<PointLier> fusion_by_valeur(ArrayDeque<PointLier>l1, 
			ArrayDeque<PointLier> l2){
		if(l1.isEmpty())
			return l2;
		if(l2.isEmpty())
			return l1;
		PointLier p_inter=l1.getFirst();
		PointLier p_inter2=l2.getFirst();
		ArrayDeque<PointLier> ad_inter;

		if(p_inter.valeur < p_inter2.valeur || 
				(p_inter.valeur == p_inter2.valeur && p_inter.valeur2 > p_inter2.valeur2)) {
			l1.removeFirst();
			ad_inter = fusion_by_valeur(l1, l2);
			ad_inter.addFirst(p_inter);
			return ad_inter;
		}else {
			l2.removeFirst();
			ad_inter = fusion_by_valeur(l1, l2);
			ad_inter.addFirst(p_inter2);
			return ad_inter;	
		}
	}
}
