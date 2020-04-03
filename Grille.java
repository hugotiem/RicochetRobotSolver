package projetS4;

public class Grille {
	// ATTRIBUTS
	protected Case [][] grille;
	protected int [][] carre;
	protected int width, height;
	protected String Objectif;

	public Grille() {
		this.width = this.height = 16;
		this.grille = new Case [this.width][this.height];
		this.carre = new int [][] {{7, 7}, {7, 8}, {8, 7}, {8, 8}};
	}
	
	// GETTER 
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public Case [][] getGrille(){
		return this.grille;
	}

	
	public int [][] getCarre(){
		return this.carre;
	}
	
	public void setObj(String obj){
		this.Objectif = obj;
	}
	
	public String getObj() {
		return this.Objectif;
	}
	
	/**
	* Creer une grille de cases;
	*/

	public void buildGrille() {
		Case c = new Case(0, 0);
		for(int i = 0; i < this.height; i++) {
			for(int j = 0; j < this.width; j++) {
				c = new Case(i, j);
				this.grille[i][j] = c;
			}
		}
	}

	/**
	* Place les murs sur la grille;
	* @param mursL les murs se trouvant a gauche ou a droite d'une case;
	* @param mueqH les murs se trouvant en haut ou en bas d'une case;
	* @param nb la position de la grille;
	* @throws Exception venant de la fonction getCase();
	*/

	public void initMurs(int [][] mursL, int [][] mursH) throws Exception {

		for(int i = 0; i < this.height; i++){
			for(int j = 0; j < this.width; j++) {
				if(i == 0)
					this.grille[i][j].setMurs(0);
				if(i == 15)
					this.grille[i][j].setMurs(2);
				if(j == 0)
					this.grille[i][j].setMurs(3);
				if(j == 15)
					this.grille[i][j].setMurs(1);
			}
		}

		for(int [] m : mursL){
			this.getCase(m).setMurs(1);
			if(m[1]+1 < 16){
				int [] murL = new int [] {m[0], m[1]+1};
				this.getCase(murL).setMurs(3);
			}
		}
		
		for(int [] m : mursH){
			this.getCase(m).setMurs(2);
			if(m[0]+1 < 16){
				int [] murH = new int [] {m[0]+1, m[1]};
				this.getCase(murH).setMurs(0);
			}
		}
	}

	/**
	* Permet d'obtenir une case de la grille;
	* @param p les coordonnees de la case voulu;
	* @param g un ArrayList des cases de la grille;
	* @throws Exeception si aucune cases n'a pour coordinnees p;
	*/

	public Case getCase(int [] p) throws Exception {
		for(int i = 0; i < this.height; i++){
			for(int j = 0; j < this.width; j++) {
				if(this.grille[i][j].equals(p)) // si les coordonnees sont identiques;
					return this.grille[i][j];
			}
		}
		throw new Exception("les coordonnees [" + p[0] + ", " + p[1] + "] ne correspondent pas a des cases de la grille. ");
	}
}
