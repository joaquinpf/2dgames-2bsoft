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
		 */
		public void rotate(int degrees){
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


		/**
		 * @uml.property  name="gameplay"
		 * @uml.associationEnd  inverse="bigBalls:Gameplay"
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
