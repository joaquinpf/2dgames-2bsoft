

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.PlayField;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
/**
 * 
 */


public class Level extends GameObject implements Observer {


	public Level(GameEngine parent,String letters,ArrayList<String> possibleWords) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	
	
	/**
	 * @uml.property  name="playfield"
	 */
	private PlayField playfield;

	/**
	 * Getter of the property <tt>playfield</tt>
	 * @return  Returns the playfield.
	 * @uml.property  name="playfield"
	 */
	public PlayField getPlayfield() {
		return playfield;
	}

	/**
	 * Setter of the property <tt>playfield</tt>
	 * @param playfield  The playfield to set.
	 * @uml.property  name="playfield"
	 */
	public void setPlayfield(PlayField playfield) {
		this.playfield = playfield;
	}

	/**
	 */
	public boolean isFinished() {
		return false;	
	}


	/**
	 */
	private void randomLettersOrder() {
	}

	@Override
	public void initResources() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(long arg0) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}


	/**
	 * @uml.property  name="clock"
	 * @uml.associationEnd  inverse="level:Clock"
	 */
	private Clock clock;

	/**
	 * Getter of the property <tt>clock</tt>
	 * @return  Returns the clock.
	 * @uml.property  name="clock"
	 */
	public Clock getClock() {
		return clock;
	}

	/**
	 * Setter of the property <tt>clock</tt>
	 * @param clock  The clock to set.
	 * @uml.property  name="clock"
	 */
	public void setClock(Clock clock) {
		this.clock = clock;
	}


	/**
	 * @uml.property  name="words"
	 */
	private ArrayList<String> words;

	/**
	 * Getter of the property <tt>words</tt>
	 * @return  Returns the words.
	 * @uml.property  name="words"
	 */
	public ArrayList<String> getWords() {
		return words;
	}

	/**
	 * Setter of the property <tt>words</tt>
	 * @param words  The words to set.
	 * @uml.property  name="words"
	 */
	public void setWords(ArrayList<String> words) {
		this.words = words;
	}



	/**
	 * @uml.property  name="letters"
	 */
	private ArrayList<Character> letters;

	/**
	 * Getter of the property <tt>letters</tt>
	 * @return  Returns the letters.
	 * @uml.property  name="letters"
	 */
	public ArrayList<Character> getLetters() {
		return letters;
	}

	/**
	 * Setter of the property <tt>letters</tt>
	 * @param letters  The letters to set.
	 * @uml.property  name="letters"
	 */
	public void setLetters(ArrayList<Character> letters) {
		this.letters = letters;
	}



	/**
	 * @uml.property  name="selectedLetters"
	 */
	private ArrayList<Character> selectedLetters;

	/**
	 * Getter of the property <tt>selectedLetters</tt>
	 * @return  Returns the selectedLetters.
	 * @uml.property  name="selectedLetters"
	 */
	public ArrayList<Character> getSelectedLetters() {
		return selectedLetters;
	}

	/**
	 * Setter of the property <tt>selectedLetters</tt>
	 * @param selectedLetters  The selectedLetters to set.
	 * @uml.property  name="selectedLetters"
	 */
	public void setSelectedLetters(ArrayList<Character> selectedLetters) {
		this.selectedLetters = selectedLetters;
	}

}
