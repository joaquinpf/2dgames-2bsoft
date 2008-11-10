import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.background.ColorBackground;


/**
 * 
 * @author pdeluca
 *
 */
public class HighScores extends GameObject {

	/**
	 *  Caracter de separacion para cada linea del high score.
	 */
	private static final String SEPARATOR_CHAR = "_";

	/**
	 * Maxima longitud del nombre del jugador a mostrar en el highscore.
	 */
	private static final int MAX_WORD_LENGTH = 400;

	/**
	 * Alto de la grilla de fuente.
	 */
	private static final int FONT_GRID_HEIGHT = 12;
	
	/**
	 * Ancho de la grilla de fuente.
	 */
	private static final int FONT_GRID_WIDTH = 8;

	/**
	 * Fondo de la pantalla.
	 */
	private Background background;
	
	/**
	 * Pantalla definida donde se graficara.
	 */
	private PlayField playFieldHighScores = new PlayField();
	
	/**
	 * Manejador de fuente.
	 */
	private GameFont font;

	/**
	 * imagen con el titulo de la seccion implementada.
	 */
	private BufferedImage imgHighScores;

	
	/**
	 * Constructor.
	 * @param parent instancia de GameEngine.
	 */
	public HighScores(final GameEngine parent) {
		super(parent);
	}
	
	/**
	 * Metodo de inicializacion.
	 */
	@Override
	public final void initResources() {
		font = fontManager.getFont(getImages("resources/images/font.png", FONT_GRID_WIDTH, FONT_GRID_HEIGHT));
		imgHighScores = getImage("resources/images/highscores.gif");

		background = new ColorBackground(Color.BLUE);
		playFieldHighScores.setBackground(background);
	}
	
	/**
	 * Genera una linea de underscores para separar el nombre del score.
	 * @param wordLen largo del nombre del jugador
	 * @return linea de separacion
	 */
	private String getHighScoreSeparator(final int wordLen) {
		int separatorStep = 10;
		String sepLine = new String();
		
		if (wordLen >= MAX_WORD_LENGTH) {
			return " ";
		}
		
		for (int i = MAX_WORD_LENGTH; i > wordLen; i = i - separatorStep) {
			sepLine = sepLine + SEPARATOR_CHAR;
		}
		return sepLine;
	}
	
	/**
	 * Dibuja los records del juego.
	 * @param g2D Instancia de Graphics2D
	 */
	private void drawHighScores(final Graphics2D g2D) {
		//TODO: Obtener, como parametro o de donde sea,
		//un vector con los pares {player||score} y listarlo en la pantalla
		int leftOffset = 200;
		int rightOffset = 100;
		int lineLength = font.getWidth("NAME") + font.getWidth("SCORE"); 

		font.drawString(g2D, "NAME" + getHighScoreSeparator(lineLength) + "SCORE", getWidth() / 2 - leftOffset, getHeight() / 2 - rightOffset);
	}
	
	/**
	 * 
	 * @param g2D Instancia de Graphics2D
	 */
	@Override
	public final void render(final Graphics2D g2D) {
		int leftOffset = 180;
		int rightOffset = 8;
		int imgXOffset = 250;
		int imgYOffset = 20;
		
		playFieldHighScores.render(g2D);
		
		drawHighScores(g2D);
		
		g2D.setColor(Color.BLACK);
		
		g2D.drawString("ENTER o click para volver al menu", getWidth() / 2 - leftOffset, getHeight() - rightOffset);
		
		g2D.drawImage(imgHighScores, getWidth() / 2 - imgXOffset, imgYOffset, null);
	}

	/**
	 * @param elapsedTime tiempo de refresco
	 */
	@Override
	public final void update(final long elapsedTime) {
		playFieldHighScores.update(elapsedTime);
		if (keyDown(KeyEvent.VK_ENTER) || keyDown(KeyEvent.VK_ESCAPE)
		   || click()) {
				parent.nextGameID = Memo.MENU_MENU;
				finish();
		}
	}


}
