package ar.edu.unicen.exa.spots;

import javax.swing.ImageIcon;

/** 
 * Implementacion de AbstractSpotBuilder para imagenes
 * 
 * @author Joaquín Alejandro Pérez Fuentes
 */
public class ImageSpotBuilder extends AbstractSpotBuilder {

	public ImageSpotBuilder(String name, Integer width, Integer height) {
		super(name, width, height);
	}

	@Override
	public void setBackground(String input) {
	    ImagePanel panel = new ImagePanel(new ImageIcon(input).getImage());
	    output.getContentPane().add(panel);
	}
}
