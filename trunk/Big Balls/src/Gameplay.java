import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;

/**
 * 
 */


public class Gameplay {

	/**
	 * @param parent
	 */
	public Gameplay(GameEngine _parent) {
		this.parent = _parent;
	}


	/**
	 * @uml.property  name="lives"
	 */
	private int lives;

	/** 
	 * Getter of the property <tt>lifes</tt>
	 * @return  Returns the lifes.
	 * @uml.property  name="lives"
	 */
	public int getLives() {
		return lives;
	}

	/** 
	 * Setter of the property <tt>lifes</tt>
	 * @param lifes  The lifes to set.
	 * @uml.property  name="lives"
	 */
	public void setLives(int lives) {
		this.lives = lives;
	}

	/** 
	 * Getter of the property <tt>GlobalScore</tt>
	 * @return  Returns the globalScore.
	 * @uml.property  name="globalScore"
	 */
	public int getGlobalScore() {
		return score;
	}

	/** 
	 * Setter of the property <tt>GlobalScore</tt>
	 * @param GlobalScore  The globalScore to set.
	 * @uml.property  name="globalScore"
	 */
	public void setGlobalScore(int globalScore) {
		score = globalScore;
	}

	/**
	 * @uml.property  name="currentLevel"
	 */
	private Level currentLevel;

	/**
	 * Getter of the property <tt>currentLevel</tt>
	 * @return  Returns the currentLevel.
	 * @uml.property  name="currentLevel"
	 */
	public Level getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * Setter of the property <tt>currentLevel</tt>
	 * @param currentLevel  The currentLevel to set.
	 * @uml.property  name="currentLevel"
	 */
	public void setCurrentLevel(Level currentLevel) {
		this.currentLevel = currentLevel;
	}

	/** 
	 * @uml.property name="level"
	 * @uml.associationEnd inverse="gameplay:Level"
	 */
	private Level level;

	/** 
	 * Getter of the property <tt>level</tt>
	 * @return  Returns the level.
	 * @uml.property  name="level"
	 */
	public Level getLevel() {
		return level;
	}

	/** 
	 * Setter of the property <tt>level</tt>
	 * @param level  The level to set.
	 * @uml.property  name="level"
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	/** 
	 * @uml.property name="levelGenerator"
	 * @uml.associationEnd inverse="gameplay:LevelGenerator"
	 */
	private LevelGenerator levelGenerator;

	/** 
	 * Getter of the property <tt>levelGenerator</tt>
	 * @return  Returns the levelGenerator.
	 * @uml.property  name="levelGenerator"
	 */
	public LevelGenerator getLevelGenerator() {
		return levelGenerator;
	}

	/** 
	 * Setter of the property <tt>levelGenerator</tt>
	 * @param levelGenerator  The levelGenerator to set.
	 * @uml.property  name="levelGenerator"
	 */
	public void setLevelGenerator(LevelGenerator levelGenerator) {
		this.levelGenerator = levelGenerator;
	}

	/**
	 * @uml.property  name="globalScore"
	 */
	private int score = 0;

		
	/**
	 */
	public GameObject getNextLevel(){
		return levelGenerator.generateLevel(parent);
	}


	/**
	 * @uml.property  name="parent"
	 */
	private GameEngine parent;

	/**
	 * Getter of the property <tt>parent</tt>
	 * @return  Returns the parent.
	 * @uml.property  name="parent"
	 */
	public GameEngine getParent() {
		return parent;
	}

	/**
	 * Setter of the property <tt>parent</tt>
	 * @param parent  The parent to set.
	 * @uml.property  name="parent"
	 */
	public void setParent(GameEngine parent) {
		this.parent = parent;
	}

		
	/**
	*/
	public void addPoints(int points){
		score = score + points;
	}

}
