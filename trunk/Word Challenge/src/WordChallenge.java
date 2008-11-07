import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;

/**
 * 
 */
public class WordChallenge extends GameEngine {

	/* (non-Javadoc)
	 * @see com.golden.gamedev.GameEngine#getGame(int)
	 */
	@Override
	public GameObject getGame(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @uml.property  name="config"
	 */
	private Configurator config;

	/**
	 * Getter of the property <tt>config</tt>
	 * @return  Returns the config.
	 * @uml.property  name="config"
	 */
	public Configurator getConfig() {
		return config;
	}

	/**
	 * Setter of the property <tt>config</tt>
	 * @param config  The config to set.
	 * @uml.property  name="config"
	 */
	public void setConfig(Configurator config) {
		this.config = config;
	}

	/**
	 * @uml.property  name="selectedLanguage"
	 */
	private String selectedLanguage = "";

	/**
	 * Getter of the property <tt>selectedLanguage</tt>
	 * @return  Returns the selectedLanguage.
	 * @uml.property  name="selectedLanguage"
	 */
	public String getSelectedLanguage() {
		return selectedLanguage;
	}

	/**
	 * Setter of the property <tt>selectedLanguage</tt>
	 * @param selectedLanguage  The selectedLanguage to set.
	 * @uml.property  name="selectedLanguage"
	 */
	public void setSelectedLanguage(String selectedLanguage) {
		this.selectedLanguage = selectedLanguage;
	}

	/**
	 * @uml.property  name="kapeluz"
	 * @uml.associationEnd  inverse="wordChallenge:Dictionary"
	 */
	private Dictionary kapeluz;

	/**
	 * Getter of the property <tt>kapeluz</tt>
	 * @return  Returns the kapeluz.
	 * @uml.property  name="kapeluz"
	 */
	public Dictionary getKapeluz() {
		return kapeluz;
	}

	/**
	 * Setter of the property <tt>kapeluz</tt>
	 * @param kapeluz  The kapeluz to set.
	 * @uml.property  name="kapeluz"
	 */
	public void setKapeluz(Dictionary kapeluz) {
		this.kapeluz = kapeluz;
	}

	/**
	 * @uml.property  name="clock"
	 * @uml.associationEnd  inverse="wordChallenge:Clock"
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

}
