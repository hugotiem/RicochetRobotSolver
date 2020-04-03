package projetS4;

import java.util.ArrayList;
import java.util.Random;

public class Pions {
	protected int [] position;
	protected String color;
	protected String type;
	private ArrayList<Pions> pions;

	/**
	* CONSTRUCTEUR PIONS *
	* @param x la coordonnee x du pion;
	* @param y la coordonnee y du pion;
	* @param color la couleur du pion;
	* @param type le type du pion;
	*/

	public Pions(int x, int y, String color, String type) {
		this.color = color;
		this.type = type;
		this.position = new int [] {x, y};
	}

	// GETTER
	public String getColor() {
		return this.color;
	}

	public int [] getPosition() {
		return this.position;
	}
	
	public String getType(){
		return this.type;
	}
	public Case getCase(){
		return new Case(this.position[0], this.position[1]);
	}
	
	//SETTER
	
	public void setPosition(int [] position){
		this.position = position;
	}

	public void setArray(ArrayList<Pions> p) {
		this.pions = p;
	}
	/**
	* @param p la liste des pions;
	* @param randomGenerator l'instance de la class Random;
	*/

	public void setPion(ArrayList<Pions> p, Random randomGenerator){
		if(!this.isEmpty(p)){
			this.position[0] = randomGenerator.nextInt(16);
			this.position[1] = randomGenerator.nextInt(16);
		}
	}
	
	// FONCTIONS BOOLEANS
	
	/**
	* @param pions la liste des pions;
	* @return vrai si le pion est sur une case vide;
	*/

	private boolean isEmpty(ArrayList<Pions> pions){
		for(Pions p : pions){
			if(this.equals(p.getPosition())){
				return false;
			}
		}
		return true;
	}

	public boolean equals(int [] position){
		return java.util.Arrays.equals(position, this.position);
	}
	
	public boolean gameOver(ArrayList <Pions> pions){
		if(this.getType() == "target") {
			for(Pions p : pions) {
				if(this.equals(p.getPosition()) && p.getType() == "robot" && p.getColor() == this.getColor())
					return true;
			}
		}		
		return false;
	}
	
	public boolean isRobotHere(int x, int y) {
		for(Pions p : this.pions) {
			if(p.getType() == "robot" && p.equals(new int [] {x, y}))
				return true;
		}
		return false; 
	}
	// MOVE PIONS 
	
	public void moveUp(Grille g) throws Exception { 
		boolean stop = false;
		while(!stop) {
			int x = this.position[0] - 1; int y = this.position[1];
			if(g.getCase(this.position).getMurs()[0] || this.isRobotHere(x, y)) 
				stop = true;
			else 
				this.setPosition(new int [] {x, y});
		} 
	}
	
	public void moveDown(Grille g) throws Exception {
		boolean stop = false;
		while(!stop) {
			int x = this.position[0] + 1; int y = this.position[1];
			if(g.getCase(this.position).getMurs()[2] || this.isRobotHere(x, y))
				stop = true;
			else
				this.setPosition(new int [] {x, y});
		}
	}
	
	public void moveRight(Grille g) throws Exception {
		boolean stop = false;
		while(!stop) {
			int x = this.position[0]; int y = this.position[1] + 1;
			if(g.getCase(this.position).getMurs()[1] || this.isRobotHere(x, y))
				stop = true;
			else 
				this.setPosition(new int [] {x, y});
		}
	}
	
	public void moveLeft(Grille g) throws Exception {
		boolean stop = false;
		while(!stop) {
			int x = this.position[0]; int y = this.position[1] - 1;
			if(g.getCase(this.position).getMurs()[3] || this.isRobotHere(x, y))
				stop = true;
			else 
				this.setPosition(new int [] {x, y});
		}
	}
	
	// TOSTRING
	@Override
	public String toString() {
		return this.getPosition()[0] + ", " + this.getPosition()[1];
	}
}
