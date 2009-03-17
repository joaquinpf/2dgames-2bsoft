package ar.edu.unicen.exa.game2d.bigballs;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.background.ImageBackground;

//import common.datatypes.Ranking;
/**
 * Pantalla de puntajes del juego.
 * 
 * @author Damian Achaga
 */

public class HighScores extends GameObject {

	/**
	 * Playfield de la pantalla.
	 */
	private PlayField pfHighScores = new PlayField();
	
	/**
	 * Contiene el Ranking del juego para mostrar
	 */
	private Ranking ranking;
	
	/**
	 * Background de la pantalla.
	 */
	private Background background;
	
	/**
	 * manegador de la fuente que se utilizara para mostrar los puntajes.
	 */
	private GameFont font;
	
	/**
	 * Imagen que contiene el titulo de la pantalla.
	 */
	private BufferedImage titleHighScores;
	
	
	/**
	 * (non-Javadoc).
	 * @see com.golden.gamedev.GameObject#initResources()
	 */
	@Override
	public final void initResources() {
		background = new ImageBackground(
				getImage("./resources/bigballs/images/menupuntuaciones.png"), 800, 600);
		font = fontManager.getFont(getImages("./resources/bigballs/images/font.png",
								   8, 12));
		//titleHighScores = getImage("./resources/bigballs/images/titlePuntajes1.gif");
		pfHighScores.setBackground(background);
	}
	
	/**
	 * este metodo construye un String de linea punteada 
	 * con la longitud necesaria para mostrar los puntajes.
	 * @param longPalabras longitud de las palabras que se mostraran.
	 * @return String con la linea punteada de longitud correcta.
	 */
	private String lineaPunteada(final int longPalabras) {
		String line = new String();
		if (longPalabras >= 500) {
			return " ";
		}
		for (int i = 500; i > longPalabras; i = i - 10) {
			line = line + ".";
		}
		return line;
	}
	/**
	 * este metodo muestra en la pantalla la lista de puntajes del juego.
	 * @param g parametro necesario para que 
	 *          el manejador de fuente dibuje el texto
	 */
	private void listarPuntos(final Graphics2D g) {
		int line = 0;
		if (this.ranking != null) {
			for (int i=0;i<this.ranking.size() && i<15;i++){
				line = line + 20;
				String name = this.ranking.getIdPlayers()[i];
				String score = String.valueOf(this.ranking.getScores()[i]);
				int longitud = font.getWidth(name)  
				                   + font.getWidth(score); 
				
				font.drawString(g, name + lineaPunteada(longitud) + score,
						        getWidth() / 2 - 350, getHeight() / 2 - 150 + line);
			}
		}
	}
	/**
	 * Dibuja la escena. Override de GTGE.
	 * @param g El objeto grafico sobre el cual se dibuja
	 * @see com.golden.gamedev.GameObject#render(java.awt.Graphics2D)
	 */
	@Override
	public final void render(final Graphics2D g) {
		//g.drawImage(highScoreIMG,0,0,null);
		pfHighScores.render(g);
		
		listarPuntos(g);
	}

	/**
	 * @see com.golden.gamedev.GameObject#update(long)
	 * @param elapsedTime tiempo transcurrido desde el ultimo update
	 */
	@Override
	public final void update(final long elapsedTime) {
		pfHighScores.update(elapsedTime);
		if (keyDown(KeyEvent.VK_ENTER) || keyDown(KeyEvent.VK_ESCAPE)
		   || click()) {
				parent.nextGameID = BigBalls.OPTION_MENU;
				finish();
		}
	}

		
	/**
	 * Metodo Constructor de la clase
	 * @param parent objeto que maneja la trancicion hacia la pantalla de menu.
	 */
	public HighScores(final GameEngine parent, Ranking ranking) {
		super(parent);
		this.ranking = ranking;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * retorna el Playfield de la pantalla
	 * @return el valor de la variable pfHighScores
	 */
	public PlayField getPfHighScores() {
		return pfHighScores;
	}

	/**
	 * setea un nuevo Playfield de pantalla
	 * @param pfHighScores el nuevo Playfield que se debe setear
	 */
	public void setPfHighScores(PlayField pfHighScores) {
		this.pfHighScores = pfHighScores;
	}
	
	/**
	 * retorna el Background de la pantalla
	 * @return el valor de la variable background
	 */
	public Background getBackground() {
		return background;
	}
	
	/**
	 * setea un nuevo Background de pantalla
	 * @param background el nuevo Background que se debe setear
	 */
	public void setBackground(Background background) {
		this.background = background;
	}

	/**
	 * retorna el manegador de la fuente
	 * @return el valor de la variable font
	 */
	public GameFont getFont() {
		return font;
	}
	
	/**
	 * setea un nuevo manegador de la fuente
	 * @param font el nuevo manegador de la fuente que se debe setear
	 */
	public void setFont(GameFont font) {
		this.font = font;
	}

	/**
	 * retorna la Imagen que contiene el titulo
	 * @return el valor de la variable titleHighScores
	 */
	public BufferedImage getTitleHighScores() {
		return titleHighScores;
	}
	
	/**
	 * setea una nueva Imagen que contenga el titulo
	 * @param titleHighScores la nueva imagen que contien el titulo
	 */
	public void setTitleHighScores(BufferedImage titleHighScores) {
		this.titleHighScores = titleHighScores;
	}
	
	/**
	 * setea el ranking que va a mostrarse
	 * @param ranking el ranking a setear
	 */
	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
	}

}
