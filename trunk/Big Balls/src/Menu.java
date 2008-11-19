import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.AnimatedSprite;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.background.ImageBackground;

/**
 * Este Clase presenta la pantalla de menu del juego.
 * 
 * @author Damian Achaga y Joaqu�n P�rez Fuentes
 */

public class Menu extends GameObject {
	/**
	 * distancia de separacion entre cada opci�n del menu.	
	 */
	private static final int ANCHO_LINE_MENU = 60;
	
	/**
	 * Background del menu.
	 */
	private Background background;
	
	/**
	 * Puntero animado que se�ala la opcion elegida.
	 */
	private AnimatedSprite pointer;
	
	/**
	 * Playfield del menu.
	 */
	private PlayField pfMenu = new PlayField();
	
	/**
	 * Manejador de fuente del menu.
	 */
	private GameFont internalFont;
	
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
	 * Largo de la grilla de fuente.
	 */
	private static final int FONT_GRID_HEIGHT = 12;
	
	/**
	 * Ancho de la grilla de fuente.
	 */
	private static final int FONT_GRID_WIDTH = 8;	
	
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
	 * m�todo que administra los eventos lanzados por teclado.
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
	 * m�todo que administra los eventos lanzados por el mouse. 
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
		//Seteo de posiciones
		posXmenu = getWidth() / 2 - 40;
		posYmenu = getHeight() / 2;
		posXpointer = posXmenu - 460;
		posYpointer = posYmenu - 30;
		
		//Fondo
		background = new ImageBackground(
				getImage("resources/images/menubackground.png"), 800, 600);
		
		//Fuente a utilizar
		internalFont = fontManager.getFont(new Font("Arial", Font.BOLD, 50));
		fontManager.getFont(getImages("resources/images/font.png",
								   FONT_GRID_WIDTH, FONT_GRID_HEIGHT));
		
		//El puntero a utilizar
		pointer = new AnimatedSprite(getImages(
				"resources/images/pointerMenu.png", 1, 1),
				posXpointer, posYpointer);
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
			g.setColor(new Color(52, 71, 66));
			g.fillRect(posXmenu - 8,
					   (posYmenu + line - 45),
					   internalFont.getWidth(text) + 16,
					   internalFont.getHeight() + 3);
		}

		//Escribe el texto en pantalla con antialias.
		Font font = new Font("Arial", Font.BOLD, 50);
		g.setFont(font);	
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.white);
		
		g.drawString(text, posXmenu, posYmenu + line);
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
		drawText(g, "Jugar", 0, (option == BigBalls.OPTION_PLAY));
		drawText(g, "Puntuaciones", ANCHO_LINE_MENU, 
				(option == BigBalls.OPTION_SCORES));
		drawText(g, "Salir", ANCHO_LINE_MENU * 2, 
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
	 * @param parent objeto que maneja la trancicion hacia las 
	 *               difenrentes pantallas segun la opcion elegida.
	 */
	public Menu(final GameEngine parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

}
