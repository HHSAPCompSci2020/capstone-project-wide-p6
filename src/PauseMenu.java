

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import processing.core.PApplet;

public class PauseMenu extends JPanel implements KeyListener,ActionListener{
	
	Main w;
	private String action;

	private JLabel jumpt, leftt, rightt, divet, dasht, lightt, heavyt;
	private JButton jump, left, right, dive, dash, light, heavy;
	public PauseMenu(Main w) {
		super();
		this.w = w;
		setLayout(null);
		action = "";
		
		reset();
	}
	/*
	 * paints the picture
	 * @param g where the pic will be drawn 
	 */
	public void paintComponent(Graphics g) {
    }


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
		/*if(e.getKeyCode() == KeyEvent.VK_P) {
			w.changePanel(1);
			
		} */
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_P) {
			action = "";
			w.changePanel(1);			
		} else if (action.equals("setJump")){
			w.jump = e.getKeyCode();
			jumpt.setText(KeyEvent.getKeyText(e.getKeyCode()));;
			action = "";
		} else if (action.equals("setLeft")){
			w.left = e.getKeyCode();
			leftt.setText(KeyEvent.getKeyText(e.getKeyCode()));;
			action = "";
		} else if (action.equals("setRight")){
			w.right = e.getKeyCode();
			rightt.setText(KeyEvent.getKeyText(e.getKeyCode()));;
			action = "";
		} else if (action.equals("setDive")){
			w.dive = e.getKeyCode();
			divet.setText(KeyEvent.getKeyText(e.getKeyCode()));;
			action = "";
		} else if (action.equals("setLight")){
			w.light = e.getKeyCode();
			lightt.setText(KeyEvent.getKeyText(e.getKeyCode()));;
			action = "";
		} else if (action.equals("setHeavy")){
			w.heavy = e.getKeyCode();
			heavyt.setText(KeyEvent.getKeyText(e.getKeyCode()));;
			action = "";
		} 
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		/*if(e.getKeyCode() == KeyEvent.VK_P) {
			w.changePanel(1);
		}*/
		
	}
	
	public void actionPerformed(ActionEvent e) {
		action = e.getActionCommand();
		requestFocus();
	} 
	
	public void reset() {
		setLayout(null);
		action = "";
		setSize(w.getWidth(), w.getHeight());
		
		JLabel text = new JLabel("Press P to unpause");
		text.setBounds(600, 0, 125, 50);
        add(text);
        
        jumpt = new JLabel("W");
  		jumpt.setBounds(675, 100, 60, 30);
  		add(jumpt);
  		
  		leftt = new JLabel("A");
  		leftt.setBounds(675, 150, 60, 30);
  		add(leftt);
  		
  		rightt = new JLabel("D");
  		rightt.setBounds(675, 200, 60, 30);
  		add(rightt);
  		
  		divet = new JLabel("S");
  		divet.setBounds(675, 250, 60, 30);
  		add(divet);
  		
  		dasht = new JLabel("" + KeyEvent.getKeyText(KeyEvent.VK_SPACE));
  		dasht.setBounds(675, 300, 60, 30);
  		add(dasht);
  		
  		lightt = new JLabel("Q");
  		lightt.setBounds(675, 350, 60, 30);
  		add(lightt);
  		
  		heavyt = new JLabel("E");
  		heavyt.setBounds(675, 400, 60, 30);
  		add(heavyt);
              
        jump = new JButton("Jump");
        jump.setBounds(575, 100, 75, 30);
        jump.setActionCommand("setJump");
        jump.addActionListener(this);
        add(jump);
        
      
        
        left = new JButton("Left");
        left.setBounds(575, 150, 75, 30);
        left.setActionCommand("setLeft");
        left.addActionListener(this);
        add(left);
        
        
        right = new JButton("Right");
        right.setBounds(575, 200, 75, 30);
        right.setActionCommand("setRight");
        right.addActionListener(this);
        add(right);
        
        
        dive = new JButton("Dive");
        dive.setBounds(575, 250, 75, 30);
        dive.setActionCommand("setDive");
        dive.addActionListener(this);
        add(dive);
        
        dash = new JButton("Dash");
        dash.setBounds(575, 300, 75, 30);
        dash.setActionCommand("setDash");
        dash.addActionListener(this);
        add(dash);
        
        light = new JButton("Light Attack");
        light.setBounds(525, 350, 125, 30);
        light.setActionCommand("setLight");
        light.addActionListener(this);
        add(light);
        
        
        heavy = new JButton("Heavy Attack");
        heavy.setBounds(525, 400, 125, 30);
        heavy.setActionCommand("setHeavy");
        heavy.addActionListener(this);
        add(heavy);
        
		setOpaque(true);
		addKeyListener(this);
	}
}