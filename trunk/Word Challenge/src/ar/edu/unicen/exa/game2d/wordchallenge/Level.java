package ar.edu.unicen.exa.game2d.wordchallenge;
/*
 * Classname Level.java
 *
 * Version 1.0
 *
 * Date 16/11/2008
 *
 * Copyright UD3 - Word Challenge (c) 
 * 
 */

// GTGE
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

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.engine.BaseInput;
import com.golden.gamedev.object.AnimatedSprite;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.background.ImageBackground;
import com.golden.gamedev.object.font.SystemFont;
import com.golden.gamedev.object.sprite.VolatileSprite;
import com.golden.gamedev.util.FontUtil;
import com.golden.gamedev.util.ImageUtil;
import com.golden.gamedev.util.Utility;

/**
 * Esta clase crea un escenario para jugar al Word Challenge. Este juego utiliza
 * el FrameWork GTGE.
 * 
 * @author Carlos Mirabella, Luis Soldavini y Pablo Melchior
 * @version 1.0
 * 
 */
public class Level extends GameObject implements Observer {

	// **********************************************************************
	// *********** COORDENADAS DE LAS SIX LETTERS EN EL ESCENARIO ***********
	// ********* Tanto para las letras grandes como para las chicas *********
	// **********************************************************************

	/**
	 * Nro de letras a ordenar en el nivel.
	 */
	private static final int MAX_LETTERS = 6;

	/**
	 * Coordenada en el eje X (en pixeles) de la primera de las seis letras
	 * desordenadas.
	 */
	private static final int BIGLETTER_POS_X = 40;

	/**
	 * Coordenada en el eje Y (en pixeles) de la primera de las seis letras
	 * desordenadas.
	 */
	private static final int BIGLETTER_POS_Y = 127;

	/**
	 * Separcion horizontal (en pixeles) que hay entre 2 letras desordendas.
	 */
	private static final int BIGDISTANCE_X_LETTERS = 5;

	/**
	 * Coordenada en el eje X (en pixeles) de la primera de las letras chiquitas
	 * sin descubrir.
	 */
	private static final int SMALLLETTER_POS_X = 210;

	/**
	 * Coordenada en el eje Y (en pixeles) de la primera de las letras chiquitas
	 * sin descubrir.
	 */
	private static final int SMALLLETTER_POS_Y = 235;

	/**
	 * Separcion horizontal (en pixeles) que hay entre 2 letras chiquitas 
	 * sin descubrir.
	 */
	private static final int SMALLDISTANCE_X_LETTERS = 10;

	// **********************************************************************
	// *************** COORDENADAS DE PALABRAS EN EL ESCENARO ***************
	// **********************************************************************

	/**
	 * Cantidad de Palabras posibles que se pueden colocar en el panel en
	 * sentido vertical.
	 */
	private static final int ROWS_POSSIBLES_WORDS = 12;

	/**
	 * Separación horizontal entre palabras en el panel de palabras a adivinar.
	 */
	private static final int SEPARATION_HORIZONTAL_WORD = 1;

	/**
	 * Separación vertical entre palabras en el panel de palabras a adivinar.
	 */
	private static final int SEPARATION_VERTICAL_WORD = 1;

	/**
	 * Posición inicial en el eje vertical de la primer palabra en el panel de
	 * palabras a adivinar.
	 */
	private static final int POS_START_ROW_WORD = 295;

	/**
	 * Posición inicial en el eje horizontal de la primer palabra en el panel de
	 * palabras a adivinar.
	 */
	private static final int POS_START_COL_WORD = 20;

	/**
	 * Ancho de una palabra de longitud máxima, en el panel de palabras a
	 * adivinar.
	 */
	private int mWidthWord = 200;

	/**
	 * Altura de una palabra, en el panel de palabras a adivinar.
	 */
	private int mHeightWord = 23;

	// **********************************************************************
	// ********************** CLOCK Y TIMERS DEL NIVEL **********************
	// **********************************************************************

	/**
	 * Timer local para un delay antes de empezar el nivel (1 segundo).
	 */
	private Timer mTimerStartLevel = new Timer(1000);

	/**
	 * Timer para que haga un delay después de hacer click sobre un botón o al
	 * elegir un Letter de los seleccionables.
	 */
	private Timer mTimerSelected = new Timer(100);

	/**
	 * Instancia privada del Clock, permite llevar la cuenta de los segundos que
	 * faltan cumplirse para terminar el nivel.
	 * 
	 * @see Clock
	 * @uml.property name="clock"
	 * @uml.associationEnd inverse="level:Clock"
	 */
	private Clock mClock;

	private AnimatedSprite spClock;

	// **********************************************************************

	/**
	 * Indica si está o no inicializado el nivel.
	 */
	private boolean mInitializedLevel = false;

	/**
	 * Variable booleana que se usa para que en el juego no se pueda hacer click
	 * por un determinado tiempo luego de hacer click sobre un botón o haber
	 * elegido un Letter.
	 */
	private boolean mWaitSelect = false;

	private boolean mFinishing = false;
	/**
	 * Indica si el nivel finalizó porque no hay mas tiempo restante.
	 */
	private boolean mFinished = false;

	/**
	 * Diccionario utilizado en el nivel. Nos permite obtener las seis letras
	 * con las que se jugará. Y nos devuelve un ArrayList de las palabras que se
	 * pueden construir con estas seis letras.
	 * 
	 * @see Dictionary
	 */
	private Dictionary mDictionary = null;

	/**
	 * PlayField del nivel, en el se organizan todos los objetos que deben
	 * renderizarse.
	 * 
	 * @uml.property name="playfield"
	 */
	private PlayField mPlayField = null;

	/**
	 * Escenario del nivel.
	 */
	private Background mBackground = null;

	/**
	 * Representa al boton de salida.
	 * 
	 * @uml.property name="buttonExit"
	 */
	private Sprite buttonExit = null;

	/**
	 * SpriteGroup que contiene las Letters que pueden elegirse.
	 */
	private SpriteGroup mGroupBigLetters = null;

	/**
	 * SpriteGroup que contiene las Letters que se eligieron.
	 */
	private SpriteGroup mGroupSmallLetters = null;

	/**
	 * SpriteGroup que contiene los botones.
	 */
	private SpriteGroup mGroupButtons = null;

	/**
	 * Fuente que se utiliza para mostrar datos del nivel en la pantalla, puntos
	 * y tiempo restante.
	 */
	private SystemFont mFontInfoLevel = null;

	/**
	 * Fuente que se utiliza para mostrar las letras que fue seleccionando el
	 * jugador.
	 */
	private SystemFont mFontClock = null;

	/**
	 * Lista de palabras que se pueden formar con las seis letras actuales.
	 * Contiene un arreglo con objetos del tipo Word.
	 */
	private ArrayList<Word> mPossibleWords = new ArrayList<Word>();

	/**
	 * Palabra formada por las sucesivas selecciones de caracteres.
	 */
	private String mBuildWord = "";

	/**
	 * Instancia de la clase Score que se usa para obtener el puntaje que suma
	 * el descubrimiento de una palabra. Ademas, indica cual es el puntaje
	 * necesario para habilitar el botón que trae las 6 letras nuevas.
	 */
	private Score mScore = null;

	/**
	 * Lleva la cuenta de los puntos obtenidos con las seis letras. Al pedir 6
	 * letras nuevas, mPointsCurrentLetters = 0.
	 */
	private int mPointsCurrentLetters = 0;

	/**
	 * Botón que permite obtener seis nuevos caracteres. Esta instancia es la
	 * única de los 4 botones que se necesita porque debe ponerse en Enabled y
	 * Disable según la cantidad de puntaje obtenido con las actuales seis
	 * letras.
	 */
	private Button mButtonNewLetters = null;
	
	private Button mButtonOk = null;
	
	private Button mButtonBack = null;
	
	private Button mButtonRandomize = null;

	// ************************************************************************
	// ***************************** METODOS **********************************
	// ************************************************************************

	/**
	 * Constructor de la clase Level.
	 * 
	 * @param parent
	 */
	public Level(final GameEngine parent, final Clock clock,
			final Dictionary dictio) {
		super(parent);
		this.mClock = clock;
		this.mDictionary = dictio;

	}

	/**
	 * Método que debe implementarse por heredar de la clase GameObject. Es
	 * usado por FrameWork GTGE, en este método las subclases de GameObject
	 * deben inicializarse.
	 */
	@Override
	public final void initResources() {

		// Agrega al clock, el nivel como observer del mismo
		mClock.addObserver(this);

		// Setea el color utilizdo como mascara para las transparencias.
		setMaskColor(Color.BLACK);

		// Obtiene el Font que se usará para mostrar información.
		mFontInfoLevel = new SystemFont(FontUtil.createTrueTypeFont(this.bsIO
				.getURL("./resources/wordchallenge/images/MaroonedOnMarsBB.ttf"), Font.BOLD,
				80f));

		mFontClock = new SystemFont(FontUtil.createTrueTypeFont(this.bsIO
				.getURL("./resources/wordchallenge/images/MaroonedOnMarsBB.ttf"), Font.BOLD,
				40f));
		mFontClock.setColor(Color.WHITE);

		// Crea el background del nivel.
		mBackground = new ImageBackground(
				getImage("./resources/wordchallenge/images/background.jpg"));

		mPlayField = new PlayField(mBackground);

		spClock = new AnimatedSprite(ImageUtil.getImages(this.bsIO
				.getURL("./resources/wordchallenge/images/clock.png"), 7, 1,
				Transparency.TRANSLUCENT));
		spClock.setLoopAnim(true);
		spClock.getAnimationTimer().setDelay(200);
		spClock.setLocation(10, 10);
		mPlayField.add(spClock);

		mGroupButtons = new SpriteGroup("Group Buttons");

		// Agrega el botón que aleatoriza las letras.
		mButtonRandomize = new Button(new CommandRandomizeLetters(this),
				ImageUtil.getImage(this.bsIO
						.getURL("./resources/wordchallenge/images/shuffle.png"),
						Transparency.TRANSLUCENT), ImageUtil.getImage(this.bsIO
						.getURL("./resources/wordchallenge/images/shuffle.png"),
						Transparency.TRANSLUCENT), 620, 235);

		mButtonRandomize.setFixedposition(true);
		mGroupButtons.add(mButtonRandomize);

		// Agrega el botón que permite obtener otras seis letras.
		mButtonNewLetters = new Button(new CommandGetSixLetters(this),
				ImageUtil.getImage(this.bsIO
						.getURL("./resources/wordchallenge/images/newword.png"),
						Transparency.TRANSLUCENT), ImageUtil.getImage(this.bsIO
						.getURL("./resources/wordchallenge/images/newword.png"),
						Transparency.TRANSLUCENT), ImageUtil.getImage(this.bsIO
						.getURL("./resources/wordchallenge/images/newwordDisable.png"),
						Transparency.TRANSLUCENT), 40, 235);

		mButtonNewLetters.setFixedposition(true);
		mGroupButtons.add(mButtonNewLetters);

		// Agrega el botón que permite borrar la última letra
		// elegida.
		mButtonBack = new Button(new CommandDelLastLetter(this), ImageUtil
				.getImage(this.bsIO.getURL("./resources/wordchallenge/images/backspace.png"),
						Transparency.TRANSLUCENT), ImageUtil.getImage(this.bsIO
				.getURL("./resources/wordchallenge/images/backspace.png"),
				Transparency.TRANSLUCENT), 690, 115);

		mButtonBack.setFixedposition(true);
		mGroupButtons.add(mButtonBack);

		// Agrega el botón que permite chequear si la palabra
		// hasta el momento construída es correcta.
		mButtonOk = new Button(new CommandCheckWord(this), ImageUtil.getImage(
				this.bsIO.getURL("./resources/wordchallenge/images/ok.png"),
				Transparency.TRANSLUCENT), ImageUtil.getImage(this.bsIO
				.getURL("./resources/wordchallenge/images/ok.png"), Transparency.TRANSLUCENT),
				690, 160);

		mButtonOk.setFixedposition(true);
		mGroupButtons.add(mButtonOk);


		// El playField del juego recibe el grupo de Botones.
		mPlayField.addGroup(mGroupButtons);		

		buttonExit = new Sprite(ImageUtil.getImage(this.bsIO
				.getURL("./resources/wordchallenge/images/ingamesalir.png"),
				Transparency.TRANSLUCENT), 742, 12);
		mPlayField.add(buttonExit);
		
		mGroupBigLetters = new SpriteGroup("Group Big Six Letters");
		mGroupSmallLetters = new SpriteGroup("Group Small Six letters");

		String vSixLetters = mDictionary.getSixLetters();
		// Crea los 6 objetos Letters y los ubica en el escenario.
		createSixLetters(vSixLetters.toUpperCase());

		// El playField del juego recibe el grupo de letras.
		mPlayField.addGroup(mGroupBigLetters);
		mPlayField.addGroup(mGroupSmallLetters);

		// Obtiene seis letras desde el diccionario y las palabras
		// que se pueden formar con las mismas.
		setLettersOrder(mDictionary.getPossibleWords(vSixLetters));

		this.mTimerStartLevel.setActive(true);
		mPlayField.getExtraGroup().getScanFrequence().setDelay(3000);
	}

	/**
	 * Crea seis objetos Letters y les setea su imagen y posicion en el
	 * escenario.
	 */
	private void createSixLetters(String vSixLetters) {

		BufferedImage bigBright = ImageUtil.getImage(this.bsIO
				.getURL("./resources/wordchallenge/images/letrabright.png"),
				Transparency.TRANSLUCENT);
		BufferedImage smallBright = ImageUtil.getImage(this.bsIO
				.getURL("./resources/wordchallenge/images/letramediabright.png"),
				Transparency.TRANSLUCENT);
		for (int i = 0; i < MAX_LETTERS; i++) {

			Letter vLetter = new Letter(ImageUtil.getImage(this.bsIO
					.getURL("./resources/wordchallenge/images/letra.png"),
					Transparency.TRANSLUCENT),
					bigBright, vSixLetters.charAt(i), 88);
			vLetter.setVisible(false);
			mGroupBigLetters.add(vLetter);

			Letter smallLetter = new Letter(ImageUtil.getImage(this.bsIO
					.getURL("./resources/wordchallenge/images/letramedia.png"),
					Transparency.TRANSLUCENT),
					smallBright, vSixLetters.charAt(i), 44);

			smallLetter.setLocation(SMALLLETTER_POS_X
					+ ((smallLetter.getWidth() + SMALLDISTANCE_X_LETTERS) * i),
					SMALLLETTER_POS_Y);
			smallLetter.setFixedposition(true);
			mGroupSmallLetters.add(smallLetter);
		}
	}

	/**
	 * Este método obtiene seis letras desde el diccionario y las palabras que
	 * se pueden formar con las mismas.
	 * 
	 * A continuación se detallan las responsabilidades: 1) obtiene las seis
	 * letras. 2) Por cada una de las letras le asigna un objeto del tipo
	 * Letter. Lo hace en el orden de como obtuvo las letras del diccionario. 3)
	 * Ordena las palabras del diccionario teniendo en cuenta la cantidad de
	 * letras que la componen, de mayor a menor. 4) Por cada palabra, crea un
	 * objeto Word y lo posiciona en la pantalla. Luego lo agrega a la lista
	 * mPossibleWords.
	 */
	public final void setLettersOrder(ArrayList<String> vPossibleWords) {

		// ********* POSSIBLE WORDS ***********************

		// 1) Ordeno la lista de posibles palabras de mayor a menor
		// de acuerdo a su longitud.
		Collections.sort(vPossibleWords, new MyComparator());

		mPossibleWords = new ArrayList<Word>(vPossibleWords.size());

		// 4) Por cada palabra, crea un objeto Word y lo posiciona en la
		// pantalla. Luego lo agrega a la lista mPossibleWords.

		int vRowsWord = 0;
		int vColsWord = 0;
		for (int i = 0; i < vPossibleWords.size(); i++) {
			Word vWord = new Word("Word " + i + ": " + vPossibleWords.get(i));
			vWord.setWord(vPossibleWords.get(i).toUpperCase());

			if (vRowsWord >= ROWS_POSSIBLES_WORDS) {
				vRowsWord = 0;
				vColsWord++;
			}

			vWord.setPosition(
							POS_START_COL_WORD
									+ ((mWidthWord + SEPARATION_HORIZONTAL_WORD) * vColsWord),
							POS_START_ROW_WORD
									+ ((mHeightWord + SEPARATION_VERTICAL_WORD) * vRowsWord));

			mPossibleWords.add(vWord);

			this.mPlayField.addGroup(vWord);

			vRowsWord++;
		}

		// Inicializa el contador de puntaje para las actuales seis letras.
		mPointsCurrentLetters = 0;

		// Pone en Disable al botón que permite obtener nuevas seis letras.
		mButtonNewLetters.setEnabled(false);
	}

	/**
	 * Clase que implementa Comparator. Compara 2 strings segun por su longitud.
	 * Esta clase se utiliza para ordenar el arreglo de posibles palabras.
	 * 
	 * Luis Soldavini y Pablo Melchior
	 * 
	 * @version 1.0
	 * 
	 */
	class MyComparator implements Comparator<String> {

		/**
		 * Compara 2 strings por su longitud. Retorna: -1 si xArg0 > xArg1 0 si
		 * xArg0 == xArg1 1 si xArg0 < xArg1
		 * 
		 * @param xArg0
		 *            string1
		 * @param xArg1
		 *            string2
		 * @return entero
		 */
		public int compare(final String xArg0, final String xArg1) {

			if (xArg0.length() > xArg1.length()) {
				return -1;
			}
			if (xArg0.length() == xArg1.length()) {
				int i = xArg0.compareTo(xArg1);
				return i;

			}
			return 1;
		}
	} // MyComparator

	/**
	 * Método que debe implementarse por heredar de la clase GameObject. Es
	 * usado por FrameWork GTGE, en este método las subclases de GameObject
	 * actualizan el estado de sus timers, las acciones a seguir luego de un
	 * click del mouse, variables de estado, determinar si no ha finalizado el
	 * juego porque el tiempo ha finalizado, etc.
	 * 
	 * @param elapsedTime
	 *            Tiempo transcurrido desde la última llamada de este método por
	 *            parte del FrameWork.
	 */
	@Override
	public final void update(final long elapsedTime) {
		// Actualiza todos los Sprites del nivel.
		this.mPlayField.update(elapsedTime);

		// Tiempo que debe transcurrir antes de empezar el nivel.
		if ((!mInitializedLevel) && (mTimerStartLevel.action(elapsedTime)) 
				&& !mFinishing) {
			mInitializedLevel = true;

			this.mClock.start();
			this.spClock.setAnimate(true);
		}
		if (mFinishing) {
			if (click() || bsInput.getKeyPressed() != BaseInput.NO_KEY) {
				if (mClock.isFinished()) {
					goToMenu();
				} else {
					goToNextLevel();
				}
			}
		}
		if (click()) {
			if (checkPosMouse(buttonExit, true)) {
				goToMenu();
			}
		}
		// Si transcurrió el tiempo de delay de inicializacion del nivel.
		if (mInitializedLevel) {

			// Si no se está esperando la seleccion de un Letter.
			if (!mWaitSelect) {

				// Si el usuario realizó un click con el mouse.
				if (click()) {

					// Me fijo si hizo click sobre una Letter.
					Letter vSelectedLetter = (Letter) checkPosMouse(
							mGroupSmallLetters, true);

					if (vSelectedLetter == null) {

						// Si no hizo click sobre un letter entonces
						// me fijo si lo hizo sobre un botón.
						Button vSelectedButton = (Button) checkPosMouse(
								mGroupButtons, true);

						if (vSelectedButton != null) {
							vSelectedButton.click();
							mTimerSelected.setActive(true);
							mWaitSelect = true;
						}
					} else {

						if (vSelectedLetter.isVisible()) {
							// Si realizo click sobre un letter seleccionable.
							selectedLetter(vSelectedLetter);
							mTimerSelected.setActive(true);
							mWaitSelect = true;
						}
					}
				}
				manejoTeclado();
				// Si el usuario presionó "Escape" o finalizó el tiempo del
				// nivel
				if (keyDown(KeyEvent.VK_ESCAPE)) {
					goToMenu();
				}
			} else if (mTimerSelected.action(elapsedTime)) {
				mWaitSelect = false;
			}
		}
		
	}

	public void levelFinishing() {
		mInitializedLevel = false;
		mFinishing = true;
		this.spClock.setAnimate(false);
		this.mClock.stop();
		Sprite overlay = new Sprite(ImageUtil.getImage(
				this.bsIO.getURL("./resources/wordchallenge/images/overlay.png"),
				Transparency.TRANSLUCENT));
		
		mPlayField.add(overlay);
		
		for (int i = 0; i < mPossibleWords.size(); i++) {
			mPossibleWords.get(i).setVisible(true);
		}
	}
	
	/**
	 * Utilizado para cuando finaliza el nivel. Ocurre solo en dos situaciones,
	 * cuando el usuario presiona Escape o cuando finaliza el tiempo.
	 */
	private void goToMenu() {
		this.parent.nextGameID = WordChallenge.OPTION_MENU;
		finish();
	}
	
	/**
	 * Utilizado para cuando finaliza el nivel. Ocurre solo en dos situaciones,
	 * cuando el usuario presiona Escape o cuando finaliza el tiempo.
	 */
	private void goToNextLevel() {
		this.parent.nextGameID = WordChallenge.OPTION_PLAY;
		finish();
	}

	private void manejoTeclado() {
		if (bsInput.getKeyPressed() != BaseInput.NO_KEY) {
			if (bsInput.getKeyPressed() >= KeyEvent.VK_A 
					&& bsInput.getKeyPressed() <= KeyEvent.VK_Z) {
				String  key = KeyEvent.getKeyText(bsInput.getKeyPressed());
				Sprite[] letters =  mGroupSmallLetters.getSprites();
				for (int i = 0; i < mGroupSmallLetters.getSize(); i++) {
					Letter let = (Letter) letters[i];
					if (let.isVisible() && let.getValue() == key.charAt(0)) {
						selectedLetter(let);
						mTimerSelected.setActive(true);
						mWaitSelect = true;
						return;
					}
				}
			}
			if (bsInput.getKeyPressed() == KeyEvent.VK_ENTER) {
				mButtonOk.click();
				mTimerSelected.setActive(true);
				mWaitSelect = true;
			}
			if (bsInput.getKeyPressed() == KeyEvent.VK_BACK_SPACE) {
				mButtonBack.click();
				mTimerSelected.setActive(true);
				mWaitSelect = true;
			}
			if (bsInput.getKeyPressed() == KeyEvent.VK_SPACE) {
				mButtonRandomize.click();
				mTimerSelected.setActive(true);
				mWaitSelect = true;
			}
		}
	}

	/**
	 * Método que debe implementarse por heredar de la clase GameObject. Es
	 * usado por FrameWork GTGE, en este método las subclases de GameObject
	 * realizan un render de sus objetos que deben mostrarse.
	 * 
	 * @param g
	 *            entorno gráfico donde se renderiza los objetos de pantalla
	 */
	@Override
	public final void render(final Graphics2D g) {
		mPlayField.render(g);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		mFontInfoLevel.setColor(Color.ORANGE);
		mFontInfoLevel.drawString(g, "Puntaje", GameFont.CENTER, 70, -5, 300);
		mFontInfoLevel.setColor(new Color(142, 239, 123));
		mFontInfoLevel.drawString(g, String
				.valueOf(((WordChallenge) this.parent).getGlobalScore()),
				GameFont.CENTER, 70, 45, 300);
		
		mFontInfoLevel.setColor(new Color(123,164,239));
		mFontInfoLevel.drawString(g, "Correctas: " +
				String
				.valueOf(((WordChallenge)parent).getCorrectWords()), GameFont.CENTER, 400, -5, 200);
		mFontInfoLevel.drawString(g, "Incorrectas: "+
				String
				.valueOf(((WordChallenge)parent).getFailWords()), GameFont.CENTER, 400, 45, 200);

		if (!mFinishing) {
			mFontClock.drawString(g, String.valueOf(mClock.getRemainingTime()),
					GameFont.CENTER, 10, 40, 100);
		}
	}
	
	/**
	 * Verifica si la palabra selecionada es valida.
	 * @return
	 */
	public final boolean isValidWord() {
		boolean esta = false;
		for (int i = 0; i < mPossibleWords.size() && !esta; i++) {
			Word vWord = mPossibleWords.get(i);
			
			// Si la palabra todavia no fue descubierta y existe.
			if ((!vWord.isVisible())
					&& (mBuildWord.equalsIgnoreCase(vWord.getWord()))) {

				vWord.setVisible(true);

				mClock.addMoreTime(mScore.getTime(vWord.getWord().length()));
				addPoints(mScore.getPoints(vWord.getWord().length()));
				esta = true;
			}
		}
		mBuildWord = "";
		return esta;
	}
	
	
	/**
	 * Verifica si la palabra formada existe en la lista de mPossibleWords.
	 */
	public final void checkWord() {

		boolean esta = isValidWord();
		
		for (int j = 0; j < MAX_LETTERS; j++) {
			((Letter) mGroupSmallLetters.getSprites()[j]).setVisible(true);
			((Letter) mGroupBigLetters.getSprites()[j]).setVisible(false);
		}
		if (esta) {
			VolatileSprite winSprite = new VolatileSprite(ImageUtil.getImages(
					this.bsIO.getURL("./resources/wordchallenge/images/tick.png"), 6, 1,
					Transparency.TRANSLUCENT), 300, 150);
			winSprite.setAnimationFrame(new int[] { 0, 1, 2, 3, 4, 5 });
			winSprite.getAnimationTimer().setDelay(100);
			winSprite.setAnimate(true);
			this.checkFinalLevel();
			mPlayField.add(winSprite);
			
			playSound("./resources/wordchallenge/sounds/wincard.wav");
			
			// Incremento la cantidad de palabras acertadas
			((WordChallenge) parent).incrementCorrectWords();
			
			// Habilito el boton de nueva palabra, si tengo
			// los puntos necesarios.
			mButtonNewLetters.setEnabled((mPointsCurrentLetters >= mScore
					.getPointsNewLetters()));
		} else {
			VolatileSprite failSprite = new VolatileSprite(ImageUtil.getImages(
					this.bsIO.getURL("resources/wordchallenge/images/fail.png"), 6, 1,
					Transparency.TRANSLUCENT), 300, 150);
			failSprite.setAnimationFrame(new int[] { 0, 1, 2, 3, 4, 5 });
			failSprite.getAnimationTimer().setDelay(100);
			failSprite.setAnimate(true);
			mPlayField.add(failSprite);
			playSound("./resources/wordchallenge/sounds/flip.wav");
			
			// Incremento la cantidad de palabras incorrectas			
			((WordChallenge) parent).incrementFailWords();
		}
	}

	/**
	 * Metodo que se fija si estan visibles todas las palabras. Y si estan
	 * visible pasa de nivel.
	 */
	private void checkFinalLevel() {

		boolean esta = true;
		for (int i = 0; i < mPossibleWords.size() && esta; i++) {
			if (!mPossibleWords.get(i).isVisible()) {
				esta = false;
			}
		}
		if (esta) {
			levelFinishing();
		}
	}

	/**
	 * Aleatoriza las letras actuales del nivel. Solo es útil para el jugador,
	 * no tiene ningún efecto sobre el juego. Aleatoriza solo las letras que
	 * están activas, y usa como estrategia el de buscar otra letra en una
	 * posición aleatoria que esté activa, si la nueva letra no está activa la
	 * búsqueda es secuencial desde la posición aleatoria y la búsqueda de la
	 * segunda letra finaliza cuando esta esté active. Luego, intercambia los
	 * valores de ambas Letter.
	 */
	public final void randomLettersOrder() {
		Letter vLetter;
		Letter vOtherLetter;
		int vPosOtherLetter;

		for (int i = 0; i < this.mGroupSmallLetters.getSize(); i++) {
			vLetter = (Letter) this.mGroupSmallLetters.getSprites()[i];

			// Si la letra esta Visible.
			if (vLetter.isVisible()) {

				// Obtengo aleatoriamente la posicion de otra letra.
				vPosOtherLetter = Utility.getRandom(0, this.mGroupSmallLetters
						.getSize() - 1);

				// Obtengo la letra.
				vOtherLetter = (Letter) this.mGroupSmallLetters.
												getSprites()[vPosOtherLetter];

				// Ciclo hasta encontrar una letra que este visible.
				while (!vOtherLetter.isVisible()) {
					vPosOtherLetter++;
					vPosOtherLetter = vPosOtherLetter
							% this.mGroupSmallLetters.getSize();

					vOtherLetter = (Letter) this.mGroupSmallLetters
							.getSprites()[vPosOtherLetter];
				}

				// Encontrada dos letras visibles, realizo el cambio.
				double posX = vLetter.getX();
				// vLetter.moveTo(2000, vOtherLetter.getX(),vLetter.getY(),0.1);
				// vOtherLetter.moveTo(2000, posX,vOtherLetter.getY(), 0.1);
				vLetter.setX(vOtherLetter.getX());
				vOtherLetter.setX(posX);
			}
		}
	}

	/**
	 * Borra la ultima letra seleccionada.
	 * 
	 * 1) Elimina la ultima letra de la palabra que se esta formando. (Setea a
	 * la letra como inactiva).
	 * 
	 * 2) Selecciona algun objeto Letter que este inactivo y coloca la letra
	 * borrada nuevamente para que pueda ser seleccionada mas adelante.
	 */
	public final void deleteLastLetter() {

		if (mBuildWord.length() > 0) {

			char vChar = mBuildWord.charAt(mBuildWord.length() - 1);

			for (int i = 0; i < MAX_LETTERS; i++) {
				Letter vLetter = (Letter) mGroupSmallLetters.getSprites()[i];

				// Si la letra no esta visible y es igual a la ultima letra
				// de la palabra armada.
				if ((!vLetter.isVisible()) && (vLetter.getValue() == vChar)) {
					vLetter.setVisible(true);
					mBuildWord = mBuildWord.substring(0,
							mBuildWord.length() - 1);
					break;
				}
			}
			int pos = 0;
			double posx = 0;
			for (int i = 0; i < MAX_LETTERS; i++) {
				Letter blet = (Letter) mGroupBigLetters.getSprites()[i];
				if (blet.isVisible() && blet.getX() > posx) {
					pos = i;
					posx = blet.getX();
				}
			}
			((Letter) mGroupBigLetters.getSprites()[pos]).setVisible(false);
		}
	}

	/**
	 * Actualiza el puntaje Global y el puntaje de las seis letras actuales.
	 * 
	 * @param xPoints
	 *            puntaje adicional
	 */
	private void addPoints(final int xPoints) {

		// Sumo los puntos al puntaje global.
		((WordChallenge) this.parent).addPoints(xPoints);

		// Sumo los puntos al acumulativo de puntos por palabra.
		mPointsCurrentLetters += xPoints;

		
	}

	/**
	 * Método privado que se ejecuta al seleccionar una de las seis letras.
	 * 
	 * @param xLetter
	 *            Letter seleccionada.
	 */
	private void selectedLetter(final Letter xLetter) {

		mBuildWord += xLetter.getValue();
		xLetter.setVisible(false);
		Sprite[] spt = mGroupBigLetters.getSprites();
		for (int i = 0; i < MAX_LETTERS; i++) {
			Letter let = (Letter) spt[i];
			if (!let.isVisible() && (let.getValue() == xLetter.getValue())) {
				let.setLocation(BIGLETTER_POS_X
						+ (BIGDISTANCE_X_LETTERS + let.getWidth())
						* (mBuildWord.length() - 1), BIGLETTER_POS_Y);
				let.setVisible(true);
				break;
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		if (mClock.isFinished() && !mFinishing)
			levelFinishing();
	}

	/**
	 * Retorna el escenario del nivel.
	 * 
	 * @return the background
	 */
	public final Background getBackground() {
		return mBackground;
	}

	/**
	 * Setea el escenario del nivel.
	 * 
	 * @param xBackground
	 *            the background to set
	 */
	public final void setBackground(final Background xBackground) {
		mBackground = xBackground;
	}

	/**
	 * Devuelve el diccionario usado en el nivel.
	 * 
	 * @return dictionary usado
	 */
	public final Dictionary getDictionary() {
		return mDictionary;
	}

	/**
	 * Método para setearle el diccionario al nivel.
	 * 
	 * @param dictionary
	 *            dicionario que va a utilizar el nivel
	 */
	public final void setDictionary(final Dictionary dictionary) {
		this.mDictionary = dictionary;
	}

	/**
	 * Devuelve el Clock usado en el nivel.
	 * 
	 * @return Clock usado
	 */
	public final Clock getClock() {
		return mClock;
	}

	/**
	 * Método para setearle el Clock al nivel.
	 * 
	 * @param Clock
	 *            Clock que va a utilizar el nivel
	 */
	public final void setClock(final Clock clock) {
		this.mClock = clock;
	}

	/**
	 * Retorna el Score utilizado en el nivel.
	 * 
	 * @return Score actual del nivel
	 */
	public final Score getScore() {
		return mScore;
	}

	/**
	 * Setea la propiedad de privada de Score.
	 * 
	 * @param score
	 *            utilizado en el nivel
	 */
	public final void setScore(final Score score) {
		mScore = score;
	}

	/**
	 * Indica si el nivel a finalizado.
	 * 
	 * @return boolean si el nivel ha finalizado o no.
	 */
	public final boolean isFinished() {
		return mFinished;
	}
	/**
	 * Devuelve el ancho de una palabra de longitud máxima, 
	 * en el panel de palabras a adivinar.
	 * 
	 * @return int widthWord
	 */
	public int getMWidthWord() {
		return mWidthWord;
	}

	/**
	 * Método para setear el ancho de una palabra de longitud máxima.
	 * 
	 * @param widthWord
	 */
	public void setMWidthWord(int widthWord) {
		mWidthWord = widthWord;
	}

	/**
	 * Devuelve la altura de una palabra, en el panel de palabras a adivinar.  
	 * 
	 * @return  int heightWord
	 */
	public int getMHeightWord() {
		return mHeightWord;
	}

	/**
	 * Método para setear la altura de una palabra.
	 * 
	 * @param heightWord
	 */
	public void setMHeightWord(int heightWord) {
		mHeightWord = heightWord;
	}

	/**
	 * Devuelve el timer local para un delay antes de empezar el nivel.
	 * 
	 * @return timer timerStartLevel
	 */
	public Timer getMTimerStartLevel() {
		return mTimerStartLevel;
	}

	/**
	 * Método para setear el timer local para un delay antes de empezar el
	 * nivel.
	 * 
	 * @param timerStartLevel
	 */
	public void setMTimerStartLevel(Timer timerStartLevel) {
		mTimerStartLevel = timerStartLevel;
	}
	
	/**
	 * Devuelve el timer que representa un delay después de hacer click sobre
	 * un botón o al elegir un Letter de los seleccionables.
	 * 
	 * @return timer timerSelected 
	 */
	public Timer getMTimerSelected() {
		return mTimerSelected;
	}

	/**
	 * Método para setear el timer que representa un delay después de hacer
	 * click sobre un botón o al elegir un Letter de los seleccionables.
	 * 
	 * @param timerSelected
	 */
	public void setMTimerSelected(Timer timerSelected) {
		mTimerSelected = timerSelected;
	}

	/**
	 * Devuelve una instancia privada del Clock que permite llevar la cuenta de
	 * los segundos que faltan cumplirse para terminar el nivel.  
	 * 
	 * @return Clock usado  
	 */
	public Clock getMClock() {
		return mClock;
	}

	/**
	 * Método para setear una instancia privada del Clock que permite llevar la
	 * cuenta de los segundos que faltan cumplirse para terminar el nivel.
	 * 
	 * @param clock 
	 */
	public void setMClock(Clock clock) {
		mClock = clock;
	}
	
	/**
	 * Getter de la variable spClock  
	 * 
	 * @return AnimatedSprite
	 */
	public AnimatedSprite getSpClock() {
		return spClock;
	}

	/**
	 * Setter de la variable spClock
	 * 
	 * @param spClock
	 */
	public void setSpClock(AnimatedSprite spClock) {
		this.spClock = spClock;
	}
	
	/**
	 * Devuelve si el nivel esta inicializado o no  
	 * 
	 * @return boolean initializedLevel
	 */
	public boolean isMInitializedLevel() {
		return mInitializedLevel;
	}

	/**
	 * Método para setear si el nivel está inicializado
	 * 
	 * @param initializedLevel
	 */
	public void setMInitializedLevel(boolean initializedLevel) {
		mInitializedLevel = initializedLevel;
	}
	
	/**
	 * Devuelve si se puede hacer click o no.  
	 * 
	 * @return boolean waitSelect
	 */
	public boolean isMWaitSelect() {
		return mWaitSelect;
	}

	/**
	 * Método para setear si se puede hacer click
	 * 
	 * @param waitSelect
	 */
	public void setMWaitSelect(boolean waitSelect) {
		mWaitSelect = waitSelect;
	}
	
	/**
	 * Getter de la variable finishing  
	 * 
	 * @return boolean finishing
	 */
	public boolean isMFinishing() {
		return mFinishing;
	}

	/**
	 * Setter de la variable finishing
	 * 
	 * @param finishing
	 */
	public void setMFinishing(boolean finishing) {
		mFinishing = finishing;
	}

	/**
	 * Devuelve si el nivel finalizó porque no hay mas tiempo restante.   
	 * 
	 * @return 	boolean finished 
	 */
	public boolean isMFinished() {
		return mFinished;
	}

	/**
	 * Método para setear si el nivel finalizó
	 * 
	 * @param finished
	 */
	public void setMFinished(boolean finished) {
		mFinished = finished;
	}
	
	/**
	 * Devuelve el PlayField del nivel, en el se organizan todos los objetos 
	 * que deben renderizarse.   
	 * 
	 * @return  PlayField usado
	 */
	public PlayField getMPlayField() {
		return mPlayField;
	}

	/**
	 * Método para setear el PlayField del nivel, en el se organizan todos los
	 * objetos que deben renderizarse.
	 * 
	 * @param playField
	 */
	public void setMPlayField(PlayField playField) {
		mPlayField = playField;
	}

	/**
	 * Devuelve la variable que representa al botón de salida 
	 * 
	 * @return Sprite buttonExit
	 */
	public Sprite getButtonExit() {
		return buttonExit;
	}

	/**
	 * Método para setear la variable que representa al botón de salida.
	 * 
	 * @param buttonExit
	 */
	public void setButtonExit(Sprite buttonExit) {
		this.buttonExit = buttonExit;
	}

	/**
	 * Devuelve la variable que contiene las letras que pueden elegirse
	 * 
	 * @return SpriteGroup groupBigLetters
	 */
	public SpriteGroup getMGroupBigLetters() {
		return mGroupBigLetters;
	}

	/**
	 * Método para setear la variable que contiene las letras que pueden 
	 * elegirse
	 * 
	 * @param groupBigLetters
	 */
	public void setMGroupBigLetters(SpriteGroup groupBigLetters) {
		mGroupBigLetters = groupBigLetters;
	}

	/**
	 * Devuelve la variable que contiene las letras que se eligieron.
	 * 
	 * @return SpriteGroup groupSmallLetters
	 */
	public SpriteGroup getMGroupSmallLetters() {
		return mGroupSmallLetters;
	}

	/**
	 * Método para setear la variable que contiene las letras que se eligieron.
	 * 
	 * @param groupSmallLetters
	 */
	public void setMGroupSmallLetters(SpriteGroup groupSmallLetters) {
		mGroupSmallLetters = groupSmallLetters;
	}
	
	/**
	 * Devuelve la variable que contiene los botones.
	 * 
	 * @return SpriteGroup groupButtons
	 */
	public SpriteGroup getMGroupButtons() {
		return mGroupButtons;
	}

	/**
	 * Método para setear la variable que contiene los botones.
	 * 
	 * @param groupButtons
	 */
	public void setMGroupButtons(SpriteGroup groupButtons) {
		mGroupButtons = groupButtons;
	}

	/**
	 * Devuelve la fuente que se utiliza para mostrar los datos del nivel 
	 * en la pantalla,puntos y tiempo restante.
	 * 
	 * @return SystemFont fontInfoLevel
	 */
	public SystemFont getMFontInfoLevel() {
		return mFontInfoLevel;
	}

	/**
	 * Método para setear la fuente que se utiliza para mostrar los datos del 
	 * nivel en la pantalla, puntos y tiempo restante.
	 * 
	 * @param fontInfoLevel
	 */
	public void setMFontInfoLevel(SystemFont fontInfoLevel) {
		mFontInfoLevel = fontInfoLevel;
	}

	/**
	 * Devuelve la fuente que utiliza para mostrar las letras que seleccionó 
	 * el jugador.
	 * 
	 * @return SystemFont fontClock
	 */
	public SystemFont getMFontClock() {
		return mFontClock;
	}

	/**
	 * Método para setear la fuente que se utiliza para mostrar las letras que 
	 * seleccionó el jugador.
	 * 
	 * @param fontClock
	 */
	public void setMFontClock(SystemFont fontClock) {
		mFontClock = fontClock;
	}

	/**
	 * Devuelve la variable que contiene las palabras que pueden formarse con 
	 * las seis letras actuales.
	 * 
	 * @return SystemFont fontClock
	 */
	public ArrayList<Word> getMPossibleWords() {
		return mPossibleWords;
	}

	/**
	 * Método para setear la variable que contiene las palabras que puede 
	 * formar el jugador con las seis letras actuales.
	 * 
	 * @param possibleWords 
	 */
	public void setMPossibleWords(ArrayList<Word> possibleWords) {
		mPossibleWords = possibleWords;
	}

	/**
	 * Devuelve la palabra formada por la sucesiva selección de caracteres.
	 * 
	 * @return String buildWord
	 */
	public String getMBuildWord() {
		return mBuildWord;
	}

	/**
	 * Método para setear la variable que contiene la palabra formada por la 
	 * sucesiva selección de carcteres.
	 * 
	 * @param buildWord
	 */
	public void setMBuildWord(String buildWord) {
		mBuildWord = buildWord;
	}

	/**
	 * Devuelve los puntos obtenidos con las seis letras.
	 * 
	 * @return int pointsCurrentLetters
	 */
	public int getMPointsCurrentLetters() {
		return mPointsCurrentLetters;
	}

	/**
	 * Método para setear los puntos obtenidos con las seis letras.
	 * 
	 * @param pointsCurrentLetters
	 */
	public void setMPointsCurrentLetters(int pointsCurrentLetters) {
		mPointsCurrentLetters = pointsCurrentLetters;
	}

	/**
	 * Devuelve la variable que representa al botón que permite obtener seis 
	 * nuevos caracteres.
	 * 
	 * @return Button buttonNewLetters
	 */
	public Button getMButtonNewLetters() {
		return mButtonNewLetters;
	}

	/**
	 * Método para setear la variable que representa al botón que permite 
	 * obtener seis nuevos caracteres.
	 * 
	 * @param buttonNewLetters
	 */
	public void setMButtonNewLetters(Button buttonNewLetters) {
		mButtonNewLetters = buttonNewLetters;
	}

	/**
	 * Devuelve la variable que representa al botón OK.
	 * 
	 * @return Button buttonOk
	 */
	public Button getMButtonOk() {
		return mButtonOk;
	}

	/**
	 * Método para setear la variable que representa al botón OK.
	 * 
	 * @param buttonOk
	 */
	public void setMButtonOk(Button buttonOk) {
		mButtonOk = buttonOk;
	}

	/**
	 * Devuelve la variable que representa al botón BACK.
	 * 
	 * @return Button buttonBack
	 */
	public Button getMButtonBack() {
		return mButtonBack;
	}

	/**
	 * Método para setear la variable que representa al botón BACK.
	 * 
	 * @param buttonBack
	 */
	public void setMButtonBack(Button buttonBack) {
		mButtonBack = buttonBack;
	}

	/**
	 * Devuelve la variable que representa al botón RANDOMIZE.
	 * 
	 * @return Button buttonRandomize
	 */
	public Button getMButtonRandomize() {
		return mButtonRandomize;
	}

	/**
	 * Método para setear la variable que representa al botón RANDOMIZE.
	 * 
	 * @param buttonRandomize
	 */
	public void setMButtonRandomize(Button buttonRandomize) {
		mButtonRandomize = buttonRandomize;
	}

	/**
	 * Devuelve el número de letras a ordenar en el nivel.
	 * 
	 * @return int max_letters
	 */
	public static int getMAX_LETTERS() {
		return MAX_LETTERS;
	}

	/**
	 * Devuelve la coordenada en el eje X de la primera de las seis letras
	 * desordenadas.
	 * 
	 * @return int coordenada X
	 */
	public static int getBIGLETTER_POS_X() {
		return BIGLETTER_POS_X;
	}

	/**
	 * Devuelve la coordenada en el eje Y de la primera de las seis letras 
	 * desordenadas.
	 * 
	 * @return int coordenada Y
	 */
	public static int getBIGLETTER_POS_Y() {
		return BIGLETTER_POS_Y;
	}

	/**
	 * Devuelve la separación horizontal que hay entre 2 letras desordenadas.
	 * 
	 * @return int separación horizontal
	 */
	public static int getBIGDISTANCE_X_LETTERS() {
		return BIGDISTANCE_X_LETTERS;
	}


	/**
	 * Devuelve la coordenada en el eje X de la primera de las letras chiquitas
	 * sin descubrir.
	 * 
	 * @return int coordenada X
	 */
	public static int getSMALLLETTER_POS_X() {
		return SMALLLETTER_POS_X;
	}


	/**
	 * Devuelve la coordenada en el eje Y de la primera de las letras chiquitas 
	 * sin descubrir.
	 * 
	 * @return int coordenada Y
	 */
	public static int getSMALLLETTER_POS_Y() {
		return SMALLLETTER_POS_Y;
	}

	/**
	 * Devuelve la separación horizontal que hay entre 2 letras chiquitas sin
	 * descubrir.
	 * 
	 * @return int separación horizontal
	 */
	public static int getSMALLDISTANCE_X_LETTERS() {
		return SMALLDISTANCE_X_LETTERS;
	}

	/**
	 * Devuelve la cantidad de palabras que se pueden colocar en el panel en
	 * sentido vertical.
	 * 
	 * @return int cantidad palabras
	 */
	public static int getROWS_POSSIBLES_WORDS() {
		return ROWS_POSSIBLES_WORDS;
	}

	/**
	 * Devuelve la separación horizontal entre palabras en el panel de
	 * palabras a adivinar.
	 * 
	 * @return int separación horizontal
	 */
	public static int getSEPARATION_HORIZONTAL_WORD() {
		return SEPARATION_HORIZONTAL_WORD;
	}

	/**
	 * Devuelve la separación vertical entre palabras en el panel de
	 * palabras a adivinar.
	 * 
	 * @return int separación vertical
	 */
	public static int getSEPARATION_VERTICAL_WORD() {
		return SEPARATION_VERTICAL_WORD;
	}

	/**
	 * Devuelve la posición inicial en el eje vertical de la primer palabra
	 * en el panel de palabras a adivinar.
	 * 
	 * @return int posición inicial 
	 */
	public static int getPOS_START_ROW_WORD() {
		return POS_START_ROW_WORD;
	}

	/**
	 * Devuelve la posición inicial en el eje horizontal de la primer palabra 
	 * en el panel de palabras a adivinar.
	 * 
	 * @return int posición inicial
	 */
	public static int getPOS_START_COL_WORD() {
		return POS_START_COL_WORD;
	}

}
