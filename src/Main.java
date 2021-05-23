/**Author: Shelby, Ethan Wang
 * Main class used to run the project.
 */

import javax.swing.*;
import java.awt.*;
/*
 * the Main class represents the class that will run the game.
 */
import java.awt.event.KeyEvent;

	
public class Main extends JFrame {

	private JPanel cardPanel;
	
	private MainMenu panel1;    
	private DrawingSurface panel2;
	private PauseMenu panel3;
	
	public int jump = KeyEvent.VK_W;
	public int left = KeyEvent.VK_A;
	public int right = KeyEvent.VK_D;
	public int dive = KeyEvent.VK_S;
	public int light = KeyEvent.VK_Q;
	public int heavy = KeyEvent.VK_E;
	public int dash = KeyEvent.VK_SPACE;
	
	public Main(String title) {
		super(title);
		setBounds(100, 100, 800, 600);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    cardPanel = new JPanel();
	    CardLayout cl = new CardLayout();
	    cardPanel.setLayout(cl);
	    
		panel1 = new MainMenu(this);    
	    panel2 = new DrawingSurface(this);
	    panel3 = new PauseMenu(this);
	    panel2.init();
	    
	    cardPanel.add(panel1,"1");
	    cardPanel.add(panel2,"2");
	    this.setGlassPane(panel3);
	    
	    add(cardPanel);
	
	    setVisible(true);
	    
	}

	public static void main(String[] args)
	{
		Main w = new Main("Slash Dash");
	}

	public void changePanel(int panel) {
		switch(panel) {
			case 0:
				((CardLayout)cardPanel.getLayout()).first(cardPanel);
				panel1.requestFocus();
				break;
			case 1:
				panel3.setVisible(false);
				((CardLayout)cardPanel.getLayout()).show(panel2.getParent(), "2");
				panel2.requestFocus();
				panel2.unpause();
				break;
			case 2:
				panel3.requestFocus();
				panel3.setVisible(true);
				panel3.repaint();
				break;
		}
			
		
	}
  
}