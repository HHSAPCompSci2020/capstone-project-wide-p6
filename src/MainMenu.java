
/** @Author Shelby
 *  The exact same as the OptionsPanel class but renamed. I will edit this later to be more of a main menu.
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MainMenu extends JPanel implements ActionListener {

	Main w;
	private JLabel jumpt, leftt, rightt, divet, dasht, lightt, heavyt, jump, left, right, dive, dash, light, heavy,title, controls, control;
/**
 * 
 * @param w the Main this is part of
 */
	public MainMenu(Main w) {
		this.w = w;
		setLayout(null);

		JButton button = new JButton("Start!");
		button.addActionListener(this);
		button.setBounds(300, 50, 125, 50);
		add(button);
		
		JLabel control = new JLabel("controls");
		control.setBounds(575, 0, 125, 50);
		add(control);
		
		JLabel controls = new JLabel("Pause");
		controls.setBounds(575, 50, 125, 50);
		add(controls);
		
		JLabel title = new JLabel("Slash Dash");
		title.setBounds(330, 0, 125, 50);
		add(title);
		
		JLabel text = new JLabel("P");
		text.setBounds(675, 50, 125, 50);
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

		dasht = new JLabel("spacebar");
		dasht.setBounds(675, 300, 60, 30);
		add(dasht);

		lightt = new JLabel("Q");
		lightt.setBounds(675, 350, 60, 30);
		add(lightt);

		heavyt = new JLabel("E");
		heavyt.setBounds(675, 400, 60, 30);
		add(heavyt);

		jump = new JLabel("Jump");
		jump.setBounds(575, 100, 75, 30);
		add(jump);

		left = new JLabel("Left");
		left.setBounds(575, 150, 75, 30);

		add(left);

		right = new JLabel("Right");
		right.setBounds(575, 200, 75, 30);

		add(right);

		dive = new JLabel("Dive");
		dive.setBounds(575, 250, 75, 30);
		add(dive);

		dash = new JLabel("Dash");
		dash.setBounds(575, 300, 75, 30);
		add(dash);

		light = new JLabel("Light Attack");
		light.setBounds(525, 350, 125, 30);
		add(light);

		heavy = new JLabel("Heavy Attack");
		heavy.setBounds(525, 400, 125, 30);
		add(heavy);

	}

	/*
	 * Changes the screen to the game
	 * 
	 * @param e when action is preformed it will be inputed into here
	 */

	public void actionPerformed(ActionEvent e) {
		w.changePanel(1);
	}

}