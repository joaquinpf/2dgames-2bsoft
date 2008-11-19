import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;

/**Clase para manejar la colisión entre dos sprites en movimiento.*/
public class BallCollision extends BasicCollisionGroup {

    /**Constante para evitar la penetración entre sprites, y el subsecuente
     * disparo de 'Iterative failure-- too close'.
     */
    private static final double ERROR_CORRECTOR = 1.0;

    /**Constructor de la clase.*/
    public BallCollision() {
    	this.pixelPerfectCollision=true;
    }

    /**
     * Metodo para calcular la nueva velocidad de los sprites cuando estos
     * colisionan entre sí.
     * @param s1 : sprite1
     * @param s2 : sprite2
     */
    @Override
    public final void collided(final Sprite s1, final Sprite s2) {
    	
    	//Si los sprites que chocan tienen las mismas direcciones de
    	//movimiento vertical, cuando chocan intercambian entre sí las
    	//velocidades verticales. El sprite chocado ahora es el más 
    	//rápido sobre el eje Y.
        if ((s1.getVerticalSpeed() >= 0 && s2.getVerticalSpeed() >= 0)
                || (s1.getVerticalSpeed() < 0 && s2.getVerticalSpeed() < 0)) {
            double temp = s1.getVerticalSpeed();
            s1.setVerticalSpeed(s2.getVerticalSpeed());
            s2.setVerticalSpeed(temp);
        //Si la dirección vertical de los sprites es opuesta cuando chocan, 
        //éstos invierten sus velocidades verticales.
        } else if ((s1.getVerticalSpeed() >= 0 && s2.getVerticalSpeed() < 0)
                || (s1.getVerticalSpeed() < 0 && s2.getVerticalSpeed() >= 0)) {
            s1.setVerticalSpeed(-s1.getVerticalSpeed());
            s2.setVerticalSpeed(-s2.getVerticalSpeed());
            //Para evitar la penetración entre los sprites y el consecuente
            //disparo de 'Iterative failure-- too close'.
            if (s1.getY() > s2.getY()) {
                s1.forceY(s1.getY() + ERROR_CORRECTOR);
            } else {
                s2.forceY(s2.getY() + ERROR_CORRECTOR);
            }
        }

        //Si los sprites que chocan tienen las mismas direcciones de
    	//movimiento horizontal, cuando chocan intercambian entre sí las
    	//velocidades horizontales. El sprite chocado ahora es el más 
    	//rápido sobre el eje X.
        if ((s1.getHorizontalSpeed() >= 0 && s2.getHorizontalSpeed() >= 0)
              || (s1.getHorizontalSpeed() < 0 && s2.getHorizontalSpeed() < 0)) {
            double temp = s1.getHorizontalSpeed();
            s1.setHorizontalSpeed(s2.getHorizontalSpeed());
            s2.setHorizontalSpeed(temp);
        //Si la dirección horizontal de los sprites es opuesta cuando chocan, 
        //estos invierten sus velocidades horizontales.
        } else if ((s1.getHorizontalSpeed() >= 0 && s2.getHorizontalSpeed() < 0)
             || (s1.getHorizontalSpeed() < 0 && s2.getHorizontalSpeed() >= 0)) {
            s1.setHorizontalSpeed(-s1.getHorizontalSpeed());
            s2.setHorizontalSpeed(-s2.getHorizontalSpeed());
            //Para evitar la penetración entre los sprites y el consecuente
            //disparo de 'Iterative failure-- too close'.
            if (s1.getX() > s2.getX()) {
                s1.forceX(s1.getX() + ERROR_CORRECTOR);
            } else {
                s2.forceX(s2.getX() + ERROR_CORRECTOR);
            }
        }
    }

}