package ar.edu.unicen.exa.game2d.wordchallenge;
/*
* Classname CommandGetSixLetters.java
*
* Version 1.0
*
* Date 16/11/2008
*
* Copyright UD3 - Word Challenge (c) 
* 
*/

/**
 * Comando utilizado por la interfaz para obtener las letras del Nivel.
 * 
 * @author Luis Soldavini y Pablo Melchior
 * @version 1.0
 * 
 */
public class CommandGetSixLetters extends AbstractCommandLevel {

	/**
	 * Constructor de la clase.
	 * 
	 * @param level  Nivel donde se está ejecutando el comando.
	 */
	public CommandGetSixLetters(final Level level) {
		super();
		this.setLevel(level);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Level.AbstractCommandLevel#execute()
	 */
	/**
	 * Ejecuta el metodo levelFinishing() de la clase Level del
	 * Word Challenge para descubrir todas las palabras. 
	 */
	public void execute() {
		this.getLevel().levelFinishing();
	}
} // CommandGetLetters
