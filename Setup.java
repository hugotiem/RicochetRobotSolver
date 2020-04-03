package projetS4;
import java.util.Random;
//import java.util.List;
//import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

public class Setup {

	// ATTRIBUTS
	protected Random randomGenerator;
	protected Grille grille;
	protected ArrayList<Pions> pions;

	// CONSTRUCTEUR
	public Setup(){
		this.randomGenerator = new Random();
		this.grille = new Grille(); 
		this.pions = new ArrayList<>();
	}

	// GETTER
	public Grille getGrille() {
		return this.grille;
	}
	
	public ArrayList<Pions> getPions() {
		return this.pions;
	}

	// FONCTION SETUP
	public void setup() {
		
		// REACTUALISE LES ATTRIBUTS 
		this.randomGenerator = new Random();
		this.grille = new Grille(); 
		this.pions = new ArrayList<>();
		
		//Integer [] ordre = new Integer [] {0, 1, 2, 3};; // tableau range selon l'ordre des grilles.

		//List<Integer> l = Arrays.asList(ordre);
		//java.util.Collections.shuffle(l);

		/*** INITIALISATION ***/
		
		// INIT GRILLE
		this.grille.buildGrille();

		// INIT PIONS
		String [] colors = {"rouge", "vert", "jaune", "bleu"};

		Pions pion = new Pions(0, 0, "", "");
		
		// REPRESENTE LE CARRE NOIR AU MILIEU DE LA GRILLE
		for(int [] carre : this.grille.getCarre()) {
			pion = new Pions(carre[0], carre[1], "black", "target");
			this.pions.add(pion);
		}
		
		switch (this.randomGenerator.nextInt(4)) {
			case 0:
				pion = new Pions(9, 10, "jaune", "target");
				break;
			case 3:
				pion = new Pions(3, 12, "vert", "target");
				break;
			case 2:
				pion = new Pions(6, 3, "bleu", "target");
				break;
			case 1:
				pion = new Pions(14, 5, "rouge", "target");
				break;
			default:
				break;
		}
		
		this.grille.setObj(pion.getColor());
		this.pions.add(pion);

		for(String color : colors){
			pion = new Pions(randomGenerator.nextInt(16), randomGenerator.nextInt(16), color, "robot");
			pion.setPion(this.pions, this.randomGenerator);
			pion.setArray(this.pions);
			this.pions.add(pion);
		}
		
		// INIT MURS
		int [][] murL = {{0}};
		int [][] murH = {{0}};
		
		try{
			murL = new int [][] {{0, 5}, {0, 8}, {1, 9}, {2, 12}, {3, 0}, {3, 11}, {4, 6}, {5, 7}, {6, 2}, {6, 13}, {7, 6}, {7, 8}, {8, 6}, {8, 8}, {9, 1}, {9, 10}, {10, 2}, {11, 6}, {11, 12}, {13, 8}, {14, 5}, {15, 6}, {15, 10}};
			murH = new int [][] {{5, 0}, {12, 0}, {2, 1}, {5, 2}, {9, 2}, {6, 3}, {14, 5}, {4, 6}, {6, 7}, {8, 7}, {10, 7}, {5, 8}, {6, 8}, {8, 8}, {12, 9}, {1, 10}, {9, 10}, {2, 12}, {10, 12}, {5, 13}, {11, 13}, {4, 15}, {12, 15}};
				
			this.grille.initMurs(murL, murH);
			
		} catch (Exception e){
			System.err.println("ERROR: " + e);
		}
		
		// COPIE LA GRILLE
		//Grille grilleTmp = new Grille();
		//ROBOT DE DEPART
//		Pions robot = null;
//		//CASE DEPART ET ARRIVEE
//		Case startCase = null;
//		Case endCase = null;
//		String winColor = ""; 
//		for(Pions p : pions){
//			if(p.getType().equals("target") && !p.getColor().equals("black")){ 
//				endCase = p.getCase();
//				winColor = p.getColor();
//			}
//		}
//		for(Pions p : pions){
//			if(p.getType().equals("robot") && p.getColor().equals(winColor)){ 
//				startCase = p.getCase();
//				robot = p;
//				System.out.println(p.getColor() + ", " + p.toString());
//			}
//		}
//
//		ArrayList<Pions> pTmp = this.pions;
//		robot.setArray(pTmp);
//		
//		//A*
//		// System.out.println(startCase.getPosition()[0] + "," + startCase.getPosition()[1]);
//		// System.out.println(endCase.getPosition()[0] + "," + endCase.getPosition()[1]);
//		AlgoritmA algo = new AlgoritmA(grilleTmp, startCase, endCase, robot);
//		algo.AStar();
//		ArrayList<Case> finalPath = algo.getPath();
//		//ArrayList<String> finalPathString = this.trad(finalPath);
//		//System.out.println(finalPathString);		
	}

}
