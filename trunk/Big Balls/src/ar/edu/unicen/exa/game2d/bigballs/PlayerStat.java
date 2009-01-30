/**
 * 
 */
package ar.edu.unicen.exa.game2d.bigballs;

/**
 * Esta clase se utiliza para modelar los distintos stats que un jugador tiene y
 * en que nivel se encuentra cada uno tiene un arreglo que contiene los puntajes
 * de los distintos niveles para el stat, y funciones que permiten indicar en
 * que nivel se encuentra el jugador en esta stat y que puntaje se neceista
 * alcanzar para pasar al siguiente nivel.
 * 
 * @author lito
 */
public class PlayerStat extends PlayerProperty {

	private static final long serialVersionUID = -8466921300779381878L;

	public static final String PS_MEMORY = "MEMORY";

	public static final String PS_ANALYTIC = "ANALYTIC";

	public static final String PS_CALCULUS = "CALCULUS";

	public static final String PS_VISUAL = "VISUAL";

	public static final String PS_LINGUISTICS = "LINGUISTICS";

	public static final String PS_PYSC_MOT_COORDINATION = "PYSC_MOT_COORDINATION";

	public static final String PS_GENERAL_KNOWLEDGE = "GENERAL_KNOWLEDGE";

	/**
	 * En cada posicion del arreglo, se guarda el limite superior del nivel
	 * correspondiente a la posicion.<BR/>
	 * Por ejemplo:<BR/>
	 * Si {@code value < levels[i]}<BR/>
	 * entonce el level del stat es {@code i}.<BR/>
	 * Ej:<BR/>
	 * puntaje: 10 30 60<BR/>
	 * posicion:0 1 2<BR/>
	 * 
	 * si tiene 05 puntos es nivel 0<BR/>
	 * si tiene 10 puntos es nivel 1<BR/>
	 * si tiene 15 puntos es nivel 1<BR/>
	 * si tiene 29 puntos es nivel 1<BR/>
	 * si tiene 30 puntos es nivel 2<BR/>
	 * si tiene 59 puntos es nivel 2<BR/>
	 * si tiene >= 60 puntos es nivel 2 igualmente ya que es el mayor nivel
	 * posible.
	 */
	protected float[] levels;

	/**
	 * Chequea el nivel del stat en el que esta el jugador, contra un arreglo
	 * que tiene los puntajes por nivel. Retorna el nivel en el que esta el
	 * jugador.
	 * 
	 * @return un int que indica en que nivel esta el stat.
	 */
	public int getCurrentLevel() {
		for (int i = 0; i < this.levels.length; i++)
			if (this.value < this.levels[i])
				return i;

		// si supero el mayor puntaje, es del ultimo nivel igualmente.
		return this.levels.length - 1;
	}

	/**
	 * Retorna el valor para pasar el proximo nivel. Esto lo hace obteniendo el
	 * valor del arreglo que esta en la posicion getcurrentLevel() + 1
	 * 
	 * @return el valor para pasar al proximo nivel. -1 si esta en el ultimo
	 *         nivel.
	 */
	public float getNextLevelValueRequierd() {
		
		//si esta en el ultimo nivel retorno -1 ya que no hay puntaje que pueda superar.
		return (this.getCurrentLevel() == this.levels.length - 1) ? -1
				: this.levels[this.getCurrentLevel()];
	}

	/**
	 * Retorna el arreglo con los distintos valores para pasar los niveles.
	 * 
	 * @return el arreglo con los distintos valores para pasar los niveles.
	 */
	public float[] getLevels() {
		return levels;
	}

	/**
	 * setea el arreglo con los valores para los distintos niveles
	 * 
	 * @param levels
	 *            el nuevo arreglo con los valores para los niveles.
	 */
	public void setLevels(float[] levels) {
		this.levels = levels;
	}
}