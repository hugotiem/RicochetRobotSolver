package projetS4;

import java.util.ArrayList;

public class Solver {
	
	private Grille g;
	private ArrayList<Pions> pions;
	private Pions robot;
	private ArrayList<Pions> robots;
	
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
	
	public void solve(){
		//INITIALISATION
		ArrayList<String> finalPathString = null; 
		Pions robotTmp = null;
		Case startCase = null;
		Case endCase = null;
		String winColor = ""; 
		for(Pions p : pions){
			if(p.getType().equals("target") && !p.getColor().equals("black")){ 
				endCase = p.getCase();
				winColor = p.getColor();
			}
		}
		for(Pions p : pions){
			if(p.getType().equals("robot")){ 
				if(p.getColor().equals(winColor)) {
					startCase = p.getCase();
					robotTmp = p;
					//System.out.println(p.getColor() + ", " + p.toString());
				}
				else 
					this.robots.add(p);
			}
		}
		ArrayList<Pions> pionsTmp = this.pions;
		robotTmp.setArray(pionsTmp);
		//A*
		AlgoritmA algo = new AlgoritmA(this.g, startCase, endCase, robotTmp);
		algo.AStar();
		ArrayList<Case> finalPath = algo.getPath();
		finalPathString = algo.trad(finalPath);
		System.out.println(finalPathString.size());
		if(!finalPathString.isEmpty()){
			System.out.println("Le solveur a trouve : " + finalPathString);	
			System.out.println("Realisable en : " + finalPathString.size() + " coups");	
			this.algoPlay(finalPathString);
		}
		else{
			System.out.println("Le solveur n'a pas trouve de solution en déplacant seulement le robot principal");
			//this.algoFinal();
		}			
	}
}
