import java.awt.Graphics2D;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;



public class Gameplay extends GameObject {

	@Override
	public void initResources() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics2D arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(long arg0) {
		// TODO Auto-generated method stub

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
	 * @param parent
	 */
	public Gameplay(GameEngine parent){

		super(parent);
		// TODO Auto-generated constructor stub
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

}
