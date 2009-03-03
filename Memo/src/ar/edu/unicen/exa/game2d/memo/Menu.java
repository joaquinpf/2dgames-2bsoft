package ar.edu.unicen.exa.game2d.memo;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.background.ImageBackground;
import com.golden.gamedev.object.font.SystemFont;
import com.golden.gamedev.util.FontUtil;

/**
 * 
 * @author Carlos Mirabella
 */

public class Menu extends GameObject {
	/**
	 * Distancia de separacion entre cada opción del menu.	
	 */
	private static final int ANCHO_LINE_MENU = 100;
	
	/**
	 * Posicion del texto en la primer linea.	
	 */
	private static final int FIRST_LINE_MENU = 200;
	
	private static final String menuMoveSound = "./resources/sounds/menuMove.wav";
	
	private static final String menuSelectSound = "./resources/sounds/menuSelect.wav";
	
	
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
	private SystemFont internalFont;
	
	/**
	 * variable que representa la opcion seleccionada.
	 */
	private int option = 1;
	
		
//////////////////////////////////////////////////////////////////	

	/**
	 * indica si el puntero del mouse esta sobre las opciones 
	 * o en otro lugar libre de la pantalla.
	 * @return true si el puntero esta sobre las opciones, 
	 *         false en caso contrario.
	 */
	private boolean mouseInMenu() {
		return checkPosMouse(0, FIRST_LINE_MENU,getWidth(), 
				             FIRST_LINE_MENU + ANCHO_LINE_MENU * 3);
	}
	
	/**
	 * método que administra los eventos lanzados por teclado.
	 */
	private void manejoTeclado() {
		switch (bsInput.getKeyPressed()) {
		case KeyEvent.VK_ENTER :
			if (option == Memo.MENU_PLAY) {
				playSound(menuSelectSound);
				parent.nextGameID = Memo.MENU_PLAY;
				finish();
			}
			if (option == Memo.MENU_HIGHSCORES) {
				playSound(menuSelectSound);
				parent.nextGameID = Memo.MENU_HIGHSCORES;
				finish();
			}
			if (option == Memo.MENU_EXIT) {
				playSound(menuSelectSound);
				finish();
			}
		break;
		case KeyEvent.VK_UP :
			option--;
			playSound(menuMoveSound);
			if (option < Memo.MENU_PLAY) {
				option = Memo.MENU_EXIT;
			}
		break;
		case KeyEvent.VK_DOWN :
			option++;
			playSound(menuMoveSound);
			if (option > Memo.MENU_EXIT) {
				option = Memo.MENU_PLAY;
			}
		break;
		case KeyEvent.VK_ESCAPE :
			playSound(menuMoveSound);
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
				if (option == Memo.MENU_PLAY) {
					playSound(menuSelectSound);
					parent.nextGameID = Memo.MENU_PLAY;
					finish();
				}
				if (option == Memo.MENU_HIGHSCORES) {
					playSound(menuSelectSound);
					parent.nextGameID = Memo.MENU_HIGHSCORES;
					finish();
				}
				if (option == Memo.MENU_EXIT) {
					playSound(menuSelectSound);
					finish();
				}
			}
			if (getMouseY() < (FIRST_LINE_MENU + ANCHO_LINE_MENU)
					&& option != Memo.MENU_PLAY) {
				option = Memo.MENU_PLAY; 
				playSound(menuMoveSound);
			}
			if ((getMouseY() > FIRST_LINE_MENU + ANCHO_LINE_MENU) 
				&& (getMouseY() < FIRST_LINE_MENU + ANCHO_LINE_MENU * 2)
				&& option != Memo.MENU_HIGHSCORES) {
				option = Memo.MENU_HIGHSCORES;
				playSound(menuMoveSound);
			}
			if (getMouseY() > (FIRST_LINE_MENU + ANCHO_LINE_MENU * 2) &&
					option != Memo.MENU_EXIT) {
				option = Memo.MENU_EXIT;
				playSound(menuMoveSound);
			}
		}
	}
	
	/**
	 * @see com.golden.gamedev.GameObject#initResources()
	 */
	@Override
	public final void initResources() {
		//Seteo de posiciones
		showCursor();
			
		//Fondo
		background = new ImageBackground(
				getImage("./resources/images/menubackground.png"), 800, 600);
		
		//Fuente a utilizar
		internalFont = new SystemFont(FontUtil.createTrueTypeFont(
				this.bsIO.getURL("./resources/images/ravie.ttf"), Font.PLAIN, 50));
	
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
		
		int inicial= (getWidth()/2) - (internalFont.getWidth(text)/2);
		if (selected) {
			// draw selected rectangle
			g.setColor(new Color(52, 71, 66));
			g.fillRoundRect(inicial - 8,
					   line+10,
					   internalFont.getWidth(text) + 16,
					   internalFont.getHeight() + 13,
					   50, 50);
		}

		
		
		if (selected)
			internalFont.setColor(new Color(123,164,239));
		else
			internalFont.setColor(Color.white);
		internalFont.drawString(g,text, inicial, line);
		//internalFont.drawString(g,text, GameFont.CENTER, 0, line, getWidth());
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
		//Escribe el texto en pantalla con antialias.
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		drawText(g, "Jugar", FIRST_LINE_MENU, (option == Memo.MENU_PLAY));
		drawText(g, "Puntuaciones", FIRST_LINE_MENU + ANCHO_LINE_MENU, 
				(option == Memo.MENU_HIGHSCORES));
		drawText(g, "Salir",FIRST_LINE_MENU + ANCHO_LINE_MENU * 2, 
				(option == Memo.MENU_EXIT));
	}
	
	/**
	 * @param elapsedTime Tiempo transcurrido desde el ultimo update
	 * @see com.golden.gamedev.GameObject#update(long)
	 */
	@Override
	public final void update(final long elapsedTime) {
		pfMenu.update(elapsedTime);
		manejoMouse();
		manejoTeclado();
	}
	
	/**
     * Getter of the property <tt>background</tt>.
     * @return  Returns the background.
     * @uml.property  name="background"
     */
	public Background getBackground(){
		return this.background;
	}
	
	/**
     * Setter of the property <tt>background</tt>.
     * @param newValue  The background to set.
     * @uml.property  name="background"
     */
	public void setBackground(Background background){
		this.background=background;
	}
	
	/**
     * Getter of the property <tt>pfMenu</tt>.
     * @return  Returns the pfMenu.
     * @uml.property  name="pfMenu"
     */
	public PlayField getPfMenu(){
		return this.pfMenu;
	}
	
	/**
     * Setter of the property <tt>pfMenu</tt>.
     * @param newValue  The pfMenu to set.
     * @uml.property  name="pfMenu"
     */
	public void setPfMenu (PlayField pfMenu){
		this.pfMenu=pfMenu;
	}
	
	/**
     * Getter of the property <tt>internalFont</tt>.
     * @return  Returns the internalFont.
     * @uml.property  name="internalFont"
     */
	public SystemFont getInternalFont(){
		return this.internalFont;
	}
	
	/**
     * Setter of the property <tt>internalFont</tt>.
     * @param newValue  The internalFont to set.
     * @uml.property  name="internalFont"
     */
	public void setInternalFont(SystemFont internalFont){
		this.internalFont=internalFont;
	}
	
	/**
     * Getter of the property <tt>option</tt>.
     * @return  Returns the option.
     * @uml.property  name="option"
     */
	public int getOption(){
		return this.option;
	}
	
	/**
     * Setter of the property <tt>option</tt>.
     * @param newValue  The option to set.
     * @uml.property  name="option"
     */
	public void setOption(int option){
		this.option=option;
	}
	
	/**
	 * 
	 * @param parent objeto que maneja la trancicion hacia las 
	 *               difenrentes pantallas segun la opcion elegida.
	 */
	public Menu(final GameEngine parent) {
		super(parent);
	}

}
