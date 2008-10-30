import com.golden.gamedev.object.Sprite;
import java.awt.image.BufferedImage;

/**
 * 
 */


public class Ball extends Sprite {

	/**
	 * @uml.property  name="value"
	 */
	private int value;

	/**
	 * Getter of the property <tt>value</tt>
	 * @return  Returns the value.
	 * @uml.property  name="value"
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Setter of the property <tt>value</tt>
	 * @param value  The value to set.
	 * @uml.property  name="value"
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @uml.property  name="description"
	 */
	private String description = "";

	/**
	 * Getter of the property <tt>description</tt>
	 * @return  Returns the description.
	 * @uml.property  name="description"
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter of the property <tt>description</tt>
	 * @param description  The description to set.
	 * @uml.property  name="description"
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @uml.property  name="spinVelocity"
	 */
	private int spinVelocity;

	/**
	 * Getter of the property <tt>spinVelocity</tt>
	 * @return  Returns the spinVelocity.
	 * @uml.property  name="spinVelocity"
	 */
	public int getSpinVelocity() {
		return spinVelocity;
	}

	/**
	 * Setter of the property <tt>spinVelocity</tt>
	 * @param spinVelocity  The spinVelocity to set.
	 * @uml.property  name="spinVelocity"
	 */
	public void setSpinVelocity(int spinVelocity) {
		this.spinVelocity = spinVelocity;
	}

		
	/**
	 */
	@Override
	public void update(long elapsedTime) {
		super.update(elapsedTime);
	}

			
	/**
	 */
	public void rotate(int degrees){
	}
	
	/**
	 */
	public Ball(int value, String text, BufferedImage ballImage, double sizePercentage){
	}

		
	/**
	 */
	private BufferedImage rezise(BufferedImage image, double sizePercentage){
		return null;
	}

			
	/**
	 */
	private BufferedImage drawString(String text, BufferedImage image){
		return null;
	}

}
