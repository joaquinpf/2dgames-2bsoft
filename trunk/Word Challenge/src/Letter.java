import com.golden.gamedev.object.Sprite;

/**
 * 
 */

public class Letter extends Sprite {

	/**
	 * @uml.property name="value"
	 */
	private char value;

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
		 */
	public void turnVisible() {
	}

}
