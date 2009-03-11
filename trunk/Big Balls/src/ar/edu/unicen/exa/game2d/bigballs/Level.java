package ar.edu.unicen.exa.game2d.bigballs;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Vector;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.AnimatedSprite;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.background.ImageBackground;
import com.golden.gamedev.object.font.SystemFont;
import com.golden.gamedev.object.sprite.AdvanceSprite;
import com.golden.gamedev.util.FontUtil;
import com.golden.gamedev.util.ImageUtil;

/**
 * @uml.dependency supplier="Ball"
 */
/**
 * Maneja un nivel del juego.
 * 
 * @author Mariano Camarzana y Joaquín Pérez Fuentes
 */
public class Level extends GameObject implements Observer {
	
	/**
	 * El playfield del nivel.
	 * @uml.property name="playfield"
	 */
	private PlayField playfield;

	/**
	 * Vector de pelotas en orden para verificar la finalizacion
	 * del nivel.
	 * @uml.property name="orderedBalls"
	 */
	private Vector<Ball> orderedBalls;

	/**
	 * Numero del presente nivel.
	 * @uml.property name="levelNumber"
	 */
	private int levelNumber;
	
	/**
	 * Timer para utilizar al final del nivel. Se debe frenar
	 * la escena para que no sea tan brusco el cambio.
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
	private ArrayList<SpriteGroup> ballCollision;

	/**
	 * Variable empleada para agrupar las pelotas.
	 */
	private SpriteGroup ballGroup;

	/**
	 * 
	 */
	private SpriteGroup backgroundGroup;

	/**
	 * Fondo del nivel.
	 */
	private Background background;

	/**
	 * Para realizar los mensajes por pantalla.
	 */
	private SystemFont font;

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
	private int holeGridStartX = 14;
	
	/**
	 * Componente Y del inicio de la grilla de agujeros.
	 */
	private int holeGridStartY = 450;
	
	/**
	 * Pixeles de salto entre fila y fila de agujeros.
	 */
	private int holeRowJump = 68;
	
	/**
	 * Pixeles de salto entre fila y fila de agujeros.
	 */
	private int holeColumnJump = 78;
	
	/**
	 * Ruta a la imagen del agujero.
	 */
	private String holeImage = "./resources/bigballs/images/agujerov3.png";
	
	/**
	 * Sprite para el icono salir.
	 */
	private Sprite salir;
	
	/**
	 * Constructor del nivel.
	 * @param parent El GameEngine padre del Level
	 */
	public Level(final GameEngine parent) {
		super(parent);
		this.engine = (BigBalls) parent;
		this.orderedBalls = new Vector<Ball>();
		ballCollision = new ArrayList<SpriteGroup>();
	}
	
	/**
	 * Agrega una pelota al nivel.
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
	 * Cuando recibe el evento de que sucedio un cambio da por
	 * finalizado el juego.
	 * @param o o
	 * @param arg arg 
	 */
	public final void update(final Observable o, final Object arg) {
		playSound("./resources/bigballs/sounds/clocktick.wav");
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
		
		//Agrega las pelotas al grupo correspondiente
		this.ballGroup = new SpriteGroup("BALLS");
		for (int i = 0; i < orderedBalls.size(); i++) {
			//Ball ball = e.nextElement();
			ballCollision.add(playfield.addGroup(new SpriteGroup("Ball" + i)));
			ballCollision.get(i).add(orderedBalls.get(i));
			playfield.add(orderedBalls.get(i));
			this.ballGroup.add(orderedBalls.get(i));
		}
		
		//Genera la mascara para colisiones contra el borde.
		Background collMask = 
			new ImageBackground(getImage("./resources/bigballs/images/collmask.png"), 
				800, 441);
		
		//Manejo de colisiones
		for (int i = 0; i < orderedBalls.size(); i++) {
			playfield.addCollisionGroup(ballCollision.get(i), 
					backgroundGroup, new BorderCollision(collMask));
		}
		
		int aux = 0;
		Random rand = new Random();
		while (aux < orderedBalls.size() - 1)	{
			for (int i = aux + 1; i < orderedBalls.size(); i++) {
				if (rand.nextBoolean()) {
					playfield.addCollisionGroup(ballCollision.get(aux), 
							ballCollision.get(i), new BallCollision());
				}
			}
			aux++;
		}
		
		//Fuente a utilizar
		font = new SystemFont(FontUtil.createTrueTypeFont(
				this.bsIO.getURL("./resources/bigballs/images/MaroonedOnMarsBB.ttf"), Font.BOLD, 40f));

		//Setea la posicion de las pelotas
		setPositionBalls();
		
		//Genera los huecos para las pelotas
		generateHoles();
		
		//Boton salir
		salir = new Sprite(ImageUtil.getImage(
				bsIO.getURL("./resources/bigballs/images/ingamesalir.png"), 
				Transparency.TRANSLUCENT));
		salir.setLocation(750, 5);
		playfield.add(salir);
		
		//Inicializacion del reloj
		clockSprite = new AnimatedSprite(ImageUtil.getImages(this.bsIO.getURL("./resources/bigballs/images/clock.png"), 7, 1, Transparency.TRANSLUCENT));
		clockSprite.getAnimationTimer().setDelay(142);
		clockSprite.setLoopAnim(true);
		clockSprite.setLocation(425, 470);
		playfield.add(clockSprite);
		clockSprite.setActive(true);
		this.clock.addObserver(this);
		this.clock.start();
		clockSprite.setAnimate(true);
		
		//Inicializacion del Timer de fin de nivel
		timerEndLevel = new Timer(3000);
		timerEndLevel.setActive(false);
		
		//Muestra el cursor, necesario cuando el juego es distributable
		this.showCursor();
	}

	/**
	 * Desordena aleatoriamente un grupo de sprites y devuelve 
	 * el resultado en un ArrayList.
	 * @param balls SpriteGroup a desordenar
	 * @return ArrayList desordenado de sprites
	 */
	public ArrayList<Ball> shuffle(final SpriteGroup balls) {
		ArrayList<Ball> shuffledBalls = new ArrayList<Ball>();
		for (int i = 0; i < balls.getSize(); i++) {
			shuffledBalls.add((Ball) balls.getSprites()[i]);
		}
		Collections.shuffle(shuffledBalls);
		return shuffledBalls;
	}
	
	/**
	 * Genera y setea las posiciones de las pelotas en pantalla.
	 * Tiene en cuenta el ancho total de las pelotas para generar
	 * dinamicamente sus posiciones e intentar evitar superposición
	 * al inicio
	 */
	private void setPositionBalls() {
		
		ArrayList<Ball> balls = shuffle(ballGroup);
		
		//Calcula el ancho combinado de todas las pelotas
		double combinedWidth = 0;
		for (int i = 0; i < balls.size(); i++) {
			Ball aBall =  balls.get(i);
			combinedWidth += aBall.getWidth();
		}	
		
		//Calcula el espaciado, basandose en el espacio restante
		double spacing = (800 - combinedWidth) / (ballGroup.getSize() + 1);
		
		double xposition = 0;
		double yposition = 50;
		for (int i = 0; i < balls.size(); i++) {
			
			//Aumenta el espaciado
			xposition += spacing;
			Ball aBall =  balls.get(i);
			aBall.setLocation(xposition, yposition % 350);
			
			//Aumenta en el ancho de la pelota actual luego de colocarla. 
			//Esto es asi porque GTGE toma el inicio de la imagen desde la 
			//izquierda
			xposition += aBall.getWidth();
			yposition += aBall.getWidth();
		}	
	}
	
	/**
	 * Genera y setea los huecos correspondientes a cada pelota del
	 * presente nivel. Estos seran ocupados por las pelotas si se
	 * avanza en el juego.
	 */
	private void generateHoles() {
		for (int i = 0; i < orderedBalls.size(); i++) {
			Sprite a;
			BufferedImage b = ImageUtil.getImage(
						bsIO.getURL(holeImage),	Transparency.TRANSLUCENT);
			if (i < 5) {
				a = new Sprite(b, holeGridStartX + holeColumnJump * i, 
						holeGridStartY);
			
			} else {
				a = new Sprite(b, holeGridStartX + holeColumnJump * (i % 5), 
						holeGridStartY + holeRowJump);
			}
			playfield.add(a);
		}
	}
	
	/**
	 * Llena un hueco con una pelota dada.
	 * @param ballSel la pelota a colocar en el siguiente hueco
	 */
	private void fillHole(final Ball ballSel) {
		int correctionFactor = 2;
		BufferedImage b = ImageUtil.getImage(
				bsIO.getURL(ballSel.getImageUsed()),Transparency.TRANSLUCENT);
		Ball a = new Ball(ballSel.getValue(), ballSel.getDescription(), b, 0.335);
		
		if (this.pos < 5) {
			a.setLocation(holeGridStartX + holeColumnJump * (this.pos % 5) 
					+ correctionFactor, holeGridStartY + correctionFactor);
		} else {
			a.setLocation(holeGridStartX + holeColumnJump * (this.pos % 5) 
					+ correctionFactor, holeGridStartY + holeRowJump 
					+ correctionFactor);
		}
		playfield.add(a);
	}
	
	/**
	 * Renderiza el juego en la pantalla.
	 * @param g El objeto grafico sobre el cual se dibuja
	 * @see com.golden.gamedev.GameObject#render(java.awt.Graphics2D)
	 */
	
	public final void render(final Graphics2D g) {
		
		playfield.render(g);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		
		//Renderiza el timer (numerico)
		g.setColor(Color.white);
		int textWidth = 
			font.getWidth(String.valueOf(this.clock.getRemainingTime()));
		font.drawString(g, String.valueOf(this.clock.getRemainingTime()), 
				474 - (textWidth / 2), 500);

		g.setColor(new Color(50, 49, 47));
		//Renderiza el nro de nivel
		font.drawString(g, "NIVEL: " 
 				+ String.valueOf(this.getLevelNumber()), 535, 455);
 		
		//Renderiza el puntaje total obtenido hasta el momento
		font.drawString(g, "PUNTOS: " 
 				+ ((BigBalls) parent).getGlobalScore(), 535, 485);
 		
		//Renderiza la cantidad de pelotas faltantes
		font.drawString(g, "FALTAN " 
 				+ String.valueOf(this.getRemainingBalls()
 				+ " PELOTAS"), 535, 515);
 		
		//Renderiza las vidas
		font.drawString(g, "VIDAS: " 
 				+ String.valueOf(((BigBalls) parent).getLives()), 535, 545);
		
	}

	/**
	 * Actualiza las variables del juego. Controla ls pelotas que va
	 * seleccionando el jugador, en caso de equivaocarse da por finalizado el
	 * juego.
	 * @param elapsedTime Tiempo transcurrido desde el ultimo update
	 * @see com.golden.gamedev.GameObject#update(long)
	 */
	@Override
	public final void update(final long elapsedTime) {
		playfield.update(elapsedTime);		
		
		//Si presiona la imagen salir vuelve al menu
		if (click() && timerEndLevel.isActive()) {
		    this.finish();
		}
		
		//Logica del juego
		if ((click()) && (!timerEndLevel.isActive())) {
			Ball ballsel = (Ball) this.checkPosMouse(this.ballGroup, true);
			if (ballsel != null) {
				if (ballsel.getValue() == this.orderedBalls.elementAt(pos)
						.getValue()) {
					ballsel.setActive(false);
					playSound("./resources/bigballs/sounds/winball.wav");
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

		//Si presiona la imagen salir vuelve al menu
		if ((click()) && (checkPosMouse(salir, true))) {
	    	this.engine.nextGameID = BigBalls.OPTION_MENU;
	    	clock.deleteObserver(this);
		    this.finish();
		}
		
		//Si presiona ESC vuelve al menu
	    if (keyDown(KeyEvent.VK_ESCAPE)) {
	    	this.engine.nextGameID = BigBalls.OPTION_MENU;
	    	clock.deleteObserver(this);
		    this.finish();
	    }		
	    
	    //Fin del nivel
	    if (timerEndLevel.action(elapsedTime)) {
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
	
	public final void loseLevelLogic()
	{
		this.engine.decreaseLives();
		if (this.engine.getLives() == 0) {
			//Si no le quedan vidas, se vuelve al menu
			this.engine.nextGameID = BigBalls.OPTION_MENU;
		} else {
			//Si le quedan vidas, sigue jugando
			this.engine.nextGameID = BigBalls.OPTION_PLAY;
		}
	}
	/**
	 * Termina el nivel habiendo perdido.
	 * Decrementa una vida y basandose en las restantes, setea el proximo 
	 * estado como OPTION_MENU u OPTION_PLAY si le queda al menos una.
	 */
	public final void loseLevel() {
		
		loseLevelLogic();
		this.clock.stop();
		this.clockSprite.setAnimate(false);
		
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
		Sprite overlay = new Sprite(ImageUtil.getImage(
				this.bsIO.getURL("./resources/bigballs/images/overlay.png"),
				Transparency.TRANSLUCENT));
		AdvanceSprite win = new AdvanceSprite(ImageUtil.getImages(
				this.bsIO.getURL("./resources/bigballs/images/tick.png"), 6, 1,
				Transparency.TRANSLUCENT));
		win.setAnimationFrame(new int[]{0, 1, 2, 3, 4, 5});
		win.getAnimationTimer().setDelay(100);
		win.setLocation(250, 100);
		win.setLoopAnim(false);
		win.setAnimate(true);
		playfield.add(overlay);
		playfield.add(win);
	}
	
	/**
	 * Genera la animacion de fin de nivel, habiendo ganado.
	 */
	public final void generateLoseAnimation() {
		Sprite overlay = new Sprite(ImageUtil.getImage(
				this.bsIO.getURL("./resources/bigballs/images/overlay.png"),
				Transparency.TRANSLUCENT));
		AdvanceSprite lose = new AdvanceSprite(ImageUtil.getImages(
				this.bsIO.getURL("./resources/bigballs/images/fail.png"), 6, 1,
				Transparency.TRANSLUCENT));
		lose.setAnimationFrame(new int[]{0, 1, 2, 3, 4, 5});
		lose.getAnimationTimer().setDelay(100);
		lose.setLocation(250, 100);
		lose.setLoopAnim(false);
		lose.setAnimate(true);
		playfield.add(overlay);
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
		
	/**
	 * Getter of the property <tt>Playfield</tt>.
	 * @return  Returns the Playfield
	 * @uml.property  name="Playfield"
	 */	
	public final PlayField getPlayfield() {
		return playfield;	
	}
	
	/**
	 * Setter of <tt>Playfield</tt>
	 * @param newPlayfield the new Playfield.
	 * @uml.property  name="Playfield"
	 */	
	public final void setPlayfield(PlayField newPlayfield) {
		playfield = newPlayfield;
	}	

	/**
	 * Setter of <tt>OrderedBalls</tt>
	 * @param newOrderedBalls the new OrderedBalls.
	 * @uml.property  name="OrderedBalls"
	 */		
	public final void setOrderedBalls(Vector<Ball> newOrderedBalls) {
		orderedBalls = newOrderedBalls;
	}	
	
	/**
	 * Getter of the property <tt>TimerEndLevel</tt>.
	 * @return  Returns the TimerEndLevel
	 * @uml.property  name="TimerEndLevel"
	 */	
	public final Timer getTimerEndLevel() {
		return timerEndLevel;	
	}
	
	/**
	 * Setter of <tt>TimerEndLevel</tt>
	 * @param newTimerEndLevel the new TimerEndLevel
	 * @uml.property  name="TimerEndLevel"
	 */	
	public final void setTimerEndLevel(Timer newTimerEndLevel) {
		timerEndLevel = newTimerEndLevel;
	}		
	
	/**
	 * Getter of the property <tt>clockSprite</tt>.
	 * @return  Returns the clockSprite
	 * @uml.property  name="clockSprite"
	 */	
	public final AnimatedSprite getClockSprite() {
		return clockSprite;	
	}
	
	/**
	 * Setter of <tt>clockSprite</tt>
	 * @param newClockSprite the new clockSprite.
	 * @uml.property  name="clockSprite"
	 */	
	public final void setClockSprite(AnimatedSprite newClockSprite) {
		clockSprite = newClockSprite;
	}	

	/**
	 * Getter of the property <tt>clock</tt>.
	 * @return  Returns the clock
	 * @uml.property  name="clock"
	 */	
	public final Clock getClock() {
		return clock;	
	}	
	
	/**
	 * Getter of the property <tt>pos</tt>.
	 * @return  Returns the pos
	 * @uml.property  name="pos"
	 */	
	public final int getPos() {
		return pos;	
	}
	
	/**
	 * Setter of <tt>pos</tt>
	 * @param newPos the new pos.
	 * @uml.property  name="pos"
	 */	
	public final void setPos(int newPos) {
		pos = newPos;
	}		
	
	/**
	 * Getter of the property <tt>ballCollision</tt>.
	 * @return  Returns the ballCollision
	 * @uml.property  name="ballCollision"
	 */	
	public final ArrayList<SpriteGroup> getBallCollision() {
		return ballCollision;	
	}
	
	/**
	 * Setter of <tt>ballCollision</tt>
	 * @param newBallCollision the ballCollision.
	 * @uml.property  name="ballCollision"
	 */	
	public final void setBallCollision(ArrayList<SpriteGroup> newBallCollision) {
		ballCollision = newBallCollision;
	}

	/**
	 * Getter of the property <tt>ballGroup</tt>.
	 * @return  Returns the ballGroup
	 * @uml.property  name="ballGroup"
	 */	
	public final SpriteGroup getBallGroup() {
		return ballGroup;	
	}
	
	/**
	 * Setter of <tt>ballGroup</tt>
	 * @param BallGroup the new ballGroup.
	 * @uml.property  name="ballGroup"
	 */	
	public final void setBallGroup(SpriteGroup newBallGroup) {
		ballGroup = newBallGroup;
	}	
	
	/**
	 * Getter of the property <tt>SpriteGroup </tt>.
	 * @return  Returns the SpriteGroup 
	 * @uml.property  name="SpriteGroup "
	 */	
	public final SpriteGroup getBackgroundGroup() {
		return backgroundGroup;	
	}
	
	/**
	 * Setter of <tt>SpriteGroup </tt>
	 * @param BackgroundGroup the new SpriteGroup.
	 * @uml.property  name="SpriteGroup "
	 */	
	public final void setBackgroundGroup(SpriteGroup newBackgroundGroup) {
		backgroundGroup = newBackgroundGroup;
	}		
	
	/**
	 * Getter of the property <tt>background</tt>.
	 * @return  Returns the background
	 * @uml.property  name="background"
	 */	
	public final Background getBackground() {
		return background;	
	}
	
	/**
	 * Setter of <tt>background</tt>
	 * @param Background the new background.
	 * @uml.property  name="background"
	 */	
	public final void setBackground(Background newBackground) {
		background = newBackground;
	}		
	
	/**
	 * Getter of the property <tt>font</tt>.
	 * @return  Returns the font
	 * @uml.property  name="font"
	 */	
	public final SystemFont getFont() {
		return font;	
	}
	
	/**
	 * Setter of <tt>font</tt>
	 * @param newFont the new font.
	 * @uml.property  name="font"
	 */	
	public final void setFont(SystemFont newFont) {
		font = newFont;
	}	
	
	/**
	 * Getter of the property <tt>engine</tt>.
	 * @return  Returns the engine
	 * @uml.property  name="engine"
	 */	
	public final BigBalls getEngine() {
		return engine;	
	}
	
	/**
	 * Setter of <tt>engine</tt>
	 * @param newEngine the new engine.
	 * @uml.property  name="engine"
	 */	
	public final void setEngine(BigBalls newEngine) {
		engine = newEngine;
	}		
	
	/**
	 * Getter of the property <tt>holeGridStartX</tt>.
	 * @return  Returns the holeGridStartX
	 * @uml.property  name="holeGridStartX"
	 */	
	public final int getHoleGridStartX() {
		return holeGridStartX;	
	}
	
	/**
	 * Setter of <tt>holeGridStartX</tt>
	 * @param newHoleGridStartX the new holeGridStartX.
	 * @uml.property  name="holeGridStartX"
	 */	
	public final void setHoleGridStartX(int newHoleGridStartX) {
		holeGridStartX = newHoleGridStartX;
	}		

	/**
	 * Getter of the property <tt>holeGridStartY</tt>.
	 * @return  Returns the holeGridStartY
	 * @uml.property  name="holeGridStartY"
	 */	
	public final int getHoleGridStartY() {
		return holeGridStartY;	
	}
	
	/**
	 * Setter of <tt>holeGridStartY</tt>
	 * @param newHoleGridStartY the new holeGridStartY.
	 * @uml.property  name="holeGridStartY"
	 */	
	public final void setHoleGridStartY(int newHoleGridStartY) {
		holeGridStartY = newHoleGridStartY;
	}	
	
	/**
	 * Getter of the property <tt>holeRowJump</tt>.
	 * @return  Returns the holeRowJump
	 * @uml.property  name="holeRowJump"
	 */	
	public final int getHoleRowJump() {
		return holeRowJump;	
	}
	
	/**
	 * Setter of <tt>holeRowJump</tt>
	 * @param newHoleRowJump the new holeRowJump.
	 * @uml.property  name="holeRowJump"
	 */	
	public final void setHoleRowJump(int newHoleRowJump) {
		holeRowJump = newHoleRowJump;
	}		
	
	/**
	 * Getter of the property <tt>holeColumnJump</tt>.
	 * @return  Returns the holeColumnJump
	 * @uml.property  name="holeColumnJump"
	 */	
	public final int getHoleColumnJump() {
		return holeColumnJump;	
	}
	
	/**
	 * Setter of <tt>holeColumnJumptt>
	 * @param newHoleColumnJump the new holeColumnJump.
	 * @uml.property  name="holeColumnJump"
	 */	
	public final void setHoleColumnJump(int newHoleColumnJump) {
		holeColumnJump = newHoleColumnJump;
	}	

	/**
	 * Getter of the property <tt>holeImage</tt>.
	 * @return  Returns the holeImage
	 * @uml.property  name="holeImage"
	 */	
	public final String getHoleImage() {
		return holeImage;	
	}
	
	/**
	 * Setter of <tt>holeImage</tt>
	 * @param newHoleImage the new holeImage.
	 * @uml.property  name="holeImage"
	 */	
	public final void setHoleImage(String newHoleImage) {
		holeImage = newHoleImage;
	}		

	/**
	 * Getter of the property <tt>v</tt>.
	 * @return  Returns the salir
	 * @uml.property  name="salir"
	 */	
	public final Sprite getSalir() {
		return salir;	
	}
	
	/**
	 * Setter of <tt>salir</tt>
	 * @param newSalir the new salir.
	 * @uml.property  name="salir"
	 */	
	public final void setSalir(Sprite newSalir) {
		salir = newSalir;
	}	
}