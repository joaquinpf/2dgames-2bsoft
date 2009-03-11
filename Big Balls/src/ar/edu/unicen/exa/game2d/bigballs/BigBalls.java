package ar.edu.unicen.exa.game2d.bigballs;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.GameObject;
//import common.datatypes.*;


/** 
 * Esta clase maneja las transiciones entre los estados del juego.
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
	
	/**Nivel actual en juego.
	 */
	private int currentLevel = 1;
	
	/**
	 * Constante que representa la opcion de ir al menu.
	 */
	public static final int  OPTION_MENU = 0;
	
	/**
	 * Constante que representa la opci�n de jugar y pasar niveles.
	 */
	public static final int OPTION_PLAY = 1;
	
	/**
	 * Constante que representa la opcion de ver la pantalla de puntajes.
	 */
	public static final int OPTION_SCORES = 2;
	
	/**
	 * Contante que representa la opci�n de salir del juego.
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
	 * Puntaje m�s alto logrado por el usuario en sucesivas partidas.
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
	 * Dependiendo del Id recibido enviar� a las diferentes
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
				return new HighScores(this,this.ranking);
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
	 * Getter of the property <tt>lives</tt>.
	 * @return  Returns the lives.
	 * @uml.property  name="lives"
	 */
	public final int getLives() {
		return lives;
	}

	/** 
	 * Setter of the property <tt>lives</tt>.
	 * @param newlives  The lives to set.
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
	 * Setter of the property <tt>GlobalScore</tt>.
	 * @param globalScore  The globalScore to set.
	 * @uml.property  name="globalScore"
	 */
	public final void setGlobalScore(final int globalScore) {
		score = globalScore;
	}
		
	/**
	 * @return the bestGlobalScore
	 */
	public int getBestGlobalScore() {
		return bestGlobalScore;
	}

	/**
	 * @param xBestGlobalScore the bestGlobalScore to set
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
	
	/**Devuelve el nivel de juego en el que se encuentra actualmente.
	 * @return  Retorna el nivel de juego actual.
	 */
	public final int getCurrentLevel() {
		return currentLevel;
	}

	/**Setea el nivel de juego actual.
	 * @param currentLev  Valor del nivel de juego a setear.
	 */
	public final void setCurrentLevel(final int currentLev) {
		this.currentLevel = currentLev;
	}

	/**
	 * M�todo main que inicia el juego.
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
	 * @return  Returns the levelGenerator
	 * @uml.property  name="levelGenerator"
	 */		
	public final LevelGenerator getLevelGenerator() {
		return levelGenerator;
	}
	
	/**
	 * Setter of <tt>levelGenerator</tt>
	 * @param newLevelGenerator new level generator
	 * @uml.property  name="levelGenerator"
	 */		
	public final void setLevelGenerator(LevelGenerator newlevelGenerator) {
		levelGenerator = newlevelGenerator;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#execute()
	 */
	@Override
	public void execute() {
		game.start();
	}

	/* (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#getID()
	 */
	@Override
	public String getId() {
		return id2DGame;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#getScore()
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
	@Override
	public List<PlayerStat> getStats() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#isPlaying()
	 */
	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#setId(java.lang.String)
	 */
	@Override
	public void setId(String xId) {
		id2DGame = xId;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#setStartStage(int)
	 */
	@Override
	public void setStartStage(int xStage) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#setTimeToPlay(float)
	 */
	@Override
	public void setTimeToPlay(float xTime) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#setPlayerId(String playerId)
	 */
	@Override
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	
	/* (non-Javadoc)
	 * @see ar.edu.unicen.exa.game2d.bigballs.I2DGame#setRanking(Ranking ranking)
	 */
	@Override
	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
	}
}
