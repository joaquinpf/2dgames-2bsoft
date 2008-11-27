import java.awt.Graphics2D;
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


/**
 * Esta clase muestra la pantalla que permite 
 * seleccionar el idioma del juego.
 * 
 * @author Damian Achaga
 */
public class LanguageMenu extends GameObject {

	/**
	 * Fuente a utilizar sobre la pelota.
	 * @uml.property  name="ttfFont"
	 */
	private String ttfFont = "resources/images/wcfont.ttf";
	
	/**
	 * Tamaño de la fuente de la pelota.
	 * @uml.property  name="fontSize"
	 */
	private int fontSize = 55;
	
	/**
	 * distancia de separacion entre cada opción del menu.	
	 */
	private static final int ANCHO_LINE_MENU = 60;
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
	ArrayList<Hashtable<String, String>> idiomas;
	
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
		return checkPosMouse(posXmenu, posYmenu - fontSize, posXmenu + 193, 
				             posYmenu + ANCHO_LINE_MENU * idiomas.size());
	}
	
	/**
	 * método que administra los eventos lanzados por el mouse. 
	 */
	private void manejoMouse() {
		if (mouseInMenu()) {	
			if (click()) {
				String lengSelected = idiomas.get(option).get("name");
				Dictionary d = parent.getConfig().getDicctionary(lengSelected);
				parent.setKapeluz(d);
				parent.setSelectedLanguage(lengSelected);
				parent.nextGameID = WordChallenge.OPTION_MENU;
				finish();
			}	
			for (int i = cantOpciones; i > 0; i--) {
				if (getMouseY() < posYmenu - fontSize + ANCHO_LINE_MENU * i
					&& getMouseY() > posYmenu - fontSize 
					+ ANCHO_LINE_MENU * (i - 1)) {
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
			String lengSelected = idiomas.get(option).get("name");
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
			parent.nextGameID = WordChallenge.OPTION_MENU;			
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
		background = new ImageBackground(getImage("resources/images/menuidiomas.png"));
		font = fontManager.getFont(getImages("resources/images/fontmenu.png",
				   8, 12));
		cantOpciones = idiomas.size();
		pfMenu.setBackground(background);
	}
	
	/**
	 * Escribe las opciones en pantalla con un highlight 
	 * en caso de que la opcion este seleccionada.
	 * @param g parametro que necesita el manejador de fuente
	 *          para escribir en pantalla.
	 * @param line lugar en que la opcion debe ser escrita.
	 * @param selected indica si la opcion esta seleccionada o no.
	 * @param flag bandera a ser dibujada.
	 * @param sprite imagen que representa el nombre del idioma
	 * @param selsprite imagen que representa el nombre del idioma seleccionado
	 */
	
	final void drawLine(final Graphics2D g, final int line,	
			final boolean selected, final String flag, final String sprite,
			final String selsprite) {
		
		BufferedImage bflag = getImage(flag);
		if (selected) {
			BufferedImage bselsprite = getImage(selsprite);
			g.drawImage(bflag, posXmenu - bflag.getWidth() - 20, posYmenu + line - ANCHO_LINE_MENU,null);
			g.drawImage(bselsprite, posXmenu , posYmenu + line - ANCHO_LINE_MENU, null);
		}
		else{
			BufferedImage bsprite = getImage(sprite);
			g.drawImage(bflag, posXmenu - bflag.getWidth() - 20, posYmenu + line - ANCHO_LINE_MENU,null);
			g.drawImage(bsprite, posXmenu , posYmenu + line - ANCHO_LINE_MENU, null);
		}
		/*Color c = new Color(251,216,121);
		g.setColor(c);
		Font auxFont;
		try {
			auxFont = Font.createFont(Font.TRUETYPE_FONT, 
					new File(ttfFont)).deriveFont((float) fontSize);
		} catch (Exception e) {
			auxFont = new Font("Arian", Font.PLAIN, fontSize);
		}
		

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(auxFont);*/
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

}
