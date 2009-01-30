package ar.edu.unicen.exa.game2d.wordchallenge;
/*
* Classname Button.java
*
* Version 1.0
*
* Date 16/11/2008
*
* Copyright UD3 - Word Challenge (c) 
* 
*/

// JAVA
import java.awt.image.BufferedImage;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.Timer;

/**
 * Clase que representa un Bot�n de Interfaz de usuario.
 * 
 * @author Luis Soldavini y Pablo Melchior
 * @version 1.0
 * 
 */
public class Button extends Sprite {

	/**
	 * ServialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Imagen del bot�n cuando se est� mostrando.
	 */
	private BufferedImage mImageUp = null;

	/**
	 * Imagen del bot�n cuando se hace click sobre el.
	 */
	private BufferedImage mImageClick = null;
	
	/**
	 * Imagen del bot�n cuando est� deshabilitado.
	 */
	private BufferedImage mImageDisabled = null;

	/**
	 * Comando que ejecuta el bot�n al hacerse click.
	 */
	private AbstractCommandLevel mCommand = null;

	/**
	 * Timer usada para mostrar la imagen cuando se hace click.
	 */
	private Timer mTimerClick = new Timer(500);

	/**
	 * Propiedad usada para habilitar o no el bot�n.
	 */
	private boolean mEnabled = true;

	

	/**
	 * Devuelve la variable que contiene la imagen del bot�n cuando se 
	 * est� mostrando.
	 * 
	 * @return BufferedImage imageUp
	 */
	public BufferedImage getMImageUp() {
		return mImageUp;
	}


	/**
	 * M�todo para setear la variable que contiene la imagen del bot�n 
	 * cuando se est� mostrando.
	 * 
	 * @param imageUp
	 */
	public void setMImageUp(BufferedImage imageUp) {
		mImageUp = imageUp;
	}

	/**
	 * Devuelve la variable que contiene la imagen del bot�n cuando se hace 
	 * click sobre �l.
	 * 
	 * @return BufferedImage imageClick
	 */
	public BufferedImage getMImageClick() {
		return mImageClick;
	}

	/**
	 * M�todo para setear la variable que contiene la imagen del bot�n cuando
	 * se hace click sobre �l.
	 * 
	 * @param imageClick
	 */
	public void setMImageClick(BufferedImage imageClick) {
		mImageClick = imageClick;
	}

	/**
	 * Devuelve la variable que contiene la imagen del bot�n cuando el mismo
	 * esta deshabilitado.
	 * 
	 * @return BufferedImage imageDisabled
	 */
	public BufferedImage getMImageDisabled() {
		return mImageDisabled;
	}

	/**
	 * M�todo para setear la variable que contiene la imagen del bot�n cuando 
	 * est� deshabilitado.
	 * 
	 * @param imageDisabled
	 */
	public void setMImageDisabled(BufferedImage imageDisabled) {
		mImageDisabled = imageDisabled;
	}

	/**
	 * Devuelve el comando que ejecuta el bot�n cuando se hace click sobre �l.
	 * 
	 * @return AbstractCommandLevel command
	 */
	public AbstractCommandLevel getMCommand() {
		return mCommand;
	}

	/**
	 * M�todo para setear el comando que ejecuta el bot�n cuando se hace click
	 * sobre �l.
	 * 
	 * @param command
	 */
	public void setMCommand(AbstractCommandLevel command) {
		mCommand = command;
	}

	/**
	 * Devuelve el timer usado para mostrar la imagen cuando se hace click.
	 * 
	 * @return Timer usado
	 */
	public Timer getMTimerClick() {
		return mTimerClick;
	}

	/**
	 * M�todo para setear el timer usado para mostrar la imagen cuando se 
	 * hace click.
	 * 
	 * @param timerClick
	 */
	public void setMTimerClick(Timer timerClick) {
		mTimerClick = timerClick;
	}

	/**
	 * Indica si est� habilitado o no el bot�n.
	 * 
	 * @return the enabled
	 */
	public final boolean isEnabled() {
		return mEnabled;
	}

	/**
	 * Setea el valor Enabled del bot�n.
	 * 
	 * @param xEnabled  Nuevo valor de la propiedad Enabled.
	 */
	public final void setEnabled(final boolean xEnabled) {			
		mEnabled = xEnabled;
		
		setImageWithState();
	}

	/**
	 * Este m�todo setea la imagen que corresponde al bot�n
	 * dependiendo del estado del mismo, si est� o no habilitado
	 * y si hay una imagen para el estado Disabled.
	 */
	private void setImageWithState() {
		//Si se usa una imagen para mostrar que est� disable
		//entonces cambio la imagen del bot�n por esa.
		if (mImageDisabled != null) {
			if (mEnabled) {
				this.setImage(mImageUp);
			} else {
				this.setImage(mImageDisabled);
			}							
		} else {
			this.setImage(mImageUp);
		}						
	}
	
	/**
	 * Constructor de la clase.
	 * 
	 * @param command  Comando interno, el que se ejecutar� en el click
	 * @param imageClick  Imagen que se mostrar� al hacer click sobre el
	 * @param imageUp  Imagen del bot�n
	 * @param x  Posici�n en pantalla de columna en pixeles
	 * @param y  Posici�n en pantalla de fila en pixeles
	 */
	public Button(final AbstractCommandLevel command,
			final BufferedImage imageClick, final BufferedImage imageUp,
			final double x, final double y) {
		this(command, imageClick, imageUp, null, x, y);
	}

	/**
	 * Constructor de la clase.
	 * 
	 * @param command  Comando interno, el que se ejecutar� en el click
	 * @param imageClick  Imagen que se mostrar� al hacer click sobre el
	 * @param imageUp  Imagen del bot�n
	 * @param imageDisabled  Imagen del bot�n	cuando est� Disabled 
	 * @param x  Posici�n en pantalla de columna en pixeles
	 * @param y  Posici�n en pantalla de fila en pixeles
	 */
	public Button(final AbstractCommandLevel command,
			final BufferedImage imageClick, final BufferedImage imageUp,
			final BufferedImage imageDisabled,
			final double x, final double y) {		
		super(imageUp, x, y);
		
		mCommand = command;
		mImageClick = imageClick;
		mImageUp = imageUp;
		mImageDisabled = imageDisabled;
		
		mTimerClick.setActive(false);
	}	
	
	/**
	 * M�todo utilizado para indicarse que se hizo click sobre el bot�n.
	 */
	public final void click() {
		if (this.isEnabled()) {
			this.setImage(mImageClick);
			mTimerClick.setActive(true);
			mCommand.execute();
		}
	}

	/**
	 * M�todo que se usa para actualizar el Sprite.
	 * 
	 * @param elapsedTime  tiempo transcurrido desde el �ltimo Update.
	 */
	@Override
	public final void update(final long elapsedTime) {
		if ((mTimerClick.isActive()) 
				&& (mTimerClick.action(elapsedTime))) {		
			setImageWithState();
			mTimerClick.setActive(false);
		}
	}
} // Button
