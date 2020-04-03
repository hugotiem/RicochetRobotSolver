package projetS4;

import java.util.ArrayList;

public class Case{

	// ATTRIBUTS
	protected int [] location;
	protected boolean [] murs = {false, false, false, false};
	private int f, g, h;
	protected Case previous;

	public Case(int x, int y) {
		this.location = new int [] {x, y};
		this.f = 0;
		this.g = 0;
		this.h = 0;
	}

	/**** FONCTIONS MODIFICATIONS ATTRIBUTS ****/

	/**
	* Place les murs de chaque case et renvoie une exception si le mur demande n'existe pas;
	* @param c l'index correspondant a un mur;
	* @throws Exception si l'index est superieur a la taille du tableau;
	*/
	
	public void setMurs(int c) throws Exception{
		if(c > 3 || c < 0)
			throw new Exception("impossible d'ajouter ce mur, l'index " + c + " n'existe pas. Celui ci dois etre compris entre 0 et 3 (inclu).");
		this.murs[c] = true;
	}

	/**
	* @param index l'index de la valeur a modifier;
	* @param valeur la nouvelle valeur;
	*/

	//public void setPosition(int index, int valeur){
	//	this.location[index] = valeur;
	//}
	
	public void setF(int f) {
		this.f = f;
	}
	
	public void setG(int g) {
		this.g = g;
	}
	
	public void setH(int h) {
		this.h = h;
	}

	/**** FONCTIONS OBTENTION VALEURS ATTRIBUTS ****/

	/**
	* @return la position de la case;
	*/

	public int [] getPosition(){
		return this.location;
	}

	/**
	* @return les murs la case;
	*/

	public boolean [] getMurs(){
		return this.murs;
	}
	
	public int getF() {
		return this.f;
	}
	
	public int getG() {
		return this.g;
	}
		
	public int getH() {
		return this.h;
	}
	
	public ArrayList<Case> getNeighbors(Grille g, Pions p){
		ArrayList<Case> neighbors = new ArrayList<Case>();
		p.setPosition(this.location);
		try {
			p.moveDown(g);
			neighbors.add(g.getCase(p.getPosition()));
			p.setPosition(this.location);
							
			p.moveUp(g);
			neighbors.add(g.getCase(p.getPosition()));
			p.setPosition(this.location);
			
			p.moveRight(g);
			neighbors.add(g.getCase(p.getPosition()));
			p.setPosition(this.location);

			p.moveLeft(g);
			neighbors.add(g.getCase(p.getPosition()));
			p.setPosition(this.location);
		} catch (Exception e) {
			System.err.println("ERROR" + e);
		}
		return neighbors;
	}

	// public ArrayList<Case> getCaseNeighbors(Grille g, Pions p){
	// 	ArrayList<Case> neighbors = new ArrayList<Case>();
	// 	p.setPosition(new int[] {this.location[0], this.location[1]});

	// 	boolean stop = false;
	// 	//UP
	// 	while(!stop){
	// 		int x = this.location[0] - 1;
	// 		int y = this.location[1];
	// 		if(this.getMurs()[0] || p.isRobotHere(x, y)){
	// 			stop = true;
	// 		}
	// 		neighbors.add(new Case(x, y));
	// 	}

	// 	stop = false;
	// 	//DOWN
	// 	while(!stop){
	// 		int x = this.location[0] + 1;
	// 		int y = this.location[1];
	// 		if(this.getMurs()[2] || p.isRobotHere(x, y)){
	// 			stop = true;
	// 		}
	// 		neighbors.add(new Case(x, y));
	// 	}
	// 	stop = false;
	// 	//RIGHT
	// 	while(!stop){
	// 		int x = this.location[0];
	// 		int y = this.location[1] + 1;
	// 		if(this.getMurs()[1] || p.isRobotHere(x, y)){
	// 			stop = true;
	// 		}
	// 		neighbors.add(new Case(x, y));
	// 	}
	// 	stop = false;
	// 	//LEFT
	// 	while(!stop){
	// 		int x = this.location[0];
	// 		int y = this.location[1] - 1;
	// 		if(this.getMurs()[3] || p.isRobotHere(x, y)){
	// 			stop = true;
	// 		}
	// 		neighbors.add(new Case(x, y));
	// 	}

	// 	return neighbors;
	// }

	// public ArrayList<Case> getAdjacent(Grille g, ArrayList<Pions> pTab){
	// 	ArrayList<Case> adjacent = new ArrayList<Case>();
		
	// 	int x = this.location[0];
	// 	int y = this.location[1];
		
	// 	if(!this.murs[0]){
	// 		adjacent.add(new Case(x - 1, y));
	// 	}
	// 	if(!this.murs[2]){
	// 		adjacent.add(new Case(x + 1, y));
	// 	}
	// 	if(!this.murs[1]){
	// 		adjacent.add(new Case(x, y + 1));
	// 	}
	// 	if(!this.murs[3]){
	// 		adjacent.add(new Case(x, y - 1));
	// 	}
	// 	return adjacent;
	// }

	public int countWalls(){
		int cpt = 0;
		for(boolean m : this.murs){
			if(m){
				cpt++;
			}
		}
		return cpt;
	}
	
	/**
	* @param position la position testee;
	* @return vrai si les coordonnees entre la position de la case et la position testee sont les memes;
	*/

	public boolean equals(int [] position){
		return java.util.Arrays.equals(position, this.location);
	}
	
	@Override
	public String toString() {
		return "("+this.location[0]+","+this.location[1]+")";
	}
}
