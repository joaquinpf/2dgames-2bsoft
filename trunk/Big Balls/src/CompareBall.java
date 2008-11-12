
import java.util.Comparator;

/**
 * Me provee soporte para realizar la comparacion para el posterior
 * prdenamiento.
 * 
 */
public class CompareBall  implements Comparator {

/**
 * 
 */
	public CompareBall() {
	}

	/**
	 * Compara por el valor del objeto en forma ascendente.
	 * @param o1 Primer objeto a comparar
	 * @param o2 Segundo objeto a comparar
	 * @return resultado de la comparacion
	 */
	public final int compare(final Object o1, final Object o2) {
		return new Integer(((Ball) o1).getValue()).
		compareTo(new Integer(((Ball) o2).getValue()));
	}

}
