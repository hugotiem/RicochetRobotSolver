package projetS4;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DrawCase extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Case cases;
	private ArrayList<Pions> p;
	
	public DrawCase(Case cases, ArrayList<Pions> p) {
		this.cases = cases;
		this.p = p;
	}
	
	/**
	 * @param color le string de la couleur voulu
	 * @return le static Color correspondant
	 */
		
	private Color getColor(String color) {
		switch(color) {
			case "rouge":
				return Color.RED;
			case "vert":
				return Color.GREEN;
			case "jaune":
				return Color.YELLOW;
			case "bleu":
				return Color.BLUE;
			default:
				return Color.BLACK;
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//DESSINE LA GRILLE
		super.paintComponent(g);
		g.drawRect(0, 0, Draw.HEIGHT/16, Draw.WIDTH/16);

		//DESSINE LES PIONS
		if(!p.isEmpty()) { // SI L'ARRAYLIST DE PIONS N'EST PAS VIDE

			for(Pions p : this.p) {
				if(this.cases.equals(p.getPosition())) {
					g.setColor(this.getColor(p.getColor()));
					switch(p.getType()) {
						case "robot":
							// RAJOUTE UN CONTOUR NOIR AUX ROBOTS
							g.setColor(Color.BLACK);
							g.fillOval(1, 1, Draw.HEIGHT/16-2, Draw.WIDTH/16-2);
							
							// DESSINNE LES ROBOTS
							g.setColor(this.getColor(p.getColor()));
							g.fillOval(3, 3, Draw.HEIGHT/16-6, Draw.WIDTH/16-6);
							break;
						case "target": // DESSINE LA CIBLE
							g.fillRect(1, 1, Draw.HEIGHT/16, Draw.WIDTH/16);
							break;
					}	
				}
			}
		}
		
		//DESSINE LES MURS
		for(int i = 0; i < cases.getMurs().length; i++) {
			g.setColor(Color.BLACK);
			if(cases.getMurs()[i]) // SI LE COTE DE LA CASE POSSEDE UN MUR
				switch(i) {
					case 0: // DESSINE LE MUR DU HAUT
						g.fillRect(0, 0, Draw.WIDTH/16, 5);
						break;
					case 1: // DESSINE LE MUR DE DROITE
						g.fillRect(Draw.WIDTH/16-5, 0, 5, Draw.HEIGHT/16);
						break;
					case 2: // DESSINE LE MUR DU BAS
						g.fillRect(0, Draw.HEIGHT/16-5, Draw.WIDTH/16, 5);
						break;
					case 3: // DESSINE LE MUR DE GAUCHE
						g.fillRect(0, 0, 5, Draw.HEIGHT/16);
				}
		}
	}

}
