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

public Card(){
		
	}
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
	 * Compara dos cartas para ver si son iguales
	 * @return  Returns 0 si son iguales.
	 * @uml.property  name="value"
	 */	
	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		if(o.value==this.getValue())
		return 0;
	return 1;
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
	 * Da vuelta la imagen de la carta
	 * @uml.property  name="turned"
	 */
	public void turnCard(){
		this.turned = this.isTurned();
		if (this.turned)
		{
			//add 1 to next image
			int nextFrame = getFrame() + 1;
		
			//image index will always be lower than ROTATION_IMAGES
			nextFrame = nextFrame % (getFinishAnimationFrame() + 1);
			setFrame(nextFrame);
		}
		else
		{
			int nextFrame = getFrame() - 1;
			//image index will neve be lower than zero
			if (nextFrame < 0)
			{
				//Le puse 10 pero seria el total de frames.
				nextFrame = 10 + (nextFrame % (getFinishAnimationFrame() + 1));
				setFrame(nextFrame);
			}
	    }
	setAnimate(true);
	}
	/**
	 * Duplica las cartas
	 * @return  Returns the Card.
	 * @uml.property  name="turned"
	 * @uml.property  name="value"
	 */
	public Card clone(){
		Card a = new Card();
		a.setValue(this.getValue());
		a.setTurned(this.isTurned());
		return a;
	}
	
}


