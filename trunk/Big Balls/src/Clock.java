

import java.util.Observable;

public class Clock extends Observable {

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
	 * @uml.property  name="totalTime"
	 */
	private String totalTime = "";

	/**
	 * Getter of the property <tt>totalTime</tt>
	 * @return  Returns the totalTime.
	 * @uml.property  name="totalTime"
	 */
	public String getTotalTime() {
		return totalTime;
	}

	/**
	 * Setter of the property <tt>totalTime</tt>
	 * @param totalTime  The totalTime to set.
	 * @uml.property  name="totalTime"
	 */
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}


	/**
	 */
	public boolean isFinished(){
		return false;	
	}


	/**
	 */
	public void start(){
	}


	/**
	 */
	public void stop(){
	}


	/**
	 */
	private void run(){
	}

}
