package projetS4;

public class Main{

	public static void main(String [] args){
		// INITIALISE LE JEU
		Setup setup = new Setup();
		
		// AFFICHE LE JEU
		Draw GUI = new Draw(setup);
		GUI.draw();
		GUI.setLocationRelativeTo(null);
	
	}
}
