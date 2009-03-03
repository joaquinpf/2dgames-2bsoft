package ar.edu.unicen.exa.game2d.wordchallenge;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Hashtable;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.background.ImageBackground;
import com.golden.gamedev.util.ImageUtil;

/**
 * Esta clase muestra la pantalla que permite seleccionar el idioma del juego.
 * 
 * @author Damian Achaga
 */
public class LanguageMenu extends GameObject {

	/**
	 * Fuente a utilizar sobre la pelota.
	 * 
	 * @uml.property name="ttfFont"
	 */
	private String ttfFont = "./resources/images/wcfont.ttf";

	/**
	 * Tamaño de la fuente de la pelota.
	 * 
	 * @uml.property name="fontSize"
	 */
	private int fontSize = 55;

	/**
	 * distancia de separacion entre cada opción del menu.
	 */
	private static int ANCHO_LINE_MENU = 60;
	/**
	 * Imagen que contiene el título del menu.
	 */
	private BufferedImage titleMenu;
	/**
	 * Background del menu.
	 */
	private Background background;
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
	private int option = 0;
	/**
	 * contiene la posición en la coordenada X del menu.
	 */
	private int posXmenu;
	/**
	 * contiene la posición en la coordenada Y del menu.
	 */
	private int posYmenu;
	/**
	 * contendrá el objeto encargado de la configuración del juego, permitirá
	 * setear el lenguaje elegido y obtener el diccionario correspondiente.
	 */
	private WordChallenge parent;
	/**
	 * Contendrá la lista de idiomas que reconoce el juego.
	 */
	ArrayList<Hashtable<String, String>> idiomas;

	/**
	 * Contiene la cantidad de idiomas a elegir.
	 */
	private int cantOpciones;

	// /////////////////////////////////////////////////////////////////

	/**
	 * indica si el puntero del mouse esta sobre las opciones o en otro lugar
	 * libre de la pantalla.
	 * 
	 * @return true si el puntero esta sobre las opciones, false en caso
	 *         contrario.
	 */
	private boolean mouseInMenu() {
		return checkPosMouse(posXmenu, posYmenu - fontSize, posXmenu + 193,
				posYmenu + ANCHO_LINE_MENU * idiomas.size());
	}

	/**
	 * método que administra los eventos lanzados por el mouse.
	 */
	private void manejoMouse() {
		if (mouseInMenu()) {
			if (click()) {
				selectIdiom();
				finish();
			}
			for (int i = cantOpciones; i > 0; i--) {
				if (getMouseY() < posYmenu - fontSize + ANCHO_LINE_MENU * i
						&& getMouseY() > posYmenu - fontSize + ANCHO_LINE_MENU
								* (i - 1)) {
					option = i - 1;
				}
			}
		}
	}

	/**
	 * Metodo que selecciona el idioma.
	 */
	public final void selectIdiom() {
		String lengSelected = idiomas.get(option).get("name");
		Dictionary d = parent.getConfig().getDicctionary(lengSelected);
		parent.setKapeluz(d);
		parent.setSelectedLanguage(lengSelected);
		parent.nextGameID = WordChallenge.OPTION_MENU;
	}

	/**
	 * método que administra los eventos lanzados por teclado.
	 */
	private void manejoTeclado() {
		switch (bsInput.getKeyPressed()) {
		case KeyEvent.VK_ENTER:
			selectIdiom();
			finish();
			break;
		case KeyEvent.VK_UP:
			option--;
			if (option < 0) {
				option = idiomas.size() - 1;
			}
			break;
		case KeyEvent.VK_DOWN:
			option++;
			if (option > idiomas.size() - 1) {
				option = 0;
			}
			break;
		case KeyEvent.VK_ESCAPE:
			parent.nextGameID = WordChallenge.OPTION_MENU;
			finish();
			break;
		default:
			;
		}
	}

	/**
	 *@param parent
	 *            objeto que maneja la trancicion hacia el menu y permite
	 *            obtener el diccionario para el lenguaje correspondiente y
	 *            setear el lenguaje elejido en el juego.
	 */
	public LanguageMenu(final GameEngine newParent) {
		super(newParent);
		this.parent = (WordChallenge) newParent;
		this.idiomas = this.parent.getLanguages();
		int i = 0;
		String name = this.parent.getSelectedLanguage();
		String auxName = "";
		while (!name.equals(auxName)) {
			auxName = idiomas.get(i).get("name");
			i++;
		}
		this.option = --i;
	}

	/**
	 * @see com.golden.gamedev.GameObject#initResources()
	 */
	@Override
	public final void initResources() {
		posXmenu = getWidth() / 2 - 65;
		posYmenu = getHeight() / 2 - 50;
		background = new ImageBackground(
				getImage("./resources/images/menuidiomas.png"));
		font = fontManager.getFont(getImages("./resources/images/fontmenu.png",
				8, 12));
		cantOpciones = idiomas.size();
		pfMenu.setBackground(background);
	}

	/**
	 * Escribe las opciones en pantalla con un highlight en caso de que la
	 * opcion este seleccionada.
	 * 
	 * @param g
	 *            parametro que necesita el manejador de fuente para escribir en
	 *            pantalla.
	 * @param line
	 *            lugar en que la opcion debe ser escrita.
	 * @param selected
	 *            indica si la opcion esta seleccionada o no.
	 * @param flag
	 *            bandera a ser dibujada.
	 * @param sprite
	 *            imagen que representa el nombre del idioma
	 * @param selsprite
	 *            imagen que representa el nombre del idioma seleccionado
	 */

	final void drawLine(final Graphics2D g, final int line,
			final boolean selected, final String flag, final String sprite,
			final String selsprite) {

		BufferedImage bflag = ImageUtil.getImage(this.bsIO.getURL(flag),
				Transparency.TRANSLUCENT);
		if (selected) {
			BufferedImage bselsprite = ImageUtil.getImage(this.bsIO
					.getURL(selsprite), Transparency.TRANSLUCENT);
			g.drawImage(bflag, posXmenu - bflag.getWidth() - 20, posYmenu
					+ line - ANCHO_LINE_MENU, null);
			g.drawImage(bselsprite, posXmenu,
					posYmenu + line - ANCHO_LINE_MENU, null);
		} else {
			BufferedImage bsprite = ImageUtil.getImage(
					this.bsIO.getURL(sprite), Transparency.TRANSLUCENT);
			g.drawImage(bflag, posXmenu - bflag.getWidth() - 20, posYmenu
					+ line - ANCHO_LINE_MENU, null);
			g.drawImage(bsprite, posXmenu, posYmenu + line - ANCHO_LINE_MENU,
					null);
		}
		/*
		 * Color c = new Color(251,216,121); g.setColor(c); Font auxFont; try {
		 * auxFont = Font.createFont(Font.TRUETYPE_FONT, new
		 * File(ttfFont)).deriveFont((float) fontSize); } catch (Exception e) {
		 * auxFont = new Font("Arian", Font.PLAIN, fontSize); }
		 * 
		 * 
		 * g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		 * RenderingHints.VALUE_ANTIALIAS_ON); g.setFont(auxFont);
		 */
	}

	/**
	 * @see com.golden.gamedev.GameObject#render(java.awt.Graphics2D)
	 */
	@Override
	public final void render(final Graphics2D g) {
		pfMenu.render(g);
		g.drawImage(titleMenu, getWidth() / 2 - 220, 20, null);
		int line = 0;
		for (int i = 0; i < idiomas.size(); i++) {
			String flag = idiomas.get(i).get("flag");
			String sprite = idiomas.get(i).get("sprite");
			String selsprite = idiomas.get(i).get("selsprite");
			drawLine(g, line, (option == i), flag, sprite, selsprite);
			line = line + ANCHO_LINE_MENU;

		}
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
	 * Retorna la fuente a utilizar sobre la pelota.
	 * 
	 * @return ttFont
	 */
	public final String getTtfFont() {
		return ttfFont;
	}

	/**
	 * Setea la variable ttFont.
	 * 
	 * @param ttfFont
	 */
	public final void setTtfFont(final String ttfFont) {
		this.ttfFont = ttfFont;
	}

	/**
	 * Retorna el tamaño de la fuente de la pelota.
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
	 * Retorna la imagen que contiene el título del menu.
	 * 
	 * @return titleMenu
	 */
	public final BufferedImage getTitleMenu() {
		return titleMenu;
	}

	/**
	 * Setea la variable titleMenu.
	 * 
	 * @param titleMenu
	 */
	public final void setTitleMenu(final BufferedImage titleMenu) {
		this.titleMenu = titleMenu;
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
	 * Setea la variable background.
	 * 
	 * @param background
	 */
	public final void setBackground(final Background background) {
		this.background = background;
	}

	/**
	 * Retorna el Playfield del menu.
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
	 * Retorna el Manejador de fuente del menu.
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
	 * Retorna una variable que representa la opcion seleccionada.
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
	public void setOption(int option) {
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
	 * Setea la variable posYmenu.
	 * 
	 * @param posYmenu
	 */
	public final void setPosYmenu(final int posYmenu) {
		this.posYmenu = posYmenu;
	}

	/**
	 * Retorna el objeto encargado de la configuración del juego.
	 * 
	 * @return parent
	 */
	public final WordChallenge getParent() {
		return parent;
	}

	/**
	 * Setea la variable parent.
	 * 
	 * @param parent
	 */
	public final void setParent(final WordChallenge parent) {
		this.parent = parent;
	}

	/**
	 * Retorna la lista de idiomas que reconoce el juego.
	 * 
	 * @return idiomas
	 */
	public final ArrayList<Hashtable<String, String>> getIdiomas() {
		return idiomas;
	}

	/**
	 * Setea la variable idiomas.
	 * 
	 * @param idiomas
	 */
	public final void setIdiomas(
			final ArrayList<Hashtable<String, String>> idiomas) {
		this.idiomas = idiomas;
	}

	/**
	 * Retorna la cantidad de idiomas a elegir.
	 * 
	 * @return cantOpciones
	 */
	public final int getCantOpciones() {
		return cantOpciones;
	}

	/**
	 * Setea la variable cantOpciones.
	 * 
	 * @param cantOpciones
	 */
	public final void setCantOpciones(final int cantOpciones) {
		this.cantOpciones = cantOpciones;
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
