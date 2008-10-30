

import com.golden.gamedev.object.PlayField;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import java.util.Collection;
/**
 * 
 */


public class Level implements Observer {

	/**
	 * @uml.property  name="totalTime"
	 */
	private int totalTime;

	/**
	 * Getter of the property <tt>totalTime</tt>
	 * @return  Returns the totalTime.
	 * @uml.property  name="totalTime"
	 */
	public int getTotalTime() {
		return totalTime;
	}

	/**
	 * Setter of the property <tt>totalTime</tt>
	 * @param totalTime  The totalTime to set.
	 * @uml.property  name="totalTime"
	 */
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	/**
	 * @uml.property  name="remainingTime"
	 */
	private int remainingTime;

	/**
	 * Getter of the property <tt>remainingTime</tt>
	 * @return  Returns the remainingTime.
	 * @uml.property  name="remainingTime"
	 */
	public int getRemainingTime() {
		return remainingTime;
	}

	/**
	 * Setter of the property <tt>remainingTime</tt>
	 * @param remainingTime  The remainingTime to set.
	 * @uml.property  name="remainingTime"
	 */
	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
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
	 * @uml.property  name="PartialScore"
	 */
	private int partialScore;

	/**
	 * Getter of the property <tt>PartialScore</tt>
	 * @return  Returns the partialScore.
	 * @uml.property  name="PartialScore"
	 */
	public int getPartialScore() {
		return partialScore;
	}

	/**
	 * Setter of the property <tt>PartialScore</tt>
	 * @param PartialScore  The partialScore to set.
	 * @uml.property  name="PartialScore"
	 */
	public void setPartialScore(int partialScore) {
		this.partialScore = partialScore;
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
	public boolean isFinished(){
		return false;	
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

}
