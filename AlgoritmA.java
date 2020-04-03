package projetS4;

import java.util.ArrayList;
import java.util.Collections;

public class AlgoritmA {

	private Grille g;
	private Case [][] grille;
	private ArrayList<Case> openSet,  closedSet, path;
	private Case start, end;
	private Pions p;
	
	public AlgoritmA (Grille g, Case startCase, Case endCase, Pions p) {
		this.g = g;
		this.grille = g.getGrille();
		this.start = grille[startCase.getPosition()[0]][startCase.getPosition()[1]];
		this.end = grille[endCase.getPosition()[0]][endCase.getPosition()[1]];
		this.p = p;
		this.openSet = new ArrayList<Case>();
		this.closedSet = new ArrayList<Case>();
		this.path = new ArrayList<Case>();
		this.openSet.add(this.start);
	} 
	
	public ArrayList<Case> getPath() {
		return this.path;
	}
	
	private int heuristic(Case a, Case b) {
		int [] x = a.getPosition();
		int [] y = b.getPosition();
		return (int) Math.sqrt(Math.pow(((double) y[0] - (double) x[0]), 2) + Math.pow(((double) y[1] - (double) x[1]), 2));
	}
	
	private void setPath(Case current){
		Case tmp = current;
		this.path.add(tmp);
		while(tmp.previous != null) {
			path.add(tmp.previous);
			tmp = tmp.previous;
		}
	}
	
	// CHANGE LE PATH CASE PAR CASE PAR UNE LISTE DES MOUVEMENTS A EFFECTUER
		public ArrayList<String> trad(ArrayList<Case> array){
			Collections.reverse(array);
			ArrayList<String> res = new ArrayList<String>();
			for(int i = 0; i < array.size() - 1; i++){
				Case elt = array.get(i);
				Case eltB = array.get(i+1);
				int x = elt.getPosition()[0]; 
				int y = elt.getPosition()[1];
				int x2 = eltB.getPosition()[0];
				int y2 = eltB.getPosition()[1];
				if(x2 > x && y == y2){
					res.add("bas");
				}
				if(x2 < x && y == y2){
					res.add("haut");
				}
				if(y2 > y && x == x2){
					res.add("droite");
				}
				if(y2 < y && x == x2){
					res.add("gauche");
				}
			}
			int finalSize = res.size();
			for(int i = 1; i < finalSize; i++) {
				//System.out.println(res.get(i-1) + ", " + res.get(i));
				if(res.get(i).equals("droite") && res.get(i-1).equals("gauche")) {
					res.remove(i-1);
					i--;
					finalSize--;
				}
				if(res.get(i).equals("gauche") && res.get(i-1).equals("droite")) {
					res.remove(i-1);
					i--;
					finalSize--;
				}
				if(res.get(i).equals("bas") && res.get(i-1).equals("haut")) {
					res.remove(i-1);
					i--;
					finalSize--;
				}
				if(res.get(i).equals("haut") && res.get(i-1).equals("bas")) {
					res.remove(i-1);
					i--;
					finalSize--;
				}
			}
			return res;
		}
	
	public void AStar() {
		int winner = 0;
		int counter = 1;
		while(!this.openSet.isEmpty())  {
			counter++;
			for(int i = 0; i < this.openSet.size(); i++) {
				if(winner == openSet.size()){
					winner--;
				}
				if(openSet.get(i).getF() < openSet.get(winner).getF()) {
					winner = i;
				}
			}
			Case current = openSet.get(winner);
			if(current == this.end) {
				this.setPath(current);
				System.out.println("Finit, " + counter + " noeuds explores"); 
				break;
			}
			this.openSet.remove(current);
			this.closedSet.add(current);
			ArrayList<Case> neighbors = current.getNeighbors(g, p);
			for (int i = 0; i < neighbors.size(); i++) {
				
				Case neighbor = neighbors.get(i);
				
				if (!closedSet.contains(neighbor)) {
					int tmpG = current.getG() + 1;
					
					if (openSet.contains(neighbor)) {
						if(tmpG < neighbor.getG()) 
							neighbor.setG(tmpG);
					} else {
						neighbor.setG(tmpG);
						openSet.add(neighbor);
					}	
					neighbor.setH(this.heuristic(neighbor, end));
					neighbor.setF(neighbor.getG() + neighbor.getH());
					neighbor.previous = current;
				}
			}
		}	
		this.p.setPosition(this.start.getPosition());
	}
}
