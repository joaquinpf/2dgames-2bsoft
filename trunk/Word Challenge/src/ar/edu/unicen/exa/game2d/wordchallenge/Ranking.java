/**
 * Ranking.java
 * 
 * @author lito
 */
package ar.edu.unicen.exa.game2d.wordchallenge;

import java.io.Serializable;

/**
 * Esta clase modela el ranking de un juego 2d. El ranking es una lista,
 * ordenada de forma decreciente por puntaje, de personas que han jugado este
 * minujugeo.
 * 
 * @author lito
 */
public class Ranking implements Serializable {

	private static final long serialVersionUID = 2452288417081184572L;

	/** Los players del ranking, en orden decreciente. */
	protected String[] idPlayers;

	/** Los puntajes de cada player, se corresponden posicion a posicion. */
	protected int[] scores;

	/** El juego 2D al que pertenece el ranking. */
	protected String id2DGame;

	/**
	 * Constructora que recibe el tamanio del arreglo de jugadores y posiciones
	 * y el id2 del juego. Crea los 2 arreglos de la misma longitud y setea el
	 * id del juego 2d.
	 * 
	 * @param size
	 *            el tamanio de los arreglos
	 * @param id2DGame
	 *            el id del juego 2d al que pertenece este ranking.
	 */
	public Ranking(int size, String id2DGame) {
		this.idPlayers = new String[size];
		this.scores = new int[size];
		this.id2DGame = id2DGame;
	}

	/**
	 * agrega una persona y su puntaje al ranking, segun una posicion dada.
	 * Sobre-escribe los valores que estaban antes en esa posicion si los
	 * hubiera.
	 * 
	 * @param position
	 *            la posicion en la cual queremos agregar esta persona
	 * @param idPlayer
	 *            el id del jugador
	 * @param score
	 *            el puntaje
	 * @throws IndexOutOfBoundsException
	 *             is la posicion en la cual se desea insertar es mayor que la
	 *             dimension del archivo
	 */
	public void putRanking(int position, String idPlayer, int score) {

		if (position >= this.idPlayers.length)
			throw new IndexOutOfBoundsException(
					"la posicion del arreglo donde se desea insertar es mayor que la longiutd del mismo");

		this.idPlayers[position] = idPlayer;
		this.scores[position] = score;
	}

	/**
	 * indica la longitud de los arreglos que contienen los puntajes y los id de
	 * los jugadores pertenecientes al ranking
	 * 
	 * @return un int que indica la longitud de los arreglos.
	 */
	public int size() {
		return this.idPlayers.length;
	}

	/**
	 * Dados 2 arreglos que indican los id de los jugadores y sus puntajes,
	 * setea el ranking.
	 * 
	 * @param idPlayers
	 *            un arreglo con los id de los jugadores
	 * @param scores
	 *            un arreglo con los puntajes
	 * @throws IllegalArgumentException
	 *             si los tamanios de los arreglos son distintos.
	 */
	public void setRanking(String[] idPlayers, int[] scores) {

		if (idPlayers.length != scores.length) {
			throw new IllegalArgumentException(
					"Los parametros tienen que tener la misma longitud");
		}
		this.idPlayers = idPlayers;
		this.scores = scores;
	}

	/**
	 * @return el id del juego 2d al cual pertenece este ranking
	 */
	public final String getId2DGame() {
		return id2DGame;
	}

	/**
	 * Setea el id del juego 2d
	 * 
	 * @param id2DGame
	 *            el id del juego 2d que queremos setear.
	 */
	public final void setId2DGame(final String id2DGame) {
		this.id2DGame = id2DGame;
	}

	/**
	 * @return el arreglo que contiene los id de los jugadores.
	 */
	public final String[] getIdPlayers() {
		return idPlayers;
	}

	/**
	 * @return el arreglo que contiene los puntajes de los jugadores.
	 */
	public final int[] getScores() {
		return scores;
	}
}