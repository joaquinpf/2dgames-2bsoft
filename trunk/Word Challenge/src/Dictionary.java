
import java.util.HashSet;
import java.util.ArrayList;

/**
 * 
 */

public class Dictionary {

	/**
	 * @uml.property name="words"
	 */
	private HashSet<String> words;

	/**
	 * Getter of the property <tt>words</tt>
	 * 
	 * @return Returns the words.
	 * @uml.property name="words"
	 */
	public HashSet<String> getWords() {
		return words;
	}

	/**
	 * Setter of the property <tt>words</tt>
	 * 
	 * @param words
	 *            The words to set.
	 * @uml.property name="words"
	 */
	public void setWords(HashSet<String> words) {
		this.words = words;
	}

	/**
	 * @uml.property name="sixLetteredWord"
	 */
	private ArrayList<String> sixLetteredWord;

	/**
	 * Getter of the property <tt>sixLetteredWord</tt>
	 * 
	 * @return Returns the sixLetteredWord.
	 * @uml.property name="sixLetteredWord"
	 */
	public ArrayList<String> getSixLetteredWord() {
		return sixLetteredWord;
	}

	/**
	 * Setter of the property <tt>sixLetteredWord</tt>
	 * 
	 * @param sixLetteredWord
	 *            The sixLetteredWord to set.
	 * @uml.property name="sixLetteredWord"
	 */
	public void setSixLetteredWord(ArrayList<String> sixLetteredWord) {
		this.sixLetteredWord = sixLetteredWord;
	}

	/**
		 */
	public String getSixLetters() {
		return "";
	}

	/**
			 */
	public ArrayList getPossibleWords(String letters) {
		return null;
	}

	/**
				 */
	private ArrayList getPossibleWords(String prefix, String remainderLetters) {
		return null;
	}

		
	/**
	*/
	public Dictionary(HashSet wordlist){
	}

		
		/**
		 */
		public void addWord(String newWord){
		}

}
