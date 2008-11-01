

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.PlayField;

import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import java.util.Collection;
/**
 * 
 */


public class Level extends GameObject implements Observer {

	/**
	 * @param parent
	 */
	public Level(GameEngine parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @uml.property  name="levelComplete"
	 */
	private boolean levelComplete = false;

	/**
	 * Getter of the property <tt>levelComplete</tt>
	 * @return  Returns the levelComplete.
	 * @uml.property  name="levelComplete"
	 */
	public boolean isLevelComplete() {
		return levelComplete;
	}

	/**
	 * Setter of the property <tt>levelComplete</tt>
	 * @param levelComplete  The levelComplete to set.
	 * @uml.property  name="levelComplete"
	 */
	public void setLevelComplete(boolean levelComplete) {
		this.levelComplete = levelComplete;
	}

	/**
	 * @uml.property  name="playfield"
	 */
	private PlayField playfield;

	/**
	 * Getter of the property <tt>playfield</tt>
	 * @return  Returns the playfield.
	 * @uml.property  name="playfield"
	 */
	public PlayField getPlayfield() {
		return playfield;
	}

	/**
	 * Setter of the property <tt>playfield</tt>
	 * @param playfield  The playfield to set.
	 * @uml.property  name="playfield"
	 */
	public void setPlayfield(PlayField playfield) {
		this.playfield = playfield;
	}

	/** 
	 * @uml.property name="orderedBalls"
	 */
	private Vector <Ball> orderedBalls;

	/** 
	 * Getter of the property <tt>orderedBalls</tt>
	 * @return  Returns the orderedBalls.
	 * @uml.property  name="orderedBalls"
	 */
	public Vector <Ball> getOrderedBalls() {
		return orderedBalls;
	}

	/** 
	 * Setter of the property <tt>orderedBalls</tt>
	 * @param orderedBalls  The orderedBalls to set.
	 * @uml.property  name="orderedBalls"
	 */
	public void setOrderedBalls(Vector <Ball> orderedBalls) {
		this.orderedBalls = orderedBalls;
	}

	/**
	 * @uml.property  name="levelNumber"
	 */
	private int levelNumber;

	/**
	 * Getter of the property <tt>levelNumber</tt>
	 * @return  Returns the levelNumber.
	 * @uml.property  name="levelNumber"
	 */
	public int getLevelNumber() {
		return levelNumber;
	}

	/**
	 * Setter of the property <tt>levelNumber</tt>
	 * @param levelNumber  The levelNumber to set.
	 * @uml.property  name="levelNumber"
	 */
	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}


	/**
	 */
	public void addBall(Ball newBall){
	}


	/**
	 */
	public void update(){
	}

	/** 
	 * @uml.property name="ball"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="level:Ball"
	 */
	private Collection ball;

	/** 
	 * Getter of the property <tt>ball</tt>
	 * @return  Returns the ball.
	 * @uml.property  name="ball"
	 */
	public Collection getBall() {
		return ball;
	}

	/** 
	 * Setter of the property <tt>ball</tt>
	 * @param ball  The ball to set.
	 * @uml.property  name="ball"
	 */
	public void setBall(Collection ball) {
		this.ball = ball;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

	/**
	 * @uml.property  name="clock"
	 */
	private Clock clock;

	/**
	 * Getter of the property <tt>clock</tt>
	 * @return  Returns the clock.
	 * @uml.property  name="clock"
	 */
	public Clock getClock() {
		return clock;
	}

	/**
	 * Setter of the property <tt>clock</tt>
	 * @param clock  The clock to set.
	 * @uml.property  name="clock"
	 */
	public void setClock(Clock clock) {
		this.clock = clock;
	}

	/* (non-Javadoc)
	 * @see com.golden.gamedev.GameObject#initResources()
	 */
	@Override
	public void initResources() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.golden.gamedev.GameObject#render(java.awt.Graphics2D)
	 */
	@Override
	public void render(Graphics2D arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.golden.gamedev.GameObject#update(long)
	 */
	@Override
	public void update(long arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @uml.property  name="gameplay"
	 * @uml.associationEnd  inverse="level:Gameplay"
	 */
	private Gameplay gameplay;

	/**
	 * Getter of the property <tt>gameplay</tt>
	 * @return  Returns the gameplay.
	 * @uml.property  name="gameplay"
	 */
	public Gameplay getGameplay() {
		return gameplay;
	}

	/**
	 * Setter of the property <tt>gameplay</tt>
	 * @param gameplay  The gameplay to set.
	 * @uml.property  name="gameplay"
	 */
	public void setGameplay(Gameplay gameplay) {
		this.gameplay = gameplay;
	}

}
