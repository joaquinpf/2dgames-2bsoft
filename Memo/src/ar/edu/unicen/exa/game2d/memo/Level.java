package ar.edu.unicen.exa.game2d.memo;
/*
* Classname Level.java
*
* Version 1.0
*
* Date 06/11/2008
*
* Copyright (c) 2008 - UD3
* 
*/

// GTGE
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.KeyEvent;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.AnimatedSprite;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.font.SystemFont;
import com.golden.gamedev.object.sprite.VolatileSprite;
import com.golden.gamedev.util.FontUtil;
import com.golden.gamedev.util.ImageUtil;
import com.golden.gamedev.util.Utility;

/**
 * Maneja un nivel del juego.
 * 
 * @author Luis Soldavini, Pablo Melchior y Carlos Mirabella
 * @version 2.0
 * 
 */
public class Level extends GameObject {

	/**
	 * Puntos que se suman al descubrir un par.
	 */
	private static int pointsPerPair = 20;
	
	/**
	*  Cantidad de puntos que puede obtener el jugador por terminar el nivel.
	*/
	private static int pointsPerLevel = 100;
	
	/**
	 * Coordenada X de la primer carta en la pantalla.
	 */
	private static int positionScreenX = 110;

	
	/**
	 * Coordenada Y de la primer carta en la pantalla.
	 */
	private static int positionScreenY = 15;
	
	
	/**
	 * Indica la separacion (en pixeles) que hay
	 * entre dos filas de cartas.
	 */
	private static int separationRow = 0;
	

	/**
	 * Indica la separacion (en pixeles) que hay
	 * entre dos columnas de cartas.
	 */
	private static int separationCol = 0;
	

	/**
	 * Indica el ancho (en pixeles) de una carta.
	 */
	private int widthCard = 0;
	
	
	/**
	 * Indica la altura (en pixeles) de una carta. 
	 */
	private int heightCard = 0;
	
		
	/** Nro. de nivel.
	 * 
	 * @uml.property  name="levelNumber"
	 */
	private int levelNumber = 0;
	
	
	/** Cantidad de cartas que posee el nivel.
	 * Este nro., siempre será multiplo de 2 (par de cartas).
	 * 
	 * @uml.property  name="tableSize"
	 */
	private int tableSize = 0;
	
	
	/**
	 * Estructura del GTGE que contiene todos los componentes
	 * del juego (Sprites, Backgrounds, etc.).
	 * 
	 * @uml.property  name="playfield"
	 */
	private PlayField playfield = null;
	
	
	/**
	 *  SpriteGroup que contiene todas las cartas del nivel.
	 */
	private SpriteGroup	mGroupCards = null;
	
	
	/**
	 * Escenario del nivel.
	 */
	private Background mBackground = null;
	
	
	/** Indica los pares de cartas que faltan descubrir.
	 * 
	 * @uml.property  name="reamainingCards"
	 */
	private int remainingCards = 0;
	

	/** Contiene la primer carta seleccionada por el usuario.
	 * 
	 * @uml.property  name="firstCard"
	 */
	private Card firstCard = null;
	

	/** 
	 * Contiene la segunda carta seleccionada por el usuario.
	 * 
	 * @uml.property  name="secondCard"
	 */
	private Card secondCard = null;
	
	/** 
	 * Representa al boton de salida.
	 * 
	 * @uml.property  name="buttonExit"
	 */
	private Sprite buttonExit = null;
	

	/**
	 * Reloj del nivel.
	 * Indica el tiempo maximo para descubrir todos los pares de cartas.
	 * 
	 * @uml.property  name="clock"
	 */
	private AnimatedSprite clock = null;
	
	/**
	 * Tablero (matriz) que posee las cartas del nivel.
	 */
	private Card [][] mCards = null;
	
	
	/**
	 * Nro de filas que posee el tablero de cartas.
	 */
	private int row = 0;
	
	
	/**
	 * Nro de columnas que posee el tablero de cartas.
	 */
	private int col = 0;
	

	/**
	 * Variable booleana que indica si el nivel ya está inicializado.
	 */
	private boolean mInitializedNivel = false;
	
	
	/**
	 * Timer para que el jugador pueda ver las dos cartas descubiertas.
	 */
	private boolean mWaitCards = false;
	
	
	/**
	 * Timer local para un delay antes de empezar el nivel (1 segundo). 
	 */
	private Timer timerStartLevel = new Timer(1000);
	
	
	/**
	 * Timer para mostrar las dos cartas destapadas en pantalla  (3 segundos).
	 */
	private Timer timerSecondCard = new Timer(800);
	
	
	/**
	 * Timer que se utiliza para clock del nivel.
	 * Cada 1 segundo decremento mRemainingTimeLevel.
	 */
	private Timer timerClock = new Timer(1000);

	
	/**
	 * Contador de aciertos de pares de cartas descubiertos.
	 */
	private int mSuccess = 0;

	
	/**
	 * Nro. de desaciertos de pares de cartas descubiertos.
	 */	
	private int mFails = 0;
	
	
	/**
	 * Tiempo total para descubrir todos los pares de cartas del nivel.
	 */
	private int mRemainingTimeLevel = 0;
	
	
	/**
	 * Fuente que se utiliza para mostrar texto en la pantalla del nivel. 
	 */
	private SystemFont mFont = null;
	
	/**
	 * Fuente que se utiliza para mostrar texto en la pantalla del nivel. 
	 */
	private SystemFont bigFont = null;
		
	
	/** 
	 * Constructor del Nivel.
	 * Crea el clock y el SpriteGroup de cartas.
	 * 
	 * @param parent  Instancia del Juego (Memo.java).
	 */	
	public Level(final GameEngine parent) {
		
		super(parent);
		mGroupCards = new SpriteGroup("Grupo de cartas");
	}


	/** Retorna TRUE si el nivel ha finalizado correctamente
	 * (osea si se descubrieron todos los pares de cartas)
	 * y FALSE en caso contrario.
	 * 
	 * @return  Returns the levelComplete.
	 * @uml.property  name="levelComplete"
	 */
	public final boolean isLevelComplete() {
		return (this.remainingCards == 0);
	}


	/** Retorna el nro de nivel.
	 * 
	 * @return  Returns the levelNumber.
	 * @uml.property  name="levelNumber"
	 */
	public final int getLevelNumber() {
		return levelNumber;
	}


	/** Setea el nro de nivel.
	 * 
	 * @param levelNumber  nro. de nivel a setear.
	 * @uml.property  name="levelNumber"
	 */
	public final void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}


	/**
	 * Retorna el playfield del nivel.
	 * 
	 * @return  Returns the playfield.
	 * @uml.property  name="playfield"
	 */
	public final PlayField getPlayfield() {
		return playfield;
	}


	/**
	 * Setea el playfield del nivel.
	 * 
	 * @param playfield  The playfield to set.
	 * @uml.property  name="playfield"
	 */
	public final void setPlayfield(PlayField playfield) {
		this.playfield = playfield;
	}


	/** Agrega una carta al nivel. Al recibir la carta esta se clona
	 *  y tambien se agrega al spriteGroup.
	 * 
	 * @param newCard  La carta que se agrega al nivel.  
	 */
	public void addCard(Card newCard) {
		
		// agrego las cartas al grupo de cartas
		mGroupCards.add(newCard);
		// Clono la carta
		mGroupCards.add(newCard.clone());
	}


	/**
	 * Retorna el nro. total de cartas que posee el nivel.
	 * 
	 * @return  Returns the tableSize.
	 * @uml.property  name="tableSize"
	 */
	public int getTableSize() {
		return tableSize;
	}


	/**
	 * Setea el nro. total de cartas que posee el nivel.
	 * 
	 * @param tableSize  The tableSize to set.
	 * @uml.property  name="tableSize"
	 */
	public void setTableSize(int tableSize) {
		this.tableSize = tableSize;
	}

	/**
	 * Retorna el nro. de cartas que faltan descubrir.
	 * 
	 * @return  Returns the reamainingCards.
	 * @uml.property  name="reamainingCards"
	 */
	public int getRemainingCards() {
		return remainingCards;
	}

	
	/**
	 * Setea el nro. de cartas que faltan descubrir.
	 * @param reamainingCards  The reamainingCards to set.
	 * @uml.property  name="reamainingCards"
	 */
	public void setRemainingCards(int reamainingCards) {
		this.remainingCards = reamainingCards;
	}


	/** 
	 * Retorna la primer carta seleccionada.
	 * 
	 * @return  Returns the firstCard.
	 * @uml.property  name="firstCard"
	 */
	public Card getFirstCard() {
		return firstCard;
	}


	/** 
	 * Setea la primer carta seleccionada.
	 * 
	 * @param firstCard  The firstCard to set.
	 * @uml.property  name="firstCard"
	 */
	public void setFirstCard(Card firstCard) {
		this.firstCard = firstCard;
	}


	/**
	 * Retorna la segunda carta seleccionada.
	 * 
	 * @return  Returns the secondCard.
	 * @uml.property  name="secondCard"
	 */
	public Card getSecondCard() {
		return secondCard;
	}


	/**
	 * Setea la segunda carta seleccionada.
	 * 
	 * @param secondCard  The secondCard to set.
	 * @uml.property  name="secondCard"
	 */
	public void setSecondCard(Card secondCard) {
		this.secondCard = secondCard;
	}
	

	/**
	 * Calcula aleatoriamente las filas y columnas que tendra el tablero
	 * en base a la cantidad de cartas que posee el nivel.
	 * 
	 */
	private void calculateRowAndCol() {
		
		int difer = Integer.MAX_VALUE;
		
		for (int i = 2; i <= 5; i++) {
			if ((this.tableSize % i) == 0) {
				int aux = Math.abs((this.tableSize / i) - i);
				if (aux < difer) {
					col = this.tableSize / i;
					row = i;
					difer = aux;
				}
			}
		}
	}

	/**
	 * Metodo Hook del GTGE definido en GameObject como abstracto. 
	 * 
	 * Inicializa todas las variables y establece las condiciones iniciales 
	 * para la ejecución del juego:
	 * 
	 * 1) Crea e inicializa el PlayField que representa a todos los objetos 
	 *    del nivel que se renderizarán.
	 * 2) Establece el Background y el color de mascara del nivel.
	 * 3) Crea y configura las estructuras necesarias: matriz de cartas y
	 *    matriz de existencia en el juego.
	 * 4) Establece la disposición de cartas en el escenario.
	 */
	@Override
	public void initResources() {
		
		// Muestra el cursor en pantalla.
		showCursor();
		
		setMaskColor(Color.BLACK); // Makes sprite background transparent.
		bigFont = new SystemFont(FontUtil.createTrueTypeFont(
			this.bsIO.getURL("./resources/memo/images/ravie.ttf"), Font.PLAIN, 40));
		
		mFont = new SystemFont(FontUtil.createTrueTypeFont(
				this.bsIO.getURL("./resources/memo/images/ravie.ttf"), Font.PLAIN, 24));
			

		playfield = new PlayField(mBackground);
		buttonExit = new Sprite(ImageUtil.getImage(this.bsIO.getURL(
				"./resources/memo/images/ingamesalir.png"), Transparency.TRANSLUCENT), 762, 0);

		playfield.addGroup(mGroupCards);
		playfield.add(buttonExit);
		
		// Seteo la cantidad de cartas que posee el nivel.
		this.tableSize = mGroupCards.getSize();
		
		// Determino las variables row y col.
		calculateRowAndCol();
		
		// Seteo el ancho y alto de las cartas.
		widthCard = mGroupCards.getSprites()[0].getWidth();
		heightCard = mGroupCards.getSprites()[0].getHeight();
		
		// Inicializo las dos matrices con los tamaños definidos anteriormente.
		mCards = new Card[row][col];
	
		positionScreenX = 110;
		positionScreenY = 15;
		float r = (float) row / 2;
		float c = (float) col / 2;
		positionScreenX = (int) (positionScreenX + (242 - (r * heightCard)));
		positionScreenY = (int) (positionScreenY + (385 - (c * widthCard)));
		
		// Ubico las cartas en el tablero y escenario.
		setPositionCards();
		
		// Seteo la cantidad de cartas que faltan descubrir.
		// Inicialmente es igual a la cantidad de cartas que posee el nivel.
		this.remainingCards = this.tableSize;
		
		
		// Reloj del juego.
		clock = new AnimatedSprite(ImageUtil.getImages(this.bsIO.getURL(
				"./resources/memo/images/clock.png"), 12, 1,
				Transparency.TRANSLUCENT));
		clock.setLoopAnim(true);
		clock.getAnimationTimer().setDelay(200);
		clock.setLocation(10,10);
		playfield.add(clock);

		this.timerStartLevel.setActive(true);
	}
	
	/** 
	 * Ubica aleatoriamente c/u de las cartas en el tablero y en base a esta
	 * posicion determina las coordenadas de la carta en la pantalla.
	 * 
	 * La posicion tablero[x][y] se corresponde con una posicion en pantalla.
	 * 
	 * Ejemplo: tablero [2][6] equivale a la posicion (100, 300) en pantalla. 
	 */
	private void setPositionCards() {
		
		for (int i = 0; i < mGroupCards.getSize(); i++) {
			Card vCarta = (Card) mGroupCards.getSprites()[i];
			
			// Obtengo una posicion aleatoria libre en la matriz "table"
			// para la carta.
			Point randomPos = getRandomPos();
			
			// Coloco la carta en el tablero en la posicion determinada. 
			mCards[randomPos.x][randomPos.y] = vCarta;
			
			// Seteo la posicion del tablero como ocupada.
			//table[randomPos.x][randomPos.y] = true;
			
			// Obtengo la posicion de la carta en el escenario en base
			// a la posicion del tablero establecida anteriormente.
			Point screenPoint = getPositionOnScreen(randomPos);
			vCarta.setLocation(screenPoint.getX(), screenPoint.getY());
			vCarta.setFixedposition(true);
		}	
	}
	

	/**
	 * Verifica si la posicion esta ocupada por una carta.
	 *  
	 * @param x  fila "x" del tablero de cartas.
	 * @param y  columna "y" del tablero de cartas.
	 * @return  TRUE si encontro una carta en dicha posicion,
	 * y FALSE en caso contrario.
	 */
	private boolean isInUsed(int x, int y) {
		return (this.mCards[x][y] != null);		
	}
	

	/** Determina aleatoriamente una posicion (x, y) libre para la carta
	 * en el tablero.  
	 * 
	 * @return vPosition  Posicion de la carta en el tablero.
	 */
	private Point getRandomPos() {
		
		Point vPosition = new Point();
		vPosition.setLocation(-1, -1);
		
		int cont = tableSize;
		
		// Retorna un nro entre min y max inclusive. 
		int x = Utility.getRandom(0, row - 1);
		int y = Utility.getRandom(0, col - 1);
		
		// Mientras la posicion este ocupada y no haya
		// recorrido todas las celdas.
		while ((isInUsed(x, y)) && (cont > 0)) {
			
			// Si estoy posicionado en la ultima columna .
			if (y == col - 1) {
				y = 0;		// columna = 0
				if (x == row - 1) {
					x = 0;
				} else {
					x++;		// fila + 1	
				}				
			} else { 
				y++;		// incremento la columna
			}
			
			// Si la posicion aleatoria de donde comence hasta
			// la posicion final del tablero estan todas ocupdas, continuo
			// buscando poisiciones libres desde el origen (0,0).
			if ((x == row) && (y == col)) {
				x = 0;
				y = 0;
			}
			
			cont--;
		}
		
		// Si encontró una posicion libre la guardo.
		if (cont != 0) {
			vPosition.setLocation(x, y);
		}
		
		return vPosition;
	}
	

	/**
	 * Calcula la posicion de la carta en pantalla en funcion de
	 * su ubicacion en el tablero.
	 * 
	 * @param xPositionTable  Posicion de la carta en el tablero.
	 * @return vPositionScreen  Posicion de la carta en la pantalla.
	 */
	private Point getPositionOnScreen(Point xPositionTable) {
		
		Point vPositionScreen = new Point();
		int width = widthCard + separationCol;
		int heigh = heightCard + separationRow;
		
		// Si la carta se ubica en la posicion (0, 0) del tablero
		if ((xPositionTable.x == 0) && (xPositionTable.y == 0)) {
			
			vPositionScreen.setLocation(positionScreenY, positionScreenX);
			
		} else {
			
			vPositionScreen.setLocation(positionScreenY
					+ (xPositionTable.y * width),
					positionScreenX + (xPositionTable.x * heigh));
		}
		
		return vPositionScreen;
	}


	/** 
	 * Renderiza los sprites y demas componentes
	 * (backgrounds, playfield, etc) del GTGE.
	 * 
	 * @param g  Instancia de Graphics2D
	 */
	@Override
	public void render(Graphics2D g) {
		this.playfield.render(g);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		
		mFont.setColor(Color.WHITE);
		mFont.drawString(g, String.valueOf(this.getRemainingTimeLevel()),
				GameFont.CENTER, 10, 35, 100);
		
		
		bigFont.setColor(new Color(239, 231, 123));
		bigFont.drawString(g, "Nivel", GameFont.CENTER, 125, 0, 100); 
		bigFont.drawString(g, String.valueOf(this.getLevelNumber()),
				GameFont.CENTER, 125, 50, 100);
		
 		
		bigFont.setColor(new Color(142, 239, 123));
		bigFont.drawString(g, "Puntos", GameFont.CENTER, 545, 0, 240);
		bigFont.drawString(g, String.valueOf(((Memo) parent).getGlobalScore())
				, GameFont.CENTER, 545, 50, 240);

 		mFont.drawString(g, "Faltan " 
 				+ String.valueOf(this.getRemainingCards()
 				+ " Cartas"), GameFont.CENTER, 300, 10, 200);
 		
 		mFont.drawString(g, "Aciertos: " 
 				+ String.valueOf(this.mSuccess), GameFont.CENTER, 300, 40, 200);
 		
 		mFont.drawString(g, "Errores: " 
 				+ String.valueOf(this.mFails), GameFont.CENTER, 300, 70, 200);
	}

	
	/**
	 * Metodo Hook del GTGE definido en GameObject como abstracto.
	 * Actualiza todas las variables y estructuras del Nivel.
	 * 
	 *  @param elapsedTime  Tiempo transcurrido desde el ultimo update.
	 */
	@Override
	public void update(long elapsedTime) {
		
		// Actualiza todos los Sprites del nivel.
		this.playfield.update(elapsedTime);
				
		// Tiempo que debe transcurrir antes de empezar el nivel.
		if  ((!mInitializedNivel) && (timerStartLevel.action(elapsedTime))) {
			mInitializedNivel = true;
			
			//this.clock.start();
			
			// Inicio el timer que me actualiza el clock.
			timerClock.setActive(true);
			clock.setAnimate(true);
		}
		
		// Si transcurrio 1 seg. de timer del clock del nivel. 
		if ((timerClock.isActive()) && (timerClock.action(elapsedTime))) {
			
			// Decremento el tiempo remanente del nivel.
			mRemainingTimeLevel--;
			
			if (mRemainingTimeLevel < 0) {
				mRemainingTimeLevel = 0;
			}
			
			// Reinicio el clock para que nuevamente cuente 1 seg.
			timerClock.refresh();
		}
		
		if (click()) {
			if (checkPosMouse(buttonExit, true)) {
				finishLevel();
			}
		}
		// Si está inicializado el nivel.
		if (mInitializedNivel) {
			
			// Si no se está esperando porque se estén mostrando las dos cartas.
			if (!mWaitCards) {
				
				// Si el juego está inicializado e hizo click.
				if (click()) {
					
					// Obtengo la carta seleccionada por el click del mouse.
					Card vSelectedCard = 
						(Card) checkPosMouse(mGroupCards, true);

					// Si seleccionó una carta que no está dada vuelta.
					if ((vSelectedCard != null)) { 
							//&& (!vSelectedCard.isTurned())) {

						// Si estoy seleccionando la primer carta.
						if (this.firstCard == null) {

							firstCard = vSelectedCard;
							firstCard.turnCard();
							playSound("./resources/memo/sounds/flip.wav");

						} else {
							
							// Seleccionó la segunda carta.
							if (!((this.firstCard.getScreenX() == vSelectedCard.getScreenX())
									&& (this.firstCard.getScreenY() == vSelectedCard.getScreenY()))
									&& this.secondCard == null)
							{
								secondCard = vSelectedCard;
								secondCard.turnCard();
								playSound("./resources/memo/sounds/flip.wav");
								timerSecondCard.setActive(true);
								mWaitCards = true;
							}
						}  
					}							    
				}	
				
				// Si seleccionaron dos cartas.
				if ((!mWaitCards) && (firstCard != null)
						&& (secondCard != null)) {
					// verifico si selecciono un par.
					checkCards();
				}					
				
			} else { 
				if (timerSecondCard.action(elapsedTime)) {
					mWaitCards = false;
				}
			}
			
			// Si desea abandonar el nivel.
		    if (keyDown(KeyEvent.VK_ESCAPE)) {
		    	finishLevel();
		    }		
		    if (this.getRemainingTimeLevel() == 0) {
		    	finishLevel();
		    }
			if ((!mWaitCards) && (remainingCards == 0)) {
				//this.clock.stop();
				winLevel();
			}	    
		}
	}
	
	
	/**
	 * Finaliza el nivel y le indica al GameEngine Memo que es lo proximo que
	 * debe ejecutarse. En este caso, retorna al Menu del juego.  
	 */
	private void finishLevel() {
		parent.nextGameID = Memo.MENU_MENU;
		finish();
	}

	
	/**
	 * Suma los puntos del nivel finalizado y le indica al GameEngine Memo
	 * que debe cargar el proximo nivel.
	 */
	private void winLevel() {
		((Memo) parent).addPoints(Level.pointsPerLevel 
				- this.mFails + this.mSuccess + this.mRemainingTimeLevel);
		
		parent.nextGameID = Memo.MENU_PLAY;
		finish();
		
	}
	
	
	/**
	 * Verificacion logica de si las dos cartas seleccionadas
	 * forman un par igual.
	 * 
	 * @return  TRUE si las dos cartas son iguales. FALSE en caso contrario. 
	 */
	private boolean checkCardsLogic() {
		boolean equals = false;
		// Si las dos cartas son iguales.
		if (firstCard.compareTo(secondCard) == 0) {
			
			// Decremento en dos el nro. de cartas faltantes. 
			this.remainingCards = this.remainingCards - 2;
			
			// Remuevo las cartas de mGroupCards.
			this.mGroupCards.remove(firstCard);
			this.mGroupCards.remove(secondCard);

			// Obtengo la posicion de la carta en el tablero
			// en funcion de su posicion en el escenario del nivel.
			Point vPos = getPositionOnTable(firstCard);
			
			// Elimino la carta del tablero.
			this.mCards[vPos.x][vPos.y] = null;			

			// Obtengo la posicion de la carta en el tablero
			// en funcion de su posicion en el escenario del nivel.
			vPos = getPositionOnTable(secondCard);
			
			// Elimino la carta del tablero.
			this.mCards[vPos.x][vPos.y] = null;
			
			// Incremento el nro de aciertos de pares de cartas descubiertos.
			
			mSuccess++;
			((Memo) parent).addPoints(Level.pointsPerPair);
			equals = true;
							
		} else {
			// Incremento el nro de desaciertos de pares de cartas descubiertos.
			firstCard.turnCard();
			secondCard.turnCard();
			mFails++;
			equals = false;
		}
		firstCard = null;				
		secondCard = null;
		return equals;
	}
	
	/**
	 * Verifica si las dos cartas seleccionadas forman un par igual.
	 * Si son iguales incremento la cantidad de aciertos en uno,
	 * en caso contrario incremento la cantidad de desaciertos.
	 */
	private void checkCards() {
		
		// Si las dos cartas son iguales.
		if (checkCardsLogic()) {

			VolatileSprite winSprite = new VolatileSprite(ImageUtil.getImages(
					this.bsIO.getURL("./resources/memo/images/tick.png"), 6, 1,
					Transparency.TRANSLUCENT),300,150);
			winSprite.setAnimationFrame(new int[]{0, 1, 2, 3, 4, 5});
			winSprite.getAnimationTimer().setDelay(100);
			winSprite.setAnimate(true);
			playfield.add(winSprite);
			playSound("./resources/memo/sounds/winCard.wav");
			
		} else {
			VolatileSprite failSprite = new VolatileSprite(ImageUtil.getImages(
					this.bsIO.getURL("./resources/memo/images/fail.png"), 6, 1,
					Transparency.TRANSLUCENT),300,150);
			failSprite.setAnimationFrame(new int[]{0, 1, 2, 3, 4, 5});
			failSprite.getAnimationTimer().setDelay(100);
			failSprite.setAnimate(true);
			playfield.add(failSprite);
			playSound("./resources/memo/sounds/flip.wav");
		}
	}


	/**
	 * Retorna la posicion de la carta en el tablero en funcion  
	 * de su posicion en la panatalla (escenario). 
	 * 
	 * @param xCard  Carta seleccionada.
	 * @return Point  Posicion de la carta en el tablero. 
	 */
	private Point getPositionOnTable(Card xCard) {
		
		// Nro. de fila. 
		int vY = (int) (xCard.getX() - positionScreenY) 
					/ (widthCard + separationCol);
		
		// Nro. de columna.
		int vX = (int) (xCard.getY() - positionScreenX) 
		           / (heightCard + separationRow);
		
		return new Point(vX, vY);		
	} 
	

	/**
	 * Indica si el nivel ha finalizado.
	 * 
	 * @return  Retorna TRUE si se descubrieron todos
	 * los pares de cartas o si se acabo el tiempo del nivel. Retorna FALSE
	 * en caso contario.
	 */
	public boolean isLevelFinished() {
		return ((remainingCards == 0) || (this.mRemainingTimeLevel == 0));
	}
	

	/** Retorna el fondo o escenario del nivel.
	 * 
	 * @return mBackground  escenario del nivel.
	 */
	public Background getBackground() {
		return this.mBackground;
	}
	

	/** Setea el background (escenario) del Nivel.
	 * 
	 * @param xBackground  escenario del nivel.
	 */
	public void setBackground(Background xBackground) {
		this.mBackground = xBackground;
	}

	
	/**
	 * Retorna el contador de aciertos de pares de cartas descubiertos.
	 * 
	 * @return  Retorna mSuccess.
	 */
	public int getSuccess() {
		return mSuccess;
	}


	/**
	 * Setea el contador de aciertos de pares descubiertos.
	 *  
	 * @param xSuccess  nro de aciertos.
	 */
	public void setSuccess(int xSuccess) {
		mSuccess = xSuccess;
	}


	/**
	 * Retorna el nro de desaciertos de pares de cartas.
	 * 
	 * @return  Retorna mFails. 
	 */
	public int getFails() {
		return mFails;
	}


	/**
	 * Setea el nro de desaciertos de pares de cartas.
	 * 
	 * @param xFails  el mFails a setear.
	 */
	public void setFails(int xFails) {
		mFails = xFails;
	}


	/**
	 * Retorna el tiempo restante que falta para que el nivel finalice.
	 * 
	 * @return  Retorna mRemainingTimeLevel.
	 */
	public int getRemainingTimeLevel() {
		return mRemainingTimeLevel;
	}


	/**
	 * Setea el tiempo restante que falta para que el nivel finalice. 
	 * 
	 * @param xRemainingTimeLevel  El xRemainingTimeLevel a setear.
	 */
	public void setRemainingTimeLevel(int xRemainingTimeLevel) {
		this.mRemainingTimeLevel = xRemainingTimeLevel;
	}
	
	
	/**
	 * Retorna la fuente utilizada para mostrar los mensajes
	 * (Nro. de nivel y Puntos) por pantalla.
	 * 
     * Getter of the property <tt>bigFont</tt>.
     * @return  Returns the bigFont.
     * @uml.property  name="bigFont"
     */
	public SystemFont getBigFont() {
		return this.bigFont;
	}
	
	
	/**
     * Setea la fuente utilizada para mostrar los mensajes
	 * (Nro. de nivel y Puntos) por pantalla.
	 * 
     * Setter of the property <tt>bigFont</tt>.
     * @param bigFont  The bigFont to set.
     * @uml.property  name="bigFont"
     */
	public void setBigFont(SystemFont bigFont) {
		this.bigFont = bigFont;
	}
	
	
	/**
     * Retorna la fuente utilizada para mostrar los mensajes
	 * (Aciertos, Errores y Cartas faltantes por descubrir) por pantalla.
     * 
     * Getter of the property <tt>mFont</tt>.
     * @return  Returns the mFont.
     * @uml.property  name="mFont"
     */
	public SystemFont getMFont() {
		return this.mFont;
	}
	
	
	/**
     * Setea la fuente utilizada para mostrar los mensajes
	 * (Aciertos, Errores y Cartas faltantes por descubrir) por pantalla.
     * 
     * Setter of the property <tt>mFont</tt>.
     * @param mFont  The mFont to set.
     * @uml.property  name="mFont"
     */
	public void setMFont(SystemFont mFont) {
		this.mFont = mFont;
	}
	
	
	/**
     * Retorna el clock utilizado en el nivel.
     * 
     * Getter of the property <tt>timerClock</tt>.
     * @return  Returns the timerClock.
     * @uml.property  name="timerClock"
     */
	public Timer getTimerClock() {
		return this.timerClock;
	}
	
	
	/**
     * Setea el clock utilizado en el nivel.
     * 
     * Setter of the property <tt>timerClock</tt>.
     * @param timerClock  The timerClock to set.
     * @uml.property  name="timerClock"
     */
	public void setTimerClock(Timer timerClock){
		this.timerClock = timerClock;
	}
	
	
	/**
	 * Retorna el Timer para mostrar las dos cartas destapadas en pantalla.
	 * 
     * Getter of the property <tt>timerSecondCard</tt>.
     * @return  Returns the timerSecondCard.
     * @uml.property  name="timerSecondCard"
     */
	public Timer getTimerSecondCard() {
		return this.timerSecondCard;
	}
	
	
	/**
	 * Setea el Timer para mostrar las dos cartas destapadas en pantalla.
	 * 
     * Setter of the property <tt>timerSecondCard</tt>.
     * @param timerSecondCard  The timerSecondCard to set.
     * @uml.property  name="timerSecondCard"
     */
	public void setTimerSecondCard(Timer timerSecondCard) {
		this.timerSecondCard = timerSecondCard;
	}
	
	
	/**
	 * Retorna el Timer local para un delay antes de empezar el nivel.
	 * 
     * Getter of the property <tt>timerStartLevel</tt>.
     * @return  Returns the timerStartLevel.
     * @uml.property  name="timerStartLevel"
     */
	public Timer getTimerStartLevel() {
		return this.timerStartLevel;
	}
	
	/**
	 * Setea el Timer local para un delay antes de empezar el nivel.
	 * 
     * Setter of the property <tt>timerStartLevel</tt>.
     * @param timerStartLevel  The timerStartLevel to set.
     * @uml.property  name="timerStartLevel"
     */
	public void setTimerStartLevel(Timer timerStartLevel) {
		this.timerStartLevel = timerStartLevel;
	}
	
	/**
	 * Retorna el Timer para que el jugador pueda ver las dos
	 * cartas descubiertas.
	 * 
     * Getter of the property <tt>mWaitCards</tt>.
     * @return  Returns the mWaitCards.
     * @uml.property  name="mWaitCards"
     */
	public boolean getMWaitCards() {
		return this.mWaitCards;
	}
	
	
	/**
     * Setea el Timer para que el jugador pueda ver las dos
	 * cartas descubiertas.
	 * 
	 * Setter of the property <tt>mWaitCards</tt>.
     * @param mWaitCards  The mWaitCards to set.
     * @uml.property  name="mWaitCards"
     */
	public void setMWaitCards(boolean mWaitCards) {
		this.mWaitCards = mWaitCards;
	}
	
	
	/**
	 * Retorna el estado del nivel: TRUE si el nivel ya está inicializado.
	 * FALSE en caso contrario.
	 * 
     * Getter of the property <tt>mInitializedNivel</tt>.
     * @return  Returns the mInitializedNivel.
     * @uml.property  name="mInitializedNivel"
     */
	public boolean getMInitializedNivel() {
		return this.mInitializedNivel;
	}
	
	
	/**
	 * Setea el estado del nivel: TRUE si el nivel está inicializado.
	 * FALSE en caso contrario.
	 * 
     * Setter of the property <tt>mInitializedNivel</tt>.
     * @param mInitializedNivel  The mInitializedNivel to set.
     * @uml.property  name="mInitializedNivel"
     */
	public void setMInitializedNivel(boolean mInitializedNivel) {
		this.mInitializedNivel = mInitializedNivel;
	}
	
	
	/**
	 * Retorna el Sprite que representa al clock del nivel.
	 * 
     * Getter of the property <tt>clock</tt>.
     * @return  Returns the clock.
     * @uml.property  name="clock"
     */
	public AnimatedSprite getClock() {
		return this.clock;
	}
	
	
	/**
	 * Setea el Sprite que representa al clock del nivel.
	 * 
     * Setter of the property <tt>clock</tt>.
     * @param clock  The clock to set.
     * @uml.property  name="clock"
     */
	public void setClock(AnimatedSprite clock) {
		this.clock = clock;
	}
	
	
	/**
     * Retorna el Sprite que representa al boton de salida.
     * 
     * Getter of the property <tt>buttonExit</tt>.
     * @return  Returns the buttonExit.
     * @uml.property  name="buttonExit"
     */
	public Sprite getButtonExit() {
		return this.buttonExit;
	}
	
	
	/**
	 * Setea el Sprite que representa al boton de salida.
	 * 
     * Setter of the property <tt>buttonExit</tt>.
     * @param buttonExit  The buttonExit to set.
     * @uml.property  name="buttonExit"
     */
	public void setButtonExit(Sprite buttonExit) {
		this.buttonExit = buttonExit;
	}
	
	
	/**
	 * Retorna el SpriteGroup que contiene todas las cartas del nivel.
	 * 
     * Getter of the property <tt>mGroupCards</tt>.
     * @return  Returns the mGroupCards.
     * @uml.property  name="mGroupCards"
     */
	public SpriteGroup	getMGroupCards() {
		return this.mGroupCards;
	}
	
	
	/**
	 * Setea el SpriteGroup que contiene todas las cartas del nivel.
	 * 
     * Setter of the property <tt>mGroupCards</tt>.
     * @param mGroupCards  The mGroupCards to set.
     * @uml.property  name="mGroupCards"
     */
	public void setMGroupCards(SpriteGroup mGroupCards) {
		this.mGroupCards = mGroupCards;
	}
	
	
	/**
	 * Retorna el Tablero (matriz) que posee las cartas del nivel.
	 * 
     * Getter of the property <tt>mCards</tt>.
     * @return  Returns the mCards.
     * @uml.property  name="mCards"
     */
	public Card [][] getMCards() {
		return this.mCards;
	}
	
	
	/**
	 * Setea el Tablero (matriz) que posee las cartas del nivel.
	 * 
     * Setter of the property <tt>mCards</tt>.
     * @param mCards  The mCards to set.
     * @uml.property  name="mCards"
     */
	public void setMCards(Card [][] mCards) {
		this.mCards = mCards;
	}
	
	
	/**
	 * Retorna la altura (en pixeles) de una carta.
	 * 
     * Getter of the property <tt>heightCard</tt>.
     * @return  Returns the heightCard.
     * @uml.property  name="heightCard"
     */
	public int getHeightCard() {
		return this.heightCard;
	}
	
	
	/**
	 * Setea la altura (en pixeles) de una carta.
	 * 
     * Setter of the property <tt>heightCard</tt>.
     * @param heightCard  The heightCard to set.
     * @uml.property  name="heightCard"
     */
	public void setHeightCard(int heightCard) {
		this.heightCard = heightCard;
	}
	
	
	/**
	 * Retorna el ancho (en pixeles) de una carta.
	 * 
     * Getter of the property <tt>widthCard</tt>.
     * @return  Returns the widthCard.
     * @uml.property  name="widthCard"
     */
	public int getWidthCard() {
		return this.widthCard;
	}
	
	
	/**
	 * Setea el ancho (en pixeles) de una carta.
	 * 
     * Setter of the property <tt>widthCard</tt>.
     * @param widthCard  The widthCard to set.
     * @uml.property  name="widthCard"
     */
	public void setWidthCard(int widthCard) {
		this.widthCard = widthCard;
	}
	
	
	/**
	 * Retorna Nro de filas que posee el tablero de cartas.
	 * 
     * Getter of the property <tt>row</tt>.
     * @return  Returns the row.
     * @uml.property  name="row"
     */
	public int getRow() {
		return this.row;
	}
	
	
	/**
	 * Setea el Nro de filas que posee el tablero de cartas.
	 * 
     * Setter of the property <tt>row</tt>.
     * @param row  The row to set.
     * @uml.property  name="row"
     */
	public void setRow(int row) {
		this.row = row;
	}
	
	
	/**
	 * Retorna el Nro de columnas que posee el tablero de cartas.
	 * 
     * Getter of the property <tt>col</tt>.
     * @return  Returns the col.
     * @uml.property  name="col"
     */
	public int getCol() {
		return this.col;
	}
	
	
	/**
	 * Setea el Nro de columnas que posee el tablero de cartas.
	 * 
     * Setter of the property <tt>col</tt>.
     * @param col  The col to set.
     * @uml.property  name="col"
     */
	public void setCol(int col) {
		this.col = col;
	}
	

	/**
	 * Retorna los puntos que se suman al descubrir un par de cartas.
	 * 
     * Getter of the property <tt>pointsPerPair</tt>.
     * @return  Returns the pointsPerPair.
     * @uml.property  name="pointsPerPair"
     */
	public static int getPointsPerPair() {
		return pointsPerPair;
	}
	
	
	/**
	 * Setea los puntos que se suman al descubrir un par de cartas.
	 * 
     * Setter of the property <tt>pointsPerPair</tt>.
     * @param xpointsPerPair  The pointsPerPair to set.
     * @uml.property  name="pointsPerPair"
     */
	public static void setPointsPerPair(int xpointsPerPair) {
		pointsPerPair = xpointsPerPair;
	}
	

	/**
     * Retorna la cantidad de puntos que puede obtener el jugador
     * por terminar el nivel.
     * 
     * Getter of the property <tt>pointsPerLevel</tt>.
     * @return  Returns the pointsPerLevel.
     * @uml.property  name="pointsPerLevel"
     */
	public static int getPointsPerLevel() {
		return pointsPerLevel;
	}
	
	
	/**
	 * Setea la cantidad de puntos que puede obtener el jugador
	 * por terminar el nivel.
     * 
     * Setter of the property <tt>pointsPerLevel</tt>.
     * @param xpointsPerLevel  The pointsPerLevel to set.
     * @uml.property  name="pointsPerLevel"
     */
	public static void setPointsPerLevel(int xpointsPerLevel) {
		pointsPerLevel = xpointsPerLevel;
	}
	

	/**
     * Retorna la coordenada X de la primer carta en la pantalla.
     * 
     * Getter of the property <tt>positionScreenX</tt>.
     * @return  Returns the positionScreenX.
     * @uml.property  name="positionScreenX"
     */
	public static int getPositionScreenX() {
		return positionScreenX;
	}
	

	/**
	 * Setea la coordenada X de la primer carta en la pantalla.
	 * 
     * Setter of the property <tt>positionScreenX</tt>.
     * @param xpositionScreenX  The positionScreenX to set.
     * @uml.property  name="positionScreenX"
     */
	public static void setPositionScreenX(int xpositionScreenX) {
		positionScreenX = xpositionScreenX;
	}
	

	/**
     * Retorna la coordenada Y de la primer carta en la pantalla.
     * 
     * Getter of the property <tt>positionScreenY</tt>.
     * @return  Returns the positionScreenY.
     * @uml.property  name="positionScreenY"
     */
	public static int getPositionScreenY() {
		return positionScreenY;
	}
	
	
	/**
	 * Setea la coordenada Y de la primer carta en la pantalla.
     * 
     * Setter of the property <tt>positionScreenY</tt>.
     * @param xpositionScreenY  The positionScreenY to set.
     * @uml.property  name="positionScreenY"
     */
	public static void setPositionScreenY(int xpositionScreenY) {
		positionScreenY = xpositionScreenY;
	}
	

	/**
     * Retorna la separacion (en pixeles) que hay entre dos filas de cartas.
     * 
     * Getter of the property <tt>separationRow</tt>.
     * @return  Returns the separationRow.
     * @uml.property  name="separationRow"
     */
	public static int getSeparationRow() {
		return separationRow;
	}
	
	
	/**
	 * Setea la separacion (en pixeles) que hay entre dos filas de cartas.
     * 
     * Setter of the property <tt>separationRow</tt>.
     * @param xseparationRow  The separationRow to set.
     * @uml.property  name="separationRow"
     */
	public static void setSeparationRow(int xseparationRow) {
		separationRow = xseparationRow;
	}
	

	/**
	 * Retorna la separacion (en pixeles) que hay entre dos columnas de cartas.
	 * 
     * Getter of the property <tt>separationCol</tt>.
     * @return  Returns the separationCol.
     * @uml.property  name="separationCol"
     */
	public static int getSeparationCol() {
		return separationCol;
	}
	
	
	/**
	 * Setea la separacion (en pixeles) que hay entre dos columnas de cartas.
	 * 
     * Setter of the property <tt>separationCol</tt>.
     * @param xseparationCol  The separationCol to set.
     * @uml.property  name="separationCol"
     */
	public static void setSeparationCol(int xseparationCol) {
		separationCol = xseparationCol;
	}

}
