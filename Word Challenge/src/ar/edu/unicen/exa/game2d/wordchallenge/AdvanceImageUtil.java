package ar.edu.unicen.exa.game2d.wordchallenge;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;


public class AdvanceImageUtil {
	
	private AdvanceImageUtil() {
	}
	
	/**
	 * Renderiza una cadena en la imagen dada.
	 * @param image la imagen
	 * @param size tamaño de la fuente
	 * @param value letra
	 */
	public static BufferedImage drawString(final BufferedImage image, final BufferedImage imageBright, final int size, final char value) {
		Graphics2D g2d = image.createGraphics();

		//Setea el color y la fuente
		g2d.setColor(Color.black);
		g2d.setFont(new Font("Arial", Font.BOLD, size));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		int width = g2d.getFontMetrics().charWidth(value);
		int posX = (image.getWidth() / 2) - (width / 2) - 2;
		int posY = (image.getHeight() / 2) + (image.getHeight() / 3);
		g2d.drawString(String.valueOf(value), posX, posY);
		if (imageBright != null) {
			g2d.drawImage(imageBright, 0, 0, null);
		}
		g2d.dispose();
		return image;
	}
}
