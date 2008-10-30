import com.golden.gamedev.object.sprite.AdvanceSprite;



public class Card extends AdvanceSprite implements Comparable<Card> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @uml.property  name="value"
	 */
	private String value = "";

	/**
	 * Getter of the property <tt>value</tt>
	 * @return  Returns the value.
	 * @uml.property  name="value"
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Setter of the property <tt>value</tt>
	 * @param value  The value to set.
	 * @uml.property  name="value"
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 
	 */	
	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @uml.property  name="turned"
	 */
	private boolean turned = true;

	/**
	 * Getter of the property <tt>turned</tt>
	 * @return  Returns the turned.
	 * @uml.property  name="turned"
	 */
	public boolean isTurned() {
		return turned;
	}

	/**
	 * Setter of the property <tt>turned</tt>
	 * @param turned  The turned to set.
	 * @uml.property  name="turned"
	 */
	public void setTurned(boolean turned) {
		this.turned = turned;
	}

		
		/**
		 */
		public void turnCard(){
		}

}
