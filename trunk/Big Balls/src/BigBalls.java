

import java.awt.Dimension;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.GameObject;



/** 
 * Esta clase maneja el juego.
 */
public class BigBalls extends GameEngine {
		
	/**
	 * objeto que irá devolviendo los diferentes niveles del juego.
	 */
	private Gameplay manager = new Gameplay(this);
	
	/**
	 * Constante que representa la opcion de ir al menu.
	 */
	public static final int  OPTION_MENU = 0;
	/**
	 * Constante que representa la opción de jugar y pasar niveles.
	 */
	public static final int OPTION_PLAY = 1;
	/**
	 * Constante que representa la opcion de ver la pantalla de puntajes.
	 */
	public static final int OPTION_SCORES = 2;
	/**
	 * Contante que representa la opción de salir del juego.
	 */
	public static final int OPTION_EXIT = 3;
	
	
	
	/**
	 * Dependiendo del Id recibido enviará a las diferentes
	 *  pantallas del juego o a los diferentes niveles.
	 *  @param gameID id que identifica la pantalla a mostrar.
	 *  @return el objeto que representa la pantalla correcta.
	 *  @Override
	 */
	public final GameObject getGame(final int gameID) {
		switch (gameID) {
			case OPTION_MENU: return new Menu(this);
			case OPTION_PLAY:	return manager.getNextLevel();
			case OPTION_SCORES: return new HighScores(this);
			default: return null;
		}
	}

	/**
	 * Método main que inicia el juego.
	 * @param args el argumento del metodo main.
	 */
	public static void main(final String[] args) {
		GameLoader game = new GameLoader();
		game.setup(new BigBalls(), new Dimension(800, 600),
				   GameLoader.ScreenMode.Window);
		game.start();
    }
}
