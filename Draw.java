package projetS4;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Draw extends JFrame {

	private static final long serialVersionUID = 1L;
	public static final int HEIGHT = 800;
	public static final int WIDTH = 800;
	
	private DrawCase cases;
	private Setup setup;
	private JPanel content;
	private String win = "";
	private Controller controller;
	
	public Draw(Setup setup) {
		super("Ricochet Robot");
		this.setup = setup;
		this.setup.setup();
		
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    this.setLayout(new BorderLayout());
	    this.setResizable(false);
	    this.setVisible(true);
	    
	    //CREER UNE INSTANCE JPANEL POUR LA GRILLE
	    content = new JPanel();
	    content.setLayout(new GridLayout(16, 16));
	    
	    //CREER UNE INSTANCE CONTROLLER ET L'AJOUTE A LA FRAME 
	    controller = new Controller(this.setup.getGrille(), this.setup.getPions(), this);
	    this.addKeyListener(controller);
	} 
	
	public void setWin(String s) {
		this.win = s;
	}
	
	public Setup getSetup() {
		return this.setup;
	}
	
	public void updateSetup(Setup s) {
		s.setup();
		this.setup = s;
	}
 
	public void draw() throws IndexOutOfBoundsException{
		
		try {	
			
			Grille grille = this.setup.getGrille();
			for(int i = 0; i < grille.getHeight(); i++) {
				for(int j = 0; j < grille.getWidth(); j++) {				
					cases = new DrawCase(grille.getGrille()[i][j], setup.getPions());
					cases.setPreferredSize(new Dimension(WIDTH/16, HEIGHT/16));
					content.add(cases);
				}	
			}
			
			JLabel label = new JLabel(win);
			label.setFont(new Font("Arial", Font.BOLD, 30));
			label.setPreferredSize(new Dimension(WIDTH, 50));
			
			// AJOUTE LE CONTENT ET LE LABEL A LA FRAME
			this.getContentPane().add(content);
			this.getContentPane().add(label, BorderLayout.SOUTH);
			
			this.pack();
			
		} catch(Exception e) {
			System.err.println(e);
		}
	}
	
	public void update() {
		this.content.removeAll();
		this.getContentPane().removeAll();
		this.draw();
	}
}


