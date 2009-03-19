package ar.edu.unicen.exa.game2d.bigballs;

import java.awt.Dimension;
import java.util.List;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.GameObject;
//import common.datatypes.*;


/** 
 * Esta clase maneja las transiciones entre los diferentes estados del juego.
 * 
 * @author Damian Achaga
 */
public class BigBalls extends GameEngine  implements I2DGame {
		
 	/**
 	 * Identificador del juego Big Balls.
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
	 * Nivel actual en el juego.
	 */
	private int currentLevel = 1;
	
	/**
	 * Constante que representa la opcion de ir al menu.
	 */
	public static final int  OPTION_MENU = 0;
	
	/**
	 * Constante que representa la opcion de jugar y pasar
	 * los diferentes niveles.
	 */
	public static final int OPTION_PLAY = 1;
	
	/**
	 * Constante que representa la opcion de ver la pantalla de puntajes.
	 */
	public static final int OPTION_SCORES = 2;
	
	/**
	 * Contante que representa la opcion de salir del juego.
	 */
	public static final int OPTION_EXIT = 3;
	
	/**
	 * Maneja la cantidad de vidas que tiene el usuario.
	 */
	private int lives = 3;
	
	/**
	 * Instancia del levelGenerator apuntando al archivo de configuracion.
	 */	
	private LevelGenerator levelGenerator = 
		new LevelGenerator("./resources/bigballs/config.xml");
	
	/**
	 * Puntaje obtenido por el usuario.
	 */
	private int score = 0;
	
	/**
	 * Puntaje mas alto logrado por el usuario en sucesivas partidas.
	 */
	private int bestGlobalScore = 0;
	
	/**
	 * Constructor de la clase.
	 */
	public BigBalls() {
		super();
		this.distribute = true;
		
		game = new GameLoader();
		game.setup(this, new Dimension(800, 600),
				   GameLoader.ScreenMode.Dialog, false);
		
	}
	
	/**
	 * Dependiendo del Id recibido enviara a las diferentes
	 *  pantallas del juego o a los diferentes niveles.
	 *  @param gameID id que identifica la pantalla a mostrar.
	 *  @return el objeto que representa la pantalla correcta.
	 *  @Override
	 */
	public final GameObject getGame(final int gameID) {
		switch (gameID) {
			case OPTION_MENU: 
			{
				if (score > bestGlobalScore) {
					bestGlobalScore = score;
				}
				this.setCurrentLevel(1);
				this.setLives(3);
				this.setGlobalScore(0);
				return new Menu(this);
			} case OPTION_PLAY: 
			{
				Level l = this.levelGenerator.generateLevel(this, currentLevel);
				if (l != null) {
					return l;
				} else {
					return this.getGame(OPTION_MENU);
				}
			}
			case OPTION_SCORES:				
				return new HighScores(this, this.ranking);
			default: return null;
		}
	}
		
	/*
	 * Metodo para Testing
	 */
	public int getNextGameID() { 
		return nextGameID; 
	}
	
	/** 
	 * Retorna la cantidad de vidas que tiene el usuario.
	 * 
	 * Getter of the property <tt>lives</tt>.
	 * @return  lives Las vidas actuales del jugador.
	 * @uml.property  name="lives"
	 */
	public final int getLives() {
		return lives;
	}

	/** 
	 * Setea la cantidad de vidas que tiene el usuario.
	 * 
	 * Setter of the property <tt>lives</tt>.
	 * @param newlives  La cantidad de vidas que se setarán.
	 * @uml.property  name="lives"
	 */
	public final void setLives(final int newlives) {
		this.lives = newlives;
	}
	
	/** 
	 * Decrementa en 1 la cantidad de vidas en <tt>lives</tt>.
	 */
	public final void decreaseLives() {
		this.lives--;
	}
	
	/** 
	 * Obtiene el puntaje global.
	 * @return score puntaje global hasta el momento
	 */
	public final int getGlobalScore() {
		return score;
	}

	/** 
	 * Setea el Puntaje obtenido por el usuario.
	 * 
	 * Setter of the property <tt>GlobalScore</tt>.
	 * @param globalScore  El nuevo puntaje global que debe setearse.
	 * @uml.property  name="globalScore"
	 */
	public final void setGlobalScore(final int globalScore) {
		score = globalScore;
	}
		
	/**
	 * Retorna el mejor puntaje global hasta el momento.
	 * @return el valor de la variable bestGlobalScore 
	 */
	public int getBestGlobalScore() {
		return bestGlobalScore;
	}

	/**
	 * Setea un nuevo mejor puntaje global.
	 * @param xBestGlobalScore el puntaje a setear
	 */
	public void setBestGlobalScore(int xBestGlobalScore) {
		bestGlobalScore = xBestGlobalScore;
	}

	/**
	 * Suma el puntaje pasado en points al puntaje global.
	 * @param points El puntaje que se desea sumar
	*/
	public final void addPoints(final int points) {
		this.score = this.score + points;
	}
	
	/**
	 * Devuelve el nivel de juego en el que se encuentra actualmente.
	 * @return  Retorna el nivel de juego actual.
	 */
	public final int getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * Setea el nivel de juego actual.
	 * @param currentLev  Valor del nivel de juego a setear.
	 */
	public final void setCurrentLevel(final int currentLev) {
		this.currentLevel = currentLev;
	}

	/**
	 * Metodo main que inicia el juego.
	 * @param args el argumento del metodo main.
	 */
	public static void main(final String[] args) {

		Class c = null;
		try {
			c = Class.forName("ar.edu.unicen.exa.game2d.bigballs.BigBalls");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		I2DGame i = null;
		try {
			i = (I2DGame)c.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		i.execute();
		
		
    }

	/**
	 * Getter of the property <tt>levelGenerator</tt>.
	 * @return  Returns la instancia del LevelGenerator
	 * @uml.property  name="levelGenerator"
	 */		
	public final LevelGenerator getLevelGenerator() {
		return levelGenerator;
	}
	
	/**
	 * Setter of <tt>levelGenerator</tt>.
	 * @param newlevelGenerator  nuevo LevelGenerator que se debe setear
	 * @uml.property  name="levelGenerator"
	 */		
	public final void setLevelGenerator(LevelGenerator newlevelGenerator) {
		levelGenerator = newlevelGenerator;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#execute()
	 */
	/**
	 * Ejecuta el juego Big Balls.
	 */
	@Override
	public void execute() {
		game.start();
	}

	/* (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#getID()
	 */
	/**
	 * Metodo perteneciente a la interface I2DGame. Retorna el identificador
	 * del juego Big Balls.
	 * @return  id2DGame
	 */
	@Override
	public String getId() {
		return id2DGame;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#getScore()
	 */
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

	/* (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#getStats()
	 */
	/**
	 * Metodo perteneciente a la interface I2DGame. No implementado.
	 * Retorna una lista de stats del jugador.
	 * @return null.
	 */
	@Override
	public List<PlayerStat> getStats() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#isPlaying()
	 */
	/**
	 * Metodo perteneciente a la interface I2DGame. No implementado.
	 * @return false.
	 */
	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#setId(java.lang.String)
	 */
	/**
	 * Metodo perteneciente a la interface I2DGame. Setea el identificador
	 * del juego Big Balls.
	 * @param xId
	 */
	@Override
	public void setId(String xId) {
		id2DGame = xId;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#setStartStage(int)
	 */
	/**
	 * Metodo perteneciente a la interface I2DGame. No implementado.
	 * @param xStage  nro. de nivel del juego.
	 */
	@Override
	public void setStartStage(int xStage) {
	}

	/* (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#setTimeToPlay(float)
	 */
	/**
	 * Metodo perteneciente a la interface I2DGame. No implementado.
	 * @param xTime  tiempo para jugar.
	 */
	@Override
	public void setTimeToPlay(float xTime) {
	}
	
	/* (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#setPlayerId(String playerId)
	 */
	/**
	 * Setea el identificador del jugador.
	 * @param playerId
	 */
	@Override
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	
	/* (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#setRanking(Ranking ranking)
	 */
	/**
	 * Setea la Tabla de Ranking.
	 * @param ranking  retorna una instancia de la clase ranking.
	 */
	@Override
	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
	}
}
