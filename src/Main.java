import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

	private JPanel cardPanel;
	
	private MainMenu panel1;    
	private DrawingSurface panel2;
	
	public Main(String title) {
		super(title);
		setBounds(100, 100, 800, 600);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    cardPanel = new JPanel();
	    CardLayout cl = new CardLayout();
	    cardPanel.setLayout(cl);
	    
		panel1 = new MainMenu(this);    
	    panel2 = new DrawingSurface(this);
	    panel2.init();
	    
	    cardPanel.add(panel1,"1");
	    cardPanel.add(panel2,"2");
	    
	    add(cardPanel);
	
	    setVisible(true);
	    
	}

	public static void main(String[] args)
	{
		Main w = new Main("Slash Dash");
	}
  
	public void changePanel(int panel) { // i'll deal with this later
		switch(panel) {
			case 0:
				((CardLayout)cardPanel.getLayout()).next(cardPanel);
				panel2.requestFocus();
				break;
			case 1:
				((CardLayout)cardPanel.getLayout()).next(cardPanel);
				panel1.requestFocus();
				break;
		}
			
		
		
	}
  
}