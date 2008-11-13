import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.background.ColorBackground;

/**
 * Menu del Juego.
 * @author pdeluca
 */
public class Menu extends GameObject {

	/**
	 * Ancho de la linea de menu.
	 */
	private static final int MENU_LINE_WIDTH = 36;
	
	/**
	 * Largo de la grilla de fuente.
	 */
	private static final int FONT_GRID_HEIGHT = 12;
	
	/**
	 * Ancho de la grilla de fuente.
	 */
	private static final int FONT_GRID_WIDTH = 8;	

	/**
	 * imagen con el titulo de la seccion implementada.
	 */
	private BufferedImage imgMenu;

	/**
	 * Fondo de la pantalla.
	 */
	private Background background;

	/**
	 * Pantalla definida donde se graficara.
	 */
	private PlayField playFieldMenu = new PlayField();

	/**
	 * Manejador de fuente.
	 */
	private GameFont font;

	private int option = 1;

	private int coordX;

	private int coordY;

	private int posXpointer;

	private int posYpointer; 

	/**
	 * Constructor.
	 * @param parent instancia de GameEngine.
	 */
	public Menu(final GameEngine parent) {
		super(parent);
	}
	
	/**
	 * @return true si el mouse esta posicionado sobre la zona del menu.
	 */
	private boolean isMouseOverMenu() {
		return checkPosMouse(coordX, coordY, coordX + 193, coordY + MENU_LINE_WIDTH * 3);
	}
	
	/**
	 * 
	 */
	private void doKeyboardEventHandling() {
		switch (bsInput.getKeyPressed()) {
			case KeyEvent.VK_ENTER :
				if (option == Memo.MENU_PLAY) {
					parent.nextGameID = Memo.MENU_PLAY;
					finish();
				}
				
				if (option == Memo.MENU_HIGHSCORES) {
					parent.nextGameID = Memo.MENU_HIGHSCORES;
					finish();
				}
				
				if (option == Memo.MENU_EXIT) {
					finish();
				}
			break;
			
			case KeyEvent.VK_UP :
				option--;
				if (option < Memo.MENU_PLAY) {
					option = Memo.MENU_EXIT;
				}
			break;
			
			case KeyEvent.VK_DOWN :
				option++;
				if (option > Memo.MENU_EXIT) {
					option = Memo.MENU_PLAY;
				}
			break;
			
			case KeyEvent.VK_ESCAPE :
				finish();
			break;
			default : ;
		}
	}
	

	private void doMouseEventHandling() {
		if (isMouseOverMenu()) {	
			if (click()) {
				if (option == Memo.MENU_PLAY) {
					parent.nextGameID = Memo.MENU_PLAY;
					finish();
				}
				if (option == Memo.MENU_HIGHSCORES) {
					parent.nextGameID = Memo.MENU_HIGHSCORES;
					finish();
				}
				if (option == Memo.MENU_EXIT) {
					finish();
				}
			}
			
			if (getMouseY() < coordY + MENU_LINE_WIDTH) {
				option = Memo.MENU_PLAY; 
			}
			
			if (getMouseY() > coordY + MENU_LINE_WIDTH 
				&& getMouseY() < coordY + MENU_LINE_WIDTH * 2) {
				option = Memo.MENU_HIGHSCORES;
			}
			
			if (getMouseY() > coordY + MENU_LINE_WIDTH * 2) {
				option = Memo.MENU_EXIT;
			}
		}
	}

	/**
	 * Inicializacion
	 */
	@Override
	public final void initResources() {
		coordX = getWidth() / 2 - 45;
		coordY = getHeight() / 2 + 50;
		posXpointer = coordX - 55;
		posYpointer = coordY - 10;
		
		imgMenu = getImage("resources/images/menu.gif", false);
		
		background = new ColorBackground(Color.BLACK);
		font = fontManager.getFont(getImages("resources/images/font.png", FONT_GRID_WIDTH, FONT_GRID_HEIGHT));
		playFieldMenu.setBackground(background);
	}

	/**
	 * Dibuja un item del menu.
	 * @param g
	 * @param text
	 * @param line
	 * @param selected
	 */
	final void drawText(final Graphics2D g, final String text, final int line, final boolean selected) {
		if (selected) {
			g.setColor(Color.ORANGE);
			g.fillRect(coordX - 1, (coordY + line) + 4, font.getWidth(text) + 2, font.getHeight() + 2);
		}
		
		font.drawString(g, text, GameFont.LEFT, coordX , coordX + line, getWidth());
	}

	/**
	 * Genera el menu.
	 * @param g2D instancia de Graphics2D.
	 */
	@Override
	public final void render(final Graphics2D g2D) {
		playFieldMenu.render(g2D);
		
		drawText(g2D, "Game", 0, (option == Memo.MENU_PLAY));
		drawText(g2D, "High Scores", MENU_LINE_WIDTH, (option == Memo.MENU_HIGHSCORES));
		drawText(g2D, "Exit", MENU_LINE_WIDTH * 2, (option == Memo.MENU_EXIT));
		
		g2D.drawImage(imgMenu, getWidth() / 2 - 130, 20, null);
	}
	
	/**
	 * @param elapsedTime
	 */
	@Override
	public final void update(final long elapsedTime) {
		playFieldMenu.update(elapsedTime);
		doKeyboardEventHandling();
		doMouseEventHandling();
	}
}
