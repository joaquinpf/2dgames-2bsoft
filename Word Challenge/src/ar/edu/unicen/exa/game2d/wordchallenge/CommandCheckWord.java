package ar.edu.unicen.exa.game2d.wordchallenge;
/*
* Classname CommandCheckWord.java
*
* Version 1.0
*
* Date 16/11/2008
*
* Copyright UD3 - Word Challenge (c) 
* 
*/

/**
 * Comando utilizado por la interfaz para verificar si la palabra constru�da
 * es una palabra correcta seg�n el diccionario utilizado.
 * 
 * @author Luis Soldavini y Pablo Melchior
 * @version 1.0
 * 
 */
public class CommandCheckWord extends AbstractCommandLevel {

	/**
	 * Constructor del CommandCheckWord.
	 * 
	 * @param xLevel  Nivel donde se est� ejecutando.
	 */
	public CommandCheckWord(final Level xLevel) {
		super();
		this.setLevel(xLevel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Level.AbstractCommandLevel#execute()
	 */
	/**
	 * Ejecuta la verificacion para comprobar si existe
	 * la palabra en el diccionario.
	 */
	public void execute() {
		getLevel().checkWord();
	}
} // CommandCheckWord
