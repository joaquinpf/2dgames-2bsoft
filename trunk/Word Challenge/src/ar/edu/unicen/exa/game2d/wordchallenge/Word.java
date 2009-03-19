package ar.edu.unicen.exa.game2d.wordchallenge;
/*
 * Classname Word.java
 *
 * Version 1.0
 *
 * Date 16/11/2008
 *
 * Copyright UD3 - Word Challenge (c) 
 * 
 */

// JAVA
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.net.URL;

import com.golden.gamedev.engine.BaseIO;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.util.ImageUtil;

/**
 * 
 * Esta clase se utiliza internamente en el nivel para organizar las palabras
 * que se pueden formar con las letras actuales del nivel. Registra una única
 * palabra y tiene como objetivo organizar los objetos que forman la palabra. En
 * este caso por cada letra hay un Sprite del tipo Letter, este tiene dos modos
 * de mostrarse, "oculto" y "descubierto". En el caso de que la palabra todavía
 * no ha sido descubierta se muestra con todos sus Letters en modo "oculto" en
 * caso contrario se muestra en modo "descubierto". Es también el encargado de
 * posicionar los Letters. Desde el nivel se le envía las coordenadas x, y de
 * inicio de la palabra.
 * 
 * @author Luis Soldavini y Pablo Melchior
 * @version 1.0
 * 
 */
public class Word extends SpriteGroup {

	/**
	 * Constante que define la separación entre letras.
	 */
	private static final int SEPARATION_LETTER = 5;

	/**
	 * Propiedad publica que registra la palabra completa.
	 */
	private String mWord = "";

	/**
	 * Indica si la palabra ya fue descubierta.
	 */
	private boolean mVisible = false;

	/**
	 * Constructor de la clase.
	 * 
	 * @param name
	 *            nombre que tendrá el SpriteGroup
	 */
	public Word(final String name) {
		super(name);
	}

	/**
	 * Posición inicial de la palabra/SpriteGroup. Esta posición representa el
	 * vertice superior izquierdo. La unidad utilizada es pixel de pantalla. Se
	 * toma como el origen (0,0) la el vertice superior izquierdo de la
	 * pantalla.
	 * 
	 * @param x
	 *            cantidad de pixeles hacia la derecha
	 * @param y
	 *            cantidad de pixeles hacia abajo
	 */
	public final void setPosition(final int x, final int y) {

		double posX;
		MiniLetter vLetter;
		BaseIO vBsIO = new BaseIO(this.getClass(), BaseIO.CLASS_URL);
		
		URL vUrl1 = vBsIO.getURL("./resources/wordchallenge/images/"
				+ "mini.png");
		URL vUrl2 = vBsIO.getURL("./resources/wordchallenge/images/"
				+ "minioculta.png");
		

		for (int i = 0; i < mWord.length(); i++) {
			BufferedImage Iv= ImageUtil.getImage(vUrl1, Color.TRANSLUCENT);
			BufferedImage Io= ImageUtil.getImage(vUrl2, Color.TRANSLUCENT);
			
			vLetter = new MiniLetter(Io, Iv, mWord.charAt(i));
			posX = x + (i * (vLetter.getWidth() + SEPARATION_LETTER));
								
			vLetter.setLocation(posX, y);
			this.add(vLetter);
		}
	}

	/**
	 * Setea el string de la palabra que representa.
	 * 
	 * @param xWord  string con la palabra.
	 */
	public final void setWord(final String xWord) {
		this.mWord = xWord;
	}

	/**
	 * Retorna el string de la palabra.
	 * 
	 * @return string con la palabra
	 */
	public final String getWord() {
		return mWord;
	}

	/**
	 * Se utiliza para indicar que la palabra fue descubierta y debe mostrarse
	 * destapada, con sus letras escribiendo sobre los recuadros.
	 * 
	 * @param xVisible  nuevo estado para asignar al letter.
	 */
	public final void setVisible(final boolean xVisible) {
		this.mVisible = xVisible;

		MiniLetter vLetter;
		for (int i = 0; i < this.getSize(); i++) {
			vLetter = (MiniLetter) this.getSprites()[i];
			vLetter.setVisible(xVisible);
		}
	}

	/**
	 * Devuelve si la palabra ya ha sido descubierta.
	 * 
	 * @return boolean indicando si fue o no descubierta
	 */
	public final boolean isVisible() {
		return mVisible;
	}

	/**
	 * Devuelve la cantidad de letras de la palabra que representa.
	 * 
	 * @return int, cantidad de letras
	 */
	public final int lengthWord() {
		return mWord.length();
	}
}
