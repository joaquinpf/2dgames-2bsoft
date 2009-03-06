package ar.edu.unicen.exa.game2d.memo;
import java.util.List;


/**
 * @author Administrador
 *
 */
public interface I2DGame {
	
	/**
	 * Retorna una lista con los Stats que son premiados al ganar el minijuego.
	 * 
	 * @return Una lista con los Stats que son premiados al ganar el minijuego.
	 */
	public List<PlayerStat> getStats();
	
	/**
	 * Retorna una lista con todos los Score obteneidos durante jugadas
	 * sucesivas.
	 * 
	 * @return Una lista con todos los Score obteneidos durante jugadas
	 *         sucesivas.
	 */
	public List<D2GameScore> getScore();
	
	/**
	 * Setea el Stage en el que se comenzara a jugar el minijuego.
	 * 
	 * @param stage el Stage en el que se comenzara a jugar el minijuego.
	 */
	public void setStartStage(int stage);
	
	/**
	 * Setea el tiempo limite para jugar el minijuego o cada stage del mismo.
	 * 
	 * @param time el tiempo limite para jugar el minijuego o cada stage del
	 *        mismo.
	 */
	public void setTimeToPlay(float time);
	
	/**
	 * Verifica si el juego esta en ejecucion.
	 * 
	 * @return TRUE si se esta jugando el minijuego. FALSE en caso contrario.
	 */
	public boolean isPlaying();
	
	/**
	 * Ejecuta el minijuego.
	 */
	public void execute();
	
	/**
	 * Retorna el identificador unico del minijuego.
	 * 
	 * @return El identificador unico del minijuego.
	 */
	public String getId();
	
	/**
	 * Setea el identificador unico del minijuego.
	 * 
	 * @param id El identificador unico del minijuego.
	 */
	public void setId(String id);
	
	/**
	 * Setea el identificador del player que jugara el minijuego.
	 * 
	 * @param id El identificador del player que jugara el minijuego.
	 */
	void setPlayerId(String PlayerId);
	
	/**
	 * Setea el Ranking del minijuego.
	 * 
	 * @param id El Ranking del minijuego.
	 */
	void setRanking(Ranking ranking);
	
}
