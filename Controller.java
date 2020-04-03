	package projetS4;

	import java.awt.event.KeyEvent;
	import java.awt.event.KeyListener;
	import java.util.ArrayList;

	public class Controller implements KeyListener {
		
		// ATTRIBUTS
		private Grille g;
		private Pions robot;
		private ArrayList<Pions> pions;
		private Draw draw;
		private boolean gameOver = false;
		
		// CONSTRUCTEUR
		Controller(Grille g, ArrayList<Pions> pions, Draw draw){
			this.g = g;
			this.pions = pions;
			this.draw = draw;
			this.setRobot();
			this.draw.setWin("   Robot selectionne : Robot " + this.robot.getColor());
		}
		
		//INITIALISE LE ROBOT (PAR DEFAUT : meme couleur que la cible)
		private void setRobot() {
			for(Pions p : this.pions) {
				if(p.getType() == "robot" && g.getObjColor() == p.getColor())
					this.robot = p;
			}
		}
		
		// FONCTION QUI VERIFIE SI LA PARTIE EST TERMINE
		private void gameOver() {
			for(Pions p : this.pions) {
				if(p.gameOver(this.pions)) {
					this.gameOver = true;
				}
			}
		}

		// public void algoFinal(Case startCase, Case endCase){
		// 	//La case courante et la cible
		// 	Case current = endCase;

		// 	//On prends les voisins de la case courante
		// 	Pions tmpP = new Pions(current.getPosition()[0], current.getPosition()[1], "white", "robot");
		// 	ArrayList<Case> currentNeighbors = current.getNeighbors(this.g, tmpP);

		// 	//Pour chaque case voisine on compte le nombre de mur
		// 	for(Case neighbor : currentNeighbors){
		// 		if(neighbor.countWalls() < 2){ //Si il y a 1 mur alors il faut placer un robot sur une case adjacente pour faire obstacle
		// 			ArrayList<Case> neighborAdjacentTab = neighbor.getAdjacent(this.g, tmpP); //On recupere les cases adjacentes
		// 			for(Case neighborAdjacent : neighborAdjacentTab){
		// 				if(neighborAdjacent.countWalls() < 2){ //On regarde si il est possible d'y enmener un robot, ici 1 mur donc non
		// 					//il faut continuer a chercher les voisins des voisins dont algo recursif
		// 					this.algoFinal();
		// 				}
		// 				else{ //Sinon on peut y amener un robot
		// 					//On utilise A* sur le robot le plus proche pour faire obstacle

		// 					//On utilise A* sur le robot principal après avoir creer l'obstacle grace a un autre robot
		// 					this.solve();
		// 				}
		// 			}
		// 		}
		// 		else{ //une solution est possible
		// 			//A* sur le robot principal
		// 			this.solve();
		// 		}
		// 	}
		// }

		@Override
		public void keyPressed(KeyEvent e) {
			// SI LA PARTIE N'EST PAS TERMINE
			if(!this.gameOver) {
				for (Pions p : this.pions) {
					if(p.getType() == "robot") {	
						// PERMET DE CHOISIR LE ROBOT QUE L'ON VEUT DEPLACER
						switch (e.getKeyCode()) {
			
							case KeyEvent.VK_R: // TOUCHE 'R'
								if(p.getColor() == "rouge")
									this.robot = p;
								break;
							
							case KeyEvent.VK_V: // TOUCHE 'V'
								if(p.getColor() == "vert")
									this.robot = p;
								break;
								
							case KeyEvent.VK_J: // TOUCHE 'J'
								if(p.getColor() == "jaune")
									this.robot = p;
								break;
								
							case KeyEvent.VK_B: // TOUCHE 'B'
								if(p.getColor() == "bleu")
									this.robot = p;
								break;
							
							default:
								break;
						}
					}
				}
				// AFFICHE LE ROBOT SELECTIONE
				draw.setWin("   Robot selectionne : Robot " + this.robot.getColor());
			}		
			//LANCE UNE NOUVELLE PARTIE
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				// REINITIALISE LE JEU
				Setup setup = new Setup();
				draw.updateSetup(setup);	
				// RECUPERE CES ATTRIBUTS MIS A JOUR
				this.g = draw.getSetup().getGrille();
				this.pions = draw.getSetup().getPions();
				// REDEFINI LE ROBOT PAR DEFAUT
				this.setRobot();
				this.draw.setWin("   Robot selectionne : Robot " + this.robot.getColor());
				//REMET LE BOOLEEN A false
				this.gameOver = false; 
			}
		}
		
		//

		@Override
		public void keyReleased(KeyEvent e){
			// SI LA PARTIE N'EST PAS TERMINE
			if(!this.gameOver) {
				if(e.getKeyCode() == KeyEvent.VK_S) { // SI "S" PRESSEE ALORS SOLVEUR S ACTIVE
					Solver solver = new Solver(g, pions, robot);
					solver.solve();
				}	
				// JOUE SUIVANT LA TOUCHE
				try {
					if(e.getKeyCode() == KeyEvent.VK_UP) {// DEPLACE VERS LE HAUT
						this.robot.moveUp(g);
					}
					if(e.getKeyCode() == KeyEvent.VK_DOWN) { 	// DEPLACE VERS LE BAS 
						this.robot.moveDown(g);
					}
					if(e.getKeyCode() == KeyEvent.VK_RIGHT) { // DEPLACE VERS LA DROITE
						this.robot.moveRight(g);
					}
					if(e.getKeyCode() == KeyEvent.VK_LEFT) { // DEPLACE VERS LA GAUCHE 
						this.robot.moveLeft(g);
					}
				} catch (Exception ex) {
					System.err.println();
				}				
				// VERIFIE SI LA PARTIE EST TERMINE
				this.gameOver();
				if(this.gameOver) {
					this.draw.setWin("   BRAVO !!!       Pressez \"Entrer\" pour recommencer.");
				}
			} 
			// ACTUALISE LA FRAME
			this.draw.update();
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

	}
