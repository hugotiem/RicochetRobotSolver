package projetS4;

import java.util.ArrayList;

public class Solver {
	
	private Grille g;
	private ArrayList<Pions> pions;
	private Pions robot;
	private ArrayList<Pions> secondaryPions = new ArrayList<>();
	private int detph;
	private ArrayList<String> path = new ArrayList<>();
	
	public Solver(Grille g, ArrayList<Pions> p, Pions r) {
		this.g = g;
		this.pions = p;
		this.robot = r;
	}
	
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
	 */
	
	private ArrayList<String> chose(ArrayList<Pions> robotsToMove, ArrayList<int []> previous, Pions robotTmp, Case startCase, Case endCase, int detph) {
		ArrayList<String> currentPath = this.path;
		ArrayList<String> tmpPath = null;
		try {
			if(detph < 10000) {
				for(Pions current : robotsToMove) {
					ArrayList<Case> neighbors = this.g.getCase(current.getPosition()).getNeighbors(g, current);
					previous.add(current.getPosition());
					for(Case neighbor : neighbors) {
						if(!previous.contains(neighbor.getPosition())) { 
							current.setPosition(neighbor.getPosition());
							robotTmp.setArray(robotsToMove);
							ArrayList<String> neighborPath = this.execAStar(startCase, endCase, robotTmp);
							tmpPath = chose(robotsToMove, previous, robotTmp, startCase, endCase, detph + 1);
							if(!neighborPath.isEmpty() && !tmpPath.isEmpty()) {
								if(neighborPath.size() > tmpPath.size()) {
									currentPath = tmpPath;
									this.path = neighborPath;
								}
							}
							else {
								currentPath = tmpPath;
							}
						}
					} 
				}
			}
			System.out.println(currentPath.size());
		} catch (Exception e) {
			System.err.println("ERROR : " + e);
		}
		return currentPath;
	}
	
	public void solve(){

		//INITIALISATION
		ArrayList<String> finalPathString = null; 
		Pions robotTmp = null;
		Case startCase = null;
		Case endCase = null;
		String winColor = ""; 
		Pions primaryPion = null;
		
		this.detph = 0;

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
		this.path = execAStar(startCase, endCase, primaryPion);
		
		ArrayList<Pions> robotsToMove = this.secondaryPions;
		ArrayList<int []> previous = new ArrayList<>();
		finalPathString = chose(robotsToMove, previous, primaryPion, startCase, endCase, this.detph);

		System.out.println("Le solveur a trouve : " + finalPathString);	
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
