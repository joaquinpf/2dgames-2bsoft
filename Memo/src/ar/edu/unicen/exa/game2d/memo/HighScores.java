package ar.edu.unicen.exa.game2d.memo;
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
	 * Tabla de Ranking
	 */
	private Ranking ranking;
	
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
	public HighScores(final GameEngine parent, Ranking ranking) {
		super(parent);
		this.ranking = ranking;
	}
	
	/**
	 * Metodo de inicializacion.
	 */
	@Override
	public final void initResources() {
		bigFont = new SystemFont(FontUtil.createTrueTypeFont(
				this.bsIO.getURL("./resources/images/ravie.ttf"), Font.PLAIN, 36));
		mFont = new SystemFont(FontUtil.createTrueTypeFont(
				this.bsIO.getURL("./resources/images/ravie.ttf"), Font.PLAIN, 24));

		background = new ImageBackground(
				getImage("./resources/images/menuPuntuacion.png"), 800, 600);
		playFieldHighScores.setBackground(background);
	}
	
	/**
	 * Dibuja los records del juego.
	 * @param g2D Instancia de Graphics2D
	 */
	private void drawHighScores(final Graphics2D g2D) {
		int lineHeight = 0;
		mFont.setColor(new Color(142, 239, 123));
		if (this.ranking != null) {
			for (int i = 0; i < this.ranking.size() && i < 10; i++){
				lineHeight += 40;
				String name = this.ranking.getIdPlayers()[i];
				String score = String.valueOf(this.ranking.getScores()[i]);
	
				mFont.drawString(g2D, name, GameFont.CENTER,0,
						120 + lineHeight, 550);
				mFont.drawString(g2D, score, GameFont.CENTER, 550,
						120 + lineHeight, 250);
			}
		}
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
		bigFont.drawString(g2D, "Nombre", GameFont.CENTER, 0, 110, 550);
		bigFont.drawString(g2D, "Puntos", GameFont.CENTER, 550, 110, 250);
		drawHighScores(g2D);
		
		mFont.setColor(new Color(239, 231, 123));
		mFont.drawString(g2D, "ENTER o click para volver al menu",GameFont.CENTER, 0, 550, 800);
	}

	/**
     * Getter of the property <tt>background</tt>.
     * @return  Returns the background.
     * @uml.property  name="background"
     */
	public Background getBackground(){
		return this.background;
	}
	
	/**
     * Setter of the property <tt>background</tt>.
     * @param newValue  The background to set.
     * @uml.property  name="background"
     */
	public void setBackground(Background background) {
        this.background = background;
	}
	
	/**
     * Getter of the property <tt>bigFont</tt>.
     * @return  Returns the bigFont.
     * @uml.property  name="bigFont"
     */
	public SystemFont getBigFont(){
		return this.bigFont;
	}
	
	/**
     * Setter of the property <tt>bigFont</tt>.
     * @param newValue  The bigFont to set.
     * @uml.property  name="bigFont"
     */
	public void setBigFont(SystemFont bigFont){
		this.bigFont=bigFont;
	}
	
	/**
     * Getter of the property <tt>mFont</tt>.
     * @return  Returns the mFont.
     * @uml.property  name="mFont"
     */
	public  SystemFont getMFont(){
		return this.mFont;
	}
	
	/**
     * Setter of the property <tt>mFont</tt>.
     * @param newValue  The mFont to set.
     * @uml.property  name="mFont"
     */
	public  void setMFont(SystemFont mFont){
		this.mFont=mFont;
	}
	
	/**
     * Getter of the property <tt>playFieldHighScores</tt>.
     * @return  Returns the playFieldHighScores.
     * @uml.property  name="playFieldHighScores"
     */
	 public  PlayField getPfHighScores() {
         return this.playFieldHighScores;
	 }

	 /**
	 * Setter of the property <tt>playFieldHighScores</tt>.
	 * @param newValue  The playFieldHighScores to set.
	 * @uml.property  name="playFieldHighScores"
	 */
	 public  void setPfHighScores(PlayField playFieldHighScores) {
         this.playFieldHighScores = playFieldHighScores;
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
