package ar.edu.unicen.exa.spots;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/** 
 * Subclase de JButton utilizada en los spots para tener acceso directo al dialogo padre
 * desde los Listener.
 * 
 * @author Joaquín Alejandro Pérez Fuentes
 */
public class SpotButton extends JButton {

	private static final long serialVersionUID = 1L;
	protected Object parentframe;
	
	public SpotButton(String text) {
	 	super(text);
	}
	public SpotButton(ImageIcon i) {
	 	super(i);
	}
	public Object getParentframe() {
		return parentframe;
	}
	public void setParentframe(Object output) {
		this.parentframe = output;
	}	
}
