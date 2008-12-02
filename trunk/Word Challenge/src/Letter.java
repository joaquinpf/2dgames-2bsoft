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
	 * valor de la ficha.
	 */
	private char value;
	
	/**
	 * Constructor.
	 * 
	 * @param letImage imagen de la ficha
	 * @param sizePercentage proporcion
	 */
	public Letter(final BufferedImage letImage, final BufferedImage letBright,
			final char valor, final int size) { 
		super();
		this.value = valor;
		setImage(AdvanceImageUtil.drawString(letImage, letBright, size,this.value));
		this.setImmutable(true);
		this.setActive(true);
		
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
	 * Getter de lapropiedad isVisible.
	 * 
	 * @return isVisible state
	 */
	public final boolean isVisible() {
		return this.isActive();
	}
	
		
	/**
	 * Muestra/oculta el valor de la ficha,
	 * de acuerdo al valor de la propiedad isVisible.
	 * Si isVisible = false, mostrara la ficha con el valor,
	 * e invertira isVisible para reflejar el actual estado.
	 * Si isVisible = true, ocultara el valor e invertira isVisible.
	 * 
	 */
	public final void setVisible(final boolean visible) {
		this.setActive(visible);
	}

}
