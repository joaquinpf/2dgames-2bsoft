
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
	 */
	public int compare(Object o1, Object o2) {
		return new Integer(((Ball) o1).getValue()).
		compareTo(new Integer(((Ball) o2).getValue()));
	}

}
