import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.background.ColorBackground;


/**
 * Esta clase muestra la pantalla que permite 
 * seleccionar el idioma del juego.
 * 
 * @author Damian Achaga
 */
public class LanguajeMenu extends GameObject {
	
	/**
	 * distancia de separacion entre cada opción del menu.	
	 */
	private static final int ANCHO_LINE_MENU = 36;
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
	 * contendrá el objeto encargado de la configuración del juego,
	 * permitirá setear el lenguaje elejido y 
	 * obtener el diccionario correspondiente.	
	 */	
	private WordChallenge parent;
	/**
	 * Contendrá la lista de idiomas que reconoce el juego.
	 */
	private List<String> idiomas;
	/**
	 * Contiene la cantidad de idiomas a elejir.
	 */
	private int cantOpciones;
///////////////////////////////////////////////////////////////////	
	
	/**
	 * indica si el puntero del mouse esta sobre las opciones 
	 * o en otro lugar libre de la pantalla.
	 * @return true si el puntero esta sobre las opciones, 
	 *         false en caso contrario.
	 */
	private boolean mouseInMenu() {
		return checkPosMouse(posXmenu, posYmenu, posXmenu + 193, 
				             posYmenu + ANCHO_LINE_MENU * idiomas.size());
	}
	
	/**
	 * método que administra los eventos lanzados por el mouse. 
	 */
	private void manejoMouse() {
		if (mouseInMenu()) {	
			if (click()) {
				String lengSelected = idiomas.get(option);
				Dictionary d = parent.getConfig().getDicctionary(lengSelected);
				parent.setKapeluz(d);
				parent.setSelectedLanguage(lengSelected);
				parent.nextGameID = WordChallenge.OPTION_MENU;
				finish();
			}	
			for (int i = cantOpciones; i > 0; i--) {
				if (getMouseY() < posYmenu + ANCHO_LINE_MENU * i
					&& getMouseY() > posYmenu + ANCHO_LINE_MENU * (i - 1)) {
					option = i - 1; 
				}
			}
		}
	}
	
	/**
	 * método que administra los eventos lanzados por teclado.
	 */
	private void manejoTeclado() {
		switch (bsInput.getKeyPressed()) {
		case KeyEvent.VK_ENTER :
			String lengSelected = idiomas.get(option);
			Dictionary d = parent.getConfig().getDicctionary(lengSelected);
			parent.setKapeluz(d);
			parent.setSelectedLanguage(lengSelected);
			parent.nextGameID = WordChallenge.OPTION_MENU;
			finish();
		break;
		case KeyEvent.VK_UP :
			option--;
			if (option < 0) {
				option = idiomas.size() - 1;
			}
		break;
		case KeyEvent.VK_DOWN :
			option++;
			if (option > idiomas.size() - 1) {
				option = 0;
			}
		break;
		case KeyEvent.VK_ESCAPE :
			finish();
		break;
		default : ;
	}
	}
	/**
	 *@param parent objeto que maneja la trancicion hacia el menu y 
	 *		 permite obtener el diccionario para el lenguaje correspondiente 
	 *       y setear el lenguaje elejido en el juego. 
	 */
	public LanguajeMenu(final GameEngine parent) {
		super(parent);
		this.parent = (WordChallenge) parent;
		this.idiomas = this.parent.getConfig().getLenguages();
		idiomas = new ArrayList<String>();
	}
	/**
	 * @see com.golden.gamedev.GameObject#initResources()
	 */
	@Override
	public final void initResources() {
		posXmenu = getWidth() / 2 - 45;
		posYmenu = getHeight() / 2 - 50;
		titleMenu = getImage("resources/images/titleIdiomas1.gif");
		background = new ColorBackground(Color.GRAY);
		font = fontManager.getFont(getImages("resources/images/fontMenu.png",
				   8, 12));
		pfMenu.setBackground(background);
		cantOpciones = idiomas.size();
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
			g.setColor(Color.blue);
						g.fillRect(posXmenu - 1, (posYmenu + line), 
						font.getWidth(text) + 2, font.getHeight() + 2);
		}
		font.drawString(g, text, GameFont.LEFT, posXmenu , posYmenu + line,
						getWidth());
	}
	
	/**
	 * @see com.golden.gamedev.GameObject#render(java.awt.Graphics2D)
	 */	
	@Override
	public final void render(final Graphics2D g) {
		pfMenu.render(g);
		g.drawImage(titleMenu, getWidth() / 2 - 220, 20, null);
		int line = 0;
		int i = 0;
		for (Iterator <String> e = idiomas.iterator(); e.hasNext();) {
			String element = e.next();
			drawText(g, element, line, (option == i));
			line = line + ANCHO_LINE_MENU;
			i++;
			
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

}
