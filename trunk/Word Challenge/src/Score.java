/*
* Classname Score.java
*
* Version 1.0
*
* Date 16/11/2008
*
* Copyright UD3 - Word Challenge (c)
* 
*/

/**
 * Esta clase permite establecer y obtener el puntaje.
 * Dentro del juego hay dos partes bien diferenciadas, una de ellas
 * se encarga de configurar distintos parámetros y otra es la encargada
 * de crear y mantener el juego actualizado.
 * La primera, dentro de las cosas que debe configurar es el puntaje que debe
 * dar el juego por cada palabra descubierta, dependiendo de la cantidad de
 * letras que la componen. La segunda parte necesita de esta configuración
 * para sumar el puntaje global y para ver si ya consiguió el puntaje 
 * que necesita para habilitar las nuevas letras.
 * 
 * @author Luis Soldavini , Pablo Melchior y Carlos Mirabella
 * @version 2.0
 * 
 */
public class Score {

	/**
	 * Propiedad privada para registrar el puntaje para palabras de 3 letras.
	 */
	private int mPoint3Letters = 0;
	
	/**
	 * Propiedad privada para registrar el puntaje para palabras de 4 letras.
	 */
	private int mPoint4Letters = 0;
	
	/**
	 * Propiedad privada para registrar el puntaje para palabras de 5 letras.
	 */
	private int mPoint5Letters = 0;
	
	/**
	 * Propiedad privada para registrar el puntaje para palabras de 6 letras.
	 */
	private int mPoint6Letters = 0;
	
	/**
	 * Propiedad privada para registrar el tiempo para palabras de 3 letras.
	 */
	private int mTime3Letters = 0;
	
	/**
	 * Propiedad privada para registrar el tiempo para palabras de 4 letras.
	 */
	private int mTime4Letters = 0;
	
	/**
	 * Propiedad privada para registrar el tiempo para palabras de 5 letras.
	 */
	private int mTime5Letters = 0;
	
	/**
	 * Propiedad privada para registrar el tiempo para palabras de 6 letras.
	 */
	private int mTime6Letters = 0;
	/***
	 * Propiedad privada para registrar el puntaje necesario para habilitar
	 * una nueva palabra.
	 */
	private int mPointsNewLetters = 0;
	
	/**
	 * Constructor de la clase.
	 */
	public Score() {
		super();
	}
	
	/**
	 * Constructor de la clase.
	 * 
	 * @param xPoint3Letters setea la propiedad Point3Letters
	 * @param xPoint4Letters setea la propiedad Point4Letters
	 * @param xPoint5Letters setea la propiedad Point5Letters
	 * @param xPoint6Letters setea la propiedad Point6Letters
	 * @param xPointsNewLetters setea la propiedad PointsNewLetters
	 */
	public Score(final int xPoint3Letters, final int xPoint4Letters,
		final int xPoint5Letters, final int xPoint6Letters, 
		final int xPointsNewLetters) {
			this();
		this.setPoint3Letters(xPoint3Letters);
		this.setPoint4Letters(xPoint4Letters);
		this.setPoint5Letters(xPoint5Letters);
		this.setPoint6Letters(xPoint6Letters);
		this.setPointsNewLetters(xPointsNewLetters);
		}

	/**
	 * Devuelve el puntaje para cuando se descubre una palabra de
	 * tres letras.
	 * 
	 * @return entero con el puntaje 
	 */
	public final int getPoint3Letters() {
		return mPoint3Letters;
	}

	/**
	 * Método que setea el puntaje para palabras de tres caracteres.
	 * 
	 * @param xPoint3Letters setea point3Letters
	 */
	public final void setPoint3Letters(final int xPoint3Letters) {
		mPoint3Letters = xPoint3Letters;
	}

	/**
	 * Devuelve el puntaje para cuando se descubre una palabra de
	 * cuatro letras.
	 * 
	 * @return entero con el puntaje 
	 */
	public final int getPoint4Letters() {
		return mPoint4Letters;
	}

	/**
	 * Método que setea el puntaje para palabras de cuatro caracteres.
	 * 
	 * @param xPoint4Letters setea point4Letters
	 */
	public final void setPoint4Letters(final int xPoint4Letters) {
		mPoint4Letters = xPoint4Letters;
	}

	/**
	 * Devuelve el puntaje para cuando se descubre una palabra de
	 * cinco letras.
	 * 
	 * @return entero con el puntaje 
	 */
	public final int getPoint5Letters() {
		return mPoint5Letters;
	}

	/**
	 * Método que setea el puntaje para palabras de cinco caracteres.
	 * 
	 * @param xPoint5Letters setea point5Letters
	 */
	public final void setPoint5Letters(final int xPoint5Letters) {
		mPoint5Letters = xPoint5Letters;
	}

	/**
	 * Devuelve el puntaje para cuando se descubre una palabra de
	 * seis letras.
	 * 
	 * @return entero con el puntaje 
	 */
	public final int getPoint6Letters() {
		return mPoint6Letters;
	}

	/**
	 * Método que setea el puntaje para palabras de seis caracteres.
	 * 
	 * @param xPoint6Letters setea point6Letters
	 */
	public final void setPoint6Letters(final int xPoint6Letters) {
		mPoint6Letters = xPoint6Letters;
	}

	/**
	 * Devuelve el Tiempo para cuando se descubre una palabra de
	 * tres letras.
	 * 
	 * @return entero con el tiempo 
	 */
	public final int getTime3Letters() {
		return mTime3Letters;
	}

	/**
	 * Método que setea el tiempo para palabras de tres caracteres.
	 * 
	 * @param xTime3Letters setea mTime3Letters
	 */
	public final void setTime3Letters(final int xTime3Letters) {
		mTime3Letters = xTime3Letters;
	}
	
	/**
	 * Devuelve el Tiempo para cuando se descubre una palabra de
	 * tres letras.
	 * 
	 * @return entero con el tiempo 
	 */
	public final int getTime4Letters() {
		return mTime4Letters;
	}

	/**
	 * Método que setea el tiempo para palabras de tres caracteres.
	 * 
	 * @param xTime4Letters setea mTime4Letters
	 */
	public final void setTime4Letters(final int xTime4Letters) {
		mTime4Letters = xTime4Letters;
	}
	
	/**
	 * Devuelve el Tiempo para cuando se descubre una palabra de
	 * tres letras.
	 * 
	 * @return entero con el tiempo 
	 */
	public final int getTime5Letters() {
		return mTime5Letters;
	}

	
	/**
	 * Método que setea el tiempo para palabras de tres caracteres.
	 * 
	 * @param xTime5Letters setea mTime5Letters
	 */
	public final void setTime5Letters(final int xTime5Letters) {
		mTime5Letters = xTime5Letters;
	}
	
	/**
	 * Devuelve el Tiempo para cuando se descubre una palabra de
	 * tres letras.
	 * 
	 * @return entero con el tiempo 
	 */
	public final int getTime6Letters() {
		return mTime6Letters;
	}

	/**
	 * Método que setea el tiempo para palabras de tres caracteres.
	 * 
	 * @param xTime6Letters setea mTime6Letters
	 */
	public final void setTime6Letters(final int xTime6Letters) {
		mTime6Letters = xTime6Letters;
	}	
	
	
	/**
	 * Setea el Score necesario para habilitar un nuevo juego de
	 * seis letras.
	 * 
	 * @param xPointsNewLetters setea el valor
	 */
	public final void setPointsNewLetters(final int xPointsNewLetters) {
		mPointsNewLetters = xPointsNewLetters;
	}

	/**
	 * Devuelve el puntaje necesario para habilitar un nuevo juego 
	 * de seis letras.
	 * 
	 * @return int con el puntaje
	 */
	public final int getPointsNewLetters() {
		return mPointsNewLetters;
	}	
	
	/**
	 * Retorna el puntaje correspondiente al largo pasado de una palabra
	 * descubierta.
	 *  
	 * @param xLengthWord largo en letras de una palabra
	 * @return int con el puntaje correspondiente
	 */
	public final int getPoints(int xLengthWord) {
		
		if (xLengthWord == 3) {
			return this.getPoint3Letters();
		}
		
		if (xLengthWord == 4) {
			return this.getPoint4Letters();
		}

		if (xLengthWord == 5) {
			return this.getPoint5Letters();
		}
		
		return this.getPoint6Letters();
	}
	
	/**
	 * Retorna el Tiempo correspondiente al largo pasado de una palabra
	 * descubierta.
	 *  
	 * @param xLengthWord largo en letras de una palabra
	 * @return int con el Tiempo correspondiente
	 */
	public final int getTime(int xLengthWord) {
		
		if (xLengthWord == 3) {
			return this.getTime3Letters();
		}
		
		if (xLengthWord == 4) {
			return this.getTime4Letters();
		}

		if (xLengthWord == 5) {
			return this.getTime5Letters();
		}
		
		return this.getTime6Letters();
	} 
}
