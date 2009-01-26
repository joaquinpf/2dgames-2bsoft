import java.util.List;


/**
 * 
 */

/**
 * @author Administrador
 *
 */
public interface I2DGame {

	List<String> getStats();
	String getScore();
	void setStartStage(int stage);
	void setTimeToPlay(float time);
	boolean isPlaying();
	void execute();
	String getID();
	void setId(String id);
	
	
}
