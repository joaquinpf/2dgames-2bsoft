package ar.edu.unicen.exa.game2d.memo;
import com.golden.gamedev.object.sprite.AdvanceSprite;

/**
 * 
 * @author Carlos Mirabella
 *
 */

public class Card extends AdvanceSprite implements Comparable<Card> {

	public static final int LEFT = 1, RIGHT = 0;

	public static final int[][] animation = new int[][] { { 0, 1, 2, 3, 4}, // right animation
														{ 4, 3, 2, 1, 0 } }; // left animation

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @uml.property  name="value"
	 */
	private String value = "";

	public Card(){
		setDirection(RIGHT);
		setLoopAnim(false);
		getAnimationTimer().setDelay(100);
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
		if (o.getValue() == this.value)
			return 0;
		else
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
	public void turnCard() {
		this.turned = !this.turned;
		this.setAnimate(true);
		if (this.turned) {
			this.setDirection(LEFT);
		} else {
			this.setDirection(RIGHT);
		}
	}

	protected void animationChanged(int oldStat, int oldDir, int status,
			int direction) {
		setAnimationFrame(animation[direction]);
	}
	
	/**
	 * Duplica las cartas
	 * @return  Returns the Card.
	 */
	public Card clone() {
		Card a = new Card();
		a.setValue(this.getValue());
		a.setTurned(this.isTurned());
	    a.setDirection(this.getDirection());
	    a.setImages(this.getImages());
	    a.setStatus(this.getStatus());
		return a;
	}
	
}


