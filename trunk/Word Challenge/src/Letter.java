import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.golden.gamedev.object.Sprite;
import com.sun.xml.internal.ws.util.StringUtils;

/**
 * 
 */

public class Letter extends Sprite {

	/**
	 * valor de la ficha
	 */
	private char value;
	
	/**
	 * estado de la ficha (visible/no visible)
	 */
	private boolean isVisible;
	
	/**
	 * representacion grafica de la ficha
	 */
	private BufferedImage letterImageCopy;
	
	/**
	 * Constructor.
	 * 
	 * @param letterImage imagen de la ficha
	 * @param sizePercentage proporcion
	 */
	public Letter(BufferedImage letterImage, double sizePercentage){ 
		super();
		
		letterImageCopy = resize(letterImage, sizePercentage);
		isVisible = false;
	}

	/**
	 * Getter of the property <tt>value</tt>
	 * 
	 * @return Returns the value.
	 * @uml.property name="value"
	 */
	public char getValue() {
		return value;
	}

	/**
	 * Setter of the property <tt>value</tt>
	 * 
	 * @param value
	 *            The value to set.
	 * @uml.property name="value"
	 */
	public void setValue(char value) {
		this.value = value;
	}

	/**
	 * Getter de lapropiedad isVisible.
	 * 
	 * @return isVisible state
	 */
	public boolean isVisible() {
		return isVisible;
	}
	
	/**
	 * Setter de la propiedad isVisible. 
	 * Seria conveniente que fuera privado, para evitar
	 * la manipulacion del estado externamente 
	 * (que solo sea posible alterar el valor 
	 * de isVisible utilizando e metodo turnVisible).
	 * 
	 * @param isVisible boolean
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	/**
	 * Muestra/oculta el valor de la ficha,
	 * de acuerdo al valor de la propiedad isVisible.
	 * Si isVisible = false, mostrara la ficha con el valor,
	 * e invertira isVisible para reflejar el actual estado.
	 * Si isVisible = true, ocultara el valor e invertira isVisible.
	 * 
	 */
	public void turnVisible() {
		if (!isVisible){
			this.drawString(String.valueOf(value), letterImageCopy);
		}
		else{
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
	private BufferedImage resize(final BufferedImage image, final double sizePercentage) {
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
		g2d.drawString(val, image.getWidth() / 2, image.getHeight() / 2);
		g2d.dispose();
	}
}
