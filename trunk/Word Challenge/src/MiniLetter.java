import java.awt.image.BufferedImage;

import com.golden.gamedev.object.Sprite;


public class MiniLetter extends Sprite {

	/**
	 * 
	 */
	private BufferedImage letterVisible;

	/**
	 * 
	 */
	private BufferedImage letterNoVisible;
	
	/**
	 * 
	 */
	private char value;
	
	/**
	 * 
	 */
	private boolean visible;

	public MiniLetter(final BufferedImage imgNoVisible,
			final BufferedImage imgVisible, final char valor) {
		super();
		this.value = valor;
		this.letterVisible = AdvanceImageUtil.drawString(imgVisible, null, 21,value);
		this.letterNoVisible = imgNoVisible;
		this.visible = false;
		this.setImage(this.letterNoVisible);
		
	}

	/**
	 * Setea la propiedad privada visible.
	 * 
	 * @param visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
		if (this.visible) {
			this.setImage(letterVisible);
		} else {
			this.setImage(letterNoVisible);
		}
	}
	
	/**
	 * Getter de la variable visible.
	 * 
	 * @return boolean visible
	 */	
	public boolean isVisible(){
		return this.visible;
	}

	/**
	 * Getter de la variable value.
	 * 
	 * @return char value
	 */	
	public char getValue() {
		return this.value;
	}
	
	/**
	 * Setea la propiedad privada value.
	 * 
	 * @param value
	 */	
	public void setValue(char value) {
		this.value=value;
	}

	/**
	 * Getter de la variable letterVisible.
	 * 
	 * @return BufferedImage letterVisible
	 */	
	public BufferedImage getLetterVisible() {
		return letterVisible;
	}

	/**
	 * Setea la propiedad privada letterVisible.
	 * 
	 * @param letterVisible
	 */
	public void setLetterVisible(BufferedImage letterVisible) {
		this.letterVisible = letterVisible;
	}

	/**
	 * Getter de la variable letterNoVisible.
	 * 
	 * @return BufferedImage letterNoVisible
	 */	
	public BufferedImage getLetterNoVisible() {
		return letterNoVisible;
	}

	/**
	 * Setea la propiedad privada letterNoVisible.
	 * 
	 * @param letterNoVisible
	 */
	public void setLetterNoVisible(BufferedImage letterNoVisible) {
		this.letterNoVisible = letterNoVisible;
	}
	
}
