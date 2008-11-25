/*
 * Classname CommandRandomizeLetters.java
 *
 * Version 1.0
 *
 * Date 16/11/2008
 *
 * Copyright UD3 - Word Challenge (c) 
 * 
 */

/**
 * Comando utilizado por la interfaz para realizar la aleatorización de las
 * letras del Nivel.
 * 
 * @author Luis Soldavini y Pablo Melchior
 * @version 1.0
 * 
 */
public class CommandRandomizeLetters extends AbstractCommandLevel {

	/**
	 * Constructor del nivel.
	 * 
	 * @param level  Nivel donde se está ejecutando
	 */
	public CommandRandomizeLetters(final Level level) {
		super();
		this.setLevel(level);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractCommandLevel#execute()
	 */
	public final void execute() {
		this.getLevel().randomLettersOrder();
	}
	
} // CommandRandomizeLetters
