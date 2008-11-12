import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.background.ImageBackground;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * @uml.dependency supplier="Ball"
 */
/**
 * @author M&M
 * 
 */
public class Level extends GameObject implements Observer {

	/**
	 * @uml.property name="playfield"
	 */
	private PlayField playfield;

	/**
	 * @uml.property name="orderedBalls"
	 */
	private Vector<Ball> orderedBalls;

	/**
	 * @uml.property name="levelNumber"
	 */
	private int levelNumber;

	/**
	 * Timer para controlar el tiempo de la partida.
	 */
	private Clock clock;

	/**
	 * variable para llevar la posicion de la pelota seleccionada y compararla.
	 * con el arreglo orderedBalss
	 */
	private int pos;

	/**
	 *Empleado para manejar las colisiones entre las pelotas (sprites).
	 */
	private BallCollision ballcollision;

	/**
	 * Empleado para manejar las colisiones de los sprites y los limites de la
	 * pantalla.
	 */
	private BorderCollision bordercollision;

	/**
	 * Variable empleada para agrupar las pelotas.
	 */
	private SpriteGroup ballGroup;

	/**
	 * 
	 */
	private SpriteGroup backgroundGroup;

	/**
	 * 
	 */
	private Background background;

	/**
	 * Para realizar los mensajes por pantalla.
	 */
	private GameFont font;

	/**
	 * Para trabajar con el GameEngine ahorrando demasiados castings.
	 */
	private BigBalls engine;
	
	/**
	 * Para trabajar con el GameEngine ahorrando demasiados castings.
	 */
	private int possiblePoints = 0;
	
	/**
	 * @param parent El GameEngine padre del Level
	 */
	public Level(final GameEngine parent) {
		super(parent);
		this.engine = (BigBalls) parent;
		this.orderedBalls = new Vector<Ball>();
	}
	
	/**
	 * Se seleciono ordenar el Vector orderedBalls empleando la interfaz
	 * Comparator, ya que de esta manera se puede extender la forma de
	 * realizar dicho ordenamiento.
	 * @param newBall La nueva pelota a agregar
	 * @param c El comparador por el cual se ordenan las pelotas
	 */
	public final void addBall(final Ball newBall, final Comparator<Ball> c) {
		orderedBalls.add(newBall);
		Collections.sort(orderedBalls, c);
	}

	/**
	 * @param o
	 *            .
	 * @param arg
	 *            Cuando recibe el evento de que sucedio un cambio da por
	 *            finalizado el juego
	 */
	public final void update(final Observable o, final Object arg) {
		if (this.clock.getRemainingTime() == 0) {
			//Pierde el nivel
			this.loseLevel();
		}
		//TODO Actualizar reloj
	}

	/**
	 * Metodo de inicializacion de las variables involucradas.
	 */
	public final void initResources() {
		this.pos = 0;
		this.playfield = new PlayField();
		playfield.setBackground(background);
		this.ballGroup = new SpriteGroup("balls");
		this.backgroundGroup = new SpriteGroup("BACKGROUND");
		for (Enumeration<Ball> e = this.orderedBalls.elements(); e
		.hasMoreElements();) {
			Ball ball = e.nextElement();
			this.playfield.add(ball);
			this.ballGroup.add(ball);
		}
		this.playfield.addGroup(this.ballGroup);
		bordercollision = new BorderCollision(this.background);
		ballcollision = new BallCollision();
		this.playfield.addCollisionGroup(this.ballGroup, this.backgroundGroup,
				this.bordercollision);
		this.playfield.addCollisionGroup(this.ballGroup, this.ballGroup, 
				this.ballcollision);

		font = fontManager.getFont(getImages("resources/font.png", 20, 3),
				" !            .,0123" + "456789:   -? ABCDEFG"
				+ "HIJKLMNOPQRSTUVWXYZ ");

		this.clock.addObserver(this);
		this.clock.start();
	}

	/**
	 * @param g
	 *            Renderiza el juego en la pantalla.
	 */
	public final void render(final Graphics2D g) {
		playfield.render(g);
		font.drawString(g,
				"LEVEL :" + new Integer(this.levelNumber).toString(), 10, 10);
	}

	/**
	 * Actualiza las variables del juego. Controla ls pelotas que va
	 * seleccionando el jugador, en caso de equivaocarse da por finalizado el
	 * juego.
	 * @param elapsedTime Tiempo transcurrido desde el ultimo update
	 */

	public final void update(final long elapsedTime) {
		playfield.update(elapsedTime);
		if (click()) {
			Ball ballsel = (Ball) this.checkPosMouse(this.playfield, true);
			if (ballsel != null) {
				if (ballsel.getValue() == this.orderedBalls.elementAt(pos)
						.getValue()) {
					ballsel.setActive(false);
					this.pos++;
					if (this.pos == this.orderedBalls.size()) {
						this.winLevel();
					}
				} else {
					//Pierde el nivel
					this.loseLevel();
				}
			}
		}
	    if (keyDown(KeyEvent.VK_ESCAPE)) {
			//Si presiona ESC vuelve al menu
	    	this.engine.nextGameID = BigBalls.OPTION_MENU;
		    this.finish();
	    }		
	}

	/**
	 * Setea el reloj del nivel.
	 * @param newClock el reloj a setear
	 */
	public final void setClock(final Clock newClock) {
		this.clock = newClock;
	}
	
	/**
	 * Getter of the property <tt>orderedBalls</tt>.
	 * 
	 * @return Returns the orderedBalls.
	 * @uml.property name="orderedBalls"
	 */
	public final Vector<Ball> getOrderedBalls() {
		return orderedBalls;
	}

	/**
	 * Getter of the property <tt>levelNumber</tt>.
	 * 
	 * @return Returns the levelNumber.
	 * @uml.property name="levelNumber"
	 */
	public final int getLevelNumber() {
		return levelNumber;
	}

	/**
	 * Setter of the property <tt>levelNumber</tt>.
	 * 
	 * @param newLevelNumber
	 *            The levelNumber to set.
	 * @uml.property name="levelNumber"
	 */
	public final void setLevelNumber(final int newLevelNumber) {
		levelNumber = newLevelNumber;
	}
	
	/**
	 * Setter of the property <tt>background</tt>.
	 * 
	 * @param backgroundRoute
	 *            La ruta al fondo a setear
	 * @uml.property name="backgroundRoute"
	 */
	public final void setBackground(final String backgroundRoute) {
		this.background = new ImageBackground(getImage(backgroundRoute), 
				640, 480);
	}
	
	/**
	 * Termina el nivel habiendo perdido.
	 * Decrementa una vida y basandose en las restantes, setea el proximo 
	 * estado como OPTION_MENU u OPTION_PLAY si le queda al menos una.
	 */
	public final void loseLevel() {
		this.engine.decreaseLives();
		if (this.engine.getLives() == 0) {
			//Si no le quedan vidas, se vuelve al menu
			this.engine.nextGameID = BigBalls.OPTION_MENU;
		} else {
			//Si le quedan vidas, sigue jugando
			this.engine.nextGameID = BigBalls.OPTION_PLAY;
		}
		this.finish();
	}
	
	/**
	 * Termina el nivel habiendo ganado.
	 * Setea el proximo estado como OPTION_PLAY.
	 */
	public final void winLevel() {
		this.engine.addPoints(this.generateScore());
		this.engine.setCurrentLevel(levelNumber + 1);
		this.engine.nextGameID = BigBalls.OPTION_PLAY;
		this.finish();
	}
	
	/**
	 * Getter of the property <tt>possiblePoints</tt>.
	 * 
	 * @return Returns the possiblePoints.
	 * @uml.property name="possiblePoints"
	 */
	public final int getPossiblePoints() {
		return possiblePoints;
	}

	/**
	 * Setter of the property <tt>possiblePoints</tt>.
	 * 
	 * @param newPossiblePoints
	 *            The possiblePoints to set.
	 * @uml.property name="possiblePoints"
	 */
	public final void setPossiblePoints(final int newPossiblePoints) {
		possiblePoints = newPossiblePoints;
	}
	
	/**
	 * Genera el puntaje obtenido en el nivel.
	 * 
	 * @return puntaje obtenido en el nivel
	 */
	public final int generateScore() {
		return this.possiblePoints * this.clock.getRemainingTime();
	}
}
