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
 * esta forma se plantea un esquema que permite separar un poco la l�gica de la
 * presentaci�n, de la interfaz de usuario. En la interfaz se utilizar�n objetos
 * que permitir�n realizar alguna operaci�n con el nivel.
 * 
 * @author Luis Soldavini y Pablo Melchior
 * @version 1.0
 * 
 */
public abstract class AbstractCommandLevel {

	/**
	 * Variable Level para que los comandos puedan llamar un m�todo del mismo
	 * para hacer su ejecuci�n.
	 */
	private Level mLevel = null;

	/**
	 * M�todo abstracto que deben implementar los distintos comandos. Es en este
	 * m�todo donde estar� la ejecuci�n particular de cada uno de los comandos,
	 * se utilizar� a causa de un evento de la interfaz.
	 */
	public abstract void execute();

	/**
	 * Setea la variable de nivel al comando.
	 * 
	 * @param xLevel  nivel que se est� ejecutando.
	 */
	protected void setLevel(final Level xLevel) {
		this.mLevel = xLevel;
	}

	/**
	 * Devuelve el Nivel del juego.
	 * 
	 * @return level donde se est� ejecutando.
	 */
	protected Level getLevel() {
		return mLevel;
	}

} // Clase AbstractCommandLevel
