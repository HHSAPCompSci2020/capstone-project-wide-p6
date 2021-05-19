

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import processing.core.PApplet;

public class PauseMenu extends JPanel implements KeyListener{
	
	Main w;

	public PauseMenu(Main w) {
		super();
		
		this.w = w;
		setSize(w.getWidth(), w.getHeight());
		JLabel text = new JLabel("Press P to unpause");
        add(text);
        JLabel text2 = new JLabel("Press P to unpause");
        add(text2);
        JSlider slider = new JSlider();
        add(slider);
		setOpaque(true);
		addKeyListener(this);
	}
	/*
	 * paints the picture
	 * @param g where the pic will be drawn 
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(150, 150, 150, 50));
        Rectangle r = g.getClipBounds();
        g.fillRect(r.x, r.y, r.width, r.height);
        g.drawString("Press P to unpause", 100, 100);
       
    }


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_P) {
			w.changePanel(1);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_P) {
			w.changePanel(1);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_P) {
			w.changePanel(1);
		}
		
	}
}