package ar.edu.unicen.exa.game2d.wordchallenge;
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
 * de un nivel, además notifica a quienes se hayan suscripto el 
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
	 * @uml.property  name="totalTime"
	 */
	private int totalTime;
	
	/**
	 * Constructor de la clase.
	 * @param time  tiempo
	 */
	public Clock(int time) {
		this.setTotalTime(time);
	}
	
	
	/**
	 * Timer privado que nos permite contar un segundo.
	 */
	private Timer timer = new Timer(1000, new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	    	run();
	     }
	});
	
	/**
	 * Método utilizado al transcurrir un segundo, 
	 * 1) Actualiza la variable remainingTime
	 * 2) llama al método setChanged, indicando que hubo cambios
	 * 3) llama al método notifyObservers, para notificar a quienes
	 *    se hayan suscritos.
	 * 4) Verfica en cada ejecución si hay tiempo remanente, si este llega
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
	public int getRemainingTime() {
		return remainingTime;
	}

	/**
	 * Setea el valor de la variable remainingTime.
	 * 
	 * @param remainingTime  The remainingTime to set.
	 * @uml.property  name="remainingTime"
	 */
	public void setRemainingTime(int remainingTime) {		
		this.remainingTime = remainingTime;
	}

	// TotalTime no sabemos para que sirve.

	/**
	 * Getter of the property <tt>totalTime</tt>.
	 * @return  Returns the totalTime.
	 * @uml.property  name="totalTime"
	 */
	public int getTotalTime() {
		return totalTime;
	}

	/**
	 * Setter of the property <tt>totalTime</tt>.
	 * @param totalTime  The totalTime to set.
	 * @uml.property  name="totalTime"
	 */
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
		this.remainingTime = totalTime;
	}


	/**
	 * Este método indica si ya transcurrió el tiempo.
	 * 
	 * @return true si ya transcurrió el tiempo, 
	 *         false en caso contrario
	 */
	public boolean isFinished() {
		return (remainingTime == 0);	
	}


	/**
	 * Método que inicia la ejecución del clock.
	 */
	public void start() {
		timer.start();
	}

	/**
	 * Detiene el clock.
	 */
	public void stop() {
		timer.stop();
	}

	/**
	 * Agrega mas tiempo.
	 * @param time  Tiempo en segundos paraa adicionar.
	 */
	public void addMoreTime(int time) {
		this.remainingTime += time;
	}
}
