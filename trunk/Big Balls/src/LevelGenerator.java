import com.golden.gamedev.GameEngine;

/**
 * 
 */


public class LevelGenerator {


	public Level generateLevel(GameEngine parent){

		return null;
	}

	/**
	 * @uml.property  name="configRoute"
	 */
	private String configRoute = "";

	/**
	 * Getter of the property <tt>configRoute</tt>
	 * @return  Returns the configRoute.
	 * @uml.property  name="configRoute"
	 */
	public String getConfigRoute() {
		return configRoute;
	}

	/**
	 * Setter of the property <tt>configRoute</tt>
	 * @param configRoute  The configRoute to set.
	 * @uml.property  name="configRoute"
	 */
	public void setConfigRoute(String configRoute) {
		this.configRoute = configRoute;
	}


	/**
	 */
	public LevelGenerator(String route){
	}

	/**
	 * @uml.property  name="currentLevel"
	 */
	private int currentLevel = 0;

	/**
	 * Getter of the property <tt>currentLevel</tt>
	 * @return  Returns the currentLevel.
	 * @uml.property  name="currentLevel"
	 */
	public int getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * Setter of the property <tt>currentLevel</tt>
	 * @param currentLevel  The currentLevel to set.
	 * @uml.property  name="currentLevel"
	 */
	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

}
