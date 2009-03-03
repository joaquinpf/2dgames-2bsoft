package ar.edu.unicen.exa.game2d.bigballs;
import java.util.List;


/**
 * 
 */

/**
 * @author Administrador
 *
 */
public interface I2DGame {

	List<PlayerStat> getStats();
	D2GameScore getScore();
	void setStartStage(int stage);
	void setTimeToPlay(float time);
	boolean isPlaying();
	void execute();
	String getID();
	void setId(String id);
	void setPlayerId(String PlayerId);
	void setRanking(Ranking ranking);
	
	
}
