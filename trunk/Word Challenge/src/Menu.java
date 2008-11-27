import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.background.ImageBackground;

/**
 * Este Clase presenta la pantalla de menu del juego.
 * 
 * @author Damian Achaga y Joaquín Pérez Fuentes
 */

public class Menu extends GameObject {
	
	/**
	 * Fuente a utilizar sobre la pelota.
	 * @uml.property  name="ttfFont"
	 */
	private String ttfFont = "resources/images/wcfont.ttf";
	
	/**
	 * Tamaño de la fuente de la pelota.
	 * @uml.property  name="fontSize"
	 */
	private int fontSize = 50;
	
	/**
	 * distancia de separacion entre cada opción del menu.	
	 */
	private static final int ANCHO_LINE_MENU = 60;
	/**
	 * Background del menu.
	 */
	private Background background;
	/**
	 * Puntero animado que señala la opcion elegida.
	 */
	private Sprite pointer;
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
		return checkPosMouse(posXmenu, posYmenu - fontSize, posXmenu + 193, 
				             posYmenu + ANCHO_LINE_MENU * 4);
	}
	
	/**
	 * método que administra los eventos lanzados por teclado.
	 */
	private void manejoTeclado() {
		switch (bsInput.getKeyPressed()) {
		case KeyEvent.VK_ENTER :
			if (option == WordChallenge.OPTION_PLAY) {
				parent.nextGameID = WordChallenge.OPTION_PLAY;
				finish();
			}
			if (option == WordChallenge.OPTION_SCORES) {
				parent.nextGameID = WordChallenge.OPTION_SCORES;
				finish();
			}
			if (option == WordChallenge.OPTION_IDIOMAS) {
				parent.nextGameID = WordChallenge.OPTION_IDIOMAS;
				finish();
			}
			if (option == WordChallenge.OPTION_EXIT) {
				// end
				finish();
			}
		break;
		case KeyEvent.VK_UP :
			option--;
			pointer.setLocation(posXpointer, pointer.getY() - ANCHO_LINE_MENU);
			if (option < WordChallenge.OPTION_PLAY) {
				option = WordChallenge.OPTION_EXIT;
				pointer.setLocation(posXpointer, 
						            posYpointer + ANCHO_LINE_MENU * 3);
			}
		break;
		case KeyEvent.VK_DOWN :
			option++;
			pointer.setLocation(posXpointer, pointer.getY() + ANCHO_LINE_MENU);
			if (option > WordChallenge.OPTION_EXIT) {
				option = WordChallenge.OPTION_PLAY;
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
				if (option == WordChallenge.OPTION_PLAY) {
					parent.nextGameID = WordChallenge.OPTION_PLAY;
					finish();
				}
				if (option == WordChallenge.OPTION_SCORES) {
					parent.nextGameID = WordChallenge.OPTION_SCORES;
					finish();
				}
				if (option == WordChallenge.OPTION_IDIOMAS) {
					parent.nextGameID = WordChallenge.OPTION_IDIOMAS;
					finish();
				}
				if (option == WordChallenge.OPTION_EXIT) {
					// end
					finish();
				}
			}
			if (getMouseY() < posYmenu - fontSize + ANCHO_LINE_MENU) {
				option = WordChallenge.OPTION_PLAY; 
				pointer.setLocation(posXpointer, posYpointer);
			}
			if (getMouseY() > posYmenu - fontSize + ANCHO_LINE_MENU 
				&& getMouseY() < posYmenu + ANCHO_LINE_MENU * 2) {
				option = WordChallenge.OPTION_SCORES;
				pointer.setLocation(posXpointer, posYpointer + ANCHO_LINE_MENU);
			}
			if (getMouseY() > posYmenu - fontSize + ANCHO_LINE_MENU * 2 
					&& getMouseY() < posYmenu + ANCHO_LINE_MENU * 3) {
					option = WordChallenge.OPTION_IDIOMAS;
					pointer.setLocation(posXpointer, posYpointer 
							            + ANCHO_LINE_MENU * 2);
				}
			
			if (getMouseY() > posYmenu - fontSize + ANCHO_LINE_MENU * 3) {
				option = WordChallenge.OPTION_EXIT;
				pointer.setLocation(posXpointer, 
						            posYpointer + ANCHO_LINE_MENU * 3);
			}
		}
	}
	
	/**
	 * @see com.golden.gamedev.GameObject#initResources()
	 */
	@Override
	public final void initResources() {
		posXmenu = getWidth() / 2 - 10;
		posYmenu = getHeight() / 2 - 30;
		posXpointer = posXmenu - 360;
		posYpointer = posYmenu - 90;
		background = new ImageBackground(getImage("resources/images/menu.png"));
		
		pointer =  new Sprite(getImage("resources/images/pointer.png"));
		pointer.setLocation(posXpointer, posYpointer);
		
		fontManager.getFont(getImages("resources/images/fontmenu.png", 8, 12));
		
		pfMenu.add(pointer);
		pfMenu.setBackground(background);
		
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
			g.drawImage(getImage("resources/images/" + text  + ".png"),
					posXmenu, posYmenu + line - 36, null);
			
		} else {
			g.drawImage(getImage("resources/images/" + text  + "ns.png"),
					posXmenu, posYmenu + line - 36, null);
		}
	}

	
	/**
	 * @see com.golden.gamedev.GameObject#render(java.awt.Graphics2D)
	 */
	@Override
	public final void render(final Graphics2D g) {
		pfMenu.render(g);
		drawText(g, "Jugar", 0, (option == WordChallenge.OPTION_PLAY));
		drawText(g, "Puntuaciones", ANCHO_LINE_MENU, 
				(option == WordChallenge.OPTION_SCORES));
		drawText(g, "Idiomas", ANCHO_LINE_MENU * 2, 
				(option == WordChallenge.OPTION_IDIOMAS));
		drawText(g, "Salir", ANCHO_LINE_MENU * 3, 
				(option == WordChallenge.OPTION_EXIT));
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
