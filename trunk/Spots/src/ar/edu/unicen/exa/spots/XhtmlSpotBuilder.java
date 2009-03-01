package ar.edu.unicen.exa.spots;

import java.io.File;

import org.xhtmlrenderer.simple.XHTMLPanel;

/** 
 * Implementacion de AbstractSpotBuilder para XHTML
 * 
 * @author Joaquín Alejandro Pérez Fuentes
 */
public class XhtmlSpotBuilder extends AbstractSpotBuilder {

	public XhtmlSpotBuilder(String name, Integer width, Integer height) {
		super(name, width, height);
	}

	@Override
	public void setBackground(String input) {
		try {
			XHTMLPanel panel = new XHTMLPanel();
			panel.setDocument(new File(input));
			panel.setBounds(0, 0, output.getWidth(), output.getHeight());
			output.getContentPane().add(panel);
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
}

