import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.background.ImageBackground;

import java.awt.Graphics2D;
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
	 * @param parent
	 */
	public Level(GameEngine parent) {
		super(parent);
		this.orderedBalls = new Vector<Ball>();
	}

	/**
	 * @uml.property name="levelComplete"
	 */
	private boolean levelComplete = false;

	/**
	 * @uml.property name="playfield"
	 */
	private PlayField playfield;

	/**
	 * @uml.property name="orderedBalls"
	 */
	private Vector<Ball> orderedBalls;

	/**
	 * Getter of the property <tt>orderedBalls</tt>.
	 * 
	 * @return Returns the orderedBalls.
	 * @uml.property name="orderedBalls"
	 */
	public Vector<Ball> getOrderedBalls() {
		return orderedBalls;
	}

	/**
	 * @uml.property name="levelNumber"
	 */
	private static int levelNumber;

	/**
	 * Getter of the property <tt>levelNumber</tt>.
	 * 
	 * @return Returns the levelNumber.
	 * @uml.property name="levelNumber"
	 */
	public int getLevelNumber() {
		return levelNumber;
	}

	/**
	 * Setter of the property <tt>levelNumber</tt>.
	 * 
	 * @param levelNumber
	 *            The levelNumber to set.
	 * @uml.property name="levelNumber"
	 */
	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}

	/**
	 * Se seleciono ordenar el Vector orderedBalls empleando la interfaz
	 * Comparator, ya que de esta manera se puede extender la forma de
	 * realizar dicho ordenamiento.
	 */
	public void addBall(Ball newBall, Comparator c) {
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
	public void update(Observable o, Object arg) {
		this.finish(); // Cuando expira el tiempo finalizar el nivel
	}

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
	private SpriteGroup BALLGROUP;

	/**
	 * 
	 */
	private SpriteGroup BACKGROUND;

	/**
	 * 
	 */
	private Background background;

	/**
	 * Para realizar los mensajes por pantalla.
	 */
	private GameFont font;

	/**
	 * Metodo de inicializacion de las variables involucradas.
	 */
	public void initResources() {
		this.pos = 0;
		this.clock = new Clock();
		this.clock.setTotalTime("200");
		this.clock.start();
		this.clock.addObserver(this);
		this.playfield = new PlayField();
		background = new ImageBackground(getImage("resources/background.jpg"),
				640, 480);
		playfield.setBackground(background);
		this.BALLGROUP = new SpriteGroup("balls");
		this.BALLGROUP = new SpriteGroup("BACKGROUND");
		this.playfield.addGroup(this.BALLGROUP);
		for (Enumeration<Ball> e = this.orderedBalls.elements(); e
		.hasMoreElements();) {
			Ball ball = e.nextElement();
			this.playfield.add(ball);
			this.BALLGROUP.add(ball);
		}
		bordercollision = new BorderCollision(this.background);
		this.playfield.addCollisionGroup(this.BALLGROUP, this.BACKGROUND,
				this.bordercollision);

		font = fontManager.getFont(getImages("resources/font.png", 20, 3),
				" !            .,0123" + "456789:   -? ABCDEFG"
				+ "HIJKLMNOPQRSTUVWXYZ ");

	}

	/**
	 * @param g
	 *            Renderiza el juego en la pantalla.
	 */
	public void render(Graphics2D g) {
		playfield.render(g);
		font.drawString(g,
				"LEVEL :" + new Integer(this.levelNumber).toString(), 10, 10);
	}

	/**
	 * Actualiza las variables del juego. Controla ls pelotas que va
	 * seleccionando el jugador, en caso de equivaocarse da por finalizado el
	 * juego.
	 */

	public void update(long elapsedTime) {
		playfield.update(elapsedTime);
		if (click()) {
			Ball ballsel = (Ball) this.checkPosMouse(this.playfield, true);
			if (ballsel != null) {
				if (ballsel.getValue() == this.orderedBalls.elementAt(pos)
						.getValue()) {
					ballsel.setActive(false);
					this.pos++;
				} else {
					this.finish();
				}
			}
		}
	}

	/**
	 * @param clock2
	 */
	public void setClock(Clock clock) {
		this.clock = clock;

	}
}
