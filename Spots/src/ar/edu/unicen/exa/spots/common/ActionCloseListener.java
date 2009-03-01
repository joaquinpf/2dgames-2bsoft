package ar.edu.unicen.exa.spots.common;
import java.awt.event.*;

import javax.swing.JDialog;

import ar.edu.unicen.exa.spots.SpotButton;

public class ActionCloseListener implements ActionListener {
	public ActionCloseListener() {
			
	}

	/**
	 * Closes parent dialog when pressed.
	 */
	public void actionPerformed(ActionEvent arg0) {
		Object o = ((SpotButton)(arg0.getSource())).getParentframe();
		((JDialog) o).setVisible(false);
		((JDialog) o).dispose();
	}	
}
