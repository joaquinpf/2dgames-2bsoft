import java.awt.Graphics2D;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;



public class Gameplay {

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
	private int globalScore;

	/**
	 * Getter of the property <tt>globalScore</tt>
	 * @return  Returns the globalScore.
	 * @uml.property  name="globalScore"
	 */
	public int getGlobalScore() {
		return globalScore;
	}

	/**
	 * Setter of the property <tt>globalScore</tt>
	 * @param globalScore  The globalScore to set.
	 * @uml.property  name="globalScore"
	 */
	public void setGlobalScore(int globalScore) {
		this.globalScore = globalScore;
	}


	/**
	 * Pasa al proximo nivel
	 * @return  Returns the GameObject.
	 * @uml.property  name="levelGenerator"
	 */
	public GameObject getNextLevel() {
		return this.levelGenerator.generateLevel(this.getParent());
	
	}

	/**
	 */
	public Gameplay(GameEngine _parent) {
		this.parent = _parent;
		LevelGenerator levelGenerator = new LevelGenerator("Resources/configGame.xml");
	}

	/** 
	 * @uml.property name="parent"
	 */
	private GameEngine parent;

	/**
	 * Suma los puntos obtenidos al puntaje global
	 * @uml.property  name="globalScore"
	 */
	public void addPoints(int points){
		this.globalScore = this.globalScore + points;
		this.setGlobalScore(globalScore);
		
	}

	/**
	 * @uml.property  name="level"
	 * @uml.associationEnd  inverse="gameplay:Level"
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

}
