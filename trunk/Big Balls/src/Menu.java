import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.AnimatedSprite;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.background.ColorBackground;

/**
 * Este Clase presenta la pantalla de menu del juego.
 */

public class Menu extends GameObject {
	/**
	 * distancia de separacion entre cada opción del menu.	
	 */
	private static final int ANCHO_LINE_MENU = 36;
	/**
	 * Imagene que contiene el título del menu.
	 */
	private BufferedImage titleMenu;
	/**
	 * Background del menu.
	 */
	private Background background;
	/**
	 * Puntero animado que señala la opcion elegida.
	 */
	private AnimatedSprite pointer;
	/**
	 * Playfield del menu.
	 */
	private PlayField pfMenu = new PlayField();
	/**
	 * Manejador de fuente del menu.
	 */
	private GameFont font;
	/**
	 * variable que representa la opcion seleccionada.
	 */
	private int option = 1;
	/**
	 * contiene la posición en la coordenada X del menu.
	 */
	private int posXmenu;
	/**
	 * contiene la posición en la coordenada Y del menu.
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
	
//////////////////////////////////////////////////////////////////	

	/**
	 * indica si el puntero del mouse esta sobre las opciones 
	 * o en otro lugar libre de la pantalla.
	 * @return true si el puntero esta sobre las opciones, 
	 *         false en caso contrario.
	 */
	private boolean mouseInMenu() {
		return checkPosMouse(posXmenu, posYmenu, posXmenu + 193, 
				             posYmenu + ANCHO_LINE_MENU * 3);
	}
	
	/**
	 * método que administra los eventos lanzados por teclado.
	 */
	private void manejoTeclado() {
		switch (bsInput.getKeyPressed()) {
		case KeyEvent.VK_ENTER :
			if (option == BigBalls.OPTION_PLAY) {
				parent.nextGameID = BigBalls.OPTION_PLAY;
				finish();
			}
			if (option == BigBalls.OPTION_SCORES) {
				parent.nextGameID = BigBalls.OPTION_SCORES;
				finish();
			}
			if (option == BigBalls.OPTION_EXIT) {
				// end
				finish();
			}
		break;
		case KeyEvent.VK_UP :
			option--;
			pointer.setLocation(posXpointer, pointer.getY() - ANCHO_LINE_MENU);
			if (option < BigBalls.OPTION_PLAY) {
				option = BigBalls.OPTION_EXIT;
				pointer.setLocation(posXpointer, 
						            posYpointer + ANCHO_LINE_MENU * 2);
			}
		break;
		case KeyEvent.VK_DOWN :
			option++;
			pointer.setLocation(posXpointer, pointer.getY() + ANCHO_LINE_MENU);
			if (option > BigBalls.OPTION_EXIT) {
				option = BigBalls.OPTION_PLAY;
				pointer.setLocation(posXpointer, posYpointer);
			}
		break;
		case KeyEvent.VK_ESCAPE :
			finish();
		break;
		default : ;
	}
	}
	
	/**
	 * método que administra los eventos lanzados por el mouse. 
	 */
	private void manejoMouse() {
		if (mouseInMenu()) {	
			if (click()) {
				if (option == BigBalls.OPTION_PLAY) {
					parent.nextGameID = BigBalls.OPTION_PLAY;
					finish();
				}
				if (option == BigBalls.OPTION_SCORES) {
					parent.nextGameID = BigBalls.OPTION_SCORES;
					finish();
				}
				if (option == BigBalls.OPTION_EXIT) {
					// end
					finish();
				}
			}
			if (getMouseY() < posYmenu + ANCHO_LINE_MENU) {
				option = BigBalls.OPTION_PLAY; 
				pointer.setLocation(posXpointer, posYpointer);
			}
			if (getMouseY() > posYmenu + ANCHO_LINE_MENU 
				&& getMouseY() < posYmenu + ANCHO_LINE_MENU * 2) {
				option = BigBalls.OPTION_SCORES;
				pointer.setLocation(posXpointer, posYpointer + ANCHO_LINE_MENU);
			}
			if (getMouseY() > posYmenu + ANCHO_LINE_MENU * 2) {
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
		posXmenu = getWidth() / 2 - 45;
		posYmenu = getHeight() / 2 + 50;
		posXpointer = posXmenu - 55;
		posYpointer = posYmenu - 10;
		titleMenu = getImage("resources/images/titleMenu1.gif", false);
		background = new ColorBackground(Color.BLACK);
		pointer = new AnimatedSprite(getImages(
									"resources/images/pointerMenu.png", 4, 4),
									posXpointer, posYpointer);
		font = fontManager.getFont(getImages("resources/images/fontMenu.png",
								   8, 12));
		pointer.setAnimate(true);
		pointer.setLoopAnim(true);
		pointer.setAnimationTimer(new Timer(200));
		pfMenu.add(pointer);
		pfMenu.setBackground(background);
		
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
			// draw selected rectangle
			g.setColor(Color.ORANGE);
			g.fillRect(posXmenu - 1,
					   (posYmenu + line) + 4,
					   font.getWidth(text) + 2,
					   font.getHeight() + 2);
		}
		font.drawString(g, text, GameFont.LEFT, posXmenu , posXmenu + line,
				        getWidth());
	}

	
	/**
	 * @see com.golden.gamedev.GameObject#render(java.awt.Graphics2D)
	 */
	@Override
	public final void render(final Graphics2D g) {
		//g.drawImage(mainMenu,0,0,null);
		pfMenu.render(g);
		drawText(g, "Jugar", 0, (option == BigBalls.OPTION_PLAY));
		drawText(g, "Puntuaciones", ANCHO_LINE_MENU, 
				(option == BigBalls.OPTION_SCORES));
		drawText(g, "Salir", ANCHO_LINE_MENU * 2, 
				(option == BigBalls.OPTION_EXIT));
		g.drawImage(titleMenu, getWidth() / 2 - 130, 20, null);
	}
	
	/**
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
	 * @param parent objeto que maneja la trancicion hacia las 
	 *               difenrentes pantallas segun la opcion elegida.
	 */
	public Menu(final GameEngine parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

}
