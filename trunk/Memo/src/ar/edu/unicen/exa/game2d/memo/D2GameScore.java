/**
 * 
 */
package ar.edu.unicen.exa.game2d.memo;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Clase que modela el puntaje de un juego 2D para un determinado jugador.
 * 
 * @author lito
 */
public class D2GameScore implements Serializable {

	private static final long serialVersionUID = -7872505465098004370L;

	/** El puntaje */
	protected int score;

	/** La stage o level en el que se comenzo a jugar. */
	protected int startedStage;

	/** La stage o level en el que se termino de jugar. */
	protected int reachedStage;

	/** El identificador del jugador que obtubo el puntaje. */
	protected String idPlayer;

	/** El identificador del juego 2D al que pertenece el score. */
	protected String id2DGame;

	/** Para cada idStat, se guarda el puntaje otorgado por el minijuego. */
	protected Hashtable<String, Integer> pointsPerIdPlayerStat;

	/**
	 * @return el puntaje que obtuvo en el minijuego.
	 */
	public final int getScore() {
		return score;
	}

	/**
	 * setea el puntaje del minjuego con el valor pasado en la variable score
	 * 
	 * @param score
	 *            el valor a setear.
	 * 
	 */
	public final void setScore(final int score) {
		this.score = score;
	}

	/**
	 * El nivel en el que se empezo a jugar.
	 * 
	 * @return el nivel en el que se empezo a jugar.
	 */
	public final int getStartedStage() {
		return startedStage;
	}

	/**
	 * Setea el nivel en el cual se compenzo a jugar
	 * 
	 * @param startedStage
	 *            un int que representa el nivel en el cual se comenzo.
	 */
	public final void setStartedStage(final int startedStage) {
		this.startedStage = startedStage;
	}

	/**
	 * Retorna el nivel al cual se llego al finalizar de jugar
	 * 
	 * @return El nivel al cual se llego al finalizar de jugar
	 */
	public final int getReachedStage() {
		return reachedStage;
	}

	/**
	 * setea el nivel en el cual se finalizo.
	 * 
	 * @param reachedStage
	 *            el nivel en el cual se in
	 */
	public final void setReachedStage(final int reachedStage) {
		this.reachedStage = reachedStage;
	}

	/**
	 * Retorna el id del jugador que tiene este puntaje.
	 * 
	 * @return el id del jugador.
	 */
	public final String getIdPlayer() {
		return idPlayer;
	}

	/**
	 * Setea el id del jugador asignado a este puntaje
	 * 
	 * @param idPlayer
	 *            el id del jugador
	 */
	public final void setIdPlayer(final String idPlayer) {
		this.idPlayer = idPlayer;
	}

	/**
	 * el id del juego 2d que tiene este puntaje.
	 * 
	 * @return the id del juego 2d.
	 */
	public final String getId2DGame() {
		return id2DGame;
	}

	/**
	 * Setea el id del juego 2d.
	 * 
	 * @param id2DGame
	 *            el id del juego 2d para setear.
	 */
	public final void setId2DGame(final String id2DGame) {
		this.id2DGame = id2DGame;
	}

	/**
	 * Retorna la tabla que tiene almacenado cada idStat con su puntaje.
	 * 
	 * @return the pointsPerIdPlayerStat
	 */
	public final Hashtable<String, Integer> getPointsPerIdPlayerStat() {
		return pointsPerIdPlayerStat;
	}

	/**
	 * Setea la tabla que tiene almacenado cada idStat con su respectivo
	 * puntaje.
	 * 
	 * @param pointsPerIdPlayerStat
	 *            la tabla a setear.
	 */
	public final void setPointsPerIdPlayerStat(
			final Hashtable<String, Integer> pointsPerIdPlayerStat) {
		this.pointsPerIdPlayerStat = pointsPerIdPlayerStat;
	}
}