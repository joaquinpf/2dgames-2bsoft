/*
* Classname Clock.java
*
* Version 1.0
*
* Date 12/11/2008
*
* Copyright (c) 2008 - UD3
* 
*/

//JAVA
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.Timer;

/**
 * Esta clase permite contar con una herramienta que nos indique el timeout
 * de un nivel, adem�s notifica a quienes se hayan suscripto el 
 * cambio de segundo.
 * 
 * @author Luis Soldavini y Pablo Melchior
 * @version 1.0
 */
public class Clock extends Observable {

	/**
	 * Tiempo remanente del clock.
	 * 
	 * @uml.property  name="remainingTime"
	 */
	private int remainingTime;
	
	/**
	 * Tiempo total del clock.
	 * 
	 * @uml.property  name="totalTime"
	 */
	private String totalTime = "";
	
	/**
	 * Timer privado que nos permite contar un segundo.
	 */
	private Timer timer = new Timer(1000, new ActionListener() {
	    public void actionPerformed(final ActionEvent e) {
	    	run();
	     }
	});
	
	/**
	 * M�todo utilizado al transcurrir un segundo, 
	 * 1) Actualiza la variable remainingTime
	 * 2) llama al m�todo setChanged, indicando que hubo cambios
	 * 3) llama al m�todo notifyObservers, para notificar a quienes
	 *    se hayan suscritos.
	 * 4) Verfica en cada ejecuci�n si hay tiempo remanente, si este llega
	 *    a 0 (cero) detiene el timer.
	 */
	private void run() {
		remainingTime--;
		if (remainingTime <= 0) {
			remainingTime = 0;
			timer.stop();
		}
		setChanged();
		notifyObservers(); 
	}

	/**
	 * Devuelve el valor de la variable remainingTime.
	 * 
	 * @return  Returns the remainingTime.
	 * @uml.property  name="remainingTime"
	 */
	public final int getRemainingTime() {
		return remainingTime;
	}

	/**
	 * Setea el valor de la variable remainingTime.
	 * 
	 * @param newRemainingTime  The remainingTime to set.
	 * @uml.property  name="remainingTime"
	 */
	public final void setRemainingTime(final int newRemainingTime) {		
		this.remainingTime = newRemainingTime;
	}

	/**
	 * Getter of the property <tt>totalTime</tt>.
	 * @return  Returns the totalTime.
	 * @uml.property  name="totalTime"
	 */
	public final String getTotalTime() {
		return totalTime;
	}

	/**
	 * Setter of the property <tt>totalTime</tt>.
	 * @param newTotalTime  The totalTime to set.
	 * @uml.property  name="totalTime"
	 */
	public final void setTotalTime(final String newTotalTime) {
		this.totalTime = newTotalTime;
	}

	/**
	 * Este m�todo indica si ya transcurri� el tiempo.
	 * 
	 * @return true si ya transcurri� el tiempo, 
	 *         false en caso contrario
	 */
	public final boolean isFinished() {
		return (remainingTime == 0);	
	}

	/**
	 * M�todo que inicia la ejecuci�n del clock.
	 */
	public final void start() {
		timer.start();
	}

	/**
	 * Detiene el clock.
	 */
	public final void stop() {
		timer.stop();
	}
	
}
