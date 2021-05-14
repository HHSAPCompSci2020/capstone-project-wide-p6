/** @Author Shelby
 *  The exact same as the OptionsPanel class but renamed. I will edit this later to be more of a main menu.
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class MainMenu extends JPanel implements ActionListener {
	
	Main w;
	
	public MainMenu(Main w) {
		this.w = w;
		JButton button = new JButton("Press me!");
		button.addActionListener(this);
		add(button);
	}
	
	public void actionPerformed(ActionEvent e) {
		w.changePanel(1);
	}
	
}