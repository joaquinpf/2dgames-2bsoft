/*
* Classname AbstractCommandLevel.java
*
* Version 1.0
*
* Date 16/11/2008
*
* Copyright UD3 - Word Challenge (c) 
* 
*/


/**
 * Clase abstracta que debe utilizarse para todos los comandos del Nivel. De
 * esta forma se plantea un esquema que permite separar un poco la lógica de la
 * presentación, de la interfaz de usuario. En la interfaz se utilizarán objetos
 * que permitirán realizar alguna operación con el nivel.
 * 
 * @author Luis Soldavini y Pablo Melchior
 * @version 1.0
 * 
 */
public abstract class AbstractCommandLevel {

	/**
	 * Variable Level para que los comandos puedan llamar un método del mismo
	 * para hacer su ejecución.
	 */
	private Level mLevel = null;

	/**
	 * Método abstracto que deben implementar los distintos comandos. Es en este
	 * método donde estará la ejecución particular de cada uno de los comandos,
	 * se utilizará a causa de un evento de la interfaz.
	 */
	public abstract void execute();

	/**
	 * Setea la variable de nivel al comando.
	 * 
	 * @param xLevel  nivel que se está ejecutando.
	 */
	protected void setLevel(final Level xLevel) {
		this.mLevel = xLevel;
	}

	/**
	 * Devuelve el Nivel del juego.
	 * 
	 * @return level donde se está ejecutando.
	 */
	protected Level getLevel() {
		return mLevel;
	}

} // Clase AbstractCommandLevel
