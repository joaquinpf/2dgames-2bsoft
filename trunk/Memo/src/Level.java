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
import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.util.Utility;

// JFC
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

// JAVA
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * 
 * @author Luis Soldavini y Pablo Melchior
 * @version 1.0
 * 
 */
public class Level extends GameObject implements Observer {

	/**
	 * Coordenada X de la primer carta en la pantalla.
	 */
	private static int positionScreenX = 20;

	
	/**
	 * Coordenada Y de la primer carta en la pantalla.
	 */
	private static int positionScreenY = 20;
	
	
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
	
	
	/** Indica si el nivel ha finalizado.
	 * 
	 * @uml.property  name="levelComplete"
	 */
	private boolean levelComplete = false;
	
	
	/** Nro. de nivel.
	 * 
	 * @uml.property  name="levelNumber"
	 */
	private int levelNumber = 0;
	
	
	/** Cantidad de cartas que posee el nivel.
	 * Este nro., siempre ser� multiplo de 2 (par de cartas).
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
	

	/** Contiene la segunda carta seleccionada por el usuario.
	 * 
	 * @uml.property  name="secondCard"
	 */
	private Card secondCard = null;
	

	/**
	 * Reloj del nivel.
	 * Indica el tiempo maximo para descubrir todos los pares de cartas.
	 * 
	 * @uml.property  name="clock"
	 */
	private Clock clock = null;
	

	/**
	 * Matriz que indica que posiciones del tablero estan libres y cuales no.
	 *  
	 * @uml.property  name="table" multiplicity="(0 -1)" dimension="2"
	 */
	private boolean[][] table = null;
	

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
	 * Controlador del juego Memo. Administra los niveles del juego
	 * y los puntos.
	 * 
	 * @uml.property  name="gameplay"
	 * @uml.associationEnd  inverse="level:Gameplay"
	 */
	private Gameplay gameplay = null;
	
	
	/**
	 * Variable booleana que indica si el nivel ya est� inicializado.
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
	private Timer timerSecondCard = new Timer(1000);
	
	
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
	private GameFont mFont = null;
	
	
	/** 
	 * Constructor del Nivel.
	 * Crea el clock y el SpriteGroup de cartas.
	 * 
	 * @param parent  Instancia del Juego (Memo.java).
	 */	
	public Level(GameEngine parent) {
		
		super(parent);
		clock = new Clock();
		mGroupCards = new SpriteGroup("Grupo de cartas");
	}


	/** Retorna TRUE si el nivel ha finalizado correctamente
	 * (osea si se descubrieron todos los pares de cartas)
	 * y FALSE en caso contrario.
	 * 
	 * @return  Returns the levelComplete.
	 * @uml.property  name="levelComplete"
	 */
	public boolean isLevelComplete() {
		return (this.remainingCards == 0);
	}


	/** Setea la variable levelComplete.
	 * 
	 * @param levelComplete  The levelComplete to set.
	 * @uml.property  name="levelComplete"
	 */
	/*public void setLevelComplete(boolean levelComplete) {
		this.levelComplete = levelComplete;
	}*/


	/** Retorna el nro de nivel.
	 * 
	 * @return  Returns the levelNumber.
	 * @uml.property  name="levelNumber"
	 */
	public int getLevelNumber() {
		return levelNumber;
	}


	/** Setea el nro de nivel.
	 * 
	 * @param levelNumber  nro. de nivel a setear.
	 * @uml.property  name="levelNumber"
	 */
	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}


	/**
	 * Retorna el playfield del nivel.
	 * 
	 * @return  Returns the playfield.
	 * @uml.property  name="playfield"
	 */
	public PlayField getPlayfield() {
		return playfield;
	}


	/**
	 * Setea el playfield del nivel.
	 * 
	 * @param playfield  The playfield to set.
	 * @uml.property  name="playfield"
	 */
	public void setPlayfield(PlayField playfield) {
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
	 * Retorna la matriz table.
	 * 
	 * @return  Returns the tables.
	 * @uml.property  name="table"
	 */
	/*public boolean[][] getTable() {
		return this.table;
	}*/


	/**
	 * Setea la matriz de estados del tablero.
	 * 
	 * @param xTable  The tables to set.
	 * @uml.property  name="table"
	 */
	/*public void setTable(boolean[][] xTable) {
		this.table = xTable;
	}*/


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
	 * Retorna el reloj del nivel.
	 * 
	 * @return  Returns the clock.
	 * @uml.property  name="clock"
	 */
	public Clock getClock() {
		return clock;
	}


	/**
	 * Setea el reloj del nivel.
	 * 
	 * @param clock  The clock to set.
	 * @uml.property  name="clock"
	 */
	public void setClock(Clock clock) {
		this.clock = clock;
	}


	/**
	 * @param o
	 * @param arg
	 */
	public void update(Observable o, Object arg) {

		// TODO no es necesario implementarlo si se usa el clock
		// de GTGE.
	}
	

	/**
	 * Calcula aleatoriamente las filas y columnas que tendra el tablero
	 * en base a la cantidad de cartas que posee el nivel.
	 * 
	 */
	private void calculateRowAndCol() {
		
		ArrayList<Point> vDimensions = new ArrayList<Point>(); 
		
		// Determino los divisores de tablesize y los guardo en el arreglo.
		for (int i = 2; i <= (this.tableSize / 2); i++) {
			if ((this.tableSize % i) == 0) {
				vDimensions.add(new Point(i, this.tableSize / i));
			}
		}
		
		// Obtengo una configuracion de filas y columnas aleatoriamente.
		int vPos = Utility.getRandom(0, vDimensions.size() - 1);
		
		row = vDimensions.get(vPos).x;
		col = vDimensions.get(vPos).y;
	}


	/**
	 * Metodo Hook del GTGE definido en GameObject como abstracto. 
	 * 
	 * Inicializa todas las variables y establece las condiciones iniciales 
	 * para la ejecuci�n del juego:
	 * 
	 * 1) Crea e inicializa el PlayField que representa a todos los objetos 
	 *    del nivel que se renderizar�n.
	 * 2) Establece el Background y el color de mascara del nivel.
	 * 3) Crea y configura las estructuras necesarias: matriz de cartas y
	 *    matriz de existencia en el juego.
	 * 4) Establece la disposici�n de cartas en el escenario.
	 */
	@Override
	public void initResources() {
		
		// Muestra el cursor en pantalla.
		showCursor();
		
		setMaskColor(Color.BLACK); // Makes sprite background transparent.
				
		mFont = fontManager.getFont(
				getImage("Resources/images/BitmapFont.png"));
		
		playfield = new PlayField(mBackground);
		playfield.addGroup(mGroupCards);
		
		// Seteo la cantidad de cartas que posee el nivel.
		this.tableSize = mGroupCards.getSize();
		
		// Determino las variables row y col.
		calculateRowAndCol();
		
		// Seteo el ancho y alto de las cartas.
		widthCard = mGroupCards.getSprites()[0].getWidth();
		heightCard = mGroupCards.getSprites()[0].getHeight();
		
		// Inicializo las dos matrices con los tama�os definidos anteriormente.
		mCards = new Card [row][col];
		//table = new boolean [row][col];
		
		// Ubico las cartas en el tablero y escenario.
		setPositionCards();
		
		// Seteo la cantidad de cartas que faltan descubrir.
		// Inicialmente es igual a la cantidad de cartas que posee el nivel.
		this.remainingCards = this.tableSize;
		
		// Seteo la duracion del nivel (en segundos). 
		clock.setRemainingTime(this.getRemainingTimeLevel());
		
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
		
		// Si encontr� una posicion libre la guardo.
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
			
			vPositionScreen.setLocation(positionScreenX, positionScreenY);
			
		} else {
			
			vPositionScreen.setLocation(positionScreenX
					+ (xPositionTable.x * width),
					 positionScreenY + (xPositionTable.y * heigh));
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
		
		mFont.drawString(g, "TIEMPO: " 
				+ String.valueOf(this.getRemainingTimeLevel()), 450, 460);
		
 		mFont.drawString(g, "NIVEL: " 
 				+ String.valueOf(this.getLevelNumber()), 450, 480);
 		
 		mFont.drawString(g, "PUNTOS: " 
 				+ String.valueOf(0), 450, 500); 
 		//gameplay.getGlobalScore()), 450, 500);
 		
 		mFont.drawString(g, "CARTAS FALTANTES: " 
 				+ String.valueOf(this.getRemainingCards()), 450, 520);
 		
 		mFont.drawString(g, "NRO. DE ACIERTOS: " 
 				+ String.valueOf(this.mSuccess), 450, 540);
 		
 		mFont.drawString(g, "NRO. DE DESACIERTOS: " 
 				+ String.valueOf(this.mFails), 450, 560);
	}

	
	/**
	 * Metodo Hook del GTGE definido en GameObject como abstracto.
	 * Actualiza todas las variables y estructuras del Nivel. 
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
			
		// Si est� inicializado el nivel.
		if (mInitializedNivel) {
			
			// Si no se est� esperando porque se est�n mostrando las dos cartas.
			if (!mWaitCards) {
				
				// Si el juego est� inicializado e hizo click.
				if (click()) {
					
					// Obtengo la carta seleccionada por el click del mouse.
					Card vSelectedCard = 
						(Card) checkPosMouse(mGroupCards, true);

					// Si seleccion� una carta que no est� dada vuelta.
					if ((vSelectedCard != null)) { 
							//&& (!vSelectedCard.isTurned())) {

						// Si estoy seleccionando la primer carta.
						if (this.firstCard == null) {

							firstCard = vSelectedCard;
							firstCard.turnCard();

						} else {
							
							// Seleccion� la segunda carta.
							secondCard = vSelectedCard;
							secondCard.turnCard();
							timerSecondCard.setActive(true);
							mWaitCards = true;
						}  
					}							    
				}	
				
				// Si seleccionaron dos cartas.
				if ((!mWaitCards) && (firstCard != null)
						&& (secondCard != null)) {
					// verifico si selecciono un par.
					checkCards();				
				}						
				
			} else if (timerSecondCard.action(elapsedTime)) {
				mWaitCards = false;
			}
			
			// Si desea abandonar el nivel.
		    if (keyDown(KeyEvent.VK_ESCAPE)) {
		    	
			    finishLevel();
		    }		
		    
			if ((!mWaitCards) && ((remainingCards == 0)
					|| (this.getRemainingTimeLevel() == 0))) {
				
				//this.clock.stop();
				finishLevel();
			}	    
		}
	}
	
	private void finishLevel() {
		finish();
	}

	/**
	 * Verifica si las dos cartas seleccionadas forman un par igual.
	 * Si son iguales incremento la cantidad de aciertos en uno,
	 * en caso contrario incremento la cantidad de desaciertos.
	 */
	private void checkCards() {
		
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
			//this.table[vPos.x][vPos.y] = false;

			// Obtengo la posicion de la carta en el tablero
			// en funcion de su posicion en el escenario del nivel.
			vPos = getPositionOnTable(secondCard);
			
			// Elimino la carta del tablero.
			this.mCards[vPos.x][vPos.y] = null;
			//this.table[vPos.x][vPos.y] = false;
			
			// Incremento el nro de aciertos de pares de cartas descubiertos.
			mSuccess++;
			
		} else {
			// Incremento el nro de desaciertos de pares de cartas descubiertos.
			mFails++;
		}
		
		firstCard = null;				
		secondCard = null;	
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
		int vX = (int) (xCard.getX() - positionScreenX) 
					/ (widthCard + separationCol);
		
		// Nro. de columna.
		int vY = (int) (xCard.getY() - positionScreenY) 
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
		return ((remainingCards == 0) || (this.clock.getRemainingTime() == 0));
	}
	

	/** 
	 * Retorna el controlador del juego Memo.
	 * 
	 * @return  Retorna el gameplay.
	 * @uml.property  name="gameplay"
	 */
	public Gameplay getGameplay() {
		return this.gameplay;
	}


	/**
	 * Setea el GamePlay del juego.
	 * 
	 * @param gameplay  El gameplay a setear.
	 * @uml.property  name="gameplay"
	 */
	public void setGameplay(Gameplay gameplay) {
		this.gameplay = gameplay;
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
	 * @param fails  el mFails a setear.
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
}
