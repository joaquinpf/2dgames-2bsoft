import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.AnimatedSprite;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.background.ImageBackground;
import com.golden.gamedev.object.sprite.AdvanceSprite;
import com.golden.gamedev.object.sprite.VolatileSprite;
import com.golden.gamedev.util.ImageUtil;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
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
	 * @uml.property name="timerEndLevel"
	 */
	private Timer timerEndLevel;
	
	/**
	 * Reloj del nivel.
	 * Indica el tiempo maximo para ordenar las bolas del juego.
	 * 
	 * @uml.property  name="clockSprite"
	 */
	private AnimatedSprite clockSprite = null;
	
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
	 * Componente X del inicio de la grilla de agujeros.
	 */
	private int holeGridStartX = 8;
	
	/**
	 * Componente Y del inicio de la grilla de agujeros.
	 */
	private int holeGridStartY = 442;
	
	/**
	 * Pixeles de salto entre fila y fila de agujeros.
	 */
	private int holeRowJump = 75;
	
	/**
	 * Pixeles de salto entre fila y fila de agujeros.
	 */
	private int holeColumnJump = 80;
	
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
	}

	/**
	 * Metodo de inicializacion de las variables involucradas.
	 */
	public final void initResources() {
		this.pos = 0;
		this.playfield = new PlayField();
		playfield.setBackground(this.background);
		this.backgroundGroup = new SpriteGroup("BACKGROUND");
		this.ballGroup = new SpriteGroup("balls");
		for (Enumeration<Ball> e = this.orderedBalls.elements(); e
		.hasMoreElements();) {
			Ball ball = e.nextElement();
			this.playfield.add(ball);
			this.ballGroup.add(ball);
		}
		playfield.addGroup(ballGroup);
		Background collMask = 
			new ImageBackground(getImage("resources/images/collmask.png"), 
				800, 441);
		//TODO Mejorar magicidad
		bordercollision = new BorderCollision(collMask);
		ballcollision = new BallCollision();
		this.playfield.addCollisionGroup(this.ballGroup, this.backgroundGroup,
				this.bordercollision);
		this.playfield.addCollisionGroup(this.ballGroup, this.ballGroup, 
				this.ballcollision);
		
		font = fontManager.getFont(getImages("resources/images/font.png", 20, 3),
				" !            .,0123" + "456789:   -? ABCDEFG"
				+ "HIJKLMNOPQRSTUVWXYZ ");
		
		clockSprite = new AnimatedSprite(ImageUtil.getImages(this.bsIO.getURL("Resources/images/clock.png"), 12, 1,Transparency.TRANSLUCENT));
		clockSprite.setLoopAnim(true);
		clockSprite.getAnimationTimer().setDelay(200);
		clockSprite.setLocation(425, 470);
		playfield.add(clockSprite);
		clockSprite.setActive(true);

		this.clock.addObserver(this);
		this.clock.start();
		clockSprite.setAnimate(true);
		
		timerEndLevel = new Timer(3000);
		timerEndLevel.setActive(false);
		
		setPositionBalls();
		generateHoles();
	}

	private ArrayList<Ball> shuffle(SpriteGroup balls) {
		ArrayList<Ball> shuffledBalls = new ArrayList<Ball>();
		for (int i=0; i<balls.getSize(); i++) {
			shuffledBalls.add((Ball)balls.getSprites()[i]);
		}
		Collections.shuffle(shuffledBalls);
		return shuffledBalls;
	}
	
	private void setPositionBalls() {
		
		ArrayList<Ball> balls = shuffle(ballGroup);
		
		//Calcula el ancho combinado de todas las pelotas
		double combinedWidth=0;
		for (int i = 0; i < balls.size(); i++) {
			Ball aBall =  balls.get(i);
			combinedWidth += aBall.getWidth();
		}	
		
		//Calcula el espaciado, basandose en el espacio restante
		double spacing = (800 - combinedWidth) / (ballGroup.getSize() + 1);
		
		double xposition = 0;
		for (int i = 0; i < balls.size() ; i++) {
			//Aumenta el espaciado
			xposition += spacing;
			Ball aBall =  balls.get(i);
			aBall.setLocation(xposition, 200);
			//Aumenta en el ancho de la pelota actual luego de colocarla. 
			//Esto es asi porque GTGE toma el inicio de la imagen desde la 
			//izquierda
			xposition += aBall.getWidth();
		}	
	}
	
	private void generateHoles() {
		for(int i=0; i<orderedBalls.size();i++){
			Sprite a;
			if(i<5){
				a = new Sprite(getImage("resources/images/agujero.png"), 
						holeGridStartX + holeColumnJump * i, holeGridStartY);
			
			} else {
				a = new Sprite(getImage("resources/images/agujero.png"), 
						holeGridStartX + holeColumnJump * (i % 5), 
						holeGridStartY + holeRowJump);
			}
			playfield.add(a);
		}
	}
	
	private void fillHole(Ball ballSel){
		int correctionFactor = 2;
		BufferedImage hole = getImage("resources/images/agujero.png");
		BufferedImage b = getImage(ballSel.getImageUsed());
		Ball a = new Ball(ballSel.getValue(),ballSel.getDescription(),b,0.37);
		
		if(this.pos<5){
			a.setLocation(holeGridStartX + holeColumnJump * (this.pos % 5) + 
					correctionFactor, holeGridStartY + correctionFactor);
		} else {
			a.setLocation(holeGridStartX + holeColumnJump * (this.pos % 5) + 
					correctionFactor, holeGridStartY + holeRowJump + 
					correctionFactor);
		}
		playfield.add(a);
	}
	
	/**
	 * @param g
	 *            Renderiza el juego en la pantalla.
	 */
	public final void render(final Graphics2D g) {
		playfield.render(g);
		font.drawString(g,
				"LEVEL :" + new Integer(this.levelNumber).toString(), 10, 10);		
				
		int textWidth = 
			font.getWidth(String.valueOf(this.clock.getRemainingTime()));
		
		font.drawString(g, String.valueOf(this.clock.getRemainingTime()), 475 - (textWidth/2), 511);
		
		font.drawString(g, "NIVEL: " 
 				+ String.valueOf(this.getLevelNumber()), 530, 480);
 		
		font.drawString(g, "PUNTOS: " 
 				+ ((BigBalls)parent).getGlobalScore(), 530, 500);
 		
		font.drawString(g, "FALTAN " 
 				+ String.valueOf(this.getRemainingBalls()
 				+ " PELOTAS"), 530, 520);
 		
		font.drawString(g, "VIDAS: " 
 				+ String.valueOf(((BigBalls)parent).getLives()), 530, 540);
				
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
			Ball ballsel = (Ball) this.checkPosMouse(this.ballGroup, true);
			if (ballsel != null) {
				if (ballsel.getValue() == this.orderedBalls.elementAt(pos)
						.getValue()) {
					ballsel.setActive(false);
					fillHole(ballsel);
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
	    if (timerEndLevel.action(elapsedTime)){
	    	
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
				800, 600);
	}
	
	/**
	 * Termina el nivel habiendo perdido.
	 * Decrementa una vida y basandose en las restantes, setea el proximo 
	 * estado como OPTION_MENU u OPTION_PLAY si le queda al menos una.
	 */
	public final void loseLevel() {
		this.clock.stop();
		this.clockSprite.setAnimate(false);
		this.engine.decreaseLives();
		if (this.engine.getLives() == 0) {
			//Si no le quedan vidas, se vuelve al menu
			this.engine.nextGameID = BigBalls.OPTION_MENU;
		} else {
			//Si le quedan vidas, sigue jugando
			this.engine.nextGameID = BigBalls.OPTION_PLAY;
		}
		generateLoseAnimation();
		timerEndLevel.setActive(true);
	}
	
	/**
	 * Termina el nivel habiendo ganado.
	 * Setea el proximo estado como OPTION_PLAY.
	 */
	public final void winLevel() {
		this.clock.stop();
		this.clockSprite.setAnimate(false);
		this.engine.addPoints(this.generateScore());
		this.engine.setCurrentLevel(levelNumber + 1);
		this.engine.nextGameID = BigBalls.OPTION_PLAY;
		generateWinAnimation();
		timerEndLevel.setActive(true);
	}
	
	/**
	 * Genera la animacion de fin de nivel, habiendo ganado.
	 */
	public final void generateWinAnimation() {
		AdvanceSprite win = new AdvanceSprite(ImageUtil.getImages
				(this.bsIO.getURL("Resources/images/tick.png"), 6, 1,
				Transparency.TRANSLUCENT));
		win.setAnimationFrame(new int[]{0,1,2,3,4,5});
		win.getAnimationTimer().setDelay(100);
		win.setLocation(250, 100);
		win.setLoopAnim(false);
		win.setAnimate(true);
		playfield.add(win);
	}
	
	/**
	 * Genera la animacion de fin de nivel, habiendo ganado.
	 */
	public final void generateLoseAnimation() {
		AdvanceSprite lose = new AdvanceSprite(ImageUtil.getImages
				(this.bsIO.getURL("Resources/images/fail.png"), 6, 1,
				Transparency.TRANSLUCENT));
		lose.setAnimationFrame(new int[]{0,1,2,3,4,5});
		lose.getAnimationTimer().setDelay(100);
		lose.setLocation(250, 100);
		lose.setLoopAnim(false);
		lose.setAnimate(true);
		playfield.add(lose);
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
	
	/**
	 * Devuelve la cantidad de pelotas restantes en el nivel.
	 * 
	 * @return cantidad de pelotas restantes
	 */
	public final int getRemainingBalls() {
		return this.orderedBalls.size() - this.pos;
	}
}
