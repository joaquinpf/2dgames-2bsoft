package game2d.memo;
import java.awt.Dimension;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.GameObject;


/**
 * Clase Principal.
 * @author pdeluca
 */
public class Memo extends GameEngine {

	private LevelGenerator levelGenerator = new LevelGenerator("resources/configGame.xml");

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

	public Memo() {
		super();
		this.distribute=true;
	}
	/**
	 * De acuerdo a la opcion elegida, mostrar lo que corresponda. 
	 * @param gameID opcion elegida en el menu
	 */
	public final GameObject getGame(final int gameID) {
		switch (gameID) {
			case MENU_MENU: 
				{
					this.currentLevel = 1;
					return new Menu(this); 
				}
			case MENU_PLAY:
				{
					Level aux= levelGenerator.generarLevel(this,this.currentLevel);
					this.currentLevel++;
					if (aux == null)
					{
						return this.getGame(MENU_HIGHSCORES);
					}
					else return aux;
				}
			case MENU_HIGHSCORES: return new HighScores(this);
			default: return null;
		}
	}
	
	/**
	 * @uml.property  name="globalScore"
	 */
	private int globalScore;

	/**
	 * Getter of the property <tt>globalScore</tt>
	 * @return  Returns the globalScore.
	 * @uml.property  name="globalScore"
	 */
	public int getGlobalScore() {
		return globalScore;
	}

	/**
	 * Setter of the property <tt>globalScore</tt>
	 * @param globalScore  The globalScore to set.
	 * @uml.property  name="globalScore"
	 */
	public void setGlobalScore(int globalScore) {
		this.globalScore = globalScore;
	}

	/**
	 * Suma los puntos obtenidos al puntaje global
	 * @uml.property  name="globalScore"
	 */
	public void addPoints(int points){
		this.globalScore = this.globalScore + points;
	}
	
	/**
	 * @uml.property  name="currentLevel"
	 */
	private int currentLevel = 1;

	/**
	 * Getter of the property <tt>currentLevel</tt>
	 * @return  Returns the currentLevel.
	 * @uml.property  name="currentLevel"
	 */
	public int getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * Setter of the property <tt>currentLevel</tt>
	 * @param currentLevel  The currentLevel to set.
	 * @uml.property  name="currentLevel"
	 */
	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}
	
	public LevelGenerator getLevelGenerator(){
		return this.levelGenerator;
	}
	
	public void setLevelGenerator(LevelGenerator levelGenerator){
		this.levelGenerator=levelGenerator;
	}
	
	/**
	 * Metodo principal.
	 * @param args 
	 */
	public static void main(final String[] args) {
		GameLoader game = new GameLoader();
		game.setup(new Memo(), new Dimension(800, 600),
				GameLoader.ScreenMode.Dialog, false);
		game.start();
    }

}
