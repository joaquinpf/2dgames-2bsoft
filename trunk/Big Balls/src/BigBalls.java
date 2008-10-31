import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.collision.BasicCollisionGroup;



/** 
 * Esta clase maneja el juego.
 */
public class BigBalls extends GameEngine {

	@Override
	public GameObject getGame(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * @uml.property  name="basicCollisionGroup"
	 * @uml.associationEnd  inverse="bigBalls:com.golden.gamedev.object.collision.BasicCollisionGroup"
	 */
	private BasicCollisionGroup basicCollisionGroup;


	/**
	 * Getter of the property <tt>basicCollisionGroup</tt>
	 * @return  Returns the basicCollisionGroup.
	 * @uml.property  name="basicCollisionGroup"
	 */
	public BasicCollisionGroup getBasicCollisionGroup() {
		return basicCollisionGroup;
	}


	/**
	 * Setter of the property <tt>basicCollisionGroup</tt>
	 * @param basicCollisionGroup  The basicCollisionGroup to set.
	 * @uml.property  name="basicCollisionGroup"
	 */
	public void setBasicCollisionGroup(
			BasicCollisionGroup basicCollisionGroup) {
				this.basicCollisionGroup = basicCollisionGroup;
			}

}
