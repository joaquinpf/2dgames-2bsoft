package ar.edu.unicen.exa.game2d.bigballs;

import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.background.ImageBackground;
import com.golden.gamedev.util.ImageUtil;

/**
 * Este Clase presenta la pantalla de menu del juego.
 * 
 * @author Damian Achaga y Joaqu�n P�rez Fuentes
 */

public class Menu extends GameObject {
		
	/**
	 * Tama�o de la fuente de la pelota.
	 * @uml.property  name="fontSize"
	 */
	private int fontSize = 60;
	
	/**
	 * distancia de separacion entre cada opci�n del menu.	
	 */
	private static final int ANCHO_LINE_MENU = 65;
	
	/**
	 * Background del menu.
	 */
	private Background background;
	
	/**
	 * Puntero animado que se�ala la opcion elegida.
	 */
	private Sprite pointer;
	
	/**
	 * Playfield del menu.
	 */
	private PlayField pfMenu = new PlayField();
		
	/**
	 * variable que representa la opcion seleccionada.
	 */
	private int option = 1;
	
	/**
	 * contiene la posici�n en la coordenada X del menu.
	 */
	private int posXmenu;
	
	/**
	 * contiene la posici�n en la coordenada Y del menu.
	 */
	private int posYmenu;
	
	/**
	 * Contiene la posicion inicial en la coordenada X del puntero.
	 */
	private int posXpointer;
	
	/**
	 * Contiene la posicion inicial en la coordenada Y del puntero.
	 */
	private int posYpointer; 
		
	/**
	 * Sonido de movimiento en el menu.
	 */
	private static final String MENU_MOVE_SOUND = 
		"./resources/bigballs/sounds/menumove.wav";
	
	/**
	 * Sonido de seleccion en el menu.
	 */
	private static final String MENU_SELECT_SOUND = 
		"./resources/bigballs/sounds/menuselect.wav";
	
//////////////////////////////////////////////////////////////////	

	/**
	 * indica si el puntero del mouse esta sobre las opciones 
	 * o en otro lugar libre de la pantalla.
	 * @return true si el puntero esta sobre las opciones, 
	 *         false en caso contrario.
	 */
	private boolean mouseInMenu() {
		return checkPosMouse(posXmenu, posYmenu - fontSize , posXmenu + 193, 
				             posYmenu + ANCHO_LINE_MENU * 3);
	}
	
	/**
	 * m�todo que administra los eventos lanzados por teclado.
	 */
	private void manejoTeclado() {
		switch (bsInput.getKeyPressed()) {
		case KeyEvent.VK_ENTER :
			if (option == BigBalls.OPTION_PLAY) {
				parent.nextGameID = BigBalls.OPTION_PLAY;
				playSound(MENU_SELECT_SOUND);
				finish();
			}
			if (option == BigBalls.OPTION_SCORES) {
				parent.nextGameID = BigBalls.OPTION_SCORES;
				playSound(MENU_SELECT_SOUND);
				finish();
			}
			if (option == BigBalls.OPTION_EXIT) {
				// end
				playSound(MENU_SELECT_SOUND);
				finish();
			}
		break;
		case KeyEvent.VK_UP :
			option--;
			playSound(MENU_MOVE_SOUND);
			pointer.setLocation(posXpointer, pointer.getY() - ANCHO_LINE_MENU);
			if (option < BigBalls.OPTION_PLAY) {
				option = BigBalls.OPTION_EXIT;
				pointer.setLocation(posXpointer, 
						            posYpointer + ANCHO_LINE_MENU * 2);
			}
		break;
		case KeyEvent.VK_DOWN :
			option++;
			playSound(MENU_MOVE_SOUND);
			pointer.setLocation(posXpointer, pointer.getY() + ANCHO_LINE_MENU);
			if (option > BigBalls.OPTION_EXIT) {
				option = BigBalls.OPTION_PLAY;
				pointer.setLocation(posXpointer, posYpointer);
			}
		break;
		case KeyEvent.VK_ESCAPE :
			playSound(MENU_MOVE_SOUND);
			finish();
		break;
		default :
		}
	}
	
	/**
	 * m�todo que administra los eventos lanzados por el mouse. 
	 */
	private void manejoMouse() {
		if (mouseInMenu()) {	
			if (click()) {
				if (option == BigBalls.OPTION_PLAY) {
					parent.nextGameID = BigBalls.OPTION_PLAY;
					playSound(MENU_SELECT_SOUND);
					finish();
				}
				if (option == BigBalls.OPTION_SCORES) {
					parent.nextGameID = BigBalls.OPTION_SCORES;
					playSound(MENU_SELECT_SOUND);
					finish();
				}
				if (option == BigBalls.OPTION_EXIT) {
					// end
					playSound(MENU_SELECT_SOUND);
					finish();
				}
			}
			if (getMouseY() < posYmenu - fontSize + ANCHO_LINE_MENU
					&& option != BigBalls.OPTION_PLAY) {
				option = BigBalls.OPTION_PLAY; 
				pointer.setLocation(posXpointer, posYpointer);
				playSound(MENU_MOVE_SOUND);
			}
			if (getMouseY() > posYmenu - fontSize + ANCHO_LINE_MENU 
				&& getMouseY() < posYmenu - fontSize + ANCHO_LINE_MENU * 2
				&& option != BigBalls.OPTION_SCORES) {
				option = BigBalls.OPTION_SCORES;
				pointer.setLocation(posXpointer, posYpointer + ANCHO_LINE_MENU);
				playSound(MENU_MOVE_SOUND);
			}
			if (getMouseY() > posYmenu - fontSize + ANCHO_LINE_MENU * 2
					&& option != BigBalls.OPTION_EXIT) {
				playSound(MENU_MOVE_SOUND);
				option = BigBalls.OPTION_EXIT;
				pointer.setLocation(posXpointer, 
						            posYpointer + ANCHO_LINE_MENU * 2);
			}
		}
	}
	
	/**
	 * @see com.golden.gamedev.GameObject#initResources()
	 */
	@Override
	public final void initResources() {
		//Seteo de posiciones
		posXmenu = getWidth() / 2 - 40;
		posYmenu = getHeight() / 2 - 20;
		posXpointer = posXmenu - 330;
		posYpointer = posYmenu - 70;
		
		//Fondo
		background = new ImageBackground(
				getImage("./resources/bigballs/images/menu.png"), 800, 600);
		
		//El puntero a utilizar
		BufferedImage b = ImageUtil.getImage(
				bsIO.getURL("./resources/bigballs/images/pointerv2.png"),
				Transparency.TRANSLUCENT);
		pointer = new Sprite(b);
		pfMenu.add(pointer);
		pfMenu.setBackground(background);
		pointer.setLocation(posXpointer, posYpointer);
	
		this.showCursor();
	}

	/**
	 * Escribe las opciones en pantalla con un highlight 
	 * en caso de que la opcion este seleccionada.
	 * @param g parametro que necesita el manejador de fuente
	 *          para escribir en pantalla.
	 * @param text texto que debe ser escrito.
	 * @param line lugar en que la opcion debe ser escrita.
	 * @param selected indica si la opcion esta seleccionada o no.
	 */
	final void drawText(final Graphics2D g, final String text, final int line,
					    final boolean selected) {
		if (selected) {
			g.drawImage(getImage("./resources/bigballs/images/" + text  + ".png"),
					posXmenu, posYmenu + line - 36, null);
			
		} else {
			g.drawImage(getImage("./resources/bigballs/images/" + text  + "ns.png"),
					posXmenu, posYmenu + line - 36, null);
		}
	}

	
	/**
	 * Dibuja la escena. Override de GTGE.
	 * @param g El objeto grafico sobre el cual se dibuja
	 * @see com.golden.gamedev.GameObject#render(java.awt.Graphics2D)
	 */
	@Override
	public final void render(final Graphics2D g) {
		//g.drawImage(mainMenu,0,0,null);
		pfMenu.render(g);
		drawText(g, "jugar", 0, (option == BigBalls.OPTION_PLAY));
		drawText(g, "puntuaciones", ANCHO_LINE_MENU, 
				(option == BigBalls.OPTION_SCORES));
		drawText(g, "salir", ANCHO_LINE_MENU * 2, 
				(option == BigBalls.OPTION_EXIT));
	}
	
	/**
	 * @param elapsedTime Tiempo transcurrido desde el ultimo update
	 * @see com.golden.gamedev.GameObject#update(long)
	 */
	@Override
	public final void update(final long elapsedTime) {
		pfMenu.update(elapsedTime);
		manejoTeclado();
		manejoMouse();
	}
		
	/**
	 * 
	 * @param parent objeto que maneja la transicion hacia las 
	 *               difenrentes pantallas segun la opcion elegida.
	 */
	public Menu(final GameEngine parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	/**
	 * retorna el tamanio de fuente actual
	 * @return valor de la fontSize
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * setea un nuevo tamanio de fuente
	 * @param fontSize nuevo tamanio de fuente a setear
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * retorna el background de la pantallal
	 * @return valor de la variable background
	 */
	public Background getBackground() {
		return background;
	}

	/**
	 * setea un nuevo background de pantalla
	 * @param background nuevo background a setear
	 */
	public void setBackground(Background background) {
		this.background = background;
	}

	/**
	 * retorna Sprite que representa el puntero que seniala las opciones
	 * @return valor de la variable pointer
	 */
	public Sprite getPointer() {
		return pointer;
	}

	/**
	 * setea un nuevo Sprite para el puntero
	 * @param pointer nuevo Sprite para setear
	 */
	public void setPointer(Sprite pointer) {
		this.pointer = pointer;
	}

	/**
	 * retorna el PlayField del menu
	 * @return valor de la variable pfMenu
	 */
	public PlayField getPfMenu() {
		return pfMenu;
	}

	/**
	 * setea un nuevo Playfield para el menu
	 * @param pfMenu nuevo Playfield a setear
	 */
	public void setPfMenu(PlayField pfMenu) {
		this.pfMenu = pfMenu;
	}

	/**
	 * retorna el numero de opcion seleccionada
	 * @return valor de la variable option
	 */
	public int getOption() {
		return option;
	}

	/**
	 * setea un nuevo numero de opcion
	 * @param option nuevo tnumero de opcion a setear
	 */
	public void setOption(int option) {
		this.option = option;
	}

	/**
	 * retorna la posicion del menu en el eje horizontal
	 * @return valor de la variable posXmenu
	 */
	public int getPosXmenu() {
		return posXmenu;
	}

	/**
	 * setea una nueva posicion del menu en el eje horizontal
	 * @param posXmenu la nueva posicion del menu en el eje 
	 * 		  horizontal a setear
	 */
	public void setPosXmenu(int posXmenu) {
		this.posXmenu = posXmenu;
	}

	/**
	 * retorna la posicion del menu en el eje vertical
	 * @return valor de la variable posYmenu
	 */
	public int getPosYmenu() {
		return posYmenu;
	}

	/**
	 * setea una nueva posicion del menu en el eje vertical
	 * @param posYmenu la nueva posicion del menu en el eje 
	 * 		  vertical a setear
	 */
	public void setPosYmenu(int posYmenu) {
		this.posYmenu = posYmenu;
	}

	/**
	 * retorna la posiscion del puntero del mouse en el eje horizontal
	 * @return valor de la variable posXpointer
	 */
	public int getPosXpointer() {
		return posXpointer;
	}

	/**
	 * setea una nueva posicion del puntero del mouse en el eje horizontal
	 * @param posXpointer la nueva posicion del puntero del mouse en el eje 
	 * 		  horizontal a setear
	 */
	public void setPosXpointer(int posXpointer) {
		this.posXpointer = posXpointer;
	}

	/**
	 * retorna la posiscion del puntero del mouse en el eje vertical
	 * @return valor de la variable posYpointer
	 */
	public int getPosYpointer() {
		return posYpointer;
	}

	/**
	 * setea una nueva posicion del puntero del mouse en el eje vertical
	 * @param posYpointer la nueva posicion del puntero del mouse en el eje
	 * 		  vertical a setear
	 */
	public void setPosYpointer(int posYpointer) {
		this.posYpointer = posYpointer;
	}

}
