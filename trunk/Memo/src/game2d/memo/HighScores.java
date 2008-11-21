package game2d.memo;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.background.ImageBackground;
import com.golden.gamedev.object.font.SystemFont;
import com.golden.gamedev.util.FontUtil;

/**
 * 
 * @author Carlos Mirabella
 *
 */
public class HighScores extends GameObject {

	/**
	 * Fondo de la pantalla.
	 */
	private Background background;
	
	/**
	 * Pantalla definida donde se graficara.
	 */
	private PlayField playFieldHighScores = new PlayField();
	
	/**
	 * Fuente Grande.
	 */
	private SystemFont bigFont;
	
	/**
	 * Fuente Pequeña.
	 */
	private SystemFont mFont;

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
		bigFont = new SystemFont(FontUtil.createTrueTypeFont(
				this.bsIO.getURL("resources/images/ravie.ttf"), Font.PLAIN, 36));
		mFont = new SystemFont(FontUtil.createTrueTypeFont(
				this.bsIO.getURL("resources/images/ravie.ttf"), Font.PLAIN, 24));

		background = new ImageBackground(
				getImage("resources/images/menuPuntuacion.png"), 800, 600);
		playFieldHighScores.setBackground(background);
	}
	
	/**
	 * Dibuja los records del juego.
	 * @param g2D Instancia de Graphics2D
	 */
	private void drawHighScores(final Graphics2D g2D) {
		int lineHeight = 40;
		mFont.setColor(new Color(142, 239, 123));
		mFont.drawString(g2D, "Nombre", GameFont.CENTER,0,
				160 + lineHeight, 600);
		mFont.drawString(g2D, "Puntuacion", GameFont.CENTER, 600,
				160 + lineHeight, 200);
		mFont.drawString(g2D, "Nombre", GameFont.CENTER,0,
				160 + lineHeight * 2, 600);
		mFont.drawString(g2D, "Puntuacion", GameFont.CENTER, 600,
				160 + lineHeight * 2, 200);
	}
	
	/**
	 * 
	 * @param g2D Instancia de Graphics2D
	 */
	@Override
	public final void render(final Graphics2D g2D) {
		
		playFieldHighScores.render(g2D);
		//Escribe el texto en pantalla con antialias.
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		bigFont.setColor(Color.WHITE);
		bigFont.drawString(g2D, "Nombre", GameFont.CENTER,0,150, 600);
		bigFont.drawString(g2D, "Puntos", GameFont.CENTER, 600, 150, 200);
		drawHighScores(g2D);
		
		mFont.setColor(new Color(239, 231, 123));
		mFont.drawString(g2D, "ENTER o click para volver al menu",GameFont.CENTER, 0, 550, 800);
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
