import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.CollisionBounds;

/**
 * Clase que controla la colisión entre los sprites y el borde de la ventana.
 * 
 * @author Marcos Lede
 */
public class BorderCollision extends CollisionBounds {
     /**
      * Variable para contener una instnacia local del background del juego.
      * @uml.property  name="background"
      */
    private Background background;

    /**
     * Constructor de la clase.
     * @param b : se pasa el componente Background del juego
     */
    public BorderCollision(final Background b) {
        super(b);
        this.background = b;
    }

    /**
     * Método para calcular la nueva velocidad de los sprites cuando estos
     * colisionan contra el borde.
     * Si el sprite choca contra el borde superior o el inferior invierte
     * su velocidad vertical.
     * Si el sprite choca contra el borde izquierdo o el derecho invierte
     * su velocidad horizontal.
     * Se utilizan los métodos forceX y forceY para evitar situaciones en las 
     * que la bola se va de la pantalla.
     *  @param s1 : sprite que colisiona contra el borde de la ventana del juego
     */
    @Override
    public final void collided(final Sprite s1) {
        if (isCollisionSide(CollisionBounds.LEFT_COLLISION)) {
            s1.setHorizontalSpeed(-s1.getHorizontalSpeed());
            s1.forceX(1);
        } else if (isCollisionSide(CollisionBounds.RIGHT_COLLISION)) {
            s1.setHorizontalSpeed(-s1.getHorizontalSpeed());
            s1.forceX(background.getWidth() - s1.getWidth());
        } else if (isCollisionSide(CollisionBounds.BOTTOM_COLLISION)) {
            s1.setVerticalSpeed(-s1.getVerticalSpeed());
            s1.forceY(background.getHeight() - s1.getHeight());
        } else {
            s1.setVerticalSpeed(-s1.getVerticalSpeed());
            s1.forceY(1);
        }
    }
}