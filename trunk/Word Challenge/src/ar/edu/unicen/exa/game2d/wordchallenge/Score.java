package ar.edu.unicen.exa.game2d.wordchallenge;
import java.util.ArrayList;
import java.util.Hashtable;

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
	 * Propiedad para guardar las reglas de puntaje.
	 */
	private Hashtable<Integer, ArrayList<Integer>> matches;
	
	/**
	 * 
	 */
	private static final int POINTS = 0;
	
	/**
	 * 
	 */
	private static final int BONUS_TIME = 1;
	
	/***
	 * Propiedad privada para registrar el puntaje necesario para habilitar
	 * una nueva palabra.
	 */
	private int mPointsNewLetters = 0;
	
	/**
	 * Constructor de la clase.
	 */
	public Score() {
		matches = new Hashtable<Integer, ArrayList<Integer>>();
	}
		
	/**
	 * Agrega una tupla "cantidad de letras, puntaje, tiempo extra"
	 * @param amount cantidad de letras para este puntaje
	 * @param points puntaje para esta cantidad de letras
	 * @param bonusTime el tiempo que se agrega por acierto de esta
	 * cantidad de letras
	 */
	public final void addPoints(final int amount, final int points, 
			final int bonusTime) {
		ArrayList<Integer> aux = new ArrayList<Integer>();
		aux.add(points);
		aux.add(bonusTime);
		matches.put(amount, aux);		
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
	public final int getPoints(final int xLengthWord) {
		return matches.get(new Integer(xLengthWord)).get(POINTS);
	}
	
	/**
	 * Retorna el Tiempo correspondiente al largo pasado de una palabra
	 * descubierta.
	 *  
	 * @param xLengthWord largo en letras de una palabra
	 * @return int con el Tiempo correspondiente
	 */
	public final int getTime(final int xLengthWord) {
		return matches.get(new Integer(xLengthWord)).get(BONUS_TIME);
	}

	/**
	 * Devuelve las reglas de puntaje.
	 * 
	 * @return Hashtable<Integer, ArrayList<Integer>> matches
	 */
	public Hashtable<Integer, ArrayList<Integer>> getMatches() {
		return matches;
	}

	/**
	 * Setea las reglas de puntaje.
	 *  
	 * @param  matches
	 */
	public void setMatches(Hashtable<Integer, ArrayList<Integer>> matches) {
		this.matches = matches;
	}

	/**
	 * Getter de la variable POINTS
	 * 
	 * @return int points
	 */
	public static int getPOINTS() {
		return POINTS;
	}

	/**
	 * Getter de la variable BONUS_TIME
	 * 
	 * @return int bomus_time
	 */
	public static int getBONUS_TIME() {
		return BONUS_TIME;
	}
}
