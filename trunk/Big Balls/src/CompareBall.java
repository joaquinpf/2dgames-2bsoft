
import java.util.Comparator;


public class CompareBall  implements Comparator{


	public CompareBall() {
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Object o1, Object o2) {
		return new Integer(((Ball)o1).getValue()).compareTo (new Integer(((Ball)o2).getValue()));
	}

}
