import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.golden.gamedev.object.Sprite;

/**
 * 
 */

public class Letter extends Sprite {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Fuente a utilizar sobre la pelota.
	 * @uml.property  name="ttfFont"
	 */
	private String ttfFont = "Arial";
	
	/**
	 * Tamaño de la fuente de la pelota.
	 * @uml.property  name="fontSize"
	 */
	private int fontSize = 36;
	
	/**
	 * valor de la ficha.
	 */
	private char value;
	
	/**
	 * estado de la ficha (visible/no visible).
	 */
	private boolean isVisible;
	
	/**
	 * representacion grafica de la ficha.
	 */
	private BufferedImage letterImageCopy;
	
	/**
	 * Constructor.
	 * 
	 * @param letImage imagen de la ficha
	 * @param sizePercentage proporcion
	 */
	public Letter(final BufferedImage letImage, final double sizePercentage) { 
		super();
		
		fontSize *= sizePercentage; 
		letterImageCopy = resize(letImage, sizePercentage);
		isVisible = false;
	}

	/**
	 * Getter of the property <tt>value</tt>.
	 * 
	 * @return Returns the value.
	 * @uml.property name="value"
	 */
	public final char getValue() {
		return value;
	}

	/**
	 * Setter of the property <tt>value</tt>.
	 * 
	 * @param newValue The value to set.
	 * @uml.property name="value"
	 */
	public final void setValue(final char newValue) {
		this.value = newValue;
	}

	/**
	 * Getter de lapropiedad isVisible.
	 * 
	 * @return isVisible state
	 */
	public final boolean isVisible() {
		return isVisible;
	}
	
	/**
	 * Setter de la propiedad isVisible. 
	 * Seria conveniente que fuera privado, para evitar
	 * la manipulacion del estado externamente 
	 * (que solo sea posible alterar el valor 
	 * de isVisible utilizando e metodo turnVisible).
	 * 
	 * @param newIsVisible boolean
	 */
	public final void setVisible(final boolean newIsVisible) {
		this.isVisible = newIsVisible;
	}

	/**
	 * Muestra/oculta el valor de la ficha,
	 * de acuerdo al valor de la propiedad isVisible.
	 * Si isVisible = false, mostrara la ficha con el valor,
	 * e invertira isVisible para reflejar el actual estado.
	 * Si isVisible = true, ocultara el valor e invertira isVisible.
	 * 
	 */
	public final void turnVisible() {
		if (!isVisible) {
			this.drawString(String.valueOf(value), letterImageCopy);
		} else {
			this.drawString(" ", letterImageCopy);
		}
		isVisible = !isVisible; 
	}

	/**
	 * Redimensiona la imagen a ala proporcion dada.
	 * @param image imagen a redimensionar
	 * @param sizePercentage proporcion
	 * @return la imagen redimensionada
	 */
	private BufferedImage resize(final BufferedImage image, 
			final double sizePercentage) {
		
		AffineTransform tx = new AffineTransform();
		tx.scale(sizePercentage, sizePercentage);
		AffineTransformOp op = 
			new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(image, null);
	}
	
	/**
	 * Renderiza una cadena en la imagen dada.
	 * @param val la cadena
	 * @param image la imagen
	 */
	private void drawString(final String val, final BufferedImage image) {
		Graphics2D g2d = image.createGraphics();

		//Setea el color y la fuente
		g2d.setColor(Color.black);
		g2d.setFont(new Font(ttfFont, Font.PLAIN, fontSize));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.drawString(val, image.getWidth() / 2, image.getHeight() / 2);
		g2d.dispose();
	}
}
