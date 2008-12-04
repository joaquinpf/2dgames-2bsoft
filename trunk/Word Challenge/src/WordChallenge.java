import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Hashtable;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.GameObject;

/**
 * Esta clase maneja las transiciones entre los estados del juego.
 * 
 * @author Joaqu�n P�rez Fuentes
 */
public class WordChallenge extends GameEngine {

	/**
	 * Constante que representa la opcion de ir al menu.
	 */
	public static final int OPTION_MENU = 0;

	/**
	 * Constante que representa la opci�n de jugar y pasar niveles.
	 */
	public static final int OPTION_PLAY = 1;

	/**
	 * Constante que representa la opcion de ver la pantalla de puntajes.
	 */
	public static final int OPTION_SCORES = 2;

	/**
	 * Constante que representa la opcion de ver la pantalla de puntajes.
	 */
	public static final int OPTION_IDIOMAS = 3;

	/**
	 * ConStante que representa la opci�n de salir del juego.
	 */
	public static final int OPTION_EXIT = 4;

	/**
	 * @uml.property name="config"
	 */
	private Configurator config;

	/**
	 * @uml.property name="selectedLanguage"
	 */
	private String selectedLanguage = "spanish";

	/**
	 * @uml.property name="kapeluz"
	 * @uml.associationEnd inverse="wordChallenge:Dictionary"
	 */
	private Dictionary kapeluz;

	/**
	 * @uml.property name="clock"
	 * @uml.associationEnd inverse="wordChallenge:Clock"
	 */
	private Clock clock;

	/**
	 * @uml.property name="score"
	 */
	private int score = 0;

	/**
	 * @uml.property name="clock"
	 * @uml.associationEnd inverse="wordChallenge:Clock"
	 */
	private Score scoringTable;

	/**
	 * @uml.property  name="correctWords"
	 */
	private int correctWords = 0;
	
	/**
	 * @uml.property  name="failWords"
	 */
	private int failWords = 0;
	
	/**
	 * Constructor de la aplicaci�n.
	 */
	public WordChallenge() {
		super();
		config = new Configurator("resources/config.xml");
		kapeluz = config.getDicctionary(selectedLanguage);
		clock = new Clock(config.getTime());
		scoringTable = config.getScore();
		this.distribute = true;
	}

	/**
	 * Dependiendo del Id recibido enviar� a las diferentes pantallas del juego
	 * o a los diferentes niveles.
	 * 
	 * @param gameID
	 *            id que identifica la pantalla a mostrar.
	 * @return el objeto que representa la pantalla correcta.
	 * @Override
	 */
	@Override
	public final GameObject getGame(final int gameID) {
		switch (gameID) {
		case OPTION_MENU: {
			clock.setTotalTime(config.getTime());
			this.setScore(0);
			this.setCorrectWords(0);
			this.setFailWords(0);
			return new Menu(this);
		}
		case OPTION_PLAY: {
			Level vLevel = new Level(this, this.clock, this.kapeluz);
			vLevel.setScore(scoringTable);
			return vLevel;
		}
		case OPTION_IDIOMAS:
			return new LanguageMenu(this);
		case OPTION_SCORES:
			return new HighScores(this);
		default:
			return null;
		}
	}

	/**
	 * Getter of the property <tt>config</tt>.
	 * 
	 * @return Returns the config.
	 * @uml.property name="config"
	 */
	public final Configurator getConfig() {
		return config;
	}

	/**
	 * Setter of the property <tt>config</tt>.
	 * 
	 * @param newConfig
	 *            The config to set.
	 * @uml.property name="config"
	 */
	public final void setConfig(final Configurator newConfig) {
		this.config = newConfig;
	}

	/**
	 * Getter of the property <tt>selectedLanguage</tt>.
	 * 
	 * @return Returns the selectedLanguage.
	 * @uml.property name="selectedLanguage"
	 */
	public final String getSelectedLanguage() {
		return selectedLanguage;
	}

	/**
	 * Setter of the property <tt>selectedLanguage</tt>.
	 * 
	 * @param newSelectedLanguage
	 *            The selectedLanguage to set.
	 * @uml.property name="selectedLanguage"
	 */
	public final void setSelectedLanguage(final String newSelectedLanguage) {
		setKapeluz(this.config.getDicctionary(newSelectedLanguage));
		this.selectedLanguage = newSelectedLanguage;
	}

	/**
	 * Getter of the property <tt>kapeluz</tt>.
	 * 
	 * @return Returns the kapeluz.
	 * @uml.property name="kapeluz"
	 */
	public final Dictionary getKapeluz() {
		return kapeluz;
	}

	/**
	 * Setter of the property <tt>kapeluz</tt>.
	 * 
	 * @param newKapeluz
	 *            The kapeluz to set.
	 * @uml.property name="kapeluz"
	 */
	public final void setKapeluz(final Dictionary newKapeluz) {
		this.kapeluz = newKapeluz;
	}

	/**
	 * Getter of the property <tt>clock</tt>.
	 * 
	 * @return Returns the clock.
	 * @uml.property name="clock"
	 */
	public final Clock getClock() {
		return clock;
	}

	/**
	 * Setter of the property <tt>clock</tt>.
	 * 
	 * @param newClock
	 *            The clock to set.
	 * @uml.property name="clock"
	 */
	public final void setClock(final Clock newClock) {
		this.clock = newClock;
	}

	/**
	 * Obtiene el puntaje.
	 * 
	 * @return score puntaje hasta el momento
	 */
	public final int getScore() {
		return score;
	}

	/**
	 * Setter of the property <tt>score</tt>.
	 * 
	 * @param newScore
	 *            The score to set.
	 * @uml.property name="score"
	 */
	public final void setScore(final int newScore) {
		score = newScore;
	}

	/**
	 * Suma el puntaje pasado en points al puntaje global.
	 * 
	 * @param points
	 *            El puntaje que se desea sumar
	 */
	public final void addPoints(final int points) {
		this.score += points;
	}

	/** 
	 * Obtiene la cantidad de Palabras Correctas ingresadas por el usuario.
	 * @return correctWords Palabras correctas
	 */
	public final int getCorrectWords() {
		return correctWords;
	}

	/** 
	 * Setter of the property <tt>correctWords</tt>.
	 * @param newCorrectWords  The correctWords to set.
	 * @uml.property  name="correctWords"
	 */
	public final void setCorrectWords(final int newCorrectWords) {
		correctWords = newCorrectWords;
	}

	/** 
	 * Obtiene la cantidad de Palabras Incorrectas ingresadas por el usuario.
	 * @return correctWords Palabras Incorrectas
	 */
	public final int getFailWords() {
		return failWords;
	}

	/** 
	 * Setter of the property <tt>failWords</tt>.
	 * @param newFailWords  The failWords to set.
	 * @uml.property  name="newFailWords"
	 */
	public final void setFailWords(final int newFailWords) {
		failWords = newFailWords;
	}
	
	
	/**
	 * Incrementa en uno la cantidad de Palabras correctas ingresadas por el usuario.
	 * 
	*/
	public final void incrementCorrectWords() {
		this.correctWords++;
	}
	
	/**
	 * Incrementa en uno la cantidad de Palabras Incorrectas ingresadas por el usuario.
	 * 
	*/
	public final void incrementFailWords() {
		this.failWords++;
	}
	/**
	 * Obtiene los lenguajes disponibles.
	 * 
	 * @return lenguajes disponibles
	 */
	public final ArrayList<Hashtable<String, String>> getLanguages() {
		return config.getLanguages();
	}

	/**
	 * M�todo main que inicia el juego.
	 * 
	 * @param args
	 *            el argumento del metodo main.
	 */
	public static void main(final String[] args) {

		GameLoader game = new GameLoader();
		game.setup(new WordChallenge(), new Dimension(800, 600),
				GameLoader.ScreenMode.Window);
		game.start();
	}

	/**
	 * 
	 * @return scoringTable
	 */
	public final Score getScoringTable() {
		return scoringTable;
	}

	/**
	 * Retorna la variable scoringTable.
	 * 
	 * @param scoringTable
	 */
	public final void setScoringTable(final Score scoringTable) {
		this.scoringTable = scoringTable;
	}

	/**
	 * Retorna una constante que representa la opcion de ir al menu
	 * 
	 * @return OPTION_MENU
	 */
	public static int getOPTION_MENU() {
		return OPTION_MENU;
	}

	/**
	 * Retorna una constante que representa la opci�n sde jugar y pasar niveles.
	 * 
	 * @return OPTION_PLAY
	 */
	public static int getOPTION_PLAY() {
		return OPTION_PLAY;
	}

	/**
	 * Retorna una constante que representa la opcion de ver la pantalla de
	 * puntajes.
	 * 
	 * @return OPTION_SCORES
	 */
	public static int getOPTION_SCORES() {
		return OPTION_SCORES;
	}

	/**
	 * Retorna una constante que representa la opcion del lenguaje.
	 * 
	 * @return
	 */

	public static int getOPTION_IDIOMAS() {
		return OPTION_IDIOMAS;
	}

	/**
	 * Retorna una constante que representa la opci�n de salir del juego.
	 * 
	 * @return OPTION_EXIT
	 */
	public static int getOPTION_EXIT() {
		return OPTION_EXIT;
	}
}
