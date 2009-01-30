/**
 * 
 */
package ar.edu.unicen.exa.game2d.bigballs;

/**
 * Clase que describe una propiedad del jugador. Esta propiedad tiene un id que
 * la identifica y un valor asociado.
 * 
 * @author lito
 */
public class PlayerProperty implements IPlayerProperty {

	private static final long serialVersionUID = -4619034912899041445L;

	public static final String PP_GLOBAL_SCORE = "GLOBAL_SCORE";

	public static final String PP_MONEY = "MONEY";

	/** El identificador de la propiedad. */
	protected String id;

	/** El valor de la propiedad. */
	protected float value;

	/**
	 * Incrementa el valor de la propiedad amount veces.
	 * 
	 * @param amount
	 *            la cantidad a incrementar.
	 */
	public void incValue(final float amount) {
		this.value += amount;
	}

	/**
	 * Decrementa el valor de la propiedad amount veces.
	 * 
	 * @param amount
	 *            la cantidad a decrementar.
	 */
	public void decValue(final float amount) {
		this.value -= amount;
	}

	/**
	 * Retorna el id de la propiedad.
	 * 
	 * @return el id de la propiedad.
	 */
	public final String getId() {
		return id;
	}

	/**
	 * Setea el id de la propiedad.
	 * 
	 * @param id
	 *            el id con el que se va a setear esta propiedad.
	 */
	public final void setId(final String id) {
		this.id = id;
	}

	/**
	 * Retorna el valor de la propiedad.
	 * 
	 * @return el valor de la propiedad.
	 */
	public final float getValue() {
		return value;
	}

	/**
	 * Setea el valor de la propiedad con el nuevo pasado como parametro.
	 * 
	 * @param value
	 *            el nuevo valor para setearle a la propiedad.
	 */
	public final void setValue(final float value) {
		this.value = value;
	}
}