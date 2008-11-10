import java.awt.Dimension;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.GameObject;


/**
 * Clase Principal.
 * @author pdeluca
 */
public class Memo extends GameEngine {

	private Gameplay gamePlay = new Gameplay(this);

	/**
	 * Opcion menu.
	 */
	public static final int MENU_MENU = 0;
	
	/**
	 * Opcion jugar.
	 */
	public static final int MENU_PLAY = 1;

	/**
	 * Opcion records.
	 */
	public static final int MENU_HIGHSCORES = 2;
	
	/**
	 * Opcion salir.
	 */
	public static final int MENU_EXIT = 3;

	/**
	 * De acuerdo a la opcion elegida, mostrar lo que corresponda. 
	 * @param gameID opcion elegida en el menu
	 */
	public final GameObject getGame(final int gameID) {
		switch (gameID) {
			case MENU_MENU: return new Menu(this); 
			case MENU_PLAY: return gamePlay.getNextLevel();
			case MENU_HIGHSCORES: return new HighScores(this);
			default: return null;
		}
	}

	/**
	 * Metodo principal.
	 * @param args 
	 */
	public static void main(final String[] args) {
		GameLoader game = new GameLoader();
		game.setup(new Memo(), new Dimension(800, 600), GameLoader.ScreenMode.Window);
		game.start();
    }

}
