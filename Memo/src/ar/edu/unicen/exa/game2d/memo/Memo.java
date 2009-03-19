package ar.edu.unicen.exa.game2d.memo;
import java.awt.Dimension;
import java.util.List;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.GameObject;
//import common.datatypes.*;

/**
 * Clase Principal.
 * Maneja las transiciones entre los estados del juego.
 * @author pdeluca
 */
public class Memo extends GameEngine implements I2DGame {

 	/**
 	 * Identificador del juego Memo.
 	 */
	private String id2DGame = null;
	
	/**
 	 * Identificador del jugador.
 	 */
	private String playerId = null;
	
	/**
 	 * Tabla de Ranking.
 	 */
	private Ranking ranking = null;
 	
 	/**
 	 * Instancia que permite cargar el juego 2d.
 	 */
 	private GameLoader game = null;
	
 	/**
 	 * Administra los niveles del juego.
 	 */
 	private LevelGenerator levelGenerator = new LevelGenerator(
 			"./resources/memo/configGame.xml");

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
	 * Constructor del Memo.
	 */
	public Memo() {
		super();
		
		game = new GameLoader();		
		game.setup(this, new Dimension(800, 600),
				GameLoader.ScreenMode.Dialog, false);
		
		this.distribute = true;
	}
	/**
	 * De acuerdo a la opcion elegida, mostrar lo que corresponda. 
	 * @param gameID opcion elegida en el menu.
	 * @return segun la opcion elegida retorna un Menu, Level o HighScores.
	 */
	public final GameObject getGame(final int gameID) {
		
		switch (gameID) {
			case MENU_MENU: 
				if (globalScore > bestGlobalScore) {
					bestGlobalScore = globalScore;
				}
				this.setGlobalScore(0);
				this.currentLevel = 1;
				return new Menu(this);
			case MENU_PLAY:
				Level aux = levelGenerator.generarLevel(this, 
						this.currentLevel);
				this.currentLevel++;
				if (aux == null) {
					return this.getGame(MENU_HIGHSCORES);
				} else {
					return aux;
				}
			case MENU_HIGHSCORES: 
				return new HighScores(this, this.ranking);
			default: return null;
		}
	}
	
	/**
	 * Puntaje obtenido por el jugador.
	 *  
	 * @uml.property  name="globalScore"
	 */
	private int globalScore = 0;

	/**
	 * Retorna el puntaje obtenido por el jugador.
	 * 
	 * Getter of the property <tt>globalScore</tt>.
	 * @return  Returns the globalScore.
	 * @uml.property  name="globalScore"
	 */
	public int getGlobalScore() {
		return globalScore;
	}

	/**
	 * Setea el puntaje obtenido por el jugador.
	 * 
	 * Setter of the property <tt>globalScore</tt>.
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
	 * Suma los puntos obtenidos al puntaje global.
	 * @uml.property  name="globalScore"
	 */
	public void addPoints(int points){
		this.globalScore = this.globalScore + points;
	}
	
	/**
	 * Nivel del juego alcanzado por el jugador.
	 * 
	 * @uml.property  name="currentLevel"
	 */
	private int currentLevel = 1;

	/**
	 * Retorna el Nivel del juego alcanzado por el jugador.
	 * 
	 * Getter of the property <tt>currentLevel</tt>.
	 * @return  Returns the currentLevel.
	 * @uml.property  name="currentLevel"
	 */
	public int getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * Setea el Nivel del juego alcanzado por el jugador.
	 * 
	 * Setter of the property <tt>currentLevel</tt>.
	 * @param currentLevel  The currentLevel to set.
	 * @uml.property  name="currentLevel"
	 */
	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}
	
	/**
	 * Retorna el levelGenerator encargado de administrar
	 * los niveles del juego.
	 *  
	 * @return  el levelGenerator.
	 */
	public LevelGenerator getLevelGenerator() {
		return this.levelGenerator;
	}
	
	/**
	 * Setea el levelGenerator encargado de administrar
	 * los niveles del juego.
	 *  
	 * @param levelGenerator
	 */
	public void setLevelGenerator(LevelGenerator levelGenerator) {
		this.levelGenerator = levelGenerator;
	}
	
	/**
	 * Ejecuta el Juego Memo.
	 */
	@Override
	public void execute() {
		game.start();				
	}
	
	/**
	 * Metodo perteneciente a la interface I2DGame. Retorna el identificador
	 * del juego Memo.
	 * 
	 * @return  id2DGame
	 */
	@Override
	public String getId() {
		return id2DGame;
	}
	
	/**
	 * Metodo perteneciente a la interface I2DGame.
	 * @return d2GameScore 
	 */
	@Override
	public D2GameScore getScore() {
		D2GameScore d2GameScore = new D2GameScore();
		d2GameScore.setId2DGame(this.id2DGame);
		d2GameScore.setScore(this.getBestGlobalScore());
		d2GameScore.setIdPlayer(this.playerId);
		return d2GameScore;
	}
	
	/**
	 * Metodo perteneciente a la interface I2DGame. No implementado.
	 * Retorna una lista de stats del jugador.
	 * @return null.
	 */
	@Override
	public List<PlayerStat> getStats() {
		return null;
	}
	
	/**
	 * Metodo perteneciente a la interface I2DGame. No implementado.
	 * @return false.
	 */
	@Override
	public boolean isPlaying() {
		return false;
	}
	
	/**
	 * Metodo perteneciente a la interface I2DGame. Setea el identificador
	 * del juego Memo.
	 * @param xId
	 */
	@Override
	public void setId(String xId) {
		id2DGame = xId; 			
	}
	/**
	 * Metodo perteneciente a la interface I2DGame. No implementado.
	 * @param xStage
	 */
	@Override
	public void setStartStage(int xStage) {
	}
	
	/**
	 * Metodo perteneciente a la interface I2DGame. No implementado.
	 * @param xTime
	 */
	@Override
	public void setTimeToPlay(float xTime) {
	}

	/**
	 * Setea el identificador del jugador.
	 * @param playerId
	 */
	/*
	 * (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#setPlayerId(String playerId)
	 */
	@Override
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	
	/**
	 * Setea la Tabla de Ranking.
	 * @param ranking  retorna una instancia de la clase ranking.
	 */
	/*
	 * (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#setRanking(Ranking ranking)
	 */
	@Override
	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
	}
	
	/**
	 * Metodo principal.
	 * @param args 
	 */
	public static void main(final String[] args) {
		Memo memo = new Memo();
		memo.execute();
    }
}
