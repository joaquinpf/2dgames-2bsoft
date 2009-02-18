package ar.edu.unicen.exa.game2d.memo;
import java.awt.Dimension;
import java.util.List;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.GameObject;


/**
 * Clase Principal.
 * @author pdeluca
 */
public class Memo extends GameEngine implements I2DGame {

 	/**
 	 * Identificador del juego Memo.
 	 */
	private String id2DGame = null;
 	
 	/**
 	 * Instancia que permite cargar el juego 2d.
 	 */
 	private GameLoader game = null;
	
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
		
		game = new GameLoader();		
		game.setup(this, new Dimension(800, 600),
				GameLoader.ScreenMode.Dialog, false);
		
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
					if (globalScore > bestGlobalScore) {
						bestGlobalScore = globalScore;
					}
					this.setGlobalScore(0);
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
	private int globalScore = 0;

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
	 * Puntaje más alto logrado por el jugador en sucesivas partidas.
	 */
	private int bestGlobalScore; 

	/**
	 *  Retorna el mejor puntaje logrado por el jugador.
	 * 
	 * @return the bestGlobalScore
	 */
	public int getBestGlobalScore() {
		return bestGlobalScore;
	}
	
	/**
	 * Setea el puntaje mas alto logrado por el jugador.
	 * 
	 * @param xBestGlobalScore the bestGlobalScore to set
	 */
	public void setBestGlobalScore(int xBestGlobalScore) {
		bestGlobalScore = xBestGlobalScore;
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
	
	@Override
	public void execute() {
		game.start();				
	}
	@Override
	public String getID() {
		return id2DGame;
	}
	@Override
	public D2GameScore getScore() {
		D2GameScore d2GameScore = new D2GameScore();
		d2GameScore.setId2DGame(this.id2DGame);
		d2GameScore.setScore(this.getBestGlobalScore());
		d2GameScore.setIdPlayer("");
		return d2GameScore;
	}
	@Override
	public List<PlayerStat> getStats() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setId(String xId) {
		id2DGame = xId; 			
	}
	@Override
	public void setStartStage(int xStage) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setTimeToPlay(float xTime) {
		// TODO Auto-generated method stub
		
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
