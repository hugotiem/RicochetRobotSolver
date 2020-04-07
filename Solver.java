package projetS4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Solver {
	
	// ATTIBUTS
	private Grille g;
	private ArrayList<Pions> pions;
	private Pions robot;
	private ArrayList<Pions> secondaryPions = new ArrayList<>();
	private ArrayList<String> currentPath = new ArrayList<>();
	private Case startCase = null;
	private Case endCase = null;
	private Pions primaryPion = null;
	private ArrayList<Case> allPaths = new ArrayList<>();
	
	// CONSTRUCTEUR
	public Solver(Grille g, ArrayList<Pions> p, Pions r) {
		this.g = g;
		this.pions = p;
		this.robot = r;
	}

	// METHODES
	
	/**
	 * JOUE SELON LES DIRECTIONS DONNEES PAR L'ALGO A*
	 * @param finalPathString l'ArrayList des directions a prendre
	 */
	
	public void algoPlay(ArrayList<String> finalPathString){
		try{
			for(String s : finalPathString){
				//Thread.sleep(500);
				//System.out.println("Color : " + this.robot.getColor() + ", Move : " + s);
				switch(s){
					case "haut": 
						this.robot.moveUp(g);
						break;
					case "bas": 
						this.robot.moveDown(g);
						break;
					case "gauche":
						this.robot.moveLeft(g);
						break;
					case "droite":
						this.robot.moveRight(g);
						break;					
					default:
						break;
				} 
				//draw.update();
			}
		} catch(Exception ex){
			System.err.println();
		}
	}		

	/**
	 * RESOU LA PARTIE
	 * @param robotsToMove la liste de robots pouvant etre deplace
	 * @param did la liste des cases deja visite
	 * @param depth la profondeur 
	 * @param currentPath la chemin optimal actuel
	 * @param previous la case precedente 
	 * @return le currentPath le plus optimal
	 */
	
	private ArrayList<String> chose(ArrayList<Pions> robotsToMove, ArrayList<Case> did, int depth, ArrayList<String> currentPath, Case previous) {
		
		try {
			if(depth < currentPath.size() - 2 || currentPath.isEmpty()) { //VERIFIE LA PROFONDEUR
				for(Pions current : robotsToMove) {
					Case caseTmp = new Case(current.getPosition()[0], current.getPosition()[1]); // CRRER UNE CASE TMP 
					
					caseTmp.setColor(current.getColor()); // RECUPERE LA COULEUR DU ROBOT ACTUEL
					caseTmp.setNumber(depth); // PERMET DE SAVOIR L'ORDE DES COUPS A JOUER GRACE A LA VALEUR DE LA PROFONDEUR
					caseTmp.previous = previous; // PERMET D'ACCEDER A LA CASE PRECEDENTE PAR LA SUITE

					if(did.isEmpty()) { //SI PREMIER PASSAGE DANS LA BOUCLE
						did.add(caseTmp);
					}
					
					ArrayList<Case> neighbors = caseTmp.getNeighbors(g, current); //OBTIENT LES CASES VOISINES D'UN PION
					System.out.println(current.getColor() + " : " + current + "    detph = " + depth + "     " + caseTmp.getColor());
					
					for(Case neighbor : neighbors) {
						int [] tmp = current.getPosition(); // RECUPERE LES COORDONNEES POUR LE REPLACER
						
						if(!did.contains(neighbor)) { // SI LE VOISIN N'EST PAS UNE CASE DEJA VISITEE
							
							did.add(neighbor); //ON GARDE LA POSITION 
							current.setPosition(neighbor.getPosition()); // ON DEPLACE LE PION A L'ENDROIT DU VOISIN

							primaryPion.setArray(robotsToMove); //ON INDIQUE LA NOUVELLE POSITION DU ROBOT LORS DU DEPLACEMENT DU ROBOT PRINCIPAL
							ArrayList<String> neighborPath = this.execAStar(startCase, endCase, primaryPion); // TEST A* APRES DEPLACEMENT 
							
							if((currentPath.size() > neighborPath.size() || currentPath.isEmpty()) && !neighborPath.isEmpty()) { // SI PATH APRES DEPLACEMENT PLUS PETIT
								currentPath = neighborPath;  
								neighbor.previous = caseTmp; 
								neighbor.setColor(current.getColor());
								neighbor.setNumber(depth + 1);
								
								if(neighbor != null) { 
									this.allPaths = new ArrayList<>();
									this.allPaths.add(neighbor);
									while(neighbor.previous != null) {
										allPaths.add(neighbor.previous);
										neighbor = neighbor.previous;
									}
								}
							}
							currentPath = chose(robotsToMove, did, depth + 1, currentPath, caseTmp); // LANCE UNE RECURSIVITE

						}
						current.setPosition(tmp);// REPLACE LE PION A CA PLACE D'ORIGINE
					} 
					
				}
			}
		} catch (Exception e) {
			System.err.println("ERROR : " + e);
		}
		return currentPath;
	}
	
	public void solve(){

		//INITIALISATION
		ArrayList<String> finalPathString = null; 
		String winColor = ""; 

		//ON TROUVE LA CIBLE
		for(Pions p : pions){
			if(p.getType().equals("target") && !p.getColor().equals("black")){ 
				endCase = p.getCase();
				winColor = p.getColor();
			}
		}

		//ON TROUVE LE ROBOT QUI DOIT ALLER SUR LA CIBLE
		for(Pions p : pions){
			if(p.getType().equals("robot")){ 
				if(p.getColor().equals(winColor)) {
					startCase = p.getCase();
					primaryPion = p;
				}//ON RECUPERER LES ROBOTS SECONDAIRES
				else {
					secondaryPions.add(p);}
			}
		}
		this.currentPath = execAStar(startCase, endCase, primaryPion);
			
		ArrayList<Pions> robotsToMove = this.secondaryPions;
		
		finalPathString = chose(robotsToMove, new ArrayList<>(), 0, this.currentPath, null); // RECUPERE LE CHEMIN OPTIMAL
		
		// AFFICHE LES DEPLACEMENTS A FAIRE
		if(!this.allPaths.isEmpty()) {
			for(Case c : this.allPaths) {
				System.out.println(c.getColor() + " : " + c + " (coup n°" + c.getNumber() + ")");
			}
		}
		
		System.out.println("Le solveur a trouve : " + finalPathString);	
//		for(Map.Entry<String, ArrayList<String>> e : this.allPaths.entrySet()) {
//			System.out.println(e.getKey() + " : " + e.getValue());
//		}
		System.out.println("Realisable en : " + finalPathString.size() + " coups");	
		
		//this.algoPlay(finalPathString);
	}

	public ArrayList<String> execAStar(Case startCase, Case endCase, Pions robotTmp){
		AlgoritmA algo = new AlgoritmA(this.g, startCase, endCase, robotTmp);
		algo.AStar();
		ArrayList<Case> finalPath = algo.getPath();
		return algo.trad(finalPath);
	}
}