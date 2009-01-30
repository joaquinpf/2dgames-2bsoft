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
 * Clase que representa un Botón de Interfaz de usuario.
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
	 * Imagen del botón cuando se está mostrando.
	 */
	private BufferedImage mImageUp = null;

	/**
	 * Imagen del botón cuando se hace click sobre el.
	 */
	private BufferedImage mImageClick = null;
	
	/**
	 * Imagen del botón cuando está deshabilitado.
	 */
	private BufferedImage mImageDisabled = null;

	/**
	 * Comando que ejecuta el botón al hacerse click.
	 */
	private AbstractCommandLevel mCommand = null;

	/**
	 * Timer usada para mostrar la imagen cuando se hace click.
	 */
	private Timer mTimerClick = new Timer(500);

	/**
	 * Propiedad usada para habilitar o no el botón.
	 */
	private boolean mEnabled = true;

	

	/**
	 * Devuelve la variable que contiene la imagen del botón cuando se 
	 * está mostrando.
	 * 
	 * @return BufferedImage imageUp
	 */
	public BufferedImage getMImageUp() {
		return mImageUp;
	}


	/**
	 * Método para setear la variable que contiene la imagen del botón 
	 * cuando se está mostrando.
	 * 
	 * @param imageUp
	 */
	public void setMImageUp(BufferedImage imageUp) {
		mImageUp = imageUp;
	}

	/**
	 * Devuelve la variable que contiene la imagen del botón cuando se hace 
	 * click sobre él.
	 * 
	 * @return BufferedImage imageClick
	 */
	public BufferedImage getMImageClick() {
		return mImageClick;
	}

	/**
	 * Método para setear la variable que contiene la imagen del botón cuando
	 * se hace click sobre él.
	 * 
	 * @param imageClick
	 */
	public void setMImageClick(BufferedImage imageClick) {
		mImageClick = imageClick;
	}

	/**
	 * Devuelve la variable que contiene la imagen del botón cuando el mismo
	 * esta deshabilitado.
	 * 
	 * @return BufferedImage imageDisabled
	 */
	public BufferedImage getMImageDisabled() {
		return mImageDisabled;
	}

	/**
	 * Método para setear la variable que contiene la imagen del botón cuando 
	 * está deshabilitado.
	 * 
	 * @param imageDisabled
	 */
	public void setMImageDisabled(BufferedImage imageDisabled) {
		mImageDisabled = imageDisabled;
	}

	/**
	 * Devuelve el comando que ejecuta el botón cuando se hace click sobre él.
	 * 
	 * @return AbstractCommandLevel command
	 */
	public AbstractCommandLevel getMCommand() {
		return mCommand;
	}

	/**
	 * Método para setear el comando que ejecuta el botón cuando se hace click
	 * sobre él.
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
	 * Método para setear el timer usado para mostrar la imagen cuando se 
	 * hace click.
	 * 
	 * @param timerClick
	 */
	public void setMTimerClick(Timer timerClick) {
		mTimerClick = timerClick;
	}

	/**
	 * Indica si está habilitado o no el botón.
	 * 
	 * @return the enabled
	 */
	public final boolean isEnabled() {
		return mEnabled;
	}

	/**
	 * Setea el valor Enabled del botón.
	 * 
	 * @param xEnabled  Nuevo valor de la propiedad Enabled.
	 */
	public final void setEnabled(final boolean xEnabled) {			
		mEnabled = xEnabled;
		
		setImageWithState();
	}

	/**
	 * Este método setea la imagen que corresponde al botón
	 * dependiendo del estado del mismo, si está o no habilitado
	 * y si hay una imagen para el estado Disabled.
	 */
	private void setImageWithState() {
		//Si se usa una imagen para mostrar que está disable
		//entonces cambio la imagen del botón por esa.
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
	 * @param command  Comando interno, el que se ejecutará en el click
	 * @param imageClick  Imagen que se mostrará al hacer click sobre el
	 * @param imageUp  Imagen del botón
	 * @param x  Posición en pantalla de columna en pixeles
	 * @param y  Posición en pantalla de fila en pixeles
	 */
	public Button(final AbstractCommandLevel command,
			final BufferedImage imageClick, final BufferedImage imageUp,
			final double x, final double y) {
		this(command, imageClick, imageUp, null, x, y);
	}

	/**
	 * Constructor de la clase.
	 * 
	 * @param command  Comando interno, el que se ejecutará en el click
	 * @param imageClick  Imagen que se mostrará al hacer click sobre el
	 * @param imageUp  Imagen del botón
	 * @param imageDisabled  Imagen del botón	cuando esté Disabled 
	 * @param x  Posición en pantalla de columna en pixeles
	 * @param y  Posición en pantalla de fila en pixeles
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
	 * Método utilizado para indicarse que se hizo click sobre el botón.
	 */
	public final void click() {
		if (this.isEnabled()) {
			this.setImage(mImageClick);
			mTimerClick.setActive(true);
			mCommand.execute();
		}
	}

	/**
	 * Método que se usa para actualizar el Sprite.
	 * 
	 * @param elapsedTime  tiempo transcurrido desde el último Update.
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
