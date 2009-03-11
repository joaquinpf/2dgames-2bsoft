package ar.edu.unicen.exa.game2d.wordchallenge;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.event.KeyEvent;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.background.ImageBackground;
import com.golden.gamedev.util.ImageUtil;

/**
 * Este Clase presenta la pantalla de menu del juego.
 * 
 * @author Damian Achaga y Joaquín Pérez Fuentes
 */

public class Menu extends GameObject {

	/**
	 * Fuente a utilizar sobre la pelota.
	 * 
	 * @uml.property name="ttfFont"
	 */
	private String ttfFont = "./resources/wordchallenge/images/wcfont.ttf";

	/**
	 * Tamaño de la fuente de la pelota.
	 * 
	 * @uml.property name="fontSize"
	 */
	private int fontSize = 50;

	/**
	 * distancia de separacion entre cada opción del menu.
	 */
	private static int ANCHO_LINE_MENU = 60;
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

	// ////////////////////////////////////////////////////////////////

	/**
	 * indica si el puntero del mouse esta sobre las opciones o en otro lugar
	 * libre de la pantalla.
	 * 
	 * @return true si el puntero esta sobre las opciones, false en caso
	 *         contrario.
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
		case KeyEvent.VK_ENTER:
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
		case KeyEvent.VK_UP:
			option--;
			pointer.setLocation(posXpointer, pointer.getY() - ANCHO_LINE_MENU);
			if (option < WordChallenge.OPTION_PLAY) {
				option = WordChallenge.OPTION_EXIT;
				pointer.setLocation(posXpointer, posYpointer + ANCHO_LINE_MENU
						* 3);
			}
			break;
		case KeyEvent.VK_DOWN:
			option++;
			pointer.setLocation(posXpointer, pointer.getY() + ANCHO_LINE_MENU);
			if (option > WordChallenge.OPTION_EXIT) {
				option = WordChallenge.OPTION_PLAY;
				pointer.setLocation(posXpointer, posYpointer);
			}
			break;
		case KeyEvent.VK_ESCAPE:
			finish();
			break;
		default:
			;
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
				pointer.setLocation(posXpointer, posYpointer + ANCHO_LINE_MENU
						* 2);
			}

			if (getMouseY() > posYmenu - fontSize + ANCHO_LINE_MENU * 3) {
				option = WordChallenge.OPTION_EXIT;
				pointer.setLocation(posXpointer, posYpointer + ANCHO_LINE_MENU
						* 3);
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
		background = new ImageBackground(getImage("./resources/wordchallenge/images/menu.png"));

		pointer = new Sprite(ImageUtil.getImage(this.bsIO
				.getURL("resources/wordchallenge/images/pointer.png"),
				Transparency.TRANSLUCENT));
		pointer.setLocation(posXpointer, posYpointer);

		fontManager.getFont(getImages("./resources/wordchallenge/images/fontmenu.png", 8, 12));

		pfMenu.add(pointer);
		pfMenu.setBackground(background);

		this.showCursor();

	}

	/**
	 * Escribe las opciones en pantalla con un highlight en caso de que la
	 * opcion este seleccionada.
	 * 
	 * @param g
	 *            parametro que necesita el manejador de fuente para escribir en
	 *            pantalla.
	 * @param text
	 *            texto que debe ser escrito.
	 * @param line
	 *            lugar en que la opcion debe ser escrita.
	 * @param selected
	 *            indica si la opcion esta seleccionada o no.
	 */
	final void drawText(final Graphics2D g, final String text, final int line,
			final boolean selected) {
		if (selected) {
			g.drawImage(ImageUtil.getImage(this.bsIO.getURL("./resources/wordchallenge/images/"
					+ text + ".png"), Transparency.TRANSLUCENT), posXmenu,
					posYmenu + line - 36, null);

		} else {
			g.drawImage(ImageUtil.getImage(this.bsIO.getURL("./resources/wordchallenge/images/"
					+ text + "ns.png"), Transparency.TRANSLUCENT), posXmenu,
					posYmenu + line - 36, null);
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
	 * @param parent
	 *            objeto que maneja la trancicion hacia las difenrentes
	 *            pantallas segun la opcion elegida.
	 */
	public Menu(final GameEngine parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Retorna la Fuente a utilizar sobre la pelota.
	 * 
	 * @return ttfont
	 */
	public final String getTtfFont() {
		return ttfFont;
	}

	/**
	 * Setea la variable ttFont.
	 * 
	 * @param ttfont
	 */
	public final void setTtfFont(final String ttfFont) {
		this.ttfFont = ttfFont;
	}

	/**
	 * Retorna el tamaño de la fuente a utilizar en la pelota.
	 * 
	 * @return fontSize
	 */
	public final int getFontSize() {
		return fontSize;
	}

	/**
	 * Setea la variable fontSize.
	 * 
	 * @param fontSize
	 */
	public final void setFontSize(final int fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * Retorna el Background del menu.
	 * 
	 * @return background
	 */
	public final Background getBackground() {
		return background;
	}

	/**
	 * Setea la variable background con el respectivo Background del menu.
	 * 
	 * @param background
	 */
	public final void setBackground(final Background background) {
		this.background = background;
	}

	/**
	 * Retorna el Puntero animado que señala la opcion elegida.
	 * 
	 * @return pointer
	 */
	public final Sprite getPointer() {
		return pointer;
	}

	/**
	 * Setea la variable pointer.
	 * 
	 * @param pointer
	 */
	public final void setPointer(final Sprite pointer) {
		this.pointer = pointer;
	}

	/**
	 * retorna el Playfield del menu.
	 * 
	 * @return pfMenu
	 */
	public final PlayField getPfMenu() {
		return pfMenu;
	}

	/**
	 * Setea la variable pfMenu.
	 * 
	 * @param pfMenu
	 */
	public final void setPfMenu(final PlayField pfMenu) {
		this.pfMenu = pfMenu;
	}

	/**
	 * Retorna la fuente del menu.
	 * 
	 * @return font
	 */
	public final GameFont getFont() {
		return font;
	}

	/**
	 * Setea la variable font.
	 * 
	 * @param font
	 */
	public final void setFont(final GameFont font) {
		this.font = font;
	}

	/**
	 * Retorna la variable que representa la opcion seleccionada.
	 * 
	 * @return option
	 */
	public final int getOption() {
		return option;
	}

	/**
	 * Setea la variable option.
	 * 
	 * @param option
	 */
	public final void setOption(final int option) {
		this.option = option;
	}

	/**
	 * Retorna la posición en la coordenada X del menu.
	 * 
	 * @return posXmenu
	 */
	public final int getPosXmenu() {
		return posXmenu;
	}

	/**
	 * Setea la variable posXmenu.
	 * 
	 * @param posXmenu
	 */
	public final void setPosXmenu(final int posXmenu) {
		this.posXmenu = posXmenu;
	}

	/**
	 * Retorna la posición en la coordenada Y del menu.
	 * 
	 * @return posYmenu
	 */
	public final int getPosYmenu() {
		return posYmenu;
	}

	/**
	 *Setea la variable posYmenu.
	 * 
	 * @param posYmenu
	 */
	public final void setPosYmenu(final int posYmenu) {
		this.posYmenu = posYmenu;
	}

	/**
	 * Retorna la posicion inicial en la coordenada X del puntero.
	 * 
	 * @return posXpointer
	 */
	public final int getPosXpointer() {
		return posXpointer;
	}

	/**
	 * Setea la variable posXpointer.
	 * 
	 * @param posXpointer
	 */
	public final void setPosXpointer(final int posXpointer) {
		this.posXpointer = posXpointer;
	}

	/**
	 * Retorna la posicion inicial en la coordenada Y del puntero.
	 * 
	 * @return posYpointer
	 */
	public final int getPosYpointer() {
		return posYpointer;
	}

	/**
	 * Setea la variable posYpointer.
	 * 
	 * @param posYpointer
	 */
	public final void setPosYpointer(final int posYpointer) {
		this.posYpointer = posYpointer;
	}

	/**
	 * Retorna la distancia de separacion entre cada opción del menu.
	 * 
	 * @return ANCHO_LINE_MENU
	 */
	public static int getANCHO_LINE_MENU() {
		return ANCHO_LINE_MENU;
	}

	/**
	 * @param ancho_line_menu
	 *            the aNCHO_LINE_MENU to set
	 */
	public static void setANCHO_LINE_MENU(int ancho_line_menu) {
		ANCHO_LINE_MENU = ancho_line_menu;
	}

}
