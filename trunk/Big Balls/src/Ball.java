

import com.golden.gamedev.object.Sprite;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * 
 */


public class Ball extends Sprite {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3447645079012315490L;
	/**
	 * @uml.property  name="value"
	 */
	private int value;

	/**
	 * Getter of the property <tt>value</tt>.
	 * @return  Returns the value.
	 * @uml.property  name="value"
	 */
	public final int getValue() {
		return value;
	}

	/**
	 * Setter of the property <tt>value</tt>.
	 * @param newValue  The value to set.
	 * @uml.property  name="value"
	 */
	public final void setValue(final int newValue) {
		this.value = newValue;
	}

	/**
	 * @uml.property  name="description"
	 */
	private String description = "";

	/**
	 * Getter of the property <tt>description</tt>.
	 * @return  Returns the description.
	 * @uml.property  name="description"
	 */
	public final String getDescription() {
		return description;
	}

	/**
	 * Setter of the property <tt>description</tt>.
	 * @param newDescription  The description to set.
	 * @uml.property  name="description"
	 */
	public final void setDescription(final String newDescription) {
		this.description = newDescription;
	}

	/**
	 * @uml.property  name="spinVelocity"
	 */
	private int spinVelocity;

	/**
	 * Getter of the property <tt>spinVelocity</tt>.
	 * @return  Returns the spinVelocity.
	 * @uml.property  name="spinVelocity"
	 */
	public final int getSpinVelocity() {
		return spinVelocity;
	}

	/**
	 * Setter of the property <tt>spinVelocity</tt>.
	 * @param newSpinVelocity  The spinVelocity to set.
	 * @uml.property  name="spinVelocity"
	 */
	public final void setSpinVelocity(final int newSpinVelocity) {
		this.spinVelocity = newSpinVelocity;
	}

		
	/**
	 */
	@Override
	public void update(long elapsedTime) {
		acumulatedRotation = acumulatedRotation + spinVelocity * elapsedTime / 100;
		super.update(elapsedTime);
	}

	public void render(Graphics2D g, int x, int y) {		
		// aplicar la rotacion acumulada y setear la Image del Sprite //
		this.setImage(rotate(acumulatedRotation, ballImageCopy));		
		
		super.render(g, x, y);
	}
			
	/**
	 */
	private BufferedImage ballImageCopy;
	
	/**
	 */
	private double acumulatedRotation;
	
	/**
	 */
	public Ball(int value, String text, BufferedImage ballImage, double sizePercentage){ 
		super();
		
		/* crear copia de la imagen ya escalada */
		ballImageCopy = resize(ballImage, sizePercentage);
		
		// le seteo el texto luego (para que no quede distorsionado) //
		this.drawString(text, ballImageCopy);		
		
		this.value = value;
		this.description = text;
	}
	
	private BufferedImage rotate(double angle, BufferedImage image){
		AffineTransform tx = new AffineTransform();
		tx.rotate(3.1419 / 180 * angle, image.getWidth() / 2, image.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	
		return op.filter(image, null);
	}
	
	/**
	 */
	private BufferedImage resize(BufferedImage image, double sizePercentage){
		AffineTransform tx = new AffineTransform();
		tx.scale(sizePercentage, sizePercentage);
		AffineTransformOp op = 
			new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(image, null);
	}
			
	/**
	 * @param text El texto a dibujar en la imagen
	 * @param image La imagen sobre la que se dibuja
	 */
	private void drawString(final String text, final BufferedImage image) {
		Graphics2D g2d = image.createGraphics();
		g2d.drawString(text, image.getWidth() / 2, image.getHeight() / 2);
		g2d.dispose();
	}
}
