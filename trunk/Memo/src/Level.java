

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.PlayField;

import java.awt.Graphics2D;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

public class Level extends GameObject implements Observer{

	public Level(GameEngine parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @uml.property  name="levelComplete"
	 */
	private boolean levelComplete = false;

	/**
	 * Getter of the property <tt>levelComplete</tt>
	 * @return  Returns the levelComplete.
	 * @uml.property  name="levelComplete"
	 */
	public boolean isLevelComplete() {
		return levelComplete;
	}

	/**
	 * Setter of the property <tt>levelComplete</tt>
	 * @param levelComplete  The levelComplete to set.
	 * @uml.property  name="levelComplete"
	 */
	public void setLevelComplete(boolean levelComplete) {
		this.levelComplete = levelComplete;
	}

	/**
	 * @uml.property  name="levelNumber"
	 */
	private int levelNumber;

	/**
	 * Getter of the property <tt>levelNumber</tt>
	 * @return  Returns the levelNumber.
	 * @uml.property  name="levelNumber"
	 */
	public int getLevelNumber() {
		return levelNumber;
	}

	/**
	 * Setter of the property <tt>levelNumber</tt>
	 * @param levelNumber  The levelNumber to set.
	 * @uml.property  name="levelNumber"
	 */
	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
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

	public void addCard(Card newCard){
	}

	/**
	 * @uml.property  name="tableSize"
	 */
	private int tableSize;

	/**
	 * Getter of the property <tt>tableSize</tt>
	 * @return  Returns the tableSize.
	 * @uml.property  name="tableSize"
	 */
	public int getTableSize() {
		return tableSize;
	}

	/**
	 * Setter of the property <tt>tableSize</tt>
	 * @param tableSize  The tableSize to set.
	 * @uml.property  name="tableSize"
	 */
	public void setTableSize(int tableSize) {
		this.tableSize = tableSize;
	}

	/**
	 * @uml.property  name="table" multiplicity="(0 -1)" dimension="2"
	 */
	private boolean[][] table;
	/**
	 * Getter of the property <tt>table</tt>
	 * @return  Returns the tables.
	 * @uml.property  name="table"
	 */
	public boolean[][] getTable() {
		return table;
	}

	/**
	 * Setter of the property <tt>table</tt>
	 * @param table  The tables to set.
	 * @uml.property  name="table"
	 */
	public void setTable(boolean[][] _table) {
		table = _table;
	}

	/**
	 * @uml.property  name="reamainingCards"
	 */
	private int reamainingCards;
	/**
	 * Getter of the property <tt>reamainingCards</tt>
	 * @return  Returns the reamainingCards.
	 * @uml.property  name="reamainingCards"
	 */
	public int getReamainingCards() {
		return reamainingCards;
	}

	/**
	 * Setter of the property <tt>reamainingCards</tt>
	 * @param reamainingCards  The reamainingCards to set.
	 * @uml.property  name="reamainingCards"
	 */
	public void setReamainingCards(int reamainingCards) {
		this.reamainingCards = reamainingCards;
	}

	/**
	 * @uml.property  name="firstCard"
	 */
	private Card firstCard;
	/** 
	 * Getter of the property <tt>lastCard</tt>
	 * @return  Returns the lastCard.
	 * @uml.property  name="firstCard"
	 */
	public Card getFirstCard() {
		return firstCard;
	}

	/** 
	 * Setter of the property <tt>lastCard</tt>
	 * @param lastCard  The lastCard to set.
	 * @uml.property  name="firstCard"
	 */
	public void setFirstCard(Card firstCard) {
		this.firstCard = firstCard;
	}

	/**
	 * @uml.property  name="secondCard"
	 */
	private Card secondCard;
	/**
	 * Getter of the property <tt>secondCard</tt>
	 * @return  Returns the secondCard.
	 * @uml.property  name="secondCard"
	 */
	public Card getSecondCard() {
		return secondCard;
	}

	/**
	 * Setter of the property <tt>secondCard</tt>
	 * @param secondCard  The secondCard to set.
	 * @uml.property  name="secondCard"
	 */
	public void setSecondCard(Card secondCard) {
		this.secondCard = secondCard;
	}

	/**
	 * @uml.property  name="clock"
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

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

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

	/**
	 * @uml.property  name="gameplay"
	 * @uml.associationEnd  inverse="level:Gameplay"
	 */
	private Gameplay gameplay;

	/**
	 * Getter of the property <tt>gameplay</tt>
	 * @return  Returns the gameplay.
	 * @uml.property  name="gameplay"
	 */
	public Gameplay getGameplay() {
		return gameplay;
	}

	/**
	 * Setter of the property <tt>gameplay</tt>
	 * @param gameplay  The gameplay to set.
	 * @uml.property  name="gameplay"
	 */
	public void setGameplay(Gameplay gameplay) {
		this.gameplay = gameplay;
	}

}
