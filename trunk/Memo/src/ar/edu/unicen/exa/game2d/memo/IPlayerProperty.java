package ar.edu.unicen.exa.game2d.memo;

import java.io.Serializable;

/**
 * Interfaz que define el comportamiento de toda propiedad de un jugador.<BR/>
 * Las mismas consitiran de un identificador de propiedad y un valor que
 * cuantifica la propiedad.
 * 
 * @author lito
 */
public interface IPlayerProperty extends Serializable {
	
	/**
	 * Incrementa el valor de la propiedad amount veces.
	 * 
	 * @param amount la cantidad a incrementar.
	 */
	void incValue(final float amount);
	
	/**
	 * Decrementa el valor de la propiedad amount veces.
	 * 
	 * @param amount la cantidad a decrementar.
	 */
	void decValue(final float amount);
	
	/**
	 * Retorna el id de la propiedad.
	 * 
	 * @return el id de la propiedad.
	 */
	String getId();
	
	/**
	 * Setea el id de la propiedad.
	 * 
	 * @param id el id con el que se va a setear esta propiedad.
	 */
	void setId(final String id);
	
	/**
	 * Retorna el valor de la propiedad.
	 * 
	 * @return el valor de la propiedad.
	 */
	float getValue();
	
	/**
	 * Setea el valor de la propiedad con el nuevo valor pasado como parametro.
	 * 
	 * @param value el nuevo valor para setearle a la propiedad.
	 */
	void setValue(final float value);
	
}
