/*
* Classname CommandDelLastLetter.java
*
* Version 1.0
*
* Date 16/11/2008
*
* Copyright UD3 - Word Challenge (c) 
* 
*/

/**
 * Comando utilizado por la interfaz para borrar la última 
 * letra seleccionada. 
 *  
 * @author Luis Soldavini y Pablo Melchior
 * @version 1.0
 * 
 */	
public class CommandDelLastLetter extends AbstractCommandLevel {

	/**
	 * Constructor del nivel.
	 * 
	 * @param level Nivel donde se está ejecutando
	 */
	public CommandDelLastLetter(final Level level) {
		super();
		this.setLevel(level);
	}		
	
	/*
	 * (non-Javadoc)
	 * @see Level.AbstractCommandLevel#execute()
	 */
	public void execute() {
		getLevel().deleteLastLetter();
	}
} //CommandDelLastLetter
