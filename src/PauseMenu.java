/** @Author Shelby
 *  The exact same as the OptionsPanel class but renamed. I will edit this later to be more of a main menu.
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import processing.core.PApplet;

public class PauseMenu extends JPanel implements KeyListener {
	
	Main w;
	PApplet p;
	public PauseMenu(Main w) {
		super();
		this.w = w;
		p = new PApplet();
		JLabel text = new JLabel("Press P to unpause");
        add(text);
		setOpaque(false);
		addKeyListener(this);
	}
	
	public void paintComponent(Graphics g) {
		setBackground(Color.BLACK);
        g.setColor(new Color(0, 0, 0, 100));
        Rectangle r = g.getClipBounds();
        g.fillRect(r.x, r.y, r.width, r.height);
        g.drawString("Press P to unpause", 100, 100);
        super.paintComponent( w.getGraphics());
    }


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == e.VK_P) {
			w.changePanel(1);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}}