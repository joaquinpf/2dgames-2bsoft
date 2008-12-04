import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.background.ImageBackground;

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
	 * Background de la pantalla.
	 */
	private Background background;

	/**
	 * manegador de la fuente que se utilizará para mostrar los puntajes.
	 */
	private GameFont font;

	/**
	 * Imagen que contiene el título de la pantalla.
	 */
	private BufferedImage titleHighScores;

	/**
	 * (non-Javadoc).
	 * 
	 * @see com.golden.gamedev.GameObject#initResources()
	 */
	@Override
	public final void initResources() {
		background = new ImageBackground(
				getImage("resources/images/menupuntuaciones.png"), 800, 600);
		font = fontManager.getFont(getImages("resources/images/fontmenu.png",
				8, 12));
		// titleHighScores = getImage("resources/images/titlePuntajes1.gif");
		pfHighScores.setBackground(background);
	}

	/**
	 * este método construye un String de linea punteada con la longitud
	 * necesaria para mostrar los puntajes.
	 * 
	 * @param longPalabras
	 *            longitud de las palabras que se mostrarán.
	 * @return String con la linea punteada de longitud correcta.
	 */
	private String lineaPunteada(final int longPalabras) {
		String line = new String();
		if (longPalabras >= 400) {
			return " ";
		}
		for (int i = 400; i > longPalabras; i = i - 10) {
			line = line + ".";
		}
		return line;
	}

	/**
	 * este método muestar en la pantalla la lista de puntajes del juego.
	 * 
	 * @param g
	 *            parametro necesario para que el manejador de fuente dibuje el
	 *            texto
	 */
	private void listarPuntos(final Graphics2D g) {
		int line = 0;
		// for (array, archivo ,xml o no se que va a tener los puntajes){
		// line = line + 10;
		int longitud = font.getWidth("nombre") + font.getWidth("puntos");

		font.drawString(g, "Nombre" + lineaPunteada(longitud) + "puntos",
				getWidth() / 2 - 260, getHeight() / 2 - 100 + line);
		// }
	}

	/**
	 * Dibuja la escena. Override de GTGE.
	 * 
	 * @param g
	 *            El objeto grafico sobre el cual se dibuja
	 * @see com.golden.gamedev.GameObject#render(java.awt.Graphics2D)
	 */
	@Override
	public final void render(final Graphics2D g) {
		// g.drawImage(highScoreIMG,0,0,null);
		pfHighScores.render(g);

		listarPuntos(g);
	}

	/**
	 * @see com.golden.gamedev.GameObject#update(long)
	 * @param elapsedTime
	 *            tiempo transcurrido desde el ultimo update
	 */
	@Override
	public final void update(final long elapsedTime) {
		pfHighScores.update(elapsedTime);
		if (keyDown(KeyEvent.VK_ENTER) || keyDown(KeyEvent.VK_ESCAPE)
				|| click()) {
			parent.nextGameID = WordChallenge.OPTION_MENU;
			finish();
		}
	}

	/**
	 * @param parent
	 *            objeto que maneja la trancicion hacia la pantalla de menu.
	 */
	public HighScores(final GameEngine parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Retorna el Playfield de la pantalla.
	 * 
	 * @return pfHighScores
	 */
	public final PlayField getPfHighScores() {
		return pfHighScores;
	}

	/**
	 * Setea la variable pfHighScore con el Playfield de la pantalla.
	 * 
	 * @param pfHighScores
	 */
	public final void setPfHighScores(final PlayField pfHighScores) {
		this.pfHighScores = pfHighScores;
	}

	/**
	 * Retorna el Background de la pantalla.
	 * 
	 * @return background
	 */
	public final Background getBackground() {
		return background;
	}

	/**
	 * Setea la variable background con el respectivo Background de la pantalla.
	 * 
	 * @param background
	 */
	public final void setBackground(final Background background) {
		this.background = background;
	}

	/**
	 * Retorna el manegador de la fuente que se utilizará. para mostrar los
	 * puntajes
	 * 
	 * @return font
	 */
	public final GameFont getFont() {
		return font;
	}

	/**
	 * Setea la variable font con la fuente que se utilizará. para mostrar los
	 * puntajes
	 * 
	 * @param font
	 */
	public final void setFont(final GameFont font) {
		this.font = font;
	}

	/**
	 * Retorna la Imagen que contiene el título de la pantalla.
	 * 
	 * @return titleHighScores
	 */
	public final BufferedImage getTitleHighScores() {
		return titleHighScores;
	}

	/**
	 * Setea la variable titleHighScores.
	 * 
	 * @param titleHighScores
	 * @return
	 */
	public final void setTitleHighScores(final BufferedImage titleHighScores) {
		this.titleHighScores = titleHighScores;
	}
}
